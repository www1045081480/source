package com.np.order.biz.invoice;

import static com.np.base.dml.Sortter.SORT_DSC;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.db.ITxProc;
import com.np.base.db.Query;
import com.np.base.db.SequenceMgr;
import com.np.base.db.TxMgr;
import com.np.base.dml.IModelFactory;
import com.np.base.dml.Matcher;
import com.np.base.dml.PojoCollections;
import com.np.base.dml.RightJoiner;
import com.np.base.dml.Sortter;
import com.np.base.models.JoinedModels;
import com.np.base.orm.Criteria;
import com.np.base.orm.Restrictions;
import com.np.base.utils.UList;
import com.np.base.utils.UString;
import com.np.order.Money;
import com.np.order.action.SessionMgr;
import com.np.order.biz.mast.CustomerMgr;
import com.np.order.models.CIFArrival;
import com.np.order.models.Customer;
import com.np.order.models.EstimationConfirm;
import com.np.order.models.EstimationInvoice;
import com.np.order.models.EstimationOrder;
import com.np.order.models.EstiomationDetail;
import com.np.order.models.EstmationCancel;
import com.np.order.models.EstmationSheet;
import com.np.order.models.InvoiceApprove;
import com.np.order.models.InvoiceCancel;
import com.np.order.models.InvoiceDetail;
import com.np.order.models.InvoiceSheet;
import com.np.order.models.Item;
import com.np.order.models.OrderAccept;
import com.np.order.models.OrderCancel;
import com.np.order.models.OrderChange;
import com.np.order.models.OrderDetail;
import com.np.order.models.OrderInvoice;
import com.np.order.models.OrderSheet;
import com.np.order.models.PackingList;
import com.np.order.models.ReceiveMoney;
import com.np.order.models.ShippingCost;
import com.np.order.objects.DeliveryResult;
import com.np.order.objects.DeliveryResultFilter;
import com.np.order.objects.EstimationResult;
import com.np.order.objects.EstimationResultFilter;
import com.np.order.objects.InvoiceInfo;
import com.np.order.objects.OrderResult;
import com.np.order.objects.OrderResultFilter;

/*
 * 実績 一覧
 */
public class ResultListMgr {
	private static Log logger = LogFactory.getLog(ResultListMgr.class);

	/*
	 * 見積実績 一覧
	 * 
	 * 検索条件： 見積番号 得意先 期間 商品種別
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<EstimationResult> getEstimationResult(EstimationResultFilter filter) throws Exception {
		List<EstimationResult> result = new ArrayList<EstimationResult>();

		Criteria<EstmationSheet> criteria = new Criteria(EstmationSheet.class);
		if (UString.notEmpty(filter.getEstimationCd()))
			criteria.and(Restrictions.eq(EstmationSheet.EstimationCd, filter.getEstimationCd()));
		if (UString.notEmpty(filter.getCustomerName())) {
			criteria.and(Restrictions.eq(EstmationSheet.CustomerName, filter.getCustomerName()));
		}
		if (UString.notEmpty(filter.getDateFrom()) || UString.notEmpty(filter.getDateTo())) {
			if (UString.notEmpty(filter.getDateFrom()) && UString.notEmpty(filter.getDateTo())) {
				criteria.and(Restrictions.between(EstmationSheet.IssueDate, filter.getDateFrom(), filter.getDateTo()));
			} else if (UString.notEmpty(filter.getDateFrom())) {
				criteria.and(Restrictions.ge(EstmationSheet.IssueDate, filter.getDateFrom()));
			} else if (UString.notEmpty(filter.getDateTo())) {
				criteria.and(Restrictions.le(EstmationSheet.IssueDate, filter.getDateTo()));
			}
		}

		// Criteria<EstimationAccept> accepts = new
		// Criteria(EstimationAccept.class);
		// accepts.addSelectedColumn(EstimationAccept.EstimationId);
		// criteria.and(Restrictions.in(EstmationSheet.EstimationId, accepts));

		Criteria<EstmationCancel> cancel = new Criteria(EstmationCancel.class);
		cancel.addSelectedColumn(EstmationCancel.EstimationId);
		criteria.and(Restrictions.notIn(EstmationSheet.EstimationId, cancel));

		List<JoinedModels> estimations = Query.queryWithDependencies(criteria, EstiomationDetail.class);

		List<Long> itemIds = PojoCollections.getKeyList(estimations, EstiomationDetail.ItemId);
		Criteria<Item> critItem = new Criteria(Item.class);
		critItem.and(Restrictions.in(Item.ItemId, itemIds));
		if (UString.notEmpty(filter.getCategoryType()))
			critItem.and(Restrictions.eq(Item.CategoryType, filter.getCategoryType()));
		List<Item> items = critItem.list();

		if (UString.notEmpty(filter.getCategoryType())) {
			for (int i = estimations.size() - 1; i >= 0; i--) {
				JoinedModels estimation = estimations.get(i);
				Long itemId = estimation.getValue(EstiomationDetail.ItemId);
				boolean exist = false;
				for (Item item : items) {
					if (item.getItemId().equals(itemId)) {
						exist = true;
						break;
					}
				}
				if (exist == false)
					estimations.remove(i);
			}
		}

		List<Long> estimationIds = PojoCollections.getKeyList(estimations, EstmationSheet.EstimationId);
		List<String> estimationCds = PojoCollections.getKeyList(estimations, EstmationSheet.EstimationCd);

		List<Long> confirmedIds = EstmationSheetMgr.getConfirmed(estimationIds);
		List<String> deliveriedCds = EstmationSheetMgr.getDeliveried(estimationCds);
		List<Long> approvedIds = EstmationSheetMgr.getApproved(estimationIds);
		// List<Long> acceptedIds =
		// EstmationSheetMgr.getAccepted(estimationIds);

		List<EstmationSheet> sheets = EstmationSheetMgr.fetchSheets(estimationIds);
		String[] joinKeys = { Item.ItemId };
		// deleveries = Matcher.innerJoin(orders, estimetions, joinKeys,
		// DeliveryInfo.class);
		result = RightJoiner.rightJoin(estimations, items, joinKeys,
				new IModelFactory<EstimationResult, JoinedModels, Item>() {
					@Override
					public EstimationResult create(JoinedModels tra, Item mas) {
						EstimationResult result = new EstimationResult();
						result.setEstimationId((Long) tra.getValue(EstmationSheet.EstimationId));
						result.setEstimationCd((String) tra.getValue(EstmationSheet.EstimationCd));
						result.setCustomerName((String) tra.getValue(EstmationSheet.CustomerName));
						result.setCustomerId((Long) tra.getValue(EstmationSheet.CustomerId));
						if (mas != null)
							result.setCategoryType((String) mas.getCategoryType());
						result.setIssueDate((String) tra.getValue(EstmationSheet.IssueDate));
						result.setLangFlg((String) tra.getValue(EstmationSheet.LangFlg));

						result.setModelCd(
								tra.getValue(EstiomationDetail.Name) + "/" + tra.getValue(EstiomationDetail.Quantity));
						result.setAmount((Money) tra.getValue(EstmationSheet.Amount));
						result.getAmount().setCurrency((String) tra.getValue(EstmationSheet.Currency));

						logger.debug("EstimationCd/Item：" + result.getEstimationCd() + "/"
								+ (mas == null ? "null" : mas.getItemId()));
						if (mas == null) {
							logger.debug("EstimationId/ItemId：" + result.getEstimationId() + "/"
									+ tra.getValue(EstiomationDetail.ItemId));
						}

						result.setEstimationOkDays((String) tra.getValue(EstmationSheet.DeliveryLimitDays));

						/*
						 * 支払
						 */
						result.setPayment((String) tra.getValue(EstmationSheet.PaymentCondition));

						/*
						 * 進捗: 承認済み 受注確定 注文済み 出荷完了 納品済み 支払済み
						 * 
						 */

						StringBuffer status = new StringBuffer();
						if (approvedIds.contains(result.getEstimationId())) {
							status.append("承認済み");
							if (confirmedIds.contains(result.getEstimationId())) {
								status.append(" ");
								status.append("受注済み ");
								if (deliveriedCds.contains(result.getEstimationCd())) {
									status.append(" ");
									status.append(" 出荷完了 ");
									if (EstmationSheetMgr.isAccepted((Long) result.getEstimationId())) {
										status.append(" ");
										status.append(" 支払済み ");
									}
								}
							}
						} else {
							// logger.debug("未承認：" + result);
							return null;
						}

						result.setStatus(status.toString());

						for (EstmationSheet sheet : sheets) {
							if (sheet.getEstimationId().equals(result.getEstimationId()) == false)
								continue;
							result.setRegTime(sheet.getRegTime());
							result.setUpdTime(sheet.getUpdTime());
							break;
						}
						return result;
					}
				});

		/*
		 * ソード；得意先、時間、商品種別
		 */
		// String[] sortKeys = { EstmationSheet.CustomerId,
		// EstmationSheet.IssueDate, EstmationSheet.EstimationCd,
		// Item.CategoryType };
		/*
		 * 2016/01/16: 更新日時、作成日時でソートする。
		 */
		String[] sortKeys = { EstmationSheet.UpdTime, EstmationSheet.IssueDate };

		Sortter.sort(result, sortKeys, SORT_DSC);

		logger.debug("result : " + result.size());
		return result;
	}

	/*
	 * 注文実績 一覧
	 * 
	 * 検索条件： 注文番号 仕入先 期間 商品種別
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<OrderResult> getOrderResult(OrderResultFilter filter) throws Exception {
		List<OrderResult> result = new ArrayList<OrderResult>();

		Criteria<OrderSheet> criteria = new Criteria(OrderSheet.class);
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

		Criteria<OrderAccept> accepts = new Criteria(OrderAccept.class);
		accepts.addSelectedColumn(OrderAccept.OrderId);
		// criteria.and(Restrictions.in(OrderAccept.OrderId, accepts));

		Criteria<OrderCancel> cancel = new Criteria(OrderCancel.class);
		cancel.addSelectedColumn(OrderCancel.OrderId);
		criteria.and(Restrictions.notIn(OrderSheet.OrderId, cancel));

		List<JoinedModels> orders = Query.queryWithDependencies(criteria, OrderDetail.class);

		List<Long> itemIds = PojoCollections.getKeyList(orders, OrderDetail.ItemId);
		Criteria<Item> critItem = new Criteria(Item.class);
		critItem.and(Restrictions.in(Item.ItemId, itemIds));
		if (UString.notEmpty(filter.getCategoryType()))
			critItem.and(Restrictions.eq(Item.CategoryType, filter.getCategoryType()));
		List<Item> items = critItem.list();

		if (UString.notEmpty(filter.getCategoryType())) {
			for (int i = orders.size() - 1; i >= 0; i--) {
				JoinedModels order = orders.get(i);
				Long itemId = order.getValue(OrderDetail.ItemId);
				boolean exist = false;
				for (Item item : items) {
					if (item.getItemId().equals(itemId)) {
						exist = true;
						break;
					}
				}
				if (exist == false)
					orders.remove(i);
			}
		}

		List<Long> orderIds = PojoCollections.getKeyList(orders, OrderSheet.OrderId);
		List<String> orderCds = PojoCollections.getKeyList(orders, OrderSheet.OrderCd);

		List<Long> approvedIds = OrderSheetMgr.getApproved(orderIds);
		List<String> deliveriedCds = OrderSheetMgr.getDeliveried(orderCds);
		List<Long> acceptedIds = OrderSheetMgr.getAccepted(orderIds);

		List<OrderChange> changeds = DeliveryMgr.getChangeds(orders);

		String[] joinKeys = { Item.ItemId };
		// deleveries = Matcher.innerJoin(orders, estimetions, joinKeys,
		// DeliveryInfo.class);
		result = RightJoiner.rightJoin(orders, items, joinKeys, new IModelFactory<OrderResult, JoinedModels, Item>() {
			@Override
			public OrderResult create(JoinedModels tra, Item mas) {
				OrderResult result = new OrderResult();
				result.setOrderId((Long) tra.getValue(OrderSheet.OrderId));
				result.setOrderCd((String) tra.getValue(OrderSheet.OrderCd));
				result.setSupplierId((Long) tra.getValue(OrderSheet.SupplierId));
				result.setSupplierName((String) tra.getValue(OrderSheet.SupplierName));
				if (mas != null)
					result.setCategoryType((String) mas.getCategoryType());
				result.setIssueDate((String) tra.getValue(OrderSheet.IssueDate));
				result.setLangFlg((String) tra.getValue(OrderSheet.LangFlg));

				result.setModelCd(tra.getValue(OrderDetail.Name) + "/" + tra.getValue(OrderDetail.Quantity));
				result.setAmount((Money) tra.getValue(OrderDetail.Amount));
				result.getAmount().setCurrency((String) tra.getValue(OrderSheet.Currency));

				result.setDeliveryDate((String) tra.getValue(OrderSheet.DeliveryDate));

				Long detailId = (Long) tra.getValue(OrderDetail.DetailId);
				for (OrderChange changed : changeds) {
					if (changed.getOrderId().equals(result.getOrderId()) == false)
						continue;
					if (changed.getDetailId().equals(detailId) == false)
						continue;
					result.setDeliveryDate(changed.getDeliveryDate());
					break;
				}
				/*
				 * 支払
				 */

				/*
				 * 進捗: 進捗:承認済み 入荷完了 支払済み
				 * 
				 */
				StringBuffer status = new StringBuffer();
				if (approvedIds.contains(result.getOrderId())) {
					status.append("承認済み");
					if (deliveriedCds.contains(result.getOrderCd())) {
						status.append(" ");
						status.append("  入荷完了 ");
						if (acceptedIds.contains(result.getOrderId())) {
							status.append(" ");
							status.append(" 支払済み ");
						}
					}
				} else {
					// logger.debug("未承認：" + result);
					return null;
				}

				result.setStatus(status.toString());
				return result;
			}
		});

		/*
		 * ソード；得意先、時間、商品種別
		 */
		String[] sortKeys = { OrderSheet.IssueDate, OrderSheet.SupplierId, OrderSheet.OrderCd, Item.CategoryType };
		Sortter.sort(result, sortKeys, Sortter.SORT_DSC);

		logger.debug("result : " + result.size());
		return result;
	}

	/*
	 * 配送実績 一覧
	 * 
	 * 検索条件： Invoice番号 得意先 期間
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<DeliveryResult> getDeliveryResult(DeliveryResultFilter filter) throws Exception {
		List<DeliveryResult> result = new ArrayList<DeliveryResult>();

		Criteria<InvoiceSheet> criteria = new Criteria(InvoiceSheet.class);
		if (UString.notEmpty(filter.getInvoiceCd()))
			criteria.and(Restrictions.eq(InvoiceSheet.InvoiceCd, filter.getInvoiceCd()));

		criteria.and(Restrictions.eq(InvoiceSheet.CustomerId, filter.getCustomerId()));

		if (UString.notEmpty(filter.getDateFrom()) || UString.notEmpty(filter.getDateTo())) {
			if (UString.notEmpty(filter.getDateFrom()) && UString.notEmpty(filter.getDateTo())) {
				criteria.and(Restrictions.between(InvoiceSheet.IssueDate, filter.getDateFrom(), filter.getDateTo()));
			} else if (UString.notEmpty(filter.getDateFrom())) {
				criteria.and(Restrictions.ge(InvoiceSheet.IssueDate, filter.getDateFrom()));
			} else if (UString.notEmpty(filter.getDateTo())) {
				criteria.and(Restrictions.le(InvoiceSheet.IssueDate, filter.getDateTo()));
			}
		}

		Criteria<ReceiveMoney> accepts = new Criteria(ReceiveMoney.class);
		accepts.addSelectedColumn(ReceiveMoney.InvoiceId);
		// criteria.and(Restrictions.in(ReceiveMoney.InvoiceId, accepts));

		Criteria<InvoiceCancel> cancel = new Criteria(InvoiceCancel.class);
		cancel.addSelectedColumn(InvoiceCancel.InvoiceId);
		criteria.and(Restrictions.notIn(InvoiceSheet.InvoiceId, cancel));

		List<JoinedModels> invoices = Query.queryWithDependencies(criteria, InvoiceDetail.class);
		if (UList.isEmpty(invoices))
			return result;

		List<String> invoiceCds = PojoCollections.getUniqueKeyList(invoices, InvoiceSheet.InvoiceCd);
		List<JoinedModels> estimations = getEstimations(invoiceCds);
		if (UList.isEmpty(estimations))
			return result;

		/*
		 * 見積書と注文をマッチ
		 */
		String[] joinKeys = { InvoiceSheet.InvoiceCd, InvoiceDetail.ItemId };
		// List<JoinedModels> estimationOrders = Matcher.innerJoin(estimations,
		// invoices, joinKeys[0]);

		/*
		 * 見積書注文とInvoiceをマッチ
		 */

		List<CIFArrival> arrivals = getArrivals(invoiceCds);
		List<ShippingCost> costs = getCosts(invoiceCds);

		List<Long> invoiceIds = PojoCollections.getUniqueKeyList(invoices, InvoiceSheet.InvoiceId);
		List<InvoiceApprove> approves = getApproves(invoiceIds);
		Map<Long, Customer> customers = getCustomers();

		List<Long> estimationIds = PojoCollections.getUniqueKeyList(estimations, EstmationSheet.EstimationId);
		List<EstimationConfirm> confirms = getConfirms(estimationIds);

		result = Matcher.innerJoin(estimations, invoices, joinKeys,
				new IModelFactory<DeliveryResult, JoinedModels, JoinedModels>() {

					@Override
					public DeliveryResult create(JoinedModels tra, JoinedModels mas) {
						JoinedModels invoice = mas;
						JoinedModels estimation = tra;

						DeliveryResult delivery = new DeliveryResult();
						delivery.setInvoiceId((Long) invoice.getValue(InvoiceSheet.InvoiceId));
						delivery.setInvoiceCd((String) invoice.getValue(InvoiceSheet.InvoiceCd));
						delivery.setIssueDate((String) invoice.getValue(InvoiceSheet.IssueDate));

						Long customerId = (Long) estimation.getValue(EstmationSheet.CustomerId);
						Customer customer = customers.get(customerId);
						if (customer != null)
							delivery.setCustomerName(customer.getCnName());

						delivery.setEstimationCd((String) estimation.getValue(EstmationSheet.EstimationCd));
						delivery.setEstimationId((Long) estimation.getValue(EstmationSheet.EstimationId));

						for (EstimationConfirm confirm : confirms) {
							if (confirm.getEstimationId().equals(delivery.getEstimationId())) {
								delivery.setOrderCd(confirm.getCustomerOrderNo());
								break;
							}
						}

						delivery.setDeliveryType((String) invoice.getValue(InvoiceSheet.DeliveryType));
						delivery.setShippingType((String) invoice.getValue(InvoiceSheet.ShippingType));
						delivery.setShippingCompany((String) invoice.getValue(InvoiceSheet.ShippingCompany));

						/*
						 * TODO:Performance
						 */
						delivery.setPackingNumber(getNumberOfPacking(delivery.getInvoiceId()));

						delivery.setItemName((String) estimation.getValue(EstiomationDetail.Name));

						delivery.setModelCd((String) invoice.getValue(InvoiceDetail.ModelCd));
						delivery.setPartsCd((String) invoice.getValue(InvoiceDetail.PartsCd));
						// delivery.setModelCd(
						// invoice.getValue(InvoiceDetail.ModelCd) + "/" +
						// invoice.getValue(InvoiceDetail.Quantity));
						delivery.setQuantity((Integer) invoice.getValue(InvoiceDetail.Quantity));
						delivery.setAmount((Money) invoice.getValue(InvoiceDetail.Amount));
						delivery.getAmount().setCurrency((String) invoice.getValue(InvoiceSheet.Currency));

						for (CIFArrival arrival : arrivals) {
							if (arrival.getInvoiceCd().equals(delivery.getInvoiceCd())) {
								delivery.setArrivalDate(arrival.getArrivalDate());
								break;
							}
						}
						for (ShippingCost cost : costs) {
							if (cost.getInvoiceCd().equals(delivery.getInvoiceCd())) {
								delivery.setCarringAmount(cost.getCostAmount());
								break;
							}
						}

						for (InvoiceApprove approve : approves) {
							if (approve.getInvoiceId().equals(delivery.getInvoiceId()) == false)
								continue;
							if (approve.getEstimationId().equals(delivery.getEstimationId()) == false)
								continue;
							delivery.setApprovedId(approve.getUserId());
							break;
						}

						return delivery;
					}

				});

		/*
		 * ソード
		 */
		String[] sortKeys = { InvoiceSheet.IssueDate, InvoiceSheet.InvoiceCd, InvoiceDetail.PartsCd };
		Sortter.sort(result, sortKeys);

		logger.debug("result : " + result.size());
		return result;

	}

	/*
	 * INVOICE一覧
	 * 
	 * 検索条件：
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<InvoiceInfo> getInvoiceInfo() throws Exception {
		List<InvoiceInfo> result = new ArrayList<InvoiceInfo>();

		Criteria<InvoiceSheet> criteria = new Criteria(InvoiceSheet.class);

		/*
		 * TODO:
		 */
		Criteria<ReceiveMoney> accepts = new Criteria(ReceiveMoney.class);
		accepts.addSelectedColumn(ReceiveMoney.InvoiceId);
		criteria.and(Restrictions.notIn(ReceiveMoney.InvoiceId, accepts));

		List<JoinedModels> invoices = Query.queryWithDependencies(criteria, InvoiceDetail.class);

		for (JoinedModels invoice : invoices) {
			InvoiceInfo invoiceInfo = new InvoiceInfo();
			invoiceInfo.setInvoiceId((Long) invoice.getValue(InvoiceSheet.InvoiceId));
			invoiceInfo.setInvoiceCd((String) invoice.getValue(InvoiceSheet.InvoiceCd));
			invoiceInfo.setIssueDate((String) invoice.getValue(InvoiceSheet.IssueDate));
			invoiceInfo.setCustomerName((String) invoice.getValue(InvoiceSheet.CustomerName));

			Criteria<EstimationInvoice> criEstimationInvoice = new Criteria(EstimationInvoice.class);
			criEstimationInvoice.and(
					Restrictions.eq(EstimationInvoice.InvoiceCd, (String) invoice.getValue(InvoiceSheet.InvoiceCd)));
			EstimationInvoice estimationInvoice = criEstimationInvoice.get();
			if (estimationInvoice != null)
				invoiceInfo.setEstimationCd(estimationInvoice.getEstimationCd());

			Criteria<OrderInvoice> criOrderInvoice = new Criteria(OrderInvoice.class);
			criOrderInvoice
					.and(Restrictions.eq(OrderInvoice.InvoiceCd, (String) invoice.getValue(InvoiceSheet.InvoiceCd)));
			OrderInvoice orderInvoice = criOrderInvoice.get();
			if (orderInvoice != null)
				invoiceInfo.setOrderCd(orderInvoice.getOrderCd());

			invoiceInfo.setDeliveryType((String) invoice.getValue(InvoiceSheet.DeliveryType));
			invoiceInfo.setShippingType((String) invoice.getValue(InvoiceSheet.ShippingType));
			invoiceInfo.setShippingCompany((String) invoice.getValue(InvoiceSheet.ShippingCompany));

			/*
			 * TODO:
			 */
			invoiceInfo.setPackingNumber(getNumberOfPacking(invoiceInfo.getInvoiceId()));

			invoiceInfo
					.setModelCd(invoice.getValue(InvoiceDetail.Name) + "/" + invoice.getValue(InvoiceDetail.Quantity));

			invoiceInfo.setAmount((Money) invoice.getValue(InvoiceDetail.Amount));
			invoiceInfo.getAmount().setCurrency((String) invoice.getValue(InvoiceSheet.Currency));

			result.add(invoiceInfo);
		}

		/*
		 * ソード；得意先、時間、商品種別
		 */
		String[] sortKeys = { InvoiceSheet.CustomerId, InvoiceSheet.IssueDate, InvoiceSheet.InvoiceCd,
				Item.CategoryType, };
		Sortter.sort(result, sortKeys);

		logger.debug("result : " + result.size());
		return result;
	}

	/*
	 * 登録:
	 */
	public static void register(EstimationResult estimation) throws Exception {

	}

	/*
	 * 登録:
	 */
	public static void register(OrderResult order) throws Exception {

	}

	/*
	 * 登録:到着日付 運賃金額
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void register(DeliveryResult delivery) throws Exception {
		if (SessionMgr.isLogined() == false)
			return;

		TxMgr.update(new ITxProc() {

			@Override
			public void process(Connection con) throws Exception {
				/*
				 * 到着日付
				 */
				if (UString.notEmpty(delivery.getArrivalDate())) {
					Criteria<CIFArrival> criteria = new Criteria(CIFArrival.class);
					criteria.and(Restrictions.eq(CIFArrival.InvoiceCd, delivery.getInvoiceCd()));
					CIFArrival arrival = criteria.get();
					if (arrival == null) {
						arrival = new CIFArrival();
						arrival.setArrvalId(SequenceMgr.nextSeq(CIFArrival.class));
						arrival.setArrivalDate(delivery.getArrivalDate());
						arrival.setInvoiceCd(delivery.getInvoiceCd());
						TxMgr.insert(con, arrival);
					} else {
						arrival.setArrivalDate(delivery.getArrivalDate());
						TxMgr.update(con, arrival);
					}
				}

				/*
				 * 運賃金額
				 */
				{
					Criteria<ShippingCost> criteria = new Criteria(ShippingCost.class);
					criteria.and(Restrictions.eq(ShippingCost.InvoiceCd, delivery.getInvoiceCd()));
					ShippingCost cost = criteria.get();
					if (cost == null) {
						cost = new ShippingCost();
						cost.setCostId(SequenceMgr.nextSeq(ShippingCost.class));
						cost.setCostAmount(delivery.getCarringAmount());
						cost.setInvoiceCd(delivery.getInvoiceCd());
						TxMgr.insert(con, cost);
					} else {
						cost.setCostAmount(delivery.getCarringAmount());
						TxMgr.update(con, cost);
					}
				}
			}
		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List<JoinedModels> getEstimations(List<String> invoiceCds) {
		Criteria<EstimationInvoice> criteriaEstimationInvoice = new Criteria(EstimationInvoice.class);
		// criteriaEstimationInvoice.addSelectedColumn(EstimationInvoice.EstimationCd);
		criteriaEstimationInvoice.and(Restrictions.in(EstimationInvoice.InvoiceCd, invoiceCds));

		List<EstimationInvoice> cdMaps = criteriaEstimationInvoice.list();

		List<String> estimationCds = PojoCollections.getKeyList(cdMaps, EstimationOrder.EstimationCd);

		Criteria<EstmationSheet> criteria = new Criteria(EstmationSheet.class);
		// 取消した見積書
		Criteria<EstmationCancel> cancels = new Criteria(EstmationCancel.class);
		cancels.addSelectedColumn(EstmationCancel.EstimationId);

		criteria.and(Restrictions.notIn(EstmationSheet.EstimationId, cancels));
		criteria.and(Restrictions.in(EstmationSheet.EstimationCd, estimationCds));

		List<JoinedModels> orders = Query.queryWithDependencies(criteria, EstiomationDetail.class);

		for (JoinedModels order : orders) {
			order.appendName(EstimationInvoice.InvoiceCd);
			String orderCd = order.getValue(EstmationSheet.EstimationCd);
			for (EstimationInvoice estmOrder : cdMaps) {
				if (estmOrder.getEstimationCd().equals(orderCd)) {
					order.setValue(EstimationInvoice.InvoiceCd, estmOrder.getInvoiceCd());
					break;
				}
			}
		}

		return orders;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static int getNumberOfPacking(Long invoiceId) {
		Criteria<PackingList> criteriaPackingList = new Criteria(PackingList.class);
		criteriaPackingList.and(Restrictions.eq(PackingList.InvoiceId, invoiceId));
		List<PackingList> packingLists = criteriaPackingList.list();
		return packingLists.size();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List<CIFArrival> getArrivals(List<String> invoiceCds) {
		Criteria<CIFArrival> criteria = new Criteria(CIFArrival.class);
		criteria.and(Restrictions.in(CIFArrival.InvoiceCd, invoiceCds));
		return criteria.list();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List<ShippingCost> getCosts(List<String> invoiceCds) {
		Criteria<ShippingCost> criteria = new Criteria(ShippingCost.class);
		criteria.and(Restrictions.in(ShippingCost.InvoiceCd, invoiceCds));
		return criteria.list();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List<InvoiceApprove> getApproves(List<Long> invoiceIds) {
		Criteria<InvoiceApprove> criteria = new Criteria(InvoiceApprove.class);
		criteria.and(Restrictions.in(InvoiceApprove.InvoiceId, invoiceIds));
		// criteria.and(Restrictions.eq(InvoiceApprove.EstimationId,
		// estimationId));
		return criteria.list();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List<EstimationConfirm> getConfirms(List<Long> estimationIds) {
		Criteria<EstimationConfirm> criteria = new Criteria(EstimationConfirm.class);
		criteria.and(Restrictions.in(EstimationConfirm.EstimationId, estimationIds));
		return criteria.list();
	}

	static Map<Long, Customer> getCustomers() {
		List<Customer> customers = CustomerMgr.getCustomers();
		Map<Long, Customer> result = new HashMap<Long, Customer>();
		for (Customer customer : customers) {
			result.put(customer.getCustomerId(), customer);
		}
		return result;
	}
}
