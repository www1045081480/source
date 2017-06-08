package com.np.order.biz.invoice;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.db.ITxProc;
import com.np.base.db.Query;
import com.np.base.db.SequenceMgr;
import com.np.base.db.TxMgr;
import com.np.base.dml.PojoCollections;
import com.np.base.dml.Sortter;
import com.np.base.orm.Criteria;
import com.np.base.orm.Restrictions;
import com.np.base.utils.UDate;
import com.np.base.utils.UModel;
import com.np.order.action.SessionMgr;
import com.np.order.biz.IllegalPriorityEception;
import com.np.order.models.EstimationInvoice;
import com.np.order.models.EstimationInvoiceHistory;
import com.np.order.models.InvoiceApprove;
import com.np.order.models.InvoiceCancel;
import com.np.order.models.InvoiceDetail;
import com.np.order.models.InvoiceSheet;
import com.np.order.models.OrderInvoice;
import com.np.order.models.OrderInvoiceHistory;
import com.np.order.models.PackingList;
import com.np.order.models.PackingListDetail;
import com.np.order.views.InvoiceDetailView;
import com.np.order.views.PackingListDetailView;

public class InvoiceSheetMgr {
	private static Log logger = LogFactory.getLog(InvoiceSheetMgr.class);

	public static void main(String... args) throws Exception {
		// List<InvoiceDetail> invoiceDetails = fetchInvoiceDetails(121L);
		// List<PackingListDetail> packingListDetails =
		// fetchPackingListDetails(127L);
		// save(invoiceDetails, packingListDetails);

		// TxMgr.update(sheet);
		TxMgr.update(new ITxProc() {

			@Override
			public void process(Connection con) throws Exception {
				updateInvoiceCd(con, "XAJ-S15-1240", "XXX-XXX-test");
			}
		});
	}

	private static class InvoiceInfo {
		InvoiceSheet invoiceSheet;
		List<InvoiceDetail> invoiceDetails = new ArrayList<InvoiceDetail>();
		List<PackingListInfo> packingLists = new ArrayList<PackingListInfo>();
	}

	private static class PackingListInfo {
		PackingList packingList;
		List<PackingListDetail> packingListDetails = new ArrayList<PackingListDetail>();
	}

	@Deprecated
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean save(List<InvoiceDetail> invoiceDetails, List<PackingListDetail> packingListDetails)
			throws Exception {
		if (SessionMgr.isDistributer() == false)
			throw new IllegalPriorityEception();

		if (invoiceDetails.isEmpty() || packingListDetails.isEmpty())
			return false;

		List<Long> invoiceIds = PojoCollections.getUniqueKeyList(invoiceDetails, InvoiceDetail.InvoiceId);

		Criteria<InvoiceSheet> criteriaInvoice = new Criteria(InvoiceSheet.class);
		criteriaInvoice.and(Restrictions.in(InvoiceSheet.InvoiceId, invoiceIds));
		List<InvoiceSheet> invoices = criteriaInvoice.list();

		Criteria<PackingList> criteriaPackingList = new Criteria(PackingList.class);
		criteriaPackingList.and(Restrictions.in(PackingList.InvoiceId, invoiceIds));
		List<PackingList> packingLists = criteriaPackingList.list();

		final List<InvoiceInfo> invoiceInfos = new ArrayList<InvoiceInfo>();
		for (InvoiceSheet invoice : invoices) {
			Long invoiceId = invoice.getInvoiceId();
			InvoiceInfo invoiceInfo = new InvoiceInfo();
			invoiceInfos.add(invoiceInfo);

			invoiceInfo.invoiceSheet = invoice;
			for (InvoiceDetail detail : invoiceDetails) {
				if (detail.getInvoiceId().equals(invoiceId))
					invoiceInfo.invoiceDetails.add(detail);
			}

			for (PackingList packingList : packingLists) {
				if (packingList.getInvoiceId().equals(invoiceId) == false)
					continue;

				PackingListInfo packingListInfo = new PackingListInfo();
				invoiceInfo.packingLists.add(packingListInfo);
				packingListInfo.packingList = packingList;

				Long packingListId = packingList.getPackingListId();
				for (PackingListDetail packingListDetail : packingListDetails) {
					if (packingListDetail.getPackingListId().equals(packingListId))
						packingListInfo.packingListDetails.add(packingListDetail);
				}
			}
		}

		// TxMgr.update(sheet);
		TxMgr.update(new ITxProc() {

			@Override
			public void process(Connection con) throws Exception {

				for (InvoiceInfo invoiceInfo : invoiceInfos) {
					updateInvoice(con, invoiceInfo);
				}

			}
		});

		return true;
	}

	@Deprecated
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void updateInvoice(Connection con, InvoiceInfo invoiceInfo) throws Exception {
		InvoiceSheet invoice = invoiceInfo.invoiceSheet;
		Long oldInvoiceId = invoice.getInvoiceId();
		/*
		 * 取消
		 */
		InvoiceCancel cancel = new InvoiceCancel();
		cancel.setInvoiceId(oldInvoiceId);
		TxMgr.insert(con, cancel);

		Long newInvoiceId = SequenceMgr.nextSeq(InvoiceSheet.class);
		invoice.setInvoiceId(newInvoiceId);
		TxMgr.insert(con, invoice);

		for (InvoiceDetail detail : invoiceInfo.invoiceDetails) {
			detail.setInvoiceId(invoice.getInvoiceId());
			Long detailId = SequenceMgr.nextSeq(InvoiceDetail.class);
			detail.setDetailId(detailId);
		}
		TxMgr.insertList(con, invoiceInfo.invoiceDetails);

		for (PackingListInfo packingInfo : invoiceInfo.packingLists) {
			PackingList packing = packingInfo.packingList;
			Long packingId = SequenceMgr.nextSeq(PackingList.class);
			packing.setInvoiceId(invoice.getInvoiceId());
			packing.setPackingListId(packingId);
			TxMgr.insert(con, packing);

			for (PackingListDetail detail : packingInfo.packingListDetails) {
				detail.setPackingListId(packing.getPackingListId());
				Long detailId = SequenceMgr.nextSeq(PackingListDetail.class);
				detail.setDetailId(detailId);
			}

			TxMgr.insertList(con, packingInfo.packingListDetails);
		}

		/*
		 * TODO:detailのInvoiceIdを更新
		 */
		// update(con, oldInvoiceId, newInvoiceId);

		/*
		 * 承認状態
		 */
		Criteria<InvoiceApprove> criteriaApprove = new Criteria(InvoiceApprove.class);
		criteriaApprove.and(Restrictions.eq(InvoiceApprove.InvoiceId, oldInvoiceId));
		List<InvoiceApprove> approves = criteriaApprove.list();
		for (InvoiceApprove approve : approves) {
			TxMgr.delete(con, approve);
			approve.setInvoiceId(invoice.getInvoiceId());
			TxMgr.insert(con, approve);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean save(List<Long> orderIds, InvoiceSheet invoice, PackingList packing) throws Exception {
		if (SessionMgr.isDistributer() == false)
			throw new IllegalPriorityEception();

		logger.debug("save.invoice...");
		final Long oldInvoiceId = invoice.getInvoiceId();
		if (oldInvoiceId == null) {
			/*
			 * 作成者
			 */
			invoice.setUserId(SessionMgr.getLoginUserId());
			return register(orderIds, invoice, packing);
		}

		// TxMgr.update(sheet);
		TxMgr.update(new ITxProc() {

			@Override
			public void process(Connection con) throws Exception {
				{
					/*
					 * チェック修正
					 */
					Criteria<InvoiceSheet> criteria = new Criteria(InvoiceSheet.class);
					criteria.and(Restrictions.eq(InvoiceSheet.InvoiceId, oldInvoiceId));
					InvoiceSheet oldSheet = criteria.get();
					if (UModel.compareModel(oldSheet, invoice)) {
						/*
						 * 変更無し
						 */
						// return;
					}
					updateInvoiceCd(con, oldSheet.getInvoiceCd(), invoice.getInvoiceCd());
				}

				/*
				 * 取消
				 */
				InvoiceCancel cancel = new InvoiceCancel();
				cancel.setInvoiceId(oldInvoiceId);
				TxMgr.insert(con, cancel);

				Long newInvoiceId = SequenceMgr.nextSeq(InvoiceSheet.class);
				invoice.setInvoiceId(newInvoiceId);
				TxMgr.insert(con, invoice);

				Long packingId = SequenceMgr.nextSeq(PackingList.class);
				packing.setInvoiceId(invoice.getInvoiceId());
				packing.setPackingListId(packingId);
				TxMgr.insert(con, packing);

				/*
				 * TODO:detailのInvoiceIdを更新
				 */
				// update(con, oldInvoiceId, newInvoiceId);

				/*
				 * 承認状態
				 */
				Criteria<InvoiceApprove> criteriaApprove = new Criteria(InvoiceApprove.class);
				criteriaApprove.and(Restrictions.eq(InvoiceApprove.InvoiceId, oldInvoiceId));
				List<InvoiceApprove> approves = criteriaApprove.list();
				for (InvoiceApprove approve : approves) {
					TxMgr.delete(con, approve);
					approve.setInvoiceId(invoice.getInvoiceId());
					TxMgr.insert(con, approve);
				}
			}

		});
		return true;
	}

	@Deprecated
	public static boolean save(PackingList packingList) throws Exception {
		TxMgr.update(packingList);
		return true;
	}

	@Deprecated
	public static boolean register(InvoiceSheet sheet) throws Exception {
		Long invoiceId = SequenceMgr.nextSeq(InvoiceSheet.class);
		sheet.setInvoiceId(invoiceId);
		TxMgr.insert(sheet);
		return true;

	}

	public static boolean registerInvoiceDetails(List<InvoiceDetail> details) throws Exception {
		for (InvoiceDetail detail : details) {
			Long detailId = SequenceMgr.nextSeq(InvoiceDetail.class);
			detail.setDetailId(detailId);
		}
		TxMgr.insertList(details);
		return true;
	}

	public static boolean registerPackingListDetails(List<PackingListDetail> details) throws Exception {
		if (SessionMgr.isDistributer() == false)
			throw new IllegalPriorityEception();

		for (PackingListDetail detail : details) {
			/*
			 * 自動採番
			 */
			Long detailId = SequenceMgr.nextSeq(PackingListDetail.class);
			detail.setDetailId(detailId);
		}

		TxMgr.insertList(details);
		return true;
	}

	/*
	 * （1）进行插入操作
	 */
	public static boolean register(List<Long> orderIds, InvoiceSheet invoice, PackingList packing) throws Exception {
		if (SessionMgr.isDistributer() == false)
			throw new IllegalPriorityEception();

		String year = UDate.getYearYY();
		SequenceMgr.nextSeq("InvoiceCD" + year);

		/*
		 * 自動採番
		 */
		Long invoiceId = SequenceMgr.nextSeq(InvoiceSheet.class);
		invoice.setInvoiceId(invoiceId);

		List<String> orderCds = OrderSheetMgr.getOrderCds(orderIds);
		List<String> estimationCds = EstmationSheetMgr.getEstimationCds(orderCds);
		String invoiceCd = invoice.getInvoiceCd();

		Long packingId = SequenceMgr.nextSeq(PackingList.class);
		packing.setPackingListId(packingId);
		packing.setInvoiceId(invoiceId);

		/*
		 * DBにInsert
		 */
		TxMgr.update(new ITxProc() {

			@Override
			public void process(Connection con) throws Exception {
				TxMgr.insert(con, invoice);
				TxMgr.insert(con, packing);

				for (String estimationCd : estimationCds) {
					EstimationInvoice estimationInvoice = new EstimationInvoice();
					estimationInvoice.setEstimationCd(estimationCd);
					estimationInvoice.setInvoiceCd(invoiceCd);
					TxMgr.insert(con, estimationInvoice);
				}

				for (String orderCd : orderCds) {
					OrderInvoice orderInvoice = new OrderInvoice();
					orderInvoice.setInvoiceCd(invoiceCd);
					orderInvoice.setOrderCd(orderCd);
					TxMgr.insert(con, orderInvoice);
				}
			}

		});

		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void updateInvoiceCd(Connection con, String oldInvoiceCd, String invoiceCd) throws Exception {
		if (invoiceCd.equals(oldInvoiceCd)) {
			return;
		}

		Criteria<EstimationInvoice> criterEstimationInvoice = new Criteria(EstimationInvoice.class);
		criterEstimationInvoice.and(Restrictions.eq(EstimationInvoice.InvoiceCd, oldInvoiceCd));
		List<EstimationInvoice> estimationInvoices = criterEstimationInvoice.list();
		for (EstimationInvoice estimationInvoice : estimationInvoices) {
			Criteria<EstimationInvoiceHistory> criterHistory = new Criteria(EstimationInvoiceHistory.class);
			criterHistory.and(Restrictions.eq(EstimationInvoiceHistory.InvoiceCd, oldInvoiceCd));
			criterHistory
					.and(Restrictions.eq(EstimationInvoiceHistory.EstimationCd, estimationInvoice.getEstimationCd()));
			EstimationInvoiceHistory history = criterHistory.get();
			if (history != null)
				continue;

			history = new EstimationInvoiceHistory();
			history.setInvoiceCd(estimationInvoice.getInvoiceCd());
			history.setEstimationCd(estimationInvoice.getEstimationCd());
			TxMgr.insert(con, history);
		}

		Criteria<OrderInvoice> criterOrderInvoice = new Criteria(OrderInvoice.class);
		criterOrderInvoice.and(Restrictions.eq(OrderInvoice.InvoiceCd, oldInvoiceCd));
		List<OrderInvoice> orderInvoices = criterOrderInvoice.list();
		for (OrderInvoice orderInvoice : orderInvoices) {
			Criteria<OrderInvoiceHistory> criterHistory = new Criteria(OrderInvoiceHistory.class);
			criterHistory.and(Restrictions.eq(OrderInvoiceHistory.InvoiceCd, oldInvoiceCd));
			criterHistory.and(Restrictions.eq(OrderInvoiceHistory.OrderCd, orderInvoice.getOrderCd()));
			OrderInvoiceHistory history = criterHistory.get();
			if (history != null)
				continue;

			history = new OrderInvoiceHistory();
			history.setInvoiceCd(orderInvoice.getInvoiceCd());
			history.setOrderCd(orderInvoice.getOrderCd());
			TxMgr.insert(con, history);
		}

		criterEstimationInvoice.delete();
		criterOrderInvoice.delete();

		for (OrderInvoice orderInvoice : orderInvoices) {
			orderInvoice.setInvoiceCd(invoiceCd);
			TxMgr.insert(con, orderInvoice);
		}
		for (EstimationInvoice estimationInvoice : estimationInvoices) {
			estimationInvoice.setInvoiceCd(invoiceCd);
			TxMgr.insert(con, estimationInvoice);
		}

	}

	/*
	 * INVOICE詳細リストを登録
	 */
	public static boolean register(List<InvoiceDetail> invoiceDetails, List<PackingListDetail> packingDetails)
			throws Exception {
		if (SessionMgr.isDistributer() == false)
			throw new IllegalPriorityEception();

		for (InvoiceDetail detail : invoiceDetails) {
			/*
			 * 自動採番
			 */
			Long detailId = SequenceMgr.nextSeq(InvoiceDetail.class);
			detail.setDetailId(detailId);
		}

		for (PackingListDetail detail : packingDetails) {
			/*
			 * 自動採番
			 */
			Long detailId = SequenceMgr.nextSeq(PackingListDetail.class);
			detail.setDetailId(detailId);
		}

		/*
		 * DBにInsert
		 */
		TxMgr.update(new ITxProc() {

			@Override
			public void process(Connection con) throws Exception {
				TxMgr.insertList(con, invoiceDetails);
				TxMgr.insertList(con, packingDetails);
			}
		});

		return true;
	}

	/*
	 * （2）返回所有EstimationId
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Long> searchInvoiceIds() throws Exception {
		Criteria<InvoiceSheet> criteria = new Criteria(InvoiceSheet.class);
		criteria.addSelectedColumn(InvoiceSheet.InvoiceId);
		List<InvoiceSheet> list = criteria.list();

		List<Long> ids = PojoCollections.getKeyList(list, InvoiceSheet.InvoiceId);
		return ids;
	}

	/*
	 * INVOICE検索
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static InvoiceSheet fetchInvoiceSheet(Long invoiceId) {
		Criteria<InvoiceSheet> criteria = new Criteria(InvoiceSheet.class);
		criteria.and(Restrictions.eq(InvoiceSheet.InvoiceId, invoiceId));
		InvoiceSheet sheet = criteria.get();
		if (sheet == null)
			return sheet;
		Criteria<InvoiceApprove> criteriaApprove = new Criteria(InvoiceApprove.class);
		criteriaApprove.and(Restrictions.eq(InvoiceApprove.InvoiceId, invoiceId));
		InvoiceApprove approve = criteriaApprove.get();
		if (approve != null) {
			sheet.setUserId(approve.getUserId());
		}

		return sheet;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<InvoiceSheet> fetchInvoiceSheets(List<Long> invoiceIds) {
		Criteria<InvoiceSheet> criteria = new Criteria(InvoiceSheet.class);
		criteria.and(Restrictions.in(InvoiceSheet.InvoiceId, invoiceIds));
		return criteria.list();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static PackingList fetchPackingList(Long packingListId) {
		Criteria<PackingList> criteria = new Criteria(PackingList.class);
		criteria.and(Restrictions.eq(PackingList.PackingListId, packingListId));
		return criteria.get();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<PackingList> fetchPackingLists(List<Long> packingListIds) {
		Criteria<PackingList> criteria = new Criteria(PackingList.class);
		criteria.and(Restrictions.in(PackingList.PackingListId, packingListIds));
		return criteria.list();
	}

	public static String getCurrency(Long invoiceId) {
		InvoiceSheet sheet = fetchInvoiceSheet(invoiceId);
		return (sheet == null) ? null : sheet.getCurrency();
	}

	/*
	 * 検索
	 */
	public static List<InvoiceSheet> searchInvoiceSheets(InvoiceSheet sheet) throws Exception {
		List<InvoiceSheet> list = Query.query(sheet);
		return list;
	}

	/*
	 * INVOICE詳細リストを検索
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<InvoiceDetail> fetchInvoiceDetails(Long invoiceId) throws Exception {
		Criteria<InvoiceDetail> criteria = new Criteria(InvoiceDetail.class);
		criteria.and(Restrictions.eq(InvoiceDetail.InvoiceId, invoiceId));
		List<InvoiceDetail> details = criteria.list();

		InvoiceSheet sheet = fetchInvoiceSheet(invoiceId);
		for (InvoiceDetail detail : details) {
			detail.getAmount().setCurrency(sheet.getCurrency());
			detail.getUnitPrice().setCurrency(sheet.getCurrency());
		}
		Sortter.sort(details, InvoiceDetail.DetailId);
		return details;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<InvoiceDetailView> fetchInvoiceDetailsForModify(Long invoiceId) throws Exception {
		List<InvoiceDetailView> result = new ArrayList<InvoiceDetailView>();

		Criteria<InvoiceSheet> criteriaSheet = new Criteria(InvoiceSheet.class);
		criteriaSheet.and(Restrictions.eq(InvoiceSheet.InvoiceId, invoiceId));
		InvoiceSheet sheet = criteriaSheet.get();

		if (sheet == null)
			return result;

		Criteria<InvoiceDetail> criteria = new Criteria(InvoiceDetail.class);
		criteria.and(Restrictions.eq(InvoiceDetail.InvoiceId, invoiceId));
		List<InvoiceDetail> details = criteria.list();
		Sortter.sort(details, InvoiceDetail.DetailId);

		for (InvoiceDetail detail : details) {
			detail.getAmount().setCurrency(sheet.getCurrency());
			detail.getUnitPrice().setCurrency(sheet.getCurrency());

			InvoiceDetailView view = new InvoiceDetailView();

			view.setInvoiceId(detail.getInvoiceId());
			view.setDetailId(detail.getDetailId());
			view.setCurrency(sheet.getCurrency());
			view.setItemId(detail.getItemId());
			view.setName(detail.getName());
			view.setType(detail.getType());
			view.setPartsCd(detail.getPartsCd());
			view.setModelCd(detail.getModelCd());
			view.setUnit(detail.getUnit());
			view.setQuantity(detail.getQuantity());
			view.setUnitPrice(detail.getUnitPrice().getValue());
			view.setAmount(detail.getAmount().getValue());

			result.add(view);
		}

		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static PackingList searchPackingList(Long invoiceId) throws Exception {
		Criteria<PackingList> criteria = new Criteria(PackingList.class);
		criteria.and(Restrictions.eq(PackingList.InvoiceId, invoiceId));
		return criteria.get();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<PackingListDetail> fetchPackingListDetails(Long packingListId) throws Exception {
		Criteria<PackingListDetail> criteria = new Criteria(PackingListDetail.class);
		criteria.and(Restrictions.eq(PackingListDetail.PackingListId, packingListId));
		List<PackingListDetail> details = criteria.list();

		PackingList sheet = fetchPackingList(packingListId);
		for (PackingListDetail detail : details) {
			detail.getAmount().setCurrency(sheet.getCurrency());
			detail.getUnitPrice().setCurrency(sheet.getCurrency());
		}

		Sortter.sort(details, PackingListDetail.DetailId);
		return details;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<PackingListDetailView> fetchPackingListDetailsForModify(Long packingListId) throws Exception {
		List<PackingListDetailView> result = new ArrayList<PackingListDetailView>();

		Criteria<PackingList> criteriaSheet = new Criteria(PackingList.class);
		criteriaSheet.and(Restrictions.eq(PackingList.PackingListId, packingListId));
		PackingList sheet = criteriaSheet.get();

		if (sheet == null)
			return result;

		Criteria<PackingListDetail> criteria = new Criteria(PackingListDetail.class);
		criteria.and(Restrictions.eq(PackingListDetail.PackingListId, packingListId));
		List<PackingListDetail> details = criteria.list();

		for (PackingListDetail detail : details) {
			// detail.getAmount().setCurrency(sheet.getCurrency());
			// detail.getUnitPrice().setCurrency(sheet.getCurrency());

			PackingListDetailView view = new PackingListDetailView();

			view.setCurrency(sheet.getCurrency());

			view.setPackingListId(detail.getPackingListId());
			view.setDetailId(detail.getDetailId());

			view.setName(detail.getName());
			view.setItemId(detail.getItemId());
			view.setType(detail.getType());
			view.setPartsCd(detail.getPartsCd());
			view.setModelCd(detail.getModelCd());
			view.setQuantity(detail.getQuantity());
			view.setUnit(detail.getUnit());
			// view.setUnitPrice(detail.getUnitPrice().getValue());
			// view.setAmount(detail.getAmount().getValue());
			view.setNw(detail.getNw());
			view.setGw(detail.getGw());
			view.setM3(detail.getM3());

			result.add(view);
		}
		return result;
	}

	/*
	 * （4）更新表
	 */
	@Deprecated
	public static boolean modify(final InvoiceSheet sheet) throws Exception {
		// TxMgr.update(sheet);
		TxMgr.update(new ITxProc() {

			@Override
			public void process(Connection con) throws Exception {
				InvoiceCancel cancel = new InvoiceCancel();
				cancel.setInvoiceId(sheet.getInvoiceId());
				TxMgr.insert(con, cancel);

				Long invoiceId = SequenceMgr.nextSeq(InvoiceSheet.class);
				sheet.setInvoiceId(invoiceId);
				TxMgr.insert(con, sheet);
			}

		});
		return true;
	}

	@Deprecated
	public static boolean modify(final InvoiceSheet sheet, List<InvoiceDetail> details) throws Exception {
		// TxMgr.update(sheet);
		TxMgr.update(new ITxProc() {

			@Override
			public void process(Connection con) throws Exception {
				InvoiceCancel cancel = new InvoiceCancel();
				cancel.setInvoiceId(sheet.getInvoiceId());
				TxMgr.insert(con, cancel);

				Long invoiceId = SequenceMgr.nextSeq(InvoiceSheet.class);
				sheet.setInvoiceId(invoiceId);
				TxMgr.insert(con, sheet);

				for (InvoiceDetail detail : details) {
					detail.setInvoiceId(invoiceId);
					Long detailId = SequenceMgr.nextSeq(InvoiceDetail.class);
					detail.setDetailId(detailId);
				}
				TxMgr.insertList(con, details);
			}
		});
		return true;
	}

	@Deprecated
	public static boolean modify(List<InvoiceDetail> details) throws Exception {
		TxMgr.updateList(details);
		return true;
	}

	@Deprecated
	public static boolean modify(PackingList packingList) throws Exception {
		Long packingId = SequenceMgr.nextSeq(PackingList.class);
		packingList.setPackingListId(packingId);
		TxMgr.update(packingList);
		return true;
	}

	@Deprecated
	public static boolean modifyPackingListDetails(List<PackingListDetail> details) throws Exception {
		for (PackingListDetail detail : details) {
			detail.setDetailId(SequenceMgr.nextSeq(PackingListDetail.class));
		}
		TxMgr.insertList(details);
		return true;
	}

	/*
	 * Invoice書を承認
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void approve(Long invoiceId) throws Exception {
		if (SessionMgr.isLogined() == false)
			return;

		Long userId = SessionMgr.getLoginUserId();

		InvoiceApprove approve = new InvoiceApprove();
		approve.setInvoiceId(invoiceId);
		/*
		 * セッションからuserIdを取得
		 */
		approve.setUserId(userId);// getEstimationIdByInvoice

		InvoiceSheet sheet = fetchInvoiceSheet(invoiceId);
		if (sheet != null)
			approve.setEstimationId(EstmationSheetMgr.getEstimationIdByInvoice(sheet.getInvoiceCd()));

		Criteria<InvoiceApprove> criteriaApprove = new Criteria(InvoiceApprove.class);
		criteriaApprove.and(Restrictions.eq(InvoiceApprove.InvoiceId, invoiceId));
		criteriaApprove.and(Restrictions.eq(InvoiceApprove.EstimationId, approve.getEstimationId()));
		InvoiceApprove exists = criteriaApprove.get();
		if (exists == null)
			TxMgr.insert(approve);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean isApproved(Long invoiceId) throws Exception {
		Criteria<InvoiceApprove> criteria = new Criteria(InvoiceApprove.class);
		criteria.and(Restrictions.eq(InvoiceApprove.InvoiceId, invoiceId));
		return criteria.get() != null;
	}

	/*
	 * Invoice書を取り消し
	 */
	public static boolean cancel(Long invoiceId) throws Exception {
		InvoiceCancel cancel = new InvoiceCancel();
		cancel.setInvoiceId(invoiceId);
		TxMgr.insert(cancel);

		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean update(Connection con, Long oldInvoiceId, Long newInvoiceId) throws Exception {
		List<InvoiceDetail> details = fetchInvoiceDetails(oldInvoiceId);
		for (InvoiceDetail detail : details) {
			detail.setInvoiceId(newInvoiceId);
			/*
			 * 自動採番
			 */
			Long detailId = SequenceMgr.nextSeq(InvoiceDetail.class);
			detail.setDetailId(detailId);
		}

		/*
		 * DBにInsert
		 */
		TxMgr.insertList(con, details);
		Criteria<PackingList> criteria = new Criteria(PackingList.class);
		criteria.and(Restrictions.eq(PackingList.InvoiceId, oldInvoiceId));

		List<PackingList> packings = criteria.list();
		for (PackingList packing : packings) {
			packing.setInvoiceId(newInvoiceId);
			/*
			 * 自動採番
			 */
			Long detailId = SequenceMgr.nextSeq(PackingList.class);
			packing.setPackingListId(detailId);
		}

		/*
		 * DBにInsert
		 */
		TxMgr.insertList(con, packings);

		return true;
	}

}
