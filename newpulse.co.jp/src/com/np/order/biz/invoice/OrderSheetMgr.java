package com.np.order.biz.invoice;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.np.base.db.ITxProc;
import com.np.base.db.Query;
import com.np.base.db.SequenceMgr;
import com.np.base.db.TxMgr;
import com.np.base.dml.Groupper;
import com.np.base.dml.PojoCollections;
import com.np.base.dml.Sortter;
import com.np.base.models.JoinedModels;
import com.np.base.orm.Criteria;
import com.np.base.orm.ModelMapper;
import com.np.base.orm.Restrictions;
import com.np.base.reflect.PojoUtils;
import com.np.base.utils.UDate;
import com.np.base.utils.UModel;
import com.np.base.utils.UString;
import com.np.order.Money;
import com.np.order.action.SessionMgr;
import com.np.order.biz.IllegalPriorityEception;
import com.np.order.biz.mast.ItemMgr;
import com.np.order.biz.mast.RolerMgr;
import com.np.order.models.Debt;
import com.np.order.models.EstimationOrder;
import com.np.order.models.EstiomationDetail;
import com.np.order.models.Item;
import com.np.order.models.OrderAccept;
import com.np.order.models.OrderApprove;
import com.np.order.models.OrderCancel;
import com.np.order.models.OrderChange;
import com.np.order.models.OrderDetail;
import com.np.order.models.OrderExcise;
import com.np.order.models.OrderInvoice;
import com.np.order.models.OrderOver;
import com.np.order.models.OrderSheet;
import com.np.order.models.PrePayment;
import com.np.order.models.Rolers;
import com.np.order.objects.EstimationApproves;
import com.np.order.objects.NoApproveOrder;
import com.np.order.objects.OrderResultFilter;
import com.np.order.views.OrderDetailView;

/*
 * 注文書
 */
public class OrderSheetMgr {

	/*
	 * （1）进行插入操作
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean save(final List<Long> estimationIds, final OrderSheet order) throws Exception {
		if (SessionMgr.isSaller() == false)
			throw new IllegalPriorityEception();

		/*
		 * DBにInsert
		 */
		TxMgr.update(new ITxProc() {

			@Override
			public void process(Connection con) throws Exception {
				Long oldOrderId = order.getOrderId();
				boolean canceled = false;
				if (oldOrderId != null) {
					Criteria<OrderSheet> criteria = new Criteria(OrderSheet.class);
					criteria.and(Restrictions.eq(OrderSheet.OrderId, oldOrderId));
					OrderSheet oldOrder = criteria.get();
					/*
					 * 変更無し
					 */
					if (UModel.compareModel(order, oldOrder))
						return;

					/*
					 * 取消
					 */
					OrderCancel cancel = new OrderCancel();
					cancel.setOrderId(oldOrderId);
					TxMgr.insert(con, cancel);
					canceled = true;

				} else {
					/*
					 * 作成者
					 */
					order.setUserId(SessionMgr.getLoginUserId());
				}

				/*
				 * 自動採番
				 */
				Long orderId = SequenceMgr.nextSeq(OrderSheet.class);
				order.setOrderId(orderId);
				TxMgr.insert(con, order);
				if (canceled) {
					/*
					 * 承認状態
					 */
					Criteria<OrderApprove> criteriaApprove = new Criteria(OrderApprove.class);
					criteriaApprove.and(Restrictions.eq(OrderApprove.OrderId, oldOrderId));
					List<OrderApprove> approves = criteriaApprove.list();
					for (OrderApprove approve : approves) {
						approve.setOrderId(order.getOrderId());
						TxMgr.update(con, approve);
					}
					return;
				}

				/*
				 * 見積書／発注書関連
				 */
				String orderCd = order.getOrderCd();

				Criteria<EstimationOrder> critEstimetionOrder = new Criteria(EstimationOrder.class);
				critEstimetionOrder.and(Restrictions.eq(EstimationOrder.OrderCd, orderCd));
				List<EstimationOrder> estimationOrders = critEstimetionOrder.list();
				List<String> estimationCds = PojoCollections.getKeyList(estimationOrders, EstimationOrder.EstimationCd);

				if (estimationIds != null) {
					for (Long estimationId : estimationIds) {
						String estimationCd = EstmationSheetMgr.getEstimationCd(estimationId);
						if (estimationCds.contains(estimationCd))
							continue;
						EstimationOrder transfer = new EstimationOrder();
						transfer.setEstimationCd(estimationCd);
						transfer.setOrderCd(orderCd);
						TxMgr.insert(con, transfer);
						estimationCds.add(estimationCd);
					}
				}
			}

		});

		return true;
	}

	/*
	 * （1）进行插入操作
	 */
	public static boolean register(Long estimationId, OrderSheet order) throws Exception {
		if (SessionMgr.isSaller() == false)
			throw new IllegalPriorityEception();

		/*
		 * 自動採番
		 */
		Long orderId = SequenceMgr.nextSeq(OrderSheet.class);
		order.setOrderId(orderId);

		// String supplierShortName = null;
		// order.setOrderCd(CodeAutoIncrement.nextOrderCode(supplierShortName));

		String estimationCd = EstmationSheetMgr.getEstimationCd(estimationId);
		String orderCd = order.getOrderCd();

		/*
		 * DBにInsert
		 */
		TxMgr.update(new ITxProc() {

			@Override
			public void process(Connection con) throws Exception {
				TxMgr.insert(con, order);

				EstimationOrder transfer = new EstimationOrder();
				transfer.setEstimationCd(estimationCd);
				transfer.setOrderCd(orderCd);
				TxMgr.insert(con, transfer);
			}

		});

		return true;
	}

	/*
	 * 注文書詳細リストを登録
	 */
	public static boolean register(List<OrderDetail> details) throws Exception {
		if (SessionMgr.isSaller() == false)
			throw new IllegalPriorityEception();

		for (OrderDetail detail : details) {
			/*
			 * 自動採番
			 */
			Long detailId = SequenceMgr.nextSeq(OrderDetail.class);
			detail.setDetailId(detailId);
		}

		/*
		 * DBにInsert
		 */
		TxMgr.insertList(details);

		return true;
	}

	public static String getCurrency(Long orderId) {
		OrderSheet sheet = fetchSheet(orderId);
		return (sheet == null) ? null : sheet.getCurrency();
	}

	/*
	 * 注文書を検索
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static OrderSheet fetchSheet(Long orderId) {
		Criteria<OrderSheet> criteria = new Criteria(OrderSheet.class);
		criteria.and(Restrictions.eq(OrderSheet.OrderId, orderId));
		return criteria.get();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<OrderSheet> fetchSheets(List<Long> orderIds) {
		Criteria<OrderSheet> criteria = new Criteria(OrderSheet.class);
		criteria.and(Restrictions.in(OrderSheet.OrderId, orderIds));
		return criteria.list();
	}

	/*
	 * 注文書詳細リストを検索
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<OrderDetail> fetchDetails(Long orderId, String lanCd) throws Exception {
		Criteria<OrderDetail> criteria = new Criteria(OrderDetail.class);
		criteria.and(Restrictions.eq(OrderDetail.OrderId, orderId));
		List<OrderDetail> details = criteria.list();

		OrderSheet sheet = fetchSheet(orderId);

		for (OrderDetail detail : details) {
			detail.getAmount().setCurrency(sheet.getCurrency());
			detail.getUnitPrice().setCurrency(sheet.getCurrency());

			Criteria<Item> critItem = new Criteria(Item.class);
			critItem.and(Restrictions.eq(OrderDetail.ItemId, detail.getItemId()));
			Item aitem = critItem.get();

			if (aitem != null) {
				if (lanCd.equals("Jp")) {
					detail.setName(aitem.getJpName());
					detail.setPartsCd(aitem.getJpDesc());
				} else if (lanCd.equals("En")) {
					detail.setName(aitem.getEnName());
					detail.setPartsCd(aitem.getEnDesc());
				}
			}
		}

		Sortter.sort(details, OrderDetail.DetailId);

		return details;
	}

	/*
	 * Invoice作成用注文書詳細
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<OrderDetailView> fetchDetailsForCreateInvoice(List<Long> orderDetailIds) throws Exception {
		Criteria<OrderDetail> criteria = new Criteria(OrderDetail.class);
		criteria.and(Restrictions.in(OrderDetail.DetailId, orderDetailIds));
		List<OrderDetail> details = criteria.list();

		List<Long> orderIds = PojoCollections.getKeyList(details, OrderDetail.OrderId);
		List<OrderSheet> sheets = fetchSheets(orderIds);

		List<String> orderCds = PojoCollections.getKeyList(sheets, OrderSheet.OrderCd);
		List<JoinedModels> estimations = SellOrderAmountMgr.getEstimations(orderCds);

		/*
		 * 商品名称を英語名称に変換
		 */
		List<OrderDetailView> result = new ArrayList<OrderDetailView>();
		for (OrderDetail detail : details) {

			for (JoinedModels estimation : estimations) {
				Long itemId = estimation.getValue(EstiomationDetail.ItemId);
				if (itemId.equals(detail.getItemId())) {
					detail.setUnitPrice(estimation.getValue(EstiomationDetail.UnitPrice));
					break;
				}
			}

			for (OrderSheet sheet : sheets) {
				if (sheet.getOrderId().equals(detail.getOrderId())) {
					detail.getAmount().setCurrency(sheet.getCurrency());
					detail.getUnitPrice().setCurrency(sheet.getCurrency());
					break;
				}
			}

			/*
			 * 金額：見積価格×注文数量
			 */
			detail.getAmount().multiply(0);
			detail.getAmount().add(detail.getQuantity());
			detail.getAmount().multiply(detail.getUnitPrice());

			OrderDetailView view = new OrderDetailView();
			Item item = ItemMgr.get(detail.getItemId());
			if (item != null) {
				if (UString.isEmpty(item.getEnName()))
					view.setName(detail.getName());
				else
					view.setName(item.getEnName());
			}

			view.setDetailId(detail.getDetailId());
			view.setOrderId(detail.getOrderId());
			view.setItemId(detail.getItemId());
			view.setType(detail.getType());

			view.setPartsCd(detail.getPartsCd());
			view.setModelCd(detail.getModelCd());
			view.setQuantity(detail.getQuantity());
			view.setUnit(detail.getUnit());
			view.setDeliveryDate(detail.getDeliveryDate());

			view.setAmount(detail.getAmount().getValue());
			view.setUnitPrice(detail.getUnitPrice().getValue());

			result.add(view);
		}
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<String> getOrderCds(List<Long> orderIds) throws Exception {
		Criteria<OrderSheet> criteria = new Criteria(OrderSheet.class);
		criteria.addSelectedColumn(OrderSheet.OrderCd);
		criteria.and(Restrictions.in(OrderSheet.OrderId, orderIds));

		List<OrderSheet> result = criteria.list();
		List<String> codes = PojoCollections.getKeyList(result, OrderSheet.OrderCd);
		return codes;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String getOrderCd(Long orderId) throws Exception {
		Criteria<OrderSheet> criteria = new Criteria(OrderSheet.class);
		criteria.addSelectedColumn(OrderSheet.OrderCd);
		criteria.and(Restrictions.eq(OrderSheet.OrderId, orderId));

		OrderSheet result = criteria.get();
		return (result == null) ? null : result.getOrderCd();
	}

	/*
	 * （4）更新表
	 */
	public static boolean modify(final OrderSheet order) throws Exception {
		// TxMgr.update(order);
		TxMgr.update(new ITxProc() {

			@Override
			public void process(Connection con) throws Exception {
				OrderCancel cancel = new OrderCancel();
				cancel.setOrderId(order.getOrderId());
				TxMgr.insert(con, cancel);

				Long orderId = SequenceMgr.nextSeq(OrderSheet.class);
				order.setOrderId(orderId);
				TxMgr.insert(con, order);
			}

		});
		return true;
	}

	@Deprecated
	public static boolean modify(final OrderSheet order, List<OrderDetail> details) throws Exception {
		// TxMgr.update(order);
		TxMgr.update(new ITxProc() {

			@Override
			public void process(Connection con) throws Exception {
				OrderCancel cancel = new OrderCancel();
				cancel.setOrderId(order.getOrderId());
				TxMgr.insert(con, cancel);

				Long orderId = SequenceMgr.nextSeq(OrderSheet.class);
				order.setOrderId(orderId);
				TxMgr.insert(con, order);

				for (OrderDetail detail : details) {
					detail.setOrderId(orderId);
					Long detailId = SequenceMgr.nextSeq(OrderDetail.class);
					detail.setDetailId(detailId);
				}
				TxMgr.insertList(con, details);
			}

		});
		return true;
	}

	@Deprecated
	public static boolean modify(List<OrderDetail> details) throws Exception {
		TxMgr.updateList(details);
		return true;
	}

	/*
	 * 検索
	 */
	public static List<OrderSheet> search(OrderSheet order) throws Exception {
		List<OrderSheet> list = Query.query(order);
		return list;
	}

	/*
	 * （2）返回所有EstimationId
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Long> searchOrderIds() throws Exception {
		Criteria<OrderSheet> criteria = new Criteria(OrderSheet.class);
		criteria.addSelectedColumn(OrderSheet.OrderId);
		List<OrderSheet> list = criteria.list();

		List<Long> ids = PojoCollections.getKeyList(list, OrderSheet.OrderId);
		return ids;
	}

	/*
	 * 注文書を承認
	 */
	public static void approve(Long orderId) throws Exception {
		if (SessionMgr.isLogined() == false)
			return;

		Long userId = SessionMgr.getLoginUserId();
		Long role = SessionMgr.getLoginRoleId();

		OrderApprove approve = new OrderApprove();

		/*
		 * 自動採番
		 */
		Long id = SequenceMgr.nextSeq(OrderApprove.class);
		approve.setApproveId(id);
		approve.setOrderId(orderId);
		/*
		 * セッションからuserIdを取得
		 */
		approve.setUserId(userId);
		approve.setRole(role);

		TxMgr.insert(approve);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean isApproved(Long orderId) throws Exception {
		Criteria<OrderApprove> criteria = new Criteria(OrderApprove.class);
		criteria.and(Restrictions.eq(OrderApprove.OrderId, orderId));
		return criteria.get() != null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Long> getApproved(List<Long> orderIds) {
		Criteria<OrderApprove> criteria = new Criteria(OrderApprove.class);
		criteria.and(Restrictions.in(OrderApprove.OrderId, orderIds));
		// criteria.addSelectedColumn(OrderApprove.OrderId);
		List<OrderApprove> list = criteria.list();

		List<Long> topAccepts = new ArrayList<Long>();
		for (OrderApprove accept : list) {
			if (accept.getRole().equals(RolerMgr.getRolerForPresident().getRolerId()))
				topAccepts.add(accept.getOrderId());
		}

		List<Long> result = new ArrayList<Long>();
		for (OrderApprove accept : list) {
			if (accept.getRole().equals(RolerMgr.getRolerForVicePresident().getRolerId())) {
				if (topAccepts.contains(accept.getOrderId()))
					result.add(accept.getOrderId());
			}
		}

		// orderIds = PojoCollections.getKeyList(list, OrderApprove.OrderId);
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean isDeliveried(String orderCd) throws Exception {
		Criteria<OrderInvoice> criteria = new Criteria(OrderInvoice.class);
		criteria.and(Restrictions.eq(OrderInvoice.OrderCd, orderCd));
		return criteria.get() != null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<String> getDeliveried(List<String> orderCds) throws Exception {
		Criteria<OrderInvoice> criteria = new Criteria(OrderInvoice.class);
		criteria.and(Restrictions.in(OrderInvoice.OrderCd, orderCds));
		criteria.addSelectedColumn(OrderInvoice.OrderCd);
		List<OrderInvoice> list = criteria.list();
		orderCds = PojoCollections.getKeyList(list, OrderInvoice.OrderCd);
		return orderCds;
	}

	/*
	 * 注文書をAccept
	 */
	public static void accept(Long orderId) throws Exception {
		if (SessionMgr.isLogined() == false)
			return;

		OrderAccept accept = new OrderAccept();
		accept.setOrderId(orderId);
		accept.setDate(UDate.getDate());
		TxMgr.insert(accept);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean isAccepted(Long orderId) throws Exception {
		Criteria<OrderAccept> criteria = new Criteria(OrderAccept.class);
		criteria.and(Restrictions.eq(OrderAccept.OrderId, orderId));
		return criteria.get() != null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Long> getAccepted(List<Long> orderIds) throws Exception {
		Criteria<OrderAccept> criteria = new Criteria(OrderAccept.class);
		criteria.and(Restrictions.in(OrderAccept.OrderId, orderIds));
		criteria.addSelectedColumn(OrderApprove.OrderId);
		List<OrderAccept> list = criteria.list();
		orderIds = PojoCollections.getKeyList(list, OrderAccept.OrderId);
		return orderIds;
	}

	/*
	 * 注文書を取り消し
	 */
	public static boolean cancel(Long orderId) throws Exception {
		OrderCancel cancel = new OrderCancel();
		cancel.setOrderId(orderId);
		TxMgr.insert(cancel);

		return true;
	}

	/*
	 * 未承認注文書リストを検索
	 */
	public static List<NoApproveOrder> fetchNotApproveList() throws Exception {
		return fetchNotApproveList(new OrderResultFilter());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<NoApproveOrder> fetchNotApproveList(OrderResultFilter filter) throws Exception {
		Criteria<OrderCancel> cancels = new Criteria(OrderCancel.class);
		cancels.addSelectedColumn(OrderCancel.OrderId);

		Criteria<OrderSheet> criteria = new Criteria(OrderSheet.class);
		criteria.and(Restrictions.notIn(OrderSheet.OrderId, cancels));
		criteria.and(Restrictions.notIn(OrderSheet.OrderId, createApproved()));

		if (UString.notEmpty(filter.getOrderCd()))
			criteria.and(Restrictions.eq(OrderSheet.OrderCd, filter.getOrderCd()));
		if (UString.notEmpty(filter.getSupplierName())) {
			criteria.and(Restrictions.eq(OrderSheet.SupplierName, filter.getSupplierName()));
		}
		if (UString.notEmpty(filter.getDateFrom()) || UString.notEmpty(filter.getDateTo())) {
			if (UString.notEmpty(filter.getDateFrom()) && UString.notEmpty(filter.getDateTo())) {
				criteria.and(Restrictions.between(OrderSheet.IssueDate, filter.getDateFrom(), filter.getDateTo()));
			} else if (UString.notEmpty(filter.getDateFrom())) {
				criteria.and(Restrictions.ge(OrderSheet.IssueDate, filter.getDateFrom()));
			} else if (UString.notEmpty(filter.getDateTo())) {
				criteria.and(Restrictions.le(OrderSheet.IssueDate, filter.getDateTo()));
			}
		}

		List<JoinedModels> orders = Query.queryWithDependencies(criteria, OrderDetail.class);

		String[] sumKeys = { OrderSheet.OrderId };
		String[] sumNames = { OrderDetail.Quantity, OrderDetail.Amount };
		orders = Groupper.sum(orders, sumKeys, sumNames);

		List<NoApproveOrder> result = new ArrayList<NoApproveOrder>(orders.size());

		ModelMapper mapper = PojoUtils.getMapper(NoApproveOrder.class);
		String[] names = mapper.getNames().toArray(new String[0]);

		List<String> ids = PojoCollections.getKeyList(orders, OrderSheet.OrderId);
		List<OrderApprove> approves = fetchApproveStatus(ids);
		for (JoinedModels order : orders) {
			NoApproveOrder noApprove = UModel.copy(order, NoApproveOrder.class, names);

			noApprove.getAmount().setCurrency((String) order.getValue(OrderSheet.Currency));
			noApprove.getAmount().minus((Money) order.getValue(OrderSheet.DiscountAmount));

			/*
			 * TODO:他の情報
			 */
			noApprove.setArriveDate((String) order.getValue(OrderDetail.DeliveryDate));
			/*
			 * 承認状態
			 */
			Long orderId = noApprove.getOrderId();
			noApprove.setApprovedStates(fetchApproveStatus(approves, orderId));

			result.add(noApprove);
		}

		return result;
	}

	/*
	 * 承認状態を取得
	 */
	public static String fetchApproveStatus(List<OrderApprove> approves, Long orderId) throws Exception {
		List<Long> rolerIds = new ArrayList<Long>();
		for (OrderApprove approve : approves) {
			if (approve.getOrderId().equals(orderId))
				rolerIds.add(approve.getRole());
		}

		return RolerMgr.searchRolersAsString(rolerIds);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<OrderApprove> fetchApproveStatus(List<String> ids) throws Exception {
		Criteria<OrderApprove> criteria = new Criteria(OrderApprove.class);
		criteria.and(Restrictions.in(OrderApprove.OrderId, ids));
		List<OrderApprove> approves = criteria.list();
		return approves;
	}

	/*
	 * 仕入納期変更
	 */
	public static void changeDeliveryDate(final OrderDetail detail, String newDeliveryDate, Integer quantity)
			throws Exception {
		if (SessionMgr.isLogined() == false)
			return;

		detail.setDeliveryDate(newDeliveryDate);
		detail.setQuantity(quantity);

		final OrderChange orderChange = new OrderChange();
		orderChange.setDetailId(detail.getDetailId());
		orderChange.setOrderId(detail.getOrderId());
		orderChange.setDeliveryDate(newDeliveryDate);
		orderChange.setQuantity(quantity);

		TxMgr.update(new ITxProc() {

			@Override
			public void process(Connection con) throws Exception {
				/*
				 * TODO:insert/ update
				 */
				TxMgr.insert(con, orderChange);
				TxMgr.update(con, detail);
			}
		});

	}

	/*
	 * 納期終了
	 */
	public static void over(Long orderId, Integer quantity) throws Exception {
		final OrderOver orderOver = new OrderOver();
		Long overId = SequenceMgr.nextSeq(OrderOver.class);
		orderOver.setOverId(overId);
		orderOver.setOrderId(orderId);
		orderOver.setQuantity(quantity);

		TxMgr.insert(orderOver);
	}

	public static void register(OrderExcise excise) throws Exception {
		excise.setDebtId(SequenceMgr.nextSeq(OrderExcise.class));
		TxMgr.insert(excise);
	}

	public static void register(Debt debt) throws Exception {
		debt.setDebtId(SequenceMgr.nextSeq(Debt.class));
		TxMgr.insert(debt);
	}

	public static void register(PrePayment prePayment) throws Exception {
		prePayment.setPrePaymentId(SequenceMgr.nextSeq(PrePayment.class));
		TxMgr.insert(prePayment);
	}

	public static boolean update(Connection con, Long oldOrderId, Long newOrderId) throws Exception {
		List<OrderDetail> details = fetchDetails(oldOrderId, "");
		for (OrderDetail detail : details) {
			detail.setOrderId(newOrderId);
			/*
			 * 自動採番
			 */
			Long detailId = SequenceMgr.nextSeq(OrderDetail.class);
			detail.setDetailId(detailId);
		}

		/*
		 * DBにInsert
		 */
		TxMgr.insertList(con, details);

		return true;
	}

	/*
	 * 承認者ユーザーID
	 * 
	 * 社長、副社長
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static EstimationApproves getApproves(Long orderId) throws Exception {
		Criteria<OrderApprove> criteria = new Criteria(OrderApprove.class);
		criteria.and(Restrictions.eq(OrderApprove.OrderId, orderId));
		List<OrderApprove> result = criteria.list();

		Rolers president = RolerMgr.getRolerForPresident();
		Rolers vicePresident = RolerMgr.getRolerForVicePresident();

		EstimationApproves apprve = new EstimationApproves();
		Long[] userIds = new Long[result.size()];
		int index = 0;
		for (OrderApprove approve : result) {
			Long userId = approve.getUserId();
			userIds[index++] = userId;

			Long rolerId = approve.getRole();
			if (president.getRolerId().equals(rolerId))
				apprve.setPresident(userId);
			else if (vicePresident.getRolerId().equals(rolerId))
				apprve.setVicePresident(userId);
		}
		apprve.setApprovers(userIds);
		return apprve;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Criteria<OrderApprove> createApproved() {
		Criteria<OrderApprove> approveds = new Criteria(OrderApprove.class);
		/*
		 * 副社長承認
		 */
		approveds.and(Restrictions.eq(OrderApprove.Role, RolerMgr.getRolerForVicePresident().getRolerId()));
		approveds.addSelectedColumn(OrderApprove.OrderId);

		Criteria<OrderApprove> approved0 = new Criteria(OrderApprove.class);
		/*
		 * 社長承認
		 */
		approved0.and(Restrictions.eq(OrderApprove.Role, RolerMgr.getRolerForPresident().getRolerId()));
		approved0.addSelectedColumn(OrderApprove.OrderId);
		approveds.and(Restrictions.in(OrderApprove.OrderId, approved0));

		return approveds;
	}
}
