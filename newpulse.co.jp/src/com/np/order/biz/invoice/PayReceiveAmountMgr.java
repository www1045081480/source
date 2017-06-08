package com.np.order.biz.invoice;

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
import com.np.base.dml.Sortter;
import com.np.base.models.JoinedModels;
import com.np.base.orm.Criteria;
import com.np.base.orm.Restrictions;
import com.np.base.utils.UDate;
import com.np.base.utils.UString;
import com.np.order.Money;
import com.np.order.action.DateFormatter;
import com.np.order.action.SessionMgr;
import com.np.order.biz.IllegalPriorityEception;
import com.np.order.biz.mast.SupplierMgr;
import com.np.order.models.Credit;
import com.np.order.models.EstimationConfirm;
import com.np.order.models.EstimationInvoice;
import com.np.order.models.EstmationCancel;
import com.np.order.models.EstmationSheet;
import com.np.order.models.InvoiceCancel;
import com.np.order.models.InvoiceDetail;
import com.np.order.models.InvoiceSheet;
import com.np.order.models.OrderCancel;
import com.np.order.models.OrderDetail;
import com.np.order.models.OrderInvoice;
import com.np.order.models.OrderRequire;
import com.np.order.models.OrderSheet;
import com.np.order.models.PayMoney;
import com.np.order.models.PayMoneyDetail;
import com.np.order.models.PayableBalance;
import com.np.order.models.ReceivableBalance;
import com.np.order.models.Supplier;
import com.np.order.objects.PayableAmount;
import com.np.order.objects.PayableAmountFilter;
import com.np.order.objects.ReceivableAmount;
import com.np.order.objects.ReceivableAmountFilter;

/*
 * 发货一览表/買掛金一覧
 */
public class PayReceiveAmountMgr {
	private static Log logger = LogFactory.getLog(PayReceiveAmountMgr.class);

	/*
	 * 検索：发货一览表
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<ReceivableAmount> getReceivableAmounts(ReceivableAmountFilter filter) throws Exception {
		List<ReceivableAmount> receivableAmounts = new ArrayList<ReceivableAmount>();
		if (filter.getCustomerId() == null)
			return receivableAmounts;

		/*
		 * TODO: ReceivableBalanceの残高は零以上の月から
		 */
		String balanceYearMonth = null;
		ReceivableBalance lastBalanced = AlarmMgr.getLasstBalancedReceivableAmounts(filter.getCustomerId());
		if (lastBalanced != null) {
			balanceYearMonth = lastBalanced.getBalanceMonth();
		}
		Criteria<InvoiceSheet> criteria = new Criteria(InvoiceSheet.class);
		criteria.and(Restrictions.eq(InvoiceSheet.CustomerId, filter.getCustomerId()));
		criteria.and(Restrictions.eq(InvoiceSheet.Currency, filter.getCurrency()));

		Criteria<InvoiceCancel> cancel = new Criteria(InvoiceCancel.class);
		cancel.addSelectedColumn(InvoiceCancel.InvoiceId);
		criteria.and(Restrictions.notIn(InvoiceSheet.InvoiceId, cancel));

		if (balanceYearMonth != null)
			criteria.and(Restrictions.gt(InvoiceSheet.IssueDate, balanceYearMonth + "31"));

		String toDate = filter.getBalanceYear() + filter.getBalanceMonth() + "31";
		if (toDate.contains("null") == false)
			criteria.and(Restrictions.le(InvoiceSheet.IssueDate, toDate));

		List<JoinedModels> invoices = Query.queryWithDependencies(criteria, InvoiceDetail.class);

		/*
		 * TODO: SORT
		 */

		List<String> invoiceCds = PojoCollections.getKeyList(invoices, InvoiceSheet.InvoiceCd);
		Map<String, String> customerOrderNos = getCustomerOrderNos(invoiceCds);

		List<Long> detailIds = PojoCollections.getKeyList(invoices, InvoiceDetail.DetailId);
		Criteria<Credit> criteriaCredit = new Criteria(Credit.class);
		criteriaCredit.and(Restrictions.in(Credit.DetailId, detailIds));
		List<Credit> credits = criteriaCredit.list();

		/*
		 * TODO:残高
		 */
		Money balance = new Money(filter.getCurrency(), 0);
		String yearMonth = null;
		Sortter.sort(invoices, InvoiceSheet.IssueDate);
		for (JoinedModels invoice : invoices) {
			String deliveryDate = (String) invoice.getValue(InvoiceSheet.IssueDate);
			String deliveryYearMonth = deliveryDate.substring(0, 7);
			/*
			 * 月残高
			 */
			if (deliveryYearMonth.equals(yearMonth) == false) {
				ReceivableAmount receivableAmount = new ReceivableAmount();
				receivableAmount.setEndingBalance(balance.copy());
				receivableAmount.setDeliveryDate("");
				receivableAmounts.add(receivableAmount);
				yearMonth = deliveryYearMonth;
			}
			ReceivableAmount receivableAmount = new ReceivableAmount();

			/*
			 * Invoice詳細ID
			 */
			receivableAmount.setDetailId((Long) invoice.getValue(InvoiceDetail.DetailId));
			receivableAmount.setCurrency((String) invoice.getValue(InvoiceSheet.Currency));

			/*
			 * 发货日期
			 */
			receivableAmount.setDeliveryDate(deliveryDate);

			{
				String invoiceCd = invoice.getValue(InvoiceSheet.InvoiceCd);
				receivableAmount.setOrderNo(customerOrderNos.get(invoiceCd));

			}

			/*
			 * TODO:新安发票号
			 */
			receivableAmount.setXinanOrderNo((String) invoice.getValue(InvoiceSheet.InvoiceCd));
			/*
			 * TODO:内容
			 */
			receivableAmount.setContent(
					(String) invoice.getValue(InvoiceDetail.Name) + invoice.getValue(InvoiceDetail.PartsCd));

			String currency = (String) invoice.getValue(InvoiceSheet.Currency);
			balance.setCurrency(currency);

			/*
			 * TODO:新增销售
			 */
			Money earningAmount = new Money(currency, 0);
			if (invoice.getValue(InvoiceDetail.UnitPrice) != null)
				earningAmount.add((Money) invoice.getValue(InvoiceDetail.UnitPrice));
			if (invoice.getValue(InvoiceDetail.Quantity) != null)
				earningAmount.multiply(((Integer) invoice.getValue(InvoiceDetail.Quantity)).intValue());
			receivableAmount.setEarningAmount(earningAmount);

			/*
			 * 己収回款金额
			 */
			String thisMonthPaymentDate = UDate.getYearYYYYMM() + "01";
			Money receivedAmount = new Money(currency, 0);
			Credit currentMonthPay = null;
			for (Credit credit : credits) {
				if (credit.getDetailId().equals(receivableAmount.getDetailId()) == false)
					continue;
				receivedAmount.add(credit.getCreditAmount());
				receivableAmount.setTradingNo(credit.getTradingNo());
				receivableAmount.setNote(credit.getNote());
				if (thisMonthPaymentDate.equals(credit.getPaymentDate()))
					currentMonthPay = credit;
			}
			receivableAmount.setReceivedAmount(receivedAmount);
			/*
			 * 回款金额
			 */
			if (currentMonthPay != null)
				receivableAmount.setReceiveAmount(currentMonthPay.getCreditAmount());
			else
				receivableAmount.setReceiveAmount(null);

			/*
			 * TODO:期末残高
			 */
			balance.add(earningAmount);
			balance.minus(receivedAmount);
			receivableAmount.setEndingBalance(balance.copy());

			receivableAmounts.add(receivableAmount);
		}

		logger.debug("发货一览: " + receivableAmounts.size());
		return receivableAmounts;
	}

	/*
	 * 検索：買掛金一覧
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<PayableAmount> getPayableAmounts(PayableAmountFilter filter) throws Exception {
		List<PayableAmount> payableAmounts = new ArrayList<PayableAmount>();
		if (filter.getSupplierId() == null)
			return payableAmounts;

		/*
		 * TODO: PayableBalanceの残高は零以上の月から
		 */
		String balanceYearMonth = null;
		PayableBalance lastBalanced = AlarmMgr.getLastBalancedPayableAmounts(filter.getSupplierId());
		if (lastBalanced != null) {
			balanceYearMonth = lastBalanced.getBalanceMonth();
		}

		Criteria<OrderSheet> criteria = new Criteria(OrderSheet.class);
		criteria.and(Restrictions.eq(OrderSheet.SupplierId, filter.getSupplierId()));
		criteria.and(Restrictions.eq(OrderSheet.Currency, filter.getCurrency()));

		Criteria<OrderCancel> cancel = new Criteria(OrderCancel.class);
		cancel.addSelectedColumn(OrderCancel.OrderId);
		criteria.and(Restrictions.notIn(OrderSheet.OrderId, cancel));

		if (balanceYearMonth != null)
			criteria.and(Restrictions.gt(InvoiceSheet.IssueDate, balanceYearMonth + "31"));

		String toDate = filter.getBalanceYear() + filter.getBalanceMonth() + "31";
		if (toDate.contains("null") == false)
			criteria.and(Restrictions.le(InvoiceSheet.IssueDate, toDate));

		List<JoinedModels> orders = Query.queryWithDependencies(criteria, OrderDetail.class);
		List<String> orderCds = PojoCollections.getKeyList(orders, OrderSheet.OrderCd);
		@SuppressWarnings("unused")
		List<Long> orderDetailIds = PojoCollections.getKeyList(orders, OrderDetail.DetailId);
		{
			Criteria<OrderInvoice> criteriaOrderInvoice = new Criteria(OrderInvoice.class);
			criteriaOrderInvoice.and(Restrictions.in(OrderInvoice.OrderCd, orderCds));

			List<OrderInvoice> cdMap = criteriaOrderInvoice.list();
			@SuppressWarnings("unused")
			List<String> invoiceCds = PojoCollections.getKeyList(cdMap, OrderInvoice.OrderCd);
			for (JoinedModels order : orders) {
				String currency = order.getValue(OrderSheet.Currency);
				((Money) order.getValue(OrderDetail.Amount)).setCurrency(currency);
				((Money) order.getValue(OrderDetail.UnitPrice)).setCurrency(currency);

				order.appendName(OrderInvoice.InvoiceCd);
				String orderCd = order.getValue(OrderSheet.OrderCd);
				for (OrderInvoice code : cdMap) {
					if (code.getOrderCd().equals(orderCd)) {
						order.setValue(OrderInvoice.InvoiceCd, code.getInvoiceCd());
						break;
					}
				}
			}

		}

		Criteria<InvoiceSheet> criteriaInvoice = new Criteria(InvoiceSheet.class);

		// INVOICE_CD
		Criteria<OrderInvoice> orderInvoice = new Criteria(OrderInvoice.class);
		orderInvoice.addSelectedColumn(OrderInvoice.InvoiceCd);
		orderInvoice.and(Restrictions.in(OrderInvoice.OrderCd, orderCds));

		criteriaInvoice.and(Restrictions.in(InvoiceSheet.InvoiceCd, orderInvoice));

		// 取消したINVOICE
		Criteria<InvoiceCancel> cancels = new Criteria(InvoiceCancel.class);
		cancels.addSelectedColumn(InvoiceCancel.InvoiceId);
		criteriaInvoice.and(Restrictions.notIn(InvoiceSheet.InvoiceId, cancels));

		List<JoinedModels> invoices = Query.queryWithDependencies(criteriaInvoice, InvoiceDetail.class);

		/*
		 * TODO: SORT
		 */

		/*
		 * TODO:Invoice
		 */

		Criteria<PayMoney> criteriaPayMoney = new Criteria(PayMoney.class);
		criteriaPayMoney.and(Restrictions.eq(PayMoney.SupplierId, filter.getSupplierId()));
		criteriaPayMoney.and(Restrictions.eq(OrderSheet.Currency, filter.getCurrency()));
		// criteriaPayMoney.and(Restrictions.in(PayMoney.OrderId,
		// orderDetailIds));

		if (toDate.contains("null") == false)
			criteriaPayMoney.and(Restrictions.le(PayMoney.Date, toDate));

		List<PayMoney> payMoneys = criteriaPayMoney.list();

		/*
		 * TODO:残高
		 */
		final Money balance = new Money(filter.getCurrency(), 0);
		/*
		 * TODO:順番
		 * 
		 * PayMoney：月支払 (payMoneys)
		 * 
		 * => 支払詳細 (orders)
		 */
		Sortter.sort(payMoneys, PayMoney.Date);

		for (PayMoney payMoney : payMoneys) {
			payMoney.setDate(DateFormatter.toView(payMoney.getDate()));
			PayableAmount payableAmount = new PayableAmount();

			payableAmount.setModelCd(payMoney.getDate() + "月分支払");
			/*
			 * 支払日
			 */
			payableAmount.setPayDate(payMoney.getDate());

			payableAmount.setExciseAmount(null);
			payableAmount.setPurchaseAmount(null);
			payableAmount.setTotalAmount(null);

			/*
			 * 支払金額
			 */
			payableAmount.setPayAmount(payMoney.getAmount());

			/*
			 * TODO:残高
			 */
			balance.setCurrency(payableAmount.getPayAmount().getCurrency());
			balance.minus(payableAmount.getPayAmount());

			// payableAmount.setBalance(balance);

			// payableAmounts.add(payableAmount);
		}

		Supplier supplier = SupplierMgr.searchSupplier(filter.getSupplierId());
		String country = (supplier == null) ? null : supplier.getCountry();
		final boolean isJapanSupplier = (country != null
				&& (country.equalsIgnoreCase("JP") || country.equalsIgnoreCase("Japanses")
						|| country.equalsIgnoreCase("Japan") || country.contains("日本")));

		/*
		 * 見積書と注文をマッチ
		 */
		String[] joinKeys = { OrderInvoice.InvoiceCd, OrderDetail.ItemId };
		payableAmounts.addAll(Matcher.innerJoin(orders, invoices, joinKeys,
				new IModelFactory<PayableAmount, JoinedModels, JoinedModels>() {
					@Override
					public PayableAmount create(JoinedModels order, JoinedModels invoice) {
						PayableAmount payableAmount = new PayableAmount();

						/*
						 * 注文詳細ID
						 */
						payableAmount.setDetailId((Long) invoice.getValue(InvoiceDetail.DetailId));

						payableAmount.setSupplierId((Long) order.getValue(OrderSheet.SupplierId));
						payableAmount.setModelCd((String) order.getValue(OrderDetail.Name));
						payableAmount.setOrderNo((String) order.getValue(OrderSheet.OrderCd));
						payableAmount.setCurrency((String) order.getValue(OrderSheet.Currency));
						/*
						 * 支払日
						 */
						payableAmount.setPayDate((String) invoice.getValue(InvoiceSheet.IssueDate));

						/*
						 * 消費税
						 */
						/*
						 * TODO:
						 */
						Criteria<OrderRequire> criteriaOrderRequire = new Criteria(OrderRequire.class);
						criteriaOrderRequire.and(Restrictions.eq(OrderRequire.DetailId, payableAmount.getDetailId()));
						List<OrderRequire> orderRequires = criteriaOrderRequire.list();

						String currency = (String) order.getValue(OrderSheet.Currency);
						Money excise = new Money(currency, 0);
						Money amount = new Money(currency, 0);
						if (order.getValue(OrderDetail.UnitPrice) != null)
							amount.add((Money) order.getValue(OrderDetail.UnitPrice));
						/*
						 * TODO:Invoice's Quantity
						 */
						amount.multiply((Integer) invoice.getValue(OrderDetail.Quantity));
						// if (filter.getCurrency().equalsIgnoreCase("JP"))
						String country = (supplier == null) ? null : supplier.getCountry();
						if (isJapanSupplier) {
							excise.add((int) (amount.intValue() * 0.08));
						}

						for (OrderRequire orderRequire : orderRequires) {
							excise.add(orderRequire.getExciseAmount());
							amount.add(orderRequire.getAdjustAmount());
							amount.add(orderRequire.getShippingCost());
						}
						payableAmount.setExciseAmount(excise);
						payableAmount.setPurchaseAmount(amount);

						/*
						 * 小計
						 */
						Money totalAmount = new Money(currency, 0);
						totalAmount.add(excise);
						totalAmount.add(amount);
						payableAmount.setTotalAmount(totalAmount);

						/*
						 * 支払金額
						 */
						payableAmount.setPayAmount(null);

						/*
						 * TODO:残高
						 */
						balance.add(payableAmount.getTotalAmount());
						balance.minus(payableAmount.getPayAmount());
						payableAmount.setBalance(balance);
						{
							Criteria<PayMoneyDetail> criteriaPayMoneyDetail = new Criteria(PayMoneyDetail.class);
							criteriaPayMoneyDetail
									.and(Restrictions.eq(PayMoneyDetail.InvoiceDetailId, payableAmount.getDetailId()));
							PayMoneyDetail payMoneyDetail = criteriaPayMoneyDetail.get();
							if (payMoneyDetail != null) {
								payableAmount.setPayAmount(payMoneyDetail.getAmount());
								payableAmount.setTradingNo(payMoneyDetail.getTradingNo());
								payableAmount.setNote(payMoneyDetail.getNote());
							}
						}

						return payableAmount;
					}
				}));

		List<PayableAmount> result = new ArrayList<PayableAmount>();

		String yearMonth = null;

		Sortter.sort(payableAmounts, "PayDate");
		for (int i = 0; i < payableAmounts.size(); i++) {
			PayableAmount payAount = payableAmounts.get(i);
			String payDate = payAount.getPayDate();
			String payYearMonth = payDate.substring(0, 7);

			/*
			 * 月残高
			 */
			if (payYearMonth.equals(yearMonth) == false) {
				PayableAmount monthAmount = new PayableAmount();
				monthAmount.setCurrency(payAount.getCurrency());

				PayMoney payedMoney = null;
				for (PayMoney payMoney : payMoneys) {
					if (payMoney.getDate().startsWith(payYearMonth) == false)
						continue;
					payedMoney = payMoney;
					break;
				}

				if (payedMoney == null) {
					monthAmount.setPayDate(UDate.getLastDay(payYearMonth));
					int month = Integer.parseInt(payYearMonth.substring(5, 7));
					month--;
					if (month <= 0)
						month = 12;
					monthAmount.setModelCd(month + "月分支払");
				} else {
					monthAmount.setPayDate(payedMoney.getDate());
					monthAmount.setModelCd(payedMoney.getName());
					monthAmount.setPayAmount(payedMoney.getAmount());

					/*
					 * 残高
					 */
					for (int j = i; j < payableAmounts.size(); j++) {
						payableAmounts.get(j).getBalance().minus(payedMoney.getAmount());
					}
				}
				result.add(monthAmount);
				yearMonth = payYearMonth;
			}

			result.add(payAount);
		}

		logger.debug("買掛金一覧: " + result.size());
		return result;
	}

	/*
	 * 登録：買掛金一覧
	 * 
	 * 対照テーブル：PayMoney, PayableBalance
	 */
	public static void registerPayableAmounts(List<PayableAmount> payableAmounts) throws Exception {
		if (SessionMgr.isFinacer() == false)
			throw new IllegalPriorityEception();

		TxMgr.update(new ITxProc() {

			@Override
			public void process(Connection con) throws Exception {
				/*
				 * PayMoney登録
				 */
				Long supplierId = null;
				for (PayableAmount payableAmount : payableAmounts) {
					if (payableAmount.getSupplierId() != null) {
						supplierId = payableAmount.getSupplierId();
						break;
					}
				}

				for (PayableAmount payableAmount : payableAmounts) {
					if (payableAmount.getSupplierId() == null)
						payableAmount.setSupplierId(supplierId);
					if (payableAmount.getDetailId() == null)
						registerPayableAmount(con, payableAmount);
					else
						registerPayableDetail(con, payableAmount);
				}

				/*
				 * PayableBalance登録
				 */
			}

		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void registerPayableAmount(Connection con, PayableAmount payableAmount) throws Exception {
		if (payableAmount.getPayAmount() == null || payableAmount.getPayAmount().intValue() == 0)
			return;

		PayMoney payMoney = new PayMoney();

		String yyyymm = DateFormatter.toModel(payableAmount.getPayDate()).substring(0, 6);

		Criteria<PayMoney> criteria = new Criteria(PayMoney.class);
		criteria.and(Restrictions.eq(PayMoney.SupplierId, payableAmount.getSupplierId()));
		criteria.and(Restrictions.between(PayMoney.Date, yyyymm + "00", yyyymm + "31"));
		payMoney = criteria.get();
		boolean update = payMoney != null;

		if (update == false) {
			payMoney = new PayMoney();
			payMoney.setPayId(SequenceMgr.nextSeq(PayMoney.class));
			payMoney.setSupplierId(payableAmount.getSupplierId());
		}

		/*
		 * TODO:支払日
		 */
		payMoney.setDate(payableAmount.getPayDate());

		payMoney.setCurrency(payableAmount.getCurrency());

		payMoney.setOrderId(payableAmount.getDetailId());
		payMoney.setName(payableAmount.getModelCd());

		/*
		 * 支払金額（月単位）
		 */
		/*
		 * 取引No
		 */
		/*
		 * 备注
		 */
		payMoney.setAmount(payableAmount.getPayAmount());
		payMoney.setTradingNo(payableAmount.getTradingNo());
		payMoney.setNote(payableAmount.getNote());

		if (update == false)
			TxMgr.insert(con, payMoney);
		else
			TxMgr.update(con, payMoney);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void registerPayableDetail(Connection con, PayableAmount payableAmount) throws Exception {

		if (payableAmount.getPayAmount() == null || payableAmount.getPayAmount().intValue() == 0) {
			// return;
			if (UString.isEmpty(payableAmount.getNote()) && UString.isEmpty(payableAmount.getTradingNo()))
				return;
		}
		PayMoneyDetail payMoney;

		Criteria<PayMoneyDetail> criteria = new Criteria(PayMoneyDetail.class);
		criteria.and(Restrictions.eq(PayMoneyDetail.InvoiceDetailId, payableAmount.getDetailId()));
		payMoney = criteria.get();
		boolean update = payMoney != null;

		if (update == false) {
			payMoney = new PayMoneyDetail();
			payMoney.setPayDetailId(SequenceMgr.nextSeq(PayMoneyDetail.class));
			payMoney.setInvoiceDetailId(payableAmount.getDetailId());
		}
		payMoney.setCurrency(payableAmount.getCurrency());
		payMoney.setAmount(payableAmount.getPayAmount());

		/*
		 * 取引No
		 */
		/*
		 * 备注
		 */

		payMoney.setTradingNo(payableAmount.getTradingNo());
		payMoney.setNote(payableAmount.getNote());
		if (update == false)
			TxMgr.insert(con, payMoney);
		else
			TxMgr.update(con, payMoney);

	}

	/*
	 * 登録：发货一览表
	 * 
	 * 対照テーブル：Credit, ReceivableBalance
	 */
	public static void registerReceivableAmounts(List<ReceivableAmount> receivableAmounts) throws Exception {
		if (SessionMgr.isFinacer() == false)
			throw new IllegalPriorityEception();

		TxMgr.update(new ITxProc() {

			@Override
			public void process(Connection con) throws Exception {
				/*
				 * Credit登録
				 */
				for (ReceivableAmount receivableAmount : receivableAmounts) {
					registerReceivableAmount(con, receivableAmount);
				}
			}

		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void registerReceivableAmount(Connection con, ReceivableAmount receivableAmount) throws Exception {

		if (receivableAmount.getReceiveAmount() == null || receivableAmount.getReceiveAmount().intValue() == 0)
			return;

		/*
		 * TODO:支払日
		 */
		String paymentDate = UDate.getYearYYYYMM() + "01";

		Credit credit;

		Criteria<Credit> criteria = new Criteria(Credit.class);
		criteria.and(Restrictions.eq(Credit.DetailId, receivableAmount.getDetailId()));
		criteria.and(Restrictions.eq(Credit.PaymentDate, paymentDate));
		credit = criteria.get();
		boolean update = credit != null;
		if (update == false) {
			credit = new Credit();
			credit.setCreditId(SequenceMgr.nextSeq(Credit.class));
			credit.setDetailId(receivableAmount.getDetailId());
			credit.setPaymentDate(paymentDate);
		}

		/*
		 * 回款金额(订单号単位)
		 */
		/*
		 * 取引No
		 */
		/*
		 * 备注
		 */
		credit.setCreditAmount(receivableAmount.getReceiveAmount());
		credit.setNote(receivableAmount.getNote());
		credit.setTradingNo(receivableAmount.getTradingNo());

		if (update)
			TxMgr.update(con, credit);
		else
			TxMgr.insert(con, credit);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map<String, String> getCustomerOrderNos(List<String> invoiceCds) {
		Map<String, String> result = new HashMap<String, String>();

		Criteria<EstimationInvoice> criteriaEstimationInvoice = new Criteria(EstimationInvoice.class);
		// criteriaEstimationInvoice.addSelectedColumn(EstimationInvoice.EstimationCd);
		criteriaEstimationInvoice.and(Restrictions.in(EstimationInvoice.InvoiceCd, invoiceCds));
		List<EstimationInvoice> estimationInvoices = criteriaEstimationInvoice.list();

		List<String> estimationCds = PojoCollections.getKeyList(estimationInvoices, EstimationInvoice.EstimationCd);

		logger.debug("invoiceCds : " + invoiceCds);
		logger.debug("estimationCds : " + estimationCds);

		Criteria<EstmationSheet> criteriaEstimation = new Criteria(EstmationSheet.class);
		// criteriaEstimation.addSelectedColumn(EstimationConfirm.EstimationId);

		Criteria<EstmationCancel> cancel = new Criteria(EstmationCancel.class);
		cancel.addSelectedColumn(EstmationCancel.EstimationId);
		criteriaEstimation.and(Restrictions.notIn(EstmationSheet.EstimationId, cancel));
		criteriaEstimation.and(Restrictions.in(EstmationSheet.EstimationCd, estimationCds));

		List<EstmationSheet> estimationSheets = criteriaEstimation.list();
		List<Long> estimationIds = PojoCollections.getKeyList(estimationSheets, EstmationSheet.EstimationId);

		logger.debug("estimationIds : " + estimationIds);

		Criteria<EstimationConfirm> estimationConfirm = new Criteria(EstimationConfirm.class);
		estimationConfirm.and(Restrictions.in(EstimationConfirm.EstimationId, estimationIds));
		List<EstimationConfirm> confirms = estimationConfirm.list();

		logger.debug("confirms : " + confirms);
		for (EstimationConfirm confirm : confirms) {
			for (EstmationSheet sheet : estimationSheets) {
				if (sheet.getEstimationId().equals(confirm.getEstimationId()) == false)
					continue;

				for (EstimationInvoice estimationInvoice : estimationInvoices) {
					if (estimationInvoice.getEstimationCd().equals(sheet.getEstimationCd()) == false)
						continue;

					result.put(estimationInvoice.getInvoiceCd(), confirm.getCustomerOrderNo());
					break;
				}
				break;
			}
		}

		return result;
	}

}
