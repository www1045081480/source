package com.np.order.biz.invoice;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.db.Query;
import com.np.base.db.SequenceMgr;
import com.np.base.db.TxMgr;
import com.np.base.dml.Groupper;
import com.np.base.dml.IControlBreakProcedure;
import com.np.base.dml.IModelFactory;
import com.np.base.dml.Matcher;
import com.np.base.dml.PojoCollections;
import com.np.base.models.JoinedModels;
import com.np.base.orm.Criteria;
import com.np.base.orm.Restrictions;
import com.np.base.utils.UDate;
import com.np.order.Money;
import com.np.order.action.SessionMgr;
import com.np.order.biz.mast.CurrencyMgr;
import com.np.order.biz.mast.ItemMgr;
import com.np.order.models.Credit;
import com.np.order.models.EstimationAccept;
import com.np.order.models.EstimationInvoice;
import com.np.order.models.EstiomationDetail;
import com.np.order.models.EstmationCancel;
import com.np.order.models.EstmationSheet;
import com.np.order.models.InvoiceCancel;
import com.np.order.models.InvoiceDetail;
import com.np.order.models.InvoiceSheet;
import com.np.order.models.Item;
import com.np.order.models.OrderCancel;
import com.np.order.models.OrderDetail;
import com.np.order.models.OrderInvoice;
import com.np.order.models.OrderRequire;
import com.np.order.models.OrderSheet;
import com.np.order.models.PayMoneyDetail;
import com.np.order.models.PayableBalance;
import com.np.order.models.ReceivableBalance;
import com.np.order.objects.ArrivalResult;
import com.np.order.objects.ArrivalResultFilter;
import com.np.order.objects.ShipResult;
import com.np.order.objects.ShipResultFilter;

/*
 * 残高一覧
 * 
 *  出荷済み一覧
 *  
 *  入荷済み一覧
 * 
 */
public class FinaceMgr {
	private static Log logger = LogFactory.getLog(FinaceMgr.class);

	/*
	 * 出荷済み一覧(出荷済（売掛金）統計一覧)
	 * 
	 * 検索条件： 平成27年度5月
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<ShipResult> getShipResult(ShipResultFilter filter) throws Exception {
		List<ShipResult> result = new ArrayList<ShipResult>();
		if (SessionMgr.isLogined() == false)
			return result;

		/*
		 * 注文リスト
		 */
		Criteria<InvoiceSheet> criteria = new Criteria(InvoiceSheet.class);
		String fromDate = filter.getShipYear() + filter.getShipMonth() + "01";
		String toDate = filter.getShipYear() + filter.getShipMonth() + "31";
		if (toDate.contains("null") == false)
			criteria.and(Restrictions.between(InvoiceSheet.IssueDate, fromDate, toDate));

		Criteria<InvoiceCancel> cancel = new Criteria(InvoiceCancel.class);
		cancel.addSelectedColumn(InvoiceCancel.InvoiceId);
		criteria.and(Restrictions.notIn(InvoiceSheet.InvoiceId, cancel));

		List<JoinedModels> invoices = Query.queryWithDependencies(criteria, InvoiceDetail.class);

		@SuppressWarnings("unused")
		List<Long> invoiceIds = PojoCollections.getKeyList(invoices, InvoiceSheet.InvoiceId);
		List<String> invoiceCds = PojoCollections.getKeyList(invoices, InvoiceSheet.InvoiceCd);

		Criteria<EstimationInvoice> criteriaEstimationInvoice = new Criteria(EstimationInvoice.class);
		criteriaEstimationInvoice.and(Restrictions.in(EstimationInvoice.InvoiceCd, invoiceCds));
		// criteriaEstimationInvoice.addSelectedColumn(EstimationInvoice.EstimationCd);
		List<EstimationInvoice> codeMap = criteriaEstimationInvoice.list();

		for (JoinedModels invoice : invoices) {
			invoice.appendName(EstimationInvoice.EstimationCd);
			String invoiceCd = invoice.getValue(EstimationInvoice.InvoiceCd);
			for (EstimationInvoice code : codeMap) {
				if (code.getInvoiceCd().equals(invoiceCd)) {
					invoice.setValue(EstimationInvoice.EstimationCd, code.getEstimationCd());
					break;
				}
			}
		}

		List<String> estimationCds = PojoCollections.getKeyList(codeMap, EstimationInvoice.EstimationCd);

		Criteria<EstmationCancel> criteriaEstimationCancel = new Criteria(EstmationCancel.class);
		criteriaEstimationCancel.addSelectedColumn(EstmationCancel.EstimationId);

		Criteria<EstmationSheet> criteriaEstimation = new Criteria(EstmationSheet.class);
		criteriaEstimation.and(Restrictions.notIn(EstmationSheet.EstimationId, criteriaEstimationCancel));
		criteriaEstimation.and(Restrictions.in(EstmationSheet.EstimationCd, estimationCds));

		List<JoinedModels> estimations = Query.queryWithDependencies(criteriaEstimation, EstiomationDetail.class);
		List<Long> estimationDetailIds = PojoCollections.getKeyList(estimations, EstiomationDetail.DetailId);

		/*
		 * 出荷済み
		 */
		Criteria<Credit> criteriaCredit = new Criteria(Credit.class);
		criteriaCredit.and(Restrictions.in(Credit.DetailId, estimationDetailIds));
		List<Credit> receivedMoneys = criteriaCredit.list();

		String limitDay = UDate.getDateByAdjustMonth(-4);

		String[] joinKeys = { EstmationSheet.EstimationCd, InvoiceDetail.ItemId };
		result = Matcher.innerJoin(invoices, estimations, joinKeys,
				new IModelFactory<ShipResult, JoinedModels, JoinedModels>() {
					@Override
					public ShipResult create(JoinedModels tra, JoinedModels mas) {
						ShipResult result = new ShipResult();
						result.setEstimationDetailId((Long) mas.getValue(EstiomationDetail.DetailId));
						result.setInvoiceDetailId((Long) tra.getValue(InvoiceDetail.DetailId));

						result.setCurrency((String) tra.getValue(InvoiceSheet.Currency));
						result.setCustomerId((Long) mas.getValue(EstmationSheet.CustomerId));
						result.setCustomerName((String) tra.getValue(InvoiceSheet.CustomerName));

						/*
						 * 商品分類
						 */
						Item item = ItemMgr.get((Long) tra.getValue(InvoiceDetail.ItemId));
						if (item != null && item.getCategoryType() != null)
							result.setCategoryType(item.getCategoryType());

						result.setCurrency((String) tra.getValue(InvoiceSheet.Currency));
						/*
						 * 今月販売金額
						 */
						Money amount = (Money) tra.getValue(InvoiceDetail.Amount);

						result.setSellAmountOfThisMonth(amount);
						result.getSellAmountOfThisMonth().setCurrency(result.getCurrency());

						/*
						 * 入金金額
						 */
						for (Credit receivedMoney : receivedMoneys) {
							if (receivedMoney.getDetailId().equals(result.getEstimationDetailId())) {
								result.setDate(receivedMoney.getPaymentDate());
								result.setDepositAmount(receivedMoney.getCreditAmount());
							}
						}
						result.getDepositAmount().setCurrency(result.getCurrency());

						/*
						 * TODO:SUM
						 */
						Money balance = result.getSellAmountOfThisMonth().copy();
						balance.minus(result.getDepositAmount());
						if (CurrencyMgr.isChinese(result.getCurrency()))
							result.setBalanceOfChinese(balance);
						else if (CurrencyMgr.isDoller(result.getCurrency()))
							result.setBalanceOfDollar(balance);
						else
							result.setBalance(balance);

						/*
						 * TODO:
						 * 
						 * 四ヶ月超未回収（見積番号/金額）
						 */
						String issueDate = tra.getValue(InvoiceSheet.IssueDate);
						if (issueDate.compareTo(limitDay) <= 0)
							result.setEstimationCdAmount(mas.getValue(EstmationSheet.EstimationCd) + "/"
									+ result.getSellAmountOfThisMonth());

						return result;
					}
				});

		/*
		 * TODO:
		 * 
		 * 残高
		 * 
		 * 集計：商品種別
		 * 
		 * 合計: 得意先、日本円・ドル、商品種別
		 */
		List<List<ShipResult>> customersList = Groupper.group(result, new String[] { InvoiceSheet.CustomerId });
		result.clear();
		for (List<ShipResult> customers : customersList) {
			/*
			 * 日本円・ドル
			 */
			List<ShipResult> japanCurrencyList = new ArrayList<ShipResult>();
			List<ShipResult> dollerCurrencyList = new ArrayList<ShipResult>();
			List<ShipResult> chinaCurrencyList = new ArrayList<ShipResult>();
			for (ShipResult ship : customers) {
				if (CurrencyMgr.isDoller(ship.getCurrency()))
					dollerCurrencyList.add(ship);
				else if (CurrencyMgr.isChinese(ship.getCurrency()))
					chinaCurrencyList.add(ship);
				else
					japanCurrencyList.add(ship);
			}

			/*
			 * 合計: 商品種別
			 */
			String[] sumKeys = { Item.CategoryType };
			SumShipByCategory cbCategoryByJapanese = new SumShipByCategory();
			Groupper.controlBreak(japanCurrencyList, sumKeys, cbCategoryByJapanese);
			result.addAll(cbCategoryByJapanese.getResult());

			SumShipByCategory cbCategoryByDoller = new SumShipByCategory();
			Groupper.controlBreak(dollerCurrencyList, sumKeys, cbCategoryByDoller);

			SumShipByCategory cbCategoryByChina = new SumShipByCategory();
			Groupper.controlBreak(chinaCurrencyList, sumKeys, cbCategoryByChina);

			/*
			 * ドル残高
			 */
			for (ShipResult arrival : cbCategoryByDoller.getResult()) {
				// arrival.setBalanceOfDollar(arrival.getBalance());
				result.add(arrival);
			}
			/*
			 * ドル残高
			 */
			for (ShipResult arrival : cbCategoryByChina.getResult()) {
				// arrival.setBalanceOfChinese(arrival.getBalance());
				result.add(arrival);
			}
		}

		/*
		 * TODO:
		 * 
		 * マッジ：仕入先、商品種類
		 * 
		 */

		logger.debug("出荷済み一覧: " + result.size());

		registerReceivableBalance(result, filter.getShipYear() + filter.getShipMonth());

		return result;
	}

	/*
	 * 入荷済み一覧(入荷済（買掛金)統計一覧)
	 * 
	 * 検索条件： 平成27年度5月
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<ArrivalResult> getArrivalResult(ArrivalResultFilter filter) throws Exception {
		List<ArrivalResult> result = new ArrayList<ArrivalResult>();
		if (SessionMgr.isLogined() == false)
			return result;

		/*
		 * 注文リスト
		 */
		Criteria<OrderSheet> criteriaOrder = new Criteria(OrderSheet.class);
		String fromDate = filter.getShipYear() + filter.getShipMonth() + "01";
		String toDate = filter.getShipYear() + filter.getShipMonth() + "31";
		if (toDate.contains("null") == false)
			criteriaOrder.and(Restrictions.between(OrderSheet.IssueDate, fromDate, toDate));

		Criteria<OrderInvoice> invoiceCds = new Criteria(OrderInvoice.class);
		invoiceCds.addSelectedColumn(OrderInvoice.OrderCd);

		criteriaOrder.and(Restrictions.in(OrderSheet.OrderCd, invoiceCds));

		Criteria<OrderCancel> cancel = new Criteria(OrderCancel.class);
		cancel.addSelectedColumn(OrderCancel.OrderId);
		criteriaOrder.and(Restrictions.notIn(OrderSheet.OrderId, cancel));

		List<JoinedModels> orders = Query.queryWithDependencies(criteriaOrder, OrderDetail.class);

		@SuppressWarnings("unused")
		List<Long> orderDetailIds = PojoCollections.getKeyList(orders, OrderDetail.DetailId);
		List<String> orderCds = PojoCollections.getKeyList(orders, OrderSheet.OrderCd);

		List<OrderInvoice> orderInvoiceCds = getOrderInvoiceCodes(orderCds);
		/*
		 * 入荷済みリスト
		 */
		List<JoinedModels> invoices = getInvoices(orderCds);
		List<Long> invoiceDetailIds = PojoCollections.getKeyList(invoices, InvoiceDetail.DetailId);

		Criteria<EstimationAccept> criteriaEstimationAccept = new Criteria(EstimationAccept.class);
		criteriaEstimationAccept.and(Restrictions.in(EstimationAccept.DetailId, invoiceDetailIds));
		// List<EstimationAccept> estimationAccepts =
		// criteriaEstimationAccept.list();

		Criteria<PayMoneyDetail> criteriaPayMoneyDetail = new Criteria(PayMoneyDetail.class);
		criteriaPayMoneyDetail.and(Restrictions.in(PayMoneyDetail.InvoiceDetailId, invoiceDetailIds));
		List<PayMoneyDetail> payMoneyDetails = criteriaPayMoneyDetail.list();

		Criteria<OrderRequire> criteriaOrderRequire = new Criteria(OrderRequire.class);
		criteriaOrderRequire.and(Restrictions.in(OrderRequire.DetailId, invoiceDetailIds));
		List<OrderRequire> orderRequires = criteriaOrderRequire.list();

		String limitDay = UDate.getDateByAdjustMonth(-2);
		for (JoinedModels tra : orders) {
			ArrivalResult arrivalResult = new ArrivalResult();

			arrivalResult.setCurrency((String) tra.getValue(OrderSheet.Currency));
			arrivalResult.setSupplierId((Long) tra.getValue(OrderSheet.SupplierId));
			arrivalResult.setSupplierName((String) tra.getValue(OrderSheet.SupplierName));

			/*
			 * 商品分類
			 */
			Item item = ItemMgr.get((Long) tra.getValue(OrderDetail.ItemId));
			if (item != null && item.getCategoryType() != null)
				arrivalResult.setCategoryType(item.getCategoryType());

			/*
			 * 今月注文金額
			 */
			arrivalResult.setOrderAmountOfThisMonth((Money) tra.getValue(OrderDetail.Amount));
			arrivalResult.getOrderAmountOfThisMonth().setCurrency(arrivalResult.getCurrency());

			/*
			 * 出金金額
			 */
			@SuppressWarnings("unused")
			Long detailId = (Long) tra.getValue(OrderDetail.DetailId);
			Long itemId = (Long) tra.getValue(OrderDetail.ItemId);
			String orderCd = (String) tra.getValue(OrderSheet.OrderCd);

			arrivalResult.setPayAmount(new Money(arrivalResult.getCurrency(), 0));
			for (OrderInvoice orderInvoiceCd : orderInvoiceCds) {
				if (orderInvoiceCd.getOrderCd().equals(orderCd) == false)
					continue;
				String invoiceCd = orderInvoiceCd.getInvoiceCd();
				for (JoinedModels invoice : invoices) {
					if (invoiceCd.equals(invoice.getValue(InvoiceSheet.InvoiceCd)) == false)
						continue;
					if (itemId.equals(invoice.getValue(InvoiceDetail.ItemId)) == false)
						continue;

					Long invoiceDetailId = (Long) invoice.getValue(InvoiceDetail.DetailId);
					/*
					 * 支払金額
					 */
					for (PayMoneyDetail payMoneyDetail : payMoneyDetails) {
						if (payMoneyDetail.getInvoiceDetailId().equals(invoiceDetailId)) {
							arrivalResult.getPayAmount().add(payMoneyDetail.getAmount());
							// logger.debug("[FinaceMgr.getArrivalResult] 支払金額 =
							// " + payMoneyDetail.getAmount());
							break;
						}
					}

					/*
					 * 調整金額、送貨料
					 */
					for (OrderRequire orderRequire : orderRequires) {
						if (orderRequire.getDetailId().equals(invoiceDetailId)) {
							arrivalResult.getOrderAmountOfThisMonth().add(orderRequire.getAdjustAmount());
							arrivalResult.getOrderAmountOfThisMonth().add(orderRequire.getShippingCost());
							arrivalResult.getOrderAmountOfThisMonth().add(orderRequire.getExciseAmount());

							// logger.debug("[FinaceMgr.getArrivalResult]
							// 調整金額、送貨料 = " + orderRequire.getAdjustAmount()
							// + "/" + orderRequire.getShippingCost() + "/" +
							// orderRequire.getExciseAmount());
							break;
						}
					}

				}
			}

			// logger.debug("[FinaceMgr.getArrivalResult] 注文金額/支払金額 = " +
			// arrivalResult.getOrderAmountOfThisMonth() + "/"
			// + arrivalResult.getPayAmount());

			/*
			 * TODO:sum
			 * 
			 * 期末残高
			 */
			Money balance = arrivalResult.getOrderAmountOfThisMonth().copy();
			balance.minus(arrivalResult.getPayAmount());
			if (CurrencyMgr.isChinese(arrivalResult.getCurrency()))
				arrivalResult.setBalanceOfChinese(balance);
			else if (CurrencyMgr.isDoller(arrivalResult.getCurrency()))
				arrivalResult.setBalanceOfDollar(balance);
			else
				arrivalResult.setBalance(balance);

			/*
			 * TODO:
			 * 
			 * 期限超未払い（注文番号/金額）
			 */
			String issueDate = tra.getValue(OrderSheet.IssueDate);
			if (issueDate.compareTo(limitDay) <= 0)
				arrivalResult.setOrderCdAmount(
						tra.getValue(OrderSheet.OrderCd) + "/" + arrivalResult.getOrderAmountOfThisMonth());

			result.add(arrivalResult);
		}

		/*
		 * TODO:
		 * 
		 * 残高
		 * 
		 * 集計：商品種別
		 * 
		 * 合計: 仕入先、日本円・ドル、商品種別
		 */
		List<List<ArrivalResult>> suppliersList = Groupper.group(result, new String[] { OrderSheet.SupplierId });
		result.clear();
		for (List<ArrivalResult> suppliers : suppliersList) {
			/*
			 * 日本円・ドル
			 */
			List<ArrivalResult> japanCurrencyList = new ArrayList<ArrivalResult>();
			List<ArrivalResult> dollerCurrencyList = new ArrayList<ArrivalResult>();
			List<ArrivalResult> chinaCurrencyList = new ArrayList<ArrivalResult>();
			for (ArrivalResult arrival : suppliers) {
				if (CurrencyMgr.isDoller(arrival.getCurrency()))
					dollerCurrencyList.add(arrival);
				else if (CurrencyMgr.isChinese(arrival.getCurrency()))
					chinaCurrencyList.add(arrival);
				else
					japanCurrencyList.add(arrival);
			}

			/*
			 * 合計:商品種別
			 */
			String[] sumKeys = { Item.CategoryType };
			SumArrivalByCategory cbCategoryByJapanese = new SumArrivalByCategory();
			Groupper.controlBreak(japanCurrencyList, sumKeys, cbCategoryByJapanese);
			result.addAll(cbCategoryByJapanese.getResult());

			SumArrivalByCategory cbCategoryByDoller = new SumArrivalByCategory();
			Groupper.controlBreak(dollerCurrencyList, sumKeys, cbCategoryByDoller);

			SumArrivalByCategory cbCategoryByChina = new SumArrivalByCategory();
			Groupper.controlBreak(chinaCurrencyList, sumKeys, cbCategoryByChina);

			/*
			 * ドル残高
			 */
			for (ArrivalResult arrival : cbCategoryByDoller.getResult()) {
				// arrival.setBalanceOfDollar(arrival.getBalance());
				result.add(arrival);
			}

			for (ArrivalResult arrival : cbCategoryByChina.getResult()) {
				// arrival.setBalanceOfChinese(arrival.getBalance());
				result.add(arrival);
			}

		}

		/*
		 * TODO:
		 * 
		 * マッジ：仕入先、商品種類
		 * 
		 */
		logger.debug("入荷済み一覧: " + result.size());

		registerPayableBalance(result, filter.getShipYear() + filter.getShipMonth());

		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List<JoinedModels> getInvoices(List<String> orderCds) {
		/*
		 * INVOICE情報
		 */

		Criteria<InvoiceSheet> criteria = new Criteria(InvoiceSheet.class);

		// INVOICE_CD
		Criteria<OrderInvoice> invoiceCds = new Criteria(OrderInvoice.class);
		invoiceCds.addSelectedColumn(OrderInvoice.InvoiceCd);
		invoiceCds.and(Restrictions.in(OrderInvoice.OrderCd, orderCds));

		criteria.and(Restrictions.in(InvoiceSheet.InvoiceCd, invoiceCds));

		// 取消したINVOICE
		Criteria<InvoiceCancel> cancels = new Criteria(InvoiceCancel.class);
		cancels.addSelectedColumn(InvoiceCancel.InvoiceId);
		criteria.and(Restrictions.notIn(InvoiceSheet.InvoiceId, cancels));

		/*
		 * INVOICEデータを取得
		 */

		List<JoinedModels> invoices = Query.queryWithDependencies(criteria, InvoiceDetail.class);

		return invoices;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List<OrderInvoice> getOrderInvoiceCodes(List<String> orderCds) {
		// INVOICE_CD
		Criteria<OrderInvoice> invoiceCds = new Criteria(OrderInvoice.class);
		invoiceCds.and(Restrictions.in(OrderInvoice.OrderCd, orderCds));
		List<OrderInvoice> result = invoiceCds.list();
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void registerPayableBalance(List<ArrivalResult> results, String balanceMonth) throws Exception {
		String accountMonth = UDate.getYearYYYYMM();

		Criteria<PayableBalance> criteria = new Criteria(PayableBalance.class);
		criteria.and(Restrictions.eq(PayableBalance.AccountMonth, accountMonth));
		criteria.delete();

		List<PayableBalance> balances = new ArrayList<PayableBalance>();
		for (ArrivalResult result : results) {
			PayableBalance balance = new PayableBalance();
			balance.setPayableIｄ(SequenceMgr.nextSeq(PayableBalance.class));
			balance.setAccountMonth(accountMonth);
			balance.setBalanceMonth(balanceMonth);
			balance.setSupplierId(result.getSupplierId());

			balance.setBalanceJpAmount(result.getBalance().longValue());
			balance.setBalanceUsAmount(result.getBalanceOfDollar().longValue());
			balance.setBalanceCnAmount(result.getBalanceOfChinese().longValue());

			balances.add(balance);
		}

		TxMgr.insertList(balances);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void registerReceivableBalance(List<ShipResult> results, String balanceMonth) throws Exception {
		String accountMonth = UDate.getYearYYYYMM();

		Criteria<ReceivableBalance> criteria = new Criteria(ReceivableBalance.class);
		criteria.and(Restrictions.eq(ReceivableBalance.AccountMonth, accountMonth));
		criteria.delete();

		List<ReceivableBalance> balances = new ArrayList<ReceivableBalance>();
		for (ShipResult result : results) {
			ReceivableBalance balance = new ReceivableBalance();
			balance.setReceivableIｄ(SequenceMgr.nextSeq(ReceivableBalance.class));
			balance.setAccountMonth(accountMonth);
			balance.setBalanceMonth(balanceMonth);
			balance.setCustomerIｄ(result.getCustomerId());

			balance.setBalanceJpAmount(result.getBalance().longValue());
			balance.setBalanceUsAmount(result.getBalanceOfDollar().longValue());
			balance.setBalanceCnAmount(result.getBalanceOfChinese().longValue());

			balances.add(balance);
		}

		TxMgr.insertList(balances);
	}

	private static class SumShipByCategory implements IControlBreakProcedure {
		private List<ShipResult> result;
		private ShipResult group;

		public SumShipByCategory() {
			this.result = new ArrayList<ShipResult>();
		}

		public void doContinue(Object e) {
			ShipResult arrival = (ShipResult) e;
			if (group == null) {
				group = arrival;
				return;
			}
			/*
			 * 集計
			 */

			group.getSellAmountOfThisMonth().add(arrival.getSellAmountOfThisMonth());
			group.getDepositAmount().add(arrival.getDepositAmount());
			group.getBalance().add(arrival.getBalance());
			group.getBalanceOfChinese().add(arrival.getBalanceOfChinese());
			group.getBalanceOfDollar().add(arrival.getBalanceOfDollar());

		}

		public void doBreak(String key) {
			result.add(group);
			group = null;
		}

		public List<ShipResult> getResult() {
			return result;
		}
	}

	private static class SumArrivalByCategory implements IControlBreakProcedure {
		private List<ArrivalResult> result;
		private ArrivalResult group;

		public SumArrivalByCategory() {
			this.result = new ArrayList<ArrivalResult>();
		}

		public void doContinue(Object e) {
			ArrivalResult arrival = (ArrivalResult) e;
			if (group == null) {
				group = arrival;
				return;
			}
			/*
			 * 集計
			 */
			group.getOrderAmountOfThisMonth().add(arrival.getOrderAmountOfThisMonth());
			group.getPayAmount().add(arrival.getPayAmount());
			group.getBalance().add(arrival.getBalance());
			group.getBalanceOfChinese().add(arrival.getBalanceOfChinese());
			group.getBalanceOfDollar().add(arrival.getBalanceOfDollar());
		}

		public void doBreak(String key) {
			result.add(group);
			group = null;
		}

		public List<ArrivalResult> getResult() {
			return result;
		}
	}

}
