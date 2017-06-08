package com.np.order.biz.invoice;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import com.np.base.utils.UList;
import com.np.base.utils.UString;
import com.np.order.Money;
import com.np.order.action.SessionMgr;
import com.np.order.biz.IllegalPriorityEception;
import com.np.order.biz.mast.CustomerMgr;
import com.np.order.biz.mast.SupplierMgr;
import com.np.order.models.CIFArrival;
import com.np.order.models.Credit;
import com.np.order.models.Customer;
import com.np.order.models.Debt;
import com.np.order.models.EstimationAccept;
import com.np.order.models.EstimationOrder;
import com.np.order.models.EstiomationDetail;
import com.np.order.models.EstmationCancel;
import com.np.order.models.EstmationSheet;
import com.np.order.models.InvoiceApprove;
import com.np.order.models.InvoiceCancel;
import com.np.order.models.InvoiceDetail;
import com.np.order.models.InvoiceSheet;
import com.np.order.models.OrderAccept;
import com.np.order.models.OrderCancel;
import com.np.order.models.OrderDetail;
import com.np.order.models.OrderInvoice;
import com.np.order.models.OrderRequire;
import com.np.order.models.OrderSheet;
import com.np.order.models.Supplier;
import com.np.order.objects.SellOrderDetail;
import com.np.order.objects.SellOrderFilter;
import com.np.order.objects.SellOrderInfo;
import com.np.order.objects.SellOrderSummary;

/*
 * 月度販売仕入取引対照
 */
public class SellOrderAmountMgr {
	private static Log logger = LogFactory.getLog(SellOrderAmountMgr.class);

	/*
	 * 登録
	 */
	public static void register(List<SellOrderInfo> sellOrders) throws Exception {
		if (SessionMgr.isFinacer() == false)
			throw new IllegalPriorityEception();

		TxMgr.update(new ITxProc() {

			@Override
			public void process(Connection con) throws Exception {
				for (SellOrderInfo sellOrder : sellOrders) {
					register(con, sellOrder);
				}

			}

		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void register(Connection con, SellOrderInfo sellOrder) throws Exception {

		/*
		 * 仕入取引：調整金額
		 */
		/*
		 * 仕入取引：送料
		 */
		Criteria<OrderRequire> criteriaOrderRequiret = new Criteria(OrderRequire.class);
		criteriaOrderRequiret.and(Restrictions.eq(OrderRequire.DetailId, sellOrder.getInvoiceDetailId()));
		OrderRequire orderRequire = criteriaOrderRequiret.get();
		if (orderRequire == null) {
			orderRequire = new OrderRequire();
			orderRequire.setRequireId(SequenceMgr.nextSeq(OrderRequire.class));
			orderRequire.setDetailId(sellOrder.getInvoiceDetailId());
			// orderRequire.setExciseAmount(sellOrder.getOrderExcise());
			orderRequire.setShippingCost(sellOrder.getDeliveryAmount());
			orderRequire.setAdjustAmount(sellOrder.getAdjustAmount());
			TxMgr.insert(con, orderRequire);
		} else {
			// orderRequire.setExciseAmount(sellOrder.getOrderExcise());
			orderRequire.setShippingCost(sellOrder.getDeliveryAmount());
			orderRequire.setAdjustAmount(sellOrder.getAdjustAmount());
			TxMgr.update(con, orderRequire);
		}

		/*
		 * TODO: 仕入取引：支払金額（三段入力）
		 */
		/*
		 * 仕入取引：支払日（三段入力）
		 */
		Criteria<Debt> criteria = new Criteria(Debt.class);
		criteria.and(Restrictions.eq(Debt.DetailId, sellOrder.getInvoiceDetailId()));
		Debt debt = criteria.get();

		if (debt == null) {
			debt = new Debt();
			debt.setDebtId(SequenceMgr.nextSeq(Debt.class));
			debt.setDetailId(sellOrder.getInvoiceDetailId());
			debt.setDebtAmount(sellOrder.getPayAmounts());
			debt.setPaymentDate(sellOrder.getPayDates());
			TxMgr.insert(con, debt);
		} else {
			debt.setDebtAmount(sellOrder.getPayAmounts());
			debt.setPaymentDate(sellOrder.getPayDates());
			TxMgr.update(con, debt);
		}

		/*
		 * 仕入取引：納入日
		 */
		Criteria<OrderAccept> criteriaOrderAccept = new Criteria(OrderAccept.class);
		criteriaOrderAccept.and(Restrictions.eq(OrderAccept.OrderId, sellOrder.getOrderDetailId()));
		OrderAccept orderAccept = criteriaOrderAccept.get();
		if (orderAccept == null) {
			orderAccept = new OrderAccept();
			orderAccept.setAcceptId(SequenceMgr.nextSeq(OrderAccept.class));
			orderAccept.setOrderId(sellOrder.getOrderDetailId());
			orderAccept.setDate(sellOrder.getDeliveryDate());
			TxMgr.insert(con, orderAccept);
		} else {
			orderAccept.setDate(sellOrder.getDeliveryDate());
			TxMgr.update(con, orderAccept);
		}

		/*
		 * 販売取引：取引No.
		 */

		/*
		 * 販売取引：売上日付
		 */
		Criteria<EstimationAccept> criteriaEstimationAccept = new Criteria(EstimationAccept.class);
		criteriaEstimationAccept.and(Restrictions.eq(EstimationAccept.DetailId, sellOrder.getInvoiceDetailId()));
		EstimationAccept estimationAccept = criteriaEstimationAccept.get();
		if (estimationAccept == null) {
			estimationAccept = new EstimationAccept();
			estimationAccept.setAcceptId(SequenceMgr.nextSeq(EstimationAccept.class));
			estimationAccept.setDetailId(sellOrder.getInvoiceDetailId());
			estimationAccept.setTradingNo(sellOrder.getTradingNo());
			estimationAccept.setDate(sellOrder.getSellDate());
			TxMgr.insert(con, estimationAccept);
		} else {
			estimationAccept.setTradingNo(sellOrder.getTradingNo());
			estimationAccept.setDate(sellOrder.getSellDate());
			TxMgr.update(con, estimationAccept);
		}
	}

	private static List sort(List list) {
		Collections.sort(list);
		return list;
	}

	/*
	 * 月度販売仕入取引対照
	 * 
	 * 検索条件：（INVOICE作成日＝画面年月）のINVOICEデータ＋ （CIF到着日＝画面年月）のINVOICEデータ
	 * 
	 * 表示順: 日本円、ドル、人民元、在庫（CIF） 設備、部品、材料。。。 会社別
	 * 
	 * 合計：日本円 ／ドル ／人民元
	 * 
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static SellOrderDetail getSellOrder(SellOrderFilter filter) throws Exception {
		List<SellOrderInfo> result = new ArrayList<SellOrderInfo>();
		long time = System.currentTimeMillis();
		SellOrderSummary summary = new SellOrderSummary();

		SellOrderDetail sellOrderDetail = new SellOrderDetail();
		sellOrderDetail.setDetails(result);
		sellOrderDetail.setSummary(summary);

		/*
		 * Invoice情報
		 */
		List<JoinedModels> invoices = getInvoices(filter);
		// logger.debug("InvoiceCds =" +
		// sort(PojoCollections.getUniqueKeyList(invoices,
		// InvoiceSheet.InvoiceCd)));
		// logger.debug("InvoiceIds =" +
		// sort(PojoCollections.getUniqueKeyList(invoices,
		// InvoiceSheet.InvoiceId)));
		// logger.debug("DetailIds =" +
		// sort(PojoCollections.getKeyList(invoices, InvoiceDetail.DetailId)));
		// logger.debug("DetailIds =" + PojoCollections.getKeyList(invoices,
		// InvoiceDetail.DetailId).size());

		if (UList.isEmpty(invoices))
			return sellOrderDetail;

		/*
		 * 仕入情報
		 */
		List<String> invoiceCds = PojoCollections.getUniqueKeyList(invoices, InvoiceSheet.InvoiceCd);
		List<JoinedModels> orders = getOrders(invoiceCds);
		if (UList.isEmpty(orders))
			return sellOrderDetail;

		@SuppressWarnings("unused")
		List<String> orderCds = PojoCollections.getUniqueKeyList(orders, OrderSheet.OrderCd);
		List<Long> orderIds = PojoCollections.getUniqueKeyList(orders, OrderSheet.OrderId);
		// List<JoinedModels> estimations = getEstimations(orderCds);
		// if (UList.isEmpty(estimations))
		// return result;

		/*
		 * 承認済みInvoices
		 */
		List<Long> invoiceIds = PojoCollections.getUniqueKeyList(invoices, InvoiceSheet.InvoiceId);
		Criteria<InvoiceApprove> criteria = new Criteria(InvoiceApprove.class);
		criteria.addSelectedColumn(InvoiceApprove.InvoiceId);
		criteria.and(Restrictions.in(InvoiceApprove.InvoiceId, invoiceIds));

		List<InvoiceApprove> approves = criteria.list();
		List<Long> approvedInvoiceIds = PojoCollections.getUniqueKeyList(approves, InvoiceSheet.InvoiceId);

		/*
		 * 見積書と注文をマッチ
		 */
		String[] joinKeys = { InvoiceSheet.InvoiceCd, OrderDetail.ItemId };
		// List<JoinedModels> estimationOrders = Matcher.innerJoin(estimations,
		// orders, joinKeys[0]);

		List<SellOrderAmountDetail> amountDetails = getSellOrderAmountDetails(orderIds, invoiceIds);

		List<Supplier> suppliers = SupplierMgr
				.getSuppliers(PojoCollections.getUniqueKeyList(orders, OrderSheet.SupplierId));

		List<Customer> customers = CustomerMgr
				.getCustomers(PojoCollections.getUniqueKeyList(invoices, InvoiceSheet.CustomerId));

		logger.debug("orders =" + orders.size());

		/*
		 * 注文とInvoiceをマッチ
		 */
		result = Matcher.innerJoin(invoices, orders, joinKeys,
				new IModelFactory<SellOrderInfo, JoinedModels, JoinedModels>() {
					@Override
					public SellOrderInfo create(JoinedModels mas, JoinedModels tra) {
						Long orderId = (Long) tra.getValue(OrderSheet.OrderId);
						String orderCd = (String) tra.getValue(OrderSheet.OrderCd);

						Long invoiceId = (Long) mas.getValue(InvoiceSheet.InvoiceId);
						String invoiceCd = (String) mas.getValue(InvoiceSheet.InvoiceCd);

						/*
						 * 未承認
						 */
						if (approvedInvoiceIds.contains(invoiceId) == false)
							return null;

						/*
						 * 月度販売仕入取引対照
						 */
						SellOrderInfo sellOrder = new SellOrderInfo();

						sellOrder.setOrderId(orderId);
						sellOrder.setInvoiceId(invoiceId);

						sellOrder.setOrderDetailId((Long) tra.getValue(OrderDetail.DetailId));
						sellOrder.setInvoiceDetailId((Long) mas.getValue(InvoiceDetail.DetailId));

						sellOrder.setSupplierId((Long) tra.getValue(OrderSheet.SupplierId));

						Supplier supplier = null;
						for (Supplier _supplier : suppliers) {
							if (_supplier.getSupplierId().equals(sellOrder.getSupplierId())) {
								sellOrder.setSupplierName(_supplier.getJpName());
								supplier = _supplier;
								break;
							}
						}

						Long customerId = (Long) mas.getValue(InvoiceSheet.CustomerId);
						Customer customer = null;
						for (Customer _customer : customers) {
							if (_customer.getCustomerId().equals(customerId)) {
								sellOrder.setCustomerName(_customer.getEnShortName());
								customer = _customer;
								break;
							}
						}

						sellOrder.setOrderCd(orderCd);

						sellOrder.setCurrency((String) mas.getValue(InvoiceSheet.Currency));

						String deliveryType = (String) mas.getValue(InvoiceSheet.DeliveryType);
						if ("CIF".equalsIgnoreCase(deliveryType))
							sellOrder.setFlag("CIF");
						else
							sellOrder.setFlag(sellOrder.getCurrency());

						/*
						 * 品名／型番
						 */
						sellOrder
								.setModeCd((String) tra.getValue(OrderDetail.Name) + tra.getValue(OrderDetail.PartsCd));
						/*
						 * 区分
						 */
						sellOrder.setType((String) tra.getValue(OrderDetail.Type));
						/*
						 * 新安発注日付
						 */
						sellOrder.setXinanOrderDate(tra.getValue(OrderSheet.IssueDate));

						/*
						 * 発注数
						 */

						sellOrder.setOrderQuantity(((Integer) tra.getValue(InvoiceDetail.Quantity)).intValue());
						/*
						 * 仕入金額
						 */
						Money unitPrice = (Money) tra.getValue(OrderDetail.UnitPrice);
						String currency = unitPrice.getCurrency();
						int orderAmount = unitPrice.intValue();
						if (mas.getValue(InvoiceDetail.Quantity) != null)
							orderAmount *= (Integer) mas.getValue(InvoiceDetail.Quantity);
						else
							orderAmount *= 0;

						sellOrder.setOrderAmount(new Money(currency, orderAmount));
						/*
						 * 消費税
						 */
						/*
						 * 調整金額
						 */
						/*
						 * 送料
						 */
						setOrderRequire(sellOrder, currency, amountDetails, supplier);

						/*
						 * 仕入合計
						 */
						Money totalAmount = sellOrder.getOrderAmount().copy();
						totalAmount.add(sellOrder.getOrderExcise());
						totalAmount.add(sellOrder.getAdjustAmount());
						totalAmount.add(sellOrder.getDeliveryAmount());
						sellOrder.setRequireAmount(totalAmount);

						/*
						 * 支払金額/日
						 */
						setPayMount(sellOrder, currency, amountDetails);

						/*
						 * 納入日
						 */

						sellOrder.setDeliveryDate((String) tra.getValue(OrderDetail.DeliveryDate));
						for (SellOrderAmountDetail amountDetail : amountDetails) {
							if (sellOrder.getOrderDetailId().equals(amountDetail.getOrderDetailId()) == false)
								continue;

							OrderAccept orderAccept = amountDetail.getOrderAccept();

							if (orderAccept != null)
								sellOrder.setDeliveryDate(orderAccept.getDate());
							break;
						}

						sellOrder.setInvoiceCd(invoiceCd);

						for (SellOrderAmountDetail amountDetail : amountDetails) {
							if (sellOrder.getInvoiceDetailId().equals(amountDetail.getInvoiceDetailId()) == false)
								continue;

							EstimationAccept estimationAccept = amountDetail.getEstimationAccept();

							if (estimationAccept != null) {
								sellOrder.setTradingNo(estimationAccept.getTradingNo());

								/*
								 * 売上日付
								 */
								sellOrder.setSellDate(estimationAccept.getDate());
								sellOrder.setFlag(sellOrder.getCurrency());
							}
							break;
						}

						if (UString.isEmpty(sellOrder.getSellDate()))
							sellOrder.setSellDate((String) mas.getValue(OrderSheet.IssueDate));

						/*
						 * 売上金額
						 */

						sellOrder.setSellAmount(mas.getValue(InvoiceDetail.Amount));

						/*
						 * 消費税
						 */
						String country = (customer == null) ? null : customer.getCountry();
						if (country != null && (country.equalsIgnoreCase("JP") || country.equalsIgnoreCase("Japanses")
								|| country.equalsIgnoreCase("Japan") || country.contains("日本"))) {
							int excise = (int) (sellOrder.getSellAmount().intValue() * 0.08);
							sellOrder.setSellExcise(new Money(currency, excise));
						} else
							sellOrder.setSellExcise(new Money(currency, 0));

						/*
						 * 粗利:売上金額ー仕入金額
						 */
						sellOrder.setGrossProfit(new Money(currency, (int) (sellOrder.getSellAmount().longValue()
								- sellOrder.getRequireAmount().longValue())));

						/*
						 * 粗利益率: 粗利/(売上金額ー消費税)
						 */
						long grossMargin = sellOrder.getGrossProfit().longValue();
						grossMargin *= 100;
						long amount = sellOrder.getSellAmount().longValue() + sellOrder.getSellExcise().longValue();
						if (amount != 0)
							grossMargin /= amount;

						sellOrder.setGrossMargin(grossMargin);

						/*
						 * 入金日
						 */

						/*
						 * 入金金額
						 */
						setReceivedAmount(sellOrder, currency, amountDetails);

						summary.sunm(sellOrder);
						return sellOrder;
					}

				});

		/*
		 * TODO
		 * 
		 * Sort: flag, type, customerName
		 * 
		 * 同じ部品、客先の順でソートすべき
		 * 
		 * 表示順 日本円、ドル、人民元、在庫（CIF） 設備、部品、材料。。
		 */
		Sortter.sort(result, new String[] { "Flag", OrderDetail.Type, "CustomerName" });

		logger.debug("result : " + result.size());
		logger.debug("time(s) : " + (System.currentTimeMillis() - time) / 1000);

		sellOrderDetail.setDetails(result);

		return sellOrderDetail;
	}

	private static void setPayMount(SellOrderInfo sellOrder, String currency,
			List<SellOrderAmountDetail> amountDetails) {
		for (SellOrderAmountDetail amountDetail : amountDetails) {
			if (sellOrder.getInvoiceDetailId().equals(amountDetail.getInvoiceDetailId()) == false)
				continue;

			Debt debt = amountDetail.getDebt();
			if (debt == null)
				break;
			sellOrder.setPayAmounts(debt.getDebtAmount());
			sellOrder.setPayDates(debt.getPaymentDate());
			break;
		}

	}

	private static void setOrderRequire(SellOrderInfo sellOrder, String currency,
			List<SellOrderAmountDetail> amountDetails, Supplier supplier) {
		for (SellOrderAmountDetail amountDetail : amountDetails) {
			if (sellOrder.getInvoiceDetailId().equals(amountDetail.getInvoiceDetailId()) == false)
				continue;

			OrderRequire orderRequire = amountDetail.getOrderRequire();
			if (orderRequire == null)
				break;

			/*
			 * 消費税
			 */
			// if (orderRequire.getExciseAmount() != null)
			// orderRequire.getExciseAmount().setCurrency(currency);
			// sellOrder.setOrderExcise(orderRequire.getExciseAmount());

			/*
			 * 調整金額
			 */
			if (orderRequire.getAdjustAmount() != null)
				orderRequire.getAdjustAmount().setCurrency(currency);
			sellOrder.setAdjustAmount(orderRequire.getAdjustAmount());
			/*
			 * 送料
			 */
			if (orderRequire.getShippingCost() != null)
				orderRequire.getShippingCost().setCurrency(currency);
			sellOrder.setDeliveryAmount(orderRequire.getShippingCost());

			break;
		}

		if (sellOrder.getOrderExcise() == null) {
			String country = (supplier == null) ? null : supplier.getCountry();

			if (country != null && (country.equalsIgnoreCase("JP") || country.equalsIgnoreCase("Japanses")
					|| country.equalsIgnoreCase("Japan") || country.contains("日本"))) {
				int excise = (int) (sellOrder.getOrderAmount().intValue() * 0.08);
				sellOrder.setOrderExcise(new Money(currency, excise));
			} else
				sellOrder.setOrderExcise(new Money(currency, 0));
		}
	}

	private static void setReceivedAmount(SellOrderInfo sellOrder, String currency,
			List<SellOrderAmountDetail> amountDetails) {
		for (SellOrderAmountDetail amountDetail : amountDetails) {
			if (sellOrder.getInvoiceDetailId().equals(amountDetail.getInvoiceDetailId()) == false)
				continue;

			Credit credit = amountDetail.getCredit();
			if (credit == null)
				break;
			/*
			 * 入金日
			 */
			sellOrder.setReceivedDate(credit.getPaymentDate());

			/*
			 * 入金金額
			 */
			if (credit.getCreditAmount() != null)
				credit.getCreditAmount().setCurrency(currency);
			sellOrder.setReceivedAmount(credit.getCreditAmount());
			break;
		}
	}

	/*
	 * 検索条件：（INVOICE作成日＝画面年月）のINVOICEデータ＋ （CIF到着日＝画面年月）のINVOICEデータ
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List<JoinedModels> getInvoices(SellOrderFilter filter) {
		logger.debug("[getInvoices] 検索条件：（INVOICE作成日＝画面年月）のINVOICEデータ＋ （CIF到着日＝画面年月）のINVOICEデータ");
		/*
		 * INVOICE情報
		 */
		String deliveryYYYYMM = filter.getShipYear() + filter.getShipMonth();

		/*
		 * CIF 売上日付
		 */
		Criteria<EstimationAccept> criteriaEstimationAccept = new Criteria(EstimationAccept.class);
		// criteriaEstimationAccept.addSelectedColumn(EstimationAccept.DetailId);
		if (deliveryYYYYMM.contains("null") == false)
			criteriaEstimationAccept
					.and(Restrictions.between(EstimationAccept.Date, deliveryYYYYMM + "01", deliveryYYYYMM + "31"));

		List<EstimationAccept> accepts = criteriaEstimationAccept.list();
		List<Long> cifInvoiceDetailIds = PojoCollections.getKeyList(accepts, EstimationAccept.DetailId);
		// if (cifInvoiceDetailIds.isEmpty())
		// return new ArrayList<JoinedModels>();
		logger.debug("cifInvoiceDetailIds =" + cifInvoiceDetailIds.size());
		/*
		 * TODO:
		 */

		Criteria<InvoiceDetail> criteriaDetail = new Criteria(InvoiceDetail.class);
		criteriaDetail.and(Restrictions.in(InvoiceDetail.DetailId, cifInvoiceDetailIds));
		criteriaDetail.addSelectedColumn(InvoiceDetail.InvoiceId);

		Criteria<InvoiceSheet> criteria = new Criteria(InvoiceSheet.class);
		criteria.and(Restrictions.in(InvoiceSheet.InvoiceId, criteriaDetail));
		List<JoinedModels> cifInvoices = Query.queryWithDependencies(criteria, InvoiceDetail.class);
		for (JoinedModels invoice : cifInvoices) {
			String currency = invoice.getValue(InvoiceSheet.Currency);
			((Money) invoice.getValue(InvoiceDetail.Amount)).setCurrency(currency);
			((Money) invoice.getValue(InvoiceDetail.UnitPrice)).setCurrency(currency);
		}
		logger.debug("cifInvoices =" + cifInvoices.size());

		criteria = new Criteria(InvoiceSheet.class);
		/*
		 * INVOICE作成日範囲
		 */
		if (deliveryYYYYMM.contains("null") == false)
			criteria.and(Restrictions.between(InvoiceSheet.IssueDate, deliveryYYYYMM + "01", deliveryYYYYMM + "31"));

		// 取消したINVOICE
		Criteria<InvoiceCancel> cancels = new Criteria(InvoiceCancel.class);
		cancels.addSelectedColumn(InvoiceCancel.InvoiceId);
		criteria.and(Restrictions.notIn(InvoiceSheet.InvoiceId, cancels));

		/*
		 * INVOICEデータを取得
		 */

		List<JoinedModels> invoices = Query.queryWithDependencies(criteria, InvoiceDetail.class);
		for (JoinedModels invoice : invoices) {
			String currency = invoice.getValue(InvoiceSheet.Currency);
			((Money) invoice.getValue(InvoiceDetail.Amount)).setCurrency(currency);
			((Money) invoice.getValue(InvoiceDetail.UnitPrice)).setCurrency(currency);
		}
		logger.debug("invoices =" + invoices.size());
		/*
		 * 入力後CIFの対応
		 */
		logger.debug("  入力後CIFの対応...");
		boolean needCheckCIF = false;
		if (needCheckCIF) {
			List<Long> invoiceDetailIds = PojoCollections.getKeyList(invoices, EstimationAccept.DetailId);

			criteriaEstimationAccept = new Criteria(EstimationAccept.class);
			criteriaEstimationAccept.addSelectedColumn(EstimationAccept.DetailId);
			criteriaEstimationAccept.and(Restrictions.in(EstimationAccept.DetailId, invoiceDetailIds));
			List<EstimationAccept> cifs = criteriaEstimationAccept.list();
			logger.debug("cifs =" + cifs.size());
			for (int i = invoices.size() - 1; i >= 0; i--) {
				JoinedModels invoice = invoices.get(i);
				Long detailId = (Long) invoice.getValue(InvoiceDetail.DetailId);
				for (EstimationAccept cif : cifs) {
					if (cif.getDetailId().equals(detailId)) {
						invoices.remove(i);
						logger.debug("  remove invoice   detailId=" + detailId);
						break;
					}
				}
			}
		}

		for (JoinedModels invoice : cifInvoices) {
			Long detailId = (Long) invoice.getValue(InvoiceDetail.DetailId);
			for (EstimationAccept accept : accepts) {
				if (accept.getDetailId().equals(detailId)) {
					invoice.setValue(InvoiceSheet.IssueDate, accept.getDate());
					break;
				}
			}
		}
		logger.debug("cifInvoices =" + cifInvoices.size());

		invoices.addAll(cifInvoices);

		/*
		 * Arrival納期範囲
		 */
		List<String> invoiceCds = PojoCollections.getKeyList(invoices, InvoiceSheet.InvoiceCd);

		Criteria<CIFArrival> criteriaArrival = new Criteria(CIFArrival.class);
		/*
		 * Arrival範囲
		 */
		if (deliveryYYYYMM.contains("null") == false)
			criteriaArrival
					.and(Restrictions.between(CIFArrival.ArrivalDate, deliveryYYYYMM + "01", deliveryYYYYMM + "31"));
		criteriaArrival.and(Restrictions.notIn(CIFArrival.InvoiceCd, invoiceCds));

		List<CIFArrival> arrivals = criteriaArrival.list();
		invoiceCds = PojoCollections.getKeyList(arrivals, CIFArrival.InvoiceCd);

		/*
		 * INVOICEデータを取得
		 */
		criteria = new Criteria(InvoiceSheet.class);
		criteria.and(Restrictions.in(InvoiceSheet.InvoiceCd, invoiceCds));
		// 取消したINVOICE
		criteria.and(Restrictions.notIn(InvoiceSheet.InvoiceId, cancels));

		/*
		 * INVOICEデータを取得
		 */

		List<JoinedModels> arrivalInvoices = Query.queryWithDependencies(criteria, InvoiceDetail.class);
		for (JoinedModels arrivalInvoice : arrivalInvoices) {
			String currency = arrivalInvoice.getValue(InvoiceSheet.Currency);
			((Money) arrivalInvoice.getValue(InvoiceDetail.Amount)).setCurrency(currency);
			((Money) arrivalInvoice.getValue(InvoiceDetail.UnitPrice)).setCurrency(currency);
		}
		logger.debug("arrivalInvoices =" + arrivalInvoices.size());

		/*
		 * TODO: merge
		 */
		invoices.addAll(arrivalInvoices);

		return invoices;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static List<JoinedModels> getOrders(List<String> invoiceCds) {
		Criteria<OrderInvoice> criteriaOrderInvoice = new Criteria(OrderInvoice.class);
		// criteriaOrderInvoice.addSelectedColumn(OrderInvoice.OrderCd);
		criteriaOrderInvoice.and(Restrictions.in(OrderInvoice.InvoiceCd, invoiceCds));

		List<OrderInvoice> cdMap = criteriaOrderInvoice.list();
		List<String> orderCds = PojoCollections.getKeyList(cdMap, OrderInvoice.OrderCd);

		Criteria<OrderSheet> criteria = new Criteria(OrderSheet.class);
		// 取消した注文
		Criteria<OrderCancel> cancels = new Criteria(OrderCancel.class);
		cancels.addSelectedColumn(OrderCancel.OrderId);

		criteria.and(Restrictions.notIn(OrderSheet.OrderId, cancels));
		criteria.and(Restrictions.in(OrderSheet.OrderCd, orderCds));

		List<JoinedModels> orders = Query.queryWithDependencies(criteria, OrderDetail.class);
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

		return orders;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static List<JoinedModels> getEstimations(List<String> orderCds) {
		Criteria<EstimationOrder> criteriaEstimationOrder = new Criteria(EstimationOrder.class);
		// criteriaEstimationOrder.addSelectedColumn(EstimationOrder.EstimationCd);
		criteriaEstimationOrder.and(Restrictions.in(EstimationOrder.OrderCd, orderCds));

		List<EstimationOrder> estmOrders = criteriaEstimationOrder.list();
		List<String> estimationCds = PojoCollections.getKeyList(estmOrders, EstimationOrder.EstimationCd);

		Criteria<EstmationSheet> criteria = new Criteria(EstmationSheet.class);
		// 取消した見積書
		Criteria<EstmationCancel> cancels = new Criteria(EstmationCancel.class);
		cancels.addSelectedColumn(EstmationCancel.EstimationId);

		criteria.and(Restrictions.notIn(EstmationSheet.EstimationId, cancels));
		criteria.and(Restrictions.in(EstmationSheet.EstimationCd, estimationCds));

		List<JoinedModels> orders = Query.queryWithDependencies(criteria, EstiomationDetail.class);
		for (JoinedModels order : orders) {
			String currency = order.getValue(EstmationSheet.Currency);
			((Money) order.getValue(EstiomationDetail.Amount)).setCurrency(currency);
			((Money) order.getValue(EstiomationDetail.UnitPrice)).setCurrency(currency);

			order.appendName(OrderSheet.OrderCd);
			String orderCd = order.getValue(EstmationSheet.EstimationCd);
			for (EstimationOrder estmOrder : estmOrders) {
				if (estmOrder.getEstimationCd().equals(orderCd)) {
					order.setValue(OrderSheet.OrderCd, estmOrder.getOrderCd());
					break;
				}
			}
		}
		return orders;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List<SellOrderAmountDetail> getSellOrderAmountDetails(List<Long> orderDetailIds,
			List<Long> invoiceDetailIds) {
		List<SellOrderAmountDetail> result = new ArrayList<SellOrderAmountDetail>();

		Criteria<OrderAccept> criteriaOrderAccept = new Criteria(OrderAccept.class);
		criteriaOrderAccept.and(Restrictions.in(OrderAccept.OrderId, orderDetailIds));
		List<OrderAccept> orderAccepts = criteriaOrderAccept.list();
		for (OrderAccept orderAccept : orderAccepts) {
			SellOrderAmountDetail amountDetail = new SellOrderAmountDetail();
			amountDetail.setOrderDetailId(orderAccept.getOrderId());
			amountDetail.setOrderAccept(orderAccept);
			result.add(amountDetail);
		}

		Criteria<EstimationAccept> criteriaEstimationAccept = new Criteria(EstimationAccept.class);
		criteriaEstimationAccept.and(Restrictions.in(EstimationAccept.DetailId, invoiceDetailIds));
		List<EstimationAccept> estimationAccepts = criteriaEstimationAccept.list();
		for (EstimationAccept estimationAccept : estimationAccepts) {
			boolean exist = false;
			for (SellOrderAmountDetail amountDetail : result) {
				if (estimationAccept.getDetailId().equals(amountDetail.getInvoiceDetailId())) {
					amountDetail.setEstimationAccept(estimationAccept);
					exist = true;
					break;
				}
			}

			if (exist == false) {
				SellOrderAmountDetail amountDetail = new SellOrderAmountDetail();
				amountDetail.setInvoiceDetailId(estimationAccept.getDetailId());
				amountDetail.setEstimationAccept(estimationAccept);
				result.add(amountDetail);
			}
		}

		Criteria<Debt> criteriaDebt = new Criteria(Debt.class);
		criteriaDebt.and(Restrictions.in(Debt.DetailId, invoiceDetailIds));
		List<Debt> debts = criteriaDebt.list();
		for (Debt debt : debts) {
			boolean exist = false;
			for (SellOrderAmountDetail amountDetail : result) {
				if (debt.getDetailId().equals(amountDetail.getInvoiceDetailId())) {
					amountDetail.setDebt(debt);
					exist = true;
					break;
				}
			}

			if (exist == false) {
				SellOrderAmountDetail amountDetail = new SellOrderAmountDetail();
				amountDetail.setInvoiceDetailId(debt.getDetailId());
				amountDetail.setDebt(debt);
				result.add(amountDetail);
			}
		}

		Criteria<OrderRequire> criteriaOrderRequire = new Criteria(OrderRequire.class);
		criteriaOrderRequire.and(Restrictions.in(OrderRequire.DetailId, invoiceDetailIds));
		List<OrderRequire> orderRequires = criteriaOrderRequire.list();
		for (OrderRequire orderRequire : orderRequires) {
			boolean exist = false;
			for (SellOrderAmountDetail amountDetail : result) {
				if (orderRequire.getDetailId().equals(amountDetail.getInvoiceDetailId())) {
					amountDetail.setOrderRequire(orderRequire);
					exist = true;
					break;
				}
			}

			if (exist == false) {
				SellOrderAmountDetail amountDetail = new SellOrderAmountDetail();
				amountDetail.setInvoiceDetailId(orderRequire.getDetailId());
				amountDetail.setOrderRequire(orderRequire);
				result.add(amountDetail);
			}
		}

		Criteria<Credit> criteriaCredit = new Criteria(Credit.class);
		criteriaCredit.and(Restrictions.in(Credit.DetailId, invoiceDetailIds));
		List<Credit> credits = criteriaCredit.list();
		for (Credit credit : credits) {
			boolean exist = false;
			for (SellOrderAmountDetail amountDetail : result) {
				if (credit.getDetailId().equals(amountDetail.getInvoiceDetailId())) {
					amountDetail.setCredit(credit);
					exist = true;
					break;
				}
			}

			if (exist == false) {
				SellOrderAmountDetail amountDetail = new SellOrderAmountDetail();
				amountDetail.setInvoiceDetailId(credit.getDetailId());
				amountDetail.setCredit(credit);
				result.add(amountDetail);
			}
		}

		return result;
	}

	private static class SellOrderAmountDetail {
		private Long orderDetailId;
		private Long invoiceDetailId;

		/*
		 * OrderDetail.DetailId
		 */
		private OrderAccept orderAccept;

		/*
		 * InvoiceDetail.DetailId
		 */
		private EstimationAccept estimationAccept;
		/*
		 * 支払金額
		 */
		private Debt debt;
		/*
		 * 入金金額
		 */
		private Credit credit;
		/*
		 * 調整金額
		 */
		private OrderRequire OrderRequire;

		public Long getOrderDetailId() {
			return orderDetailId;
		}

		public void setOrderDetailId(Long orderDetailId) {
			this.orderDetailId = orderDetailId;
		}

		public Long getInvoiceDetailId() {
			return invoiceDetailId;
		}

		public void setInvoiceDetailId(Long invoiceDetailId) {
			this.invoiceDetailId = invoiceDetailId;
		}

		public OrderAccept getOrderAccept() {
			return orderAccept;
		}

		public void setOrderAccept(OrderAccept orderAccept) {
			this.orderAccept = orderAccept;
		}

		public EstimationAccept getEstimationAccept() {
			return estimationAccept;
		}

		public void setEstimationAccept(EstimationAccept estimationAccept) {
			this.estimationAccept = estimationAccept;
		}

		public Debt getDebt() {
			return debt;
		}

		public void setDebt(Debt debt) {
			this.debt = debt;
		}

		public Credit getCredit() {
			return credit;
		}

		public void setCredit(Credit credit) {
			this.credit = credit;
		}

		public OrderRequire getOrderRequire() {
			return OrderRequire;
		}

		public void setOrderRequire(OrderRequire orderRequire) {
			OrderRequire = orderRequire;
		}

	}

}
