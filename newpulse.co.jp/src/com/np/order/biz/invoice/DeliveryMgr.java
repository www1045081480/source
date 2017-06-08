package com.np.order.biz.invoice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.db.Query;
import com.np.base.db.TxMgr;
import com.np.base.dml.IModelFactory;
import com.np.base.dml.Matcher;
import com.np.base.dml.PojoCollections;
import com.np.base.dml.Sortter;
import com.np.base.models.JoinKey;
import com.np.base.models.JoinedModels;
import com.np.base.orm.Criteria;
import com.np.base.orm.Restrictions;
import com.np.base.utils.UDate;
import com.np.base.utils.UList;
import com.np.base.utils.UString;
import com.np.order.Money;
import com.np.order.action.SessionMgr;
import com.np.order.models.EstimationConfirm;
import com.np.order.models.EstimationOrder;
import com.np.order.models.EstiomationDetail;
import com.np.order.models.EstmationCancel;
import com.np.order.models.EstmationSheet;
import com.np.order.models.InvoiceCancel;
import com.np.order.models.InvoiceDetail;
import com.np.order.models.InvoiceSheet;
import com.np.order.models.OrderAccept;
import com.np.order.models.OrderCancel;
import com.np.order.models.OrderChange;
import com.np.order.models.OrderDetail;
import com.np.order.models.OrderInvoice;
import com.np.order.models.OrderOver;
import com.np.order.models.OrderSheet;
import com.np.order.objects.DeliveryFilter;
import com.np.order.objects.DeliveryInfo;

/*
 * 配送一覧
 */
public class DeliveryMgr {
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(DeliveryMgr.class);

	/*
	 * 納期変更
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void registerDelivery(DeliveryInfo delivery) throws Exception {
		if (SessionMgr.isLogined() == false)
			return;

		Long orderId = delivery.getOrderId();
		Long detailId = delivery.getDetailId();
		@SuppressWarnings("unused")
		Long estimationId = delivery.getEstimationId();

		/*
		 * 変更納期
		 */
		String newDeliveryDate = delivery.getNewDeliveryDate();
		Criteria<OrderChange> criteriaChange = new Criteria(OrderChange.class);
		criteriaChange.and(Restrictions.eq(OrderChange.OrderId, orderId));
		criteriaChange.and(Restrictions.eq(OrderChange.DetailId, detailId));
		OrderChange orderChange = criteriaChange.get();
		if (orderChange == null) {
			orderChange = new OrderChange();
			orderChange.setDetailId(detailId);
			orderChange.setOrderId(orderId);
			orderChange.setDeliveryDate(newDeliveryDate);
			orderChange.setQuantity(delivery.getQuantity());
			TxMgr.insert(orderChange);
		} else {
			orderChange.setDeliveryDate(newDeliveryDate);
			orderChange.setQuantity(delivery.getQuantity());
			TxMgr.update(orderChange);
		}
	}

	/*
	 * 当月配送一覧
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<DeliveryInfo> getCurrentMonthDeliveryInfos() throws Exception {
		List<DeliveryInfo> deleveries = new ArrayList<DeliveryInfo>();
		/*
		 * 仕入納期（年月）⇒OrderSheetリストを取得
		 */
		Criteria<OrderDetail> criteriaOrderDetails = new Criteria(OrderDetail.class);
		Criteria<OrderSheet> criteriaOrderSheets = new Criteria(OrderSheet.class);
		/*
		 * 仕入納期範囲
		 */
		String lastDeliveryYYYYMM = UDate.getYearYYYYMM() + "31";
		criteriaOrderDetails.and(Restrictions.le(OrderDetail.DeliveryDate, lastDeliveryYYYYMM));

		// 取消した注文
		Criteria<OrderCancel> cancels = new Criteria(OrderCancel.class);
		cancels.addSelectedColumn(OrderCancel.OrderId);
		criteriaOrderDetails.and(Restrictions.notIn(OrderDetail.OrderId, cancels));
		criteriaOrderSheets.and(Restrictions.notIn(OrderDetail.OrderId, cancels));

		// 納期終了注文
		Criteria<OrderOver> overs = new Criteria(OrderOver.class);
		overs.addSelectedColumn(OrderOver.OrderId);
		criteriaOrderSheets.and(Restrictions.notIn(OrderSheet.OrderId, overs));

		Criteria<OrderAccept> accepts = new Criteria(OrderAccept.class);
		accepts.addSelectedColumn(OrderAccept.OrderId);
		// criteriaOrderDetails.and(Restrictions.notIn(OrderDetail.OrderId,
		// accepts));
		// criteriaOrderSheets.and(Restrictions.notIn(OrderDetail.OrderId,
		// accepts));

		/*
		 * 注文データを取得
		 */
		List<OrderSheet> orderSheets = criteriaOrderSheets.list();
		List<OrderDetail> orderDetails = criteriaOrderDetails.list();

		JoinKey joinKey = new JoinKey();
		joinKey.setLeftKey(OrderSheet.OrderId);
		joinKey.setRightKey(OrderSheet.OrderId);

		List<JoinedModels> orders = Matcher.innerJoin(orderSheets, orderDetails, joinKey);
		if (UList.isEmpty(orders))
			return deleveries;

		List<String> orderCds = PojoCollections.getUniqueKeyList(orders, OrderSheet.OrderCd);

		/*
		 * 見積書コードを取得
		 */
		Criteria<EstimationOrder> criteriaEstmOrder = new Criteria(EstimationOrder.class);
		criteriaEstmOrder.and(Restrictions.in(EstimationOrder.OrderCd, orderCds));
		List<EstimationOrder> estmOrders = criteriaEstmOrder.list();

		for (JoinedModels order : orders) {
			order.appendName(EstmationSheet.EstimationCd);
			String orderCd = order.getValue(OrderSheet.OrderCd);
			for (EstimationOrder estmOrder : estmOrders) {
				if (estmOrder.getOrderCd().equals(orderCd)) {
					order.setValue(EstmationSheet.EstimationCd, estmOrder.getEstimationCd());
					break;
				}
			}
		}

		List<String> estimetionCds = PojoCollections.getUniqueKeyList(estmOrders, EstimationOrder.EstimationCd);

		/*
		 * 見積書と見積書詳細を取得
		 */
		Criteria<EstmationSheet> criteriaEstmSheet = new Criteria(EstmationSheet.class);
		criteriaEstmSheet.and(Restrictions.in(EstmationSheet.EstimationCd, estimetionCds));

		Criteria<EstmationCancel> cancelEstimations = new Criteria(EstmationCancel.class);
		cancelEstimations.addSelectedColumn(EstmationCancel.EstimationId);
		criteriaEstmSheet.and(Restrictions.notIn(EstmationSheet.EstimationId, cancelEstimations));

		List<JoinedModels> estimetions = Query.queryWithDependencies(criteriaEstmSheet, EstimationConfirm.class,
				EstiomationDetail.class);
		for (JoinedModels estimetion : estimetions) {
			estimetion.appendName(OrderSheet.OrderCd);
			String estimationCd = estimetion.getValue(EstmationSheet.EstimationCd);
			for (EstimationOrder estmOrder : estmOrders) {
				if (estmOrder.getEstimationCd().equals(estimationCd)) {
					estimetion.setValue(OrderSheet.OrderCd, estmOrder.getOrderCd());
					break;
				}
			}
		}

		createResult(deleveries, estimetions, orders, lastDeliveryYYYYMM);
		return deleveries;
	}

	/*
	 * 指定月配送一覧
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<DeliveryInfo> getDeliveryInfos(DeliveryFilter filter) throws Exception {
		List<DeliveryInfo> deleveries = new ArrayList<DeliveryInfo>();

		/*
		 * 注文番号
		 */
		String orderCd = null;
		if (UString.notEmpty(filter.getOrderNo())) {
			Criteria<EstimationConfirm> estimationConfirm = new Criteria(EstimationConfirm.class);
			estimationConfirm.and(Restrictions.eq(EstimationConfirm.CustomerOrderNo, filter.getOrderNo()));
			estimationConfirm.addSelectedColumn(EstimationConfirm.EstimationId);

			Criteria<EstmationSheet> estimation = new Criteria(EstmationSheet.class);
			estimation.and(Restrictions.in(EstmationSheet.EstimationId, estimationConfirm));
			estimation.addSelectedColumn(EstmationSheet.EstimationCd);

			Criteria<EstimationOrder> order = new Criteria(EstimationOrder.class);
			order.and(Restrictions.in(EstimationOrder.EstimationCd, estimation));
			order.addSelectedColumn(EstimationOrder.OrderCd);

			EstimationOrder estimationOrder = order.get();
			if (estimationOrder != null)
				orderCd = estimationOrder.getOrderCd();
		}

		/*
		 * 仕入納期（年月）⇒OrderSheetリストを取得
		 */
		Criteria<OrderDetail> criteriaOrderDetails = new Criteria(OrderDetail.class);
		Criteria<OrderSheet> criteriaOrderSheets = new Criteria(OrderSheet.class);
		/*
		 * 仕入納期範囲(必須)
		 */
		String deliveryYYYYMM = filter.getDeliveryYear() + filter.getDeliveryMonth();
		String lastDeliveryYYYYMM = deliveryYYYYMM + "31";
		criteriaOrderDetails
				.and(Restrictions.between(OrderDetail.DeliveryDate, deliveryYYYYMM + "00", deliveryYYYYMM + "31"));

		// 取消した注文
		Criteria<OrderCancel> cancels = new Criteria(OrderCancel.class);
		cancels.addSelectedColumn(OrderCancel.OrderId);
		criteriaOrderDetails.and(Restrictions.notIn(OrderDetail.OrderId, cancels));
		criteriaOrderSheets.and(Restrictions.notIn(OrderDetail.OrderId, cancels));

		criteriaOrderSheets.and(Restrictions.gt(OrderSheet.DeliveryDate, deliveryYYYYMM + "00"));

		/*
		 * 注文番号
		 */
		if (UString.notEmpty(orderCd)) {
			criteriaOrderSheets.and(Restrictions.eq(OrderSheet.OrderCd, orderCd));
		}

		// 納期終了注文
		Criteria<OrderOver> overs = new Criteria(OrderOver.class);
		overs.addSelectedColumn(OrderOver.OrderId);
		criteriaOrderSheets.and(Restrictions.notIn(OrderSheet.OrderId, overs));

		Criteria<OrderAccept> accepts = new Criteria(OrderAccept.class);
		accepts.addSelectedColumn(OrderAccept.OrderId);
		// criteriaOrderDetails.and(Restrictions.notIn(OrderDetail.OrderId,
		// accepts));
		// criteriaOrderSheets.and(Restrictions.notIn(OrderDetail.OrderId,
		// accepts));

		/*
		 * 注文データを取得
		 */
		List<OrderSheet> orderSheets = criteriaOrderSheets.list();
		List<OrderDetail> orderDetails = criteriaOrderDetails.list();

		JoinKey joinKey = new JoinKey();
		joinKey.setLeftKey(OrderSheet.OrderId);
		joinKey.setRightKey(OrderSheet.OrderId);

		List<JoinedModels> orders = Matcher.innerJoin(orderSheets, orderDetails, joinKey);

		orders.addAll(getOrderByChangeds(deliveryYYYYMM));

		if (UList.isEmpty(orders))
			return deleveries;

		List<String> orderCds = PojoCollections.getUniqueKeyList(orders, OrderSheet.OrderCd);

		/*
		 * 見積書コードを取得
		 */
		Criteria<EstimationOrder> criteriaEstmOrder = new Criteria(EstimationOrder.class);
		criteriaEstmOrder.and(Restrictions.in(EstimationOrder.OrderCd, orderCds));
		List<EstimationOrder> estmOrders = criteriaEstmOrder.list();

		for (JoinedModels order : orders) {
			order.appendName(EstmationSheet.EstimationCd);
			orderCd = order.getValue(OrderSheet.OrderCd);
			for (EstimationOrder estmOrder : estmOrders) {
				if (estmOrder.getOrderCd().equals(orderCd)) {
					order.setValue(EstmationSheet.EstimationCd, estmOrder.getEstimationCd());
					break;
				}
			}
		}

		List<String> estimetionCds = PojoCollections.getUniqueKeyList(estmOrders, EstimationOrder.EstimationCd);

		/*
		 * 見積書と見積書詳細を取得
		 */
		Criteria<EstmationSheet> criteriaEstmSheet = new Criteria(EstmationSheet.class);
		criteriaEstmSheet.and(Restrictions.in(EstmationSheet.EstimationCd, estimetionCds));

		Criteria<EstmationCancel> cancelEstimations = new Criteria(EstmationCancel.class);
		cancelEstimations.addSelectedColumn(EstmationCancel.EstimationId);
		criteriaEstmSheet.and(Restrictions.notIn(EstmationSheet.EstimationId, cancelEstimations));

		/*
		 * 仕入先ID
		 */
		criteriaEstmSheet.and(Restrictions.eq(EstmationSheet.CustomerId, filter.getSupplierId()));

		List<JoinedModels> estimetions = Query.queryWithDependencies(criteriaEstmSheet, EstimationConfirm.class,
				EstiomationDetail.class);

		for (JoinedModels estimetion : estimetions) {
			estimetion.appendName(OrderSheet.OrderCd);
			String estimationCd = estimetion.getValue(EstmationSheet.EstimationCd);
			for (EstimationOrder estmOrder : estmOrders) {
				if (estmOrder.getEstimationCd().equals(estimationCd)) {
					estimetion.setValue(OrderSheet.OrderCd, estmOrder.getOrderCd());
					break;
				}
			}
		}

		createResult(deleveries, estimetions, orders, lastDeliveryYYYYMM);
		return deleveries;
	}

	private static void createResult(List<DeliveryInfo> deleveries, List<JoinedModels> estimetions,
			List<JoinedModels> orders, String lastDeliveryYYYYMM) {
		/*
		 * 関連情報
		 */
		List<InvoiceDetail> finisheds = getFinisheds(orders);
		List<OrderChange> changeds = getChangeds(orders);
		Map<String, List<Long>> orderCd2Invoices = getOrderCd2InvoiceIds(orders);

		List<Long> orderIds = PojoCollections.getKeyList(orders, OrderSheet.OrderId);
		List<Long> approvedIds = OrderSheetMgr.getApproved(orderIds);

		/*
		 * 見積書と注文をマッチ
		 */
		// String[] joinKeys = { EstmationSheet.EstimationCd,
		// EstiomationDetail.ItemId };
		String[] joinKeys = { OrderSheet.OrderCd, EstiomationDetail.ItemId };
		// deleveries = Matcher.innerJoin(orders, estimetions, joinKeys,
		// DeliveryInfo.class);

		deleveries.addAll(Matcher.innerJoin(estimetions, orders, joinKeys,
				new IModelFactory<DeliveryInfo, JoinedModels, JoinedModels>() {
					@Override
					public DeliveryInfo create(JoinedModels mas, JoinedModels tra) {
						Long orderId = (Long) tra.getValue(OrderSheet.OrderId);

						// log.debug(" orderId=" + orderId);
						/*
						 * 未承認注文
						 */
						if (approvedIds.contains(orderId) == false)
							return null;

						/*
						 * TODO:除く：配送完了
						 */
						String orderCd = (String) tra.getValue(OrderSheet.OrderCd);
						int orderQuantity = 0;
						if (tra.getValue(OrderDetail.Quantity) != null)
							orderQuantity = (Integer) tra.getValue(OrderDetail.Quantity);

						/*
						 * X15--0915
						 */
						// log.debug(orderCd + " => " + orderQuantity);
						List<Long> invoiceIds = orderCd2Invoices.get(orderCd);
						// log.debug("invoiceIds => " + invoiceIds);
						if (invoiceIds != null) {
							Long itemId = (Long) tra.getValue(OrderDetail.ItemId);
							// log.debug("itemId => " + itemId);
							for (InvoiceDetail finished : finisheds) {
								// log.debug("finished InvoiceId/ItemId=> " +
								// finished.getInvoiceId() + "/" +
								// finished.getItemId());
								if (invoiceIds.contains(finished.getInvoiceId()) == false)
									continue;
								if (finished.getItemId().equals(itemId) == false)
									continue;
								// log.debug("finished getQuantity=> " +
								// finished.getQuantity());
								if (finished.getQuantity() != null)
									orderQuantity -= finished.getQuantity();
							}
						}
						// log.debug(orderCd + " 配送済み=> " + orderQuantity);
						/*
						 * 配送済み
						 */
						if (orderQuantity <= 0)
							return null;

						/*
						 * 未配送
						 */
						DeliveryInfo deliveryInfo = new DeliveryInfo();

						deliveryInfo.setEstimationId((Long) mas.getValue(EstmationSheet.EstimationId));
						deliveryInfo.setOrderId((Long) tra.getValue(OrderDetail.OrderId));
						deliveryInfo.setDetailId((Long) tra.getValue(OrderDetail.DetailId));

						deliveryInfo.setCustomerOrderNo(mas.getValue(EstimationConfirm.CustomerOrderNo));

						// deliveryInfo.setDeliveryDate((String)
						// mas.getValue(EstmationSheet.DeliveryLimitDays));
						deliveryInfo.setDeliveryDate((String) tra.getValue(OrderDetail.DeliveryDate));

						/*
						 * TODO
						 */
						{
							for (OrderChange changed : changeds) {
								if (changed.getOrderId().equals(deliveryInfo.getOrderId()) == false)
									continue;
								if (changed.getDetailId().equals(deliveryInfo.getDetailId()) == false)
									continue;

								deliveryInfo.setDeliveryDate(changed.getDeliveryDate());
								/*
								 * 納期は対照範囲外
								 */
								if (lastDeliveryYYYYMM.compareTo(changed.getDeliveryDate()) < 0) {
									// log.debug(" 納期は対照範囲外=" +
									// lastDeliveryYYYYMM + "/" +
									// changed.getDeliveryDate());
									return null;
								}
								break;
							}
						}

						deliveryInfo.setCustomerId((Long) mas.getValue(EstmationSheet.CustomerId));
						deliveryInfo.setCustomerName((String) mas.getValue(EstmationSheet.CustomerName));

						deliveryInfo.setItemId((Long) tra.getValue(OrderDetail.ItemId));
						deliveryInfo.setItemName((String) tra.getValue(OrderDetail.Name));
						deliveryInfo.setModelCd((String) tra.getValue(OrderDetail.ModelCd));
						deliveryInfo.setPartsCd((String) tra.getValue(OrderDetail.PartsCd));

						/*
						 * 販売金額：受注数量
						 */
						deliveryInfo.setQuantity(orderQuantity);

						/*
						 * 販売金額：見積書の価格
						 */
						deliveryInfo.setUnitPrice((Money) mas.getValue(EstiomationDetail.UnitPrice));

						deliveryInfo.setCurrency((String) tra.getValue(OrderSheet.Currency));

						deliveryInfo.getUnitPrice().setCurrency(deliveryInfo.getCurrency());

						Money amount = deliveryInfo.getUnitPrice().copy();
						amount.multiply(deliveryInfo.getQuantity());
						deliveryInfo.setAmount(amount);

						deliveryInfo.setOrderCd(orderCd);
						deliveryInfo.setSupplierName((String) tra.getValue(OrderSheet.SupplierName));

						return deliveryInfo;
					}

				}));

		/*
		 * ソード
		 */
		String[] sortKeys = { OrderDetail.DeliveryDate, EstimationConfirm.CustomerOrderNo, OrderDetail.PartsCd };
		Sortter.sort(deleveries, sortKeys);

	}

	/*
	 * 納期変更
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<OrderChange> getChangeds(List<JoinedModels> orders) {
		List<Long> orderIds = PojoCollections.getKeyList(orders, OrderSheet.OrderId);

		Criteria<OrderChange> criteriaChange = new Criteria(OrderChange.class);
		criteriaChange.and(Restrictions.in(OrderChange.OrderId, orderIds));
		// criteriaChange.and(Restrictions.eq(OrderChange.DetailId,
		// deliveryInfo.getDetailId()));

		return criteriaChange.list();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List<JoinedModels> getOrderByChangeds(String deliveryYYYYMM) {
		Criteria<OrderChange> criteriaChange = new Criteria(OrderChange.class);
		criteriaChange
				.and(Restrictions.between(OrderChange.DeliveryDate, deliveryYYYYMM + "00", deliveryYYYYMM + "31"));
		List<OrderChange> changes = criteriaChange.list();

		List<Long> orderIds = PojoCollections.getUniqueKeyList(changes, OrderChange.OrderId);
		List<Long> orderDetailIds = PojoCollections.getUniqueKeyList(changes, OrderChange.DetailId);

		Criteria<OrderSheet> criteriaSheet = new Criteria(OrderSheet.class);
		criteriaSheet.and(Restrictions.in(OrderSheet.OrderId, orderIds));
		List<OrderSheet> orderSheets = criteriaSheet.list();

		Criteria<OrderDetail> criteriaDetail = new Criteria(OrderDetail.class);
		criteriaDetail.and(Restrictions.in(OrderDetail.DetailId, orderDetailIds));
		List<OrderDetail> orderDetails = criteriaDetail.list();

		JoinKey joinKey = new JoinKey();
		joinKey.setLeftKey(OrderSheet.OrderId);
		joinKey.setRightKey(OrderSheet.OrderId);
		List<JoinedModels> orders = Matcher.innerJoin(orderSheets, orderDetails, joinKey);
		return orders;
	}

	/*
	 * OrderCd -> InvoiceIds
	 */
	private static Map<String, List<Long>> getOrderCd2InvoiceIds(List<JoinedModels> orders) {
		Map<String, String> invoiceToOrderCodes = getInvoiceOrderCodeMap(orders);
		Map<String, Long> invoiceCodes = getInvoiceCodeMap(invoiceToOrderCodes);

		Map<String, List<Long>> orderCd2Invoices = new HashMap<String, List<Long>>();
		for (Map.Entry<String, String> invoiceToOrderCode : invoiceToOrderCodes.entrySet()) {
			String invoiceCd = invoiceToOrderCode.getKey();
			String orderCd = invoiceToOrderCode.getValue();
			if (orderCd2Invoices.containsKey(orderCd) == false) {
				orderCd2Invoices.put(orderCd, new ArrayList<Long>());
			}
			if (invoiceCodes.containsKey(invoiceCd))
				orderCd2Invoices.get(orderCd).add(invoiceCodes.get(invoiceCd));
		}

		return orderCd2Invoices;
	}

	/*
	 * InvpiceCd -> OrderCd
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map<String, String> getInvoiceOrderCodeMap(List<JoinedModels> orders) {
		List<String> orderCds = PojoCollections.getKeyList(orders, OrderSheet.OrderCd);

		// 配送した注文
		Criteria<OrderInvoice> orderInvoices = new Criteria(OrderInvoice.class);
		orderInvoices.and(Restrictions.in(OrderSheet.OrderCd, orderCds));

		List<OrderInvoice> invoices = orderInvoices.list();

		Map<String, String> result = new HashMap<String, String>();
		for (OrderInvoice invoice : invoices) {
			result.put(invoice.getInvoiceCd(), invoice.getOrderCd());
		}
		return result;
	}

	/*
	 * InvoiceCd -> InvoiceId
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map<String, Long> getInvoiceCodeMap(Map<String, String> codeMap) {
		List<String> invoiceCds = Arrays.asList(codeMap.keySet().toArray(new String[0]));

		Criteria<InvoiceCancel> cancels = new Criteria(InvoiceCancel.class);
		cancels.addSelectedColumn(InvoiceCancel.InvoiceId);

		Criteria<InvoiceSheet> criteriaInvoiceSheets = new Criteria(InvoiceSheet.class);
		criteriaInvoiceSheets.addSelectedColumn(InvoiceSheet.InvoiceId);
		criteriaInvoiceSheets.addSelectedColumn(InvoiceSheet.InvoiceCd);

		criteriaInvoiceSheets.and(Restrictions.in(InvoiceSheet.InvoiceCd, invoiceCds));
		criteriaInvoiceSheets.and(Restrictions.notIn(InvoiceSheet.InvoiceId, cancels));

		List<InvoiceSheet> invoices = criteriaInvoiceSheets.list();

		Map<String, Long> result = new HashMap<String, Long>();
		for (InvoiceSheet invoice : invoices) {
			result.put(invoice.getInvoiceCd(), invoice.getInvoiceId());
		}
		return result;
	}

	/*
	 * 配送完了
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List<InvoiceDetail> getFinisheds(List<JoinedModels> orders) {
		List<String> orderCds = PojoCollections.getKeyList(orders, OrderSheet.OrderCd);

		// 配送した注文
		Criteria<OrderInvoice> orderInvoices = new Criteria(OrderInvoice.class);
		orderInvoices.and(Restrictions.in(OrderSheet.OrderCd, orderCds));
		orderInvoices.addSelectedColumn(OrderInvoice.InvoiceCd);

		Criteria<InvoiceCancel> cancels = new Criteria(InvoiceCancel.class);
		cancels.addSelectedColumn(InvoiceCancel.InvoiceId);

		Criteria<InvoiceSheet> criteriaInvoiceSheets = new Criteria(InvoiceSheet.class);
		criteriaInvoiceSheets.addSelectedColumn(InvoiceSheet.InvoiceId);
		criteriaInvoiceSheets.and(Restrictions.in(InvoiceSheet.InvoiceCd, orderInvoices));
		criteriaInvoiceSheets.and(Restrictions.notIn(InvoiceSheet.InvoiceId, cancels));

		Criteria<InvoiceDetail> criteriaInvoiceDetails = new Criteria(InvoiceDetail.class);
		// criteriaInvoiceDetails.and(Restrictions.eq(InvoiceDetail.ItemId,
		// (Long) tra.getValue(OrderDetail.ItemId)));
		criteriaInvoiceDetails.and(Restrictions.in(InvoiceDetail.InvoiceId, criteriaInvoiceSheets));
		List<InvoiceDetail> invoiceDetails = criteriaInvoiceDetails.list();
		return invoiceDetails;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	private static List<InvoiceDetail> getFinisheds(String orderCd) {
		// 配送した注文
		Criteria<OrderInvoice> orderInvoices = new Criteria(OrderInvoice.class);
		orderInvoices.and(Restrictions.eq(OrderSheet.OrderCd, orderCd));
		orderInvoices.addSelectedColumn(OrderInvoice.InvoiceCd);

		Criteria<InvoiceCancel> cancels = new Criteria(InvoiceCancel.class);
		cancels.addSelectedColumn(InvoiceCancel.InvoiceId);

		Criteria<InvoiceSheet> criteriaInvoiceSheets = new Criteria(InvoiceSheet.class);
		criteriaInvoiceSheets.addSelectedColumn(InvoiceSheet.InvoiceId);
		criteriaInvoiceSheets.and(Restrictions.in(InvoiceSheet.InvoiceCd, orderInvoices));
		criteriaInvoiceSheets.and(Restrictions.notIn(InvoiceSheet.InvoiceId, cancels));

		Criteria<InvoiceDetail> criteriaInvoiceDetails = new Criteria(InvoiceDetail.class);
		// criteriaInvoiceDetails.and(Restrictions.eq(InvoiceDetail.ItemId,
		// (Long) tra.getValue(OrderDetail.ItemId)));
		criteriaInvoiceDetails.and(Restrictions.in(InvoiceDetail.InvoiceId, criteriaInvoiceSheets));
		List<InvoiceDetail> invoiceDetails = criteriaInvoiceDetails.list();
		return invoiceDetails;
	}

}
