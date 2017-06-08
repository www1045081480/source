package com.np.order.objects;

import com.np.order.Money;
import com.np.order.action.DateFormatter;

/*
 * 月度販売仕入取引対照
 */
public class SellOrderInfo {
	private Long orderDetailId;
	private Long invoiceDetailId;
	private Long orderId;
	private Long invoiceId;
	private Long supplierId;

	/*
	 * 幣種
	 */
	private String currency;
	/*
	 * 円、ドル、元、CIF
	 */
	private String flag;
	private String supplierName;

	private String customerName;
	private String orderCd;
	/*
	 * 品名／型番
	 */
	private String modeCd;
	/*
	 * 区分：設備、材料
	 */
	private String type;
	/*
	 * 新安発注日付
	 */
	private String xinanOrderDate;
	/*
	 * 発注数
	 */
	private Integer orderQuantity;
	/*
	 * 仕入金額
	 */
	private Money orderAmount;

	/*
	 * 消費税
	 */
	private Money orderExcise;
	/*
	 * 調整金額
	 */
	private Money adjustAmount;
	/*
	 * 送料
	 */
	private Money deliveryAmount;
	/*
	 * 仕入合計
	 */
	private Money requireAmount;
	/*
	 * 支払金額(三段)
	 */
	private String payAmounts;
	/*
	 * 支払日(三段)
	 */
	private String payDates;
	/*
	 * 納入日
	 */
	private String deliveryDate;
	private String invoiceCd;
	/*
	 * 取引No.
	 */
	private String tradingNo;

	/*
	 * 売上日付
	 */
	private String sellDate;
	/*
	 * 売上金額
	 */
	private Money sellAmount;
	/*
	 * 消費税
	 */
	private Money sellExcise;
	/*
	 * 粗利益率
	 */
	private Long grossMargin;
	/*
	 * 粗利
	 */
	private Money grossProfit;

	/*
	 * 入金日
	 */
	private String receivedDate;
	/*
	 * 入金金額
	 */
	private Money receivedAmount;

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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getOrderCd() {
		return orderCd;
	}

	public void setOrderCd(String orderCd) {
		this.orderCd = orderCd;
	}

	public String getModeCd() {
		return modeCd;
	}

	public void setModeCd(String modeCd) {
		this.modeCd = modeCd;
	}

	public String getXinanOrderDate() {
		return xinanOrderDate;
	}

	public void setXinanOrderDate(String xinanOrderDate) {
		xinanOrderDate = DateFormatter.toView(xinanOrderDate);
		this.xinanOrderDate = xinanOrderDate;
	}

	public Integer getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(Integer orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public Money getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Money orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Money getOrderExcise() {
		return orderExcise;
	}

	public void setOrderExcise(Money orderExcise) {
		this.orderExcise = orderExcise;
	}

	public Money getAdjustAmount() {
		return adjustAmount;
	}

	public void setAdjustAmount(Money adjustAmount) {
		this.adjustAmount = adjustAmount;
	}

	public Money getDeliveryAmount() {
		return deliveryAmount;
	}

	public void setDeliveryAmount(Money deliveryAmount) {
		this.deliveryAmount = deliveryAmount;
	}

	public Money getRequireAmount() {
		return requireAmount;
	}

	public void setRequireAmount(Money totalAmount) {
		this.requireAmount = totalAmount;
	}

	public String getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(String receivedDate) {
		receivedDate = DateFormatter.toView(receivedDate);
		this.receivedDate = receivedDate;
	}

	public String getInvoiceCd() {
		return invoiceCd;
	}

	public void setInvoiceCd(String invoiceCd) {
		this.invoiceCd = invoiceCd;
	}

	/**
	 * @deprecated Use {@link #getTradingNo()} instead
	 */
	public String getOrderNo() {
		return getTradingNo();
	}

	public String getTradingNo() {
		return tradingNo;
	}

	/**
	 * @deprecated Use {@link #setTradingNo(String)} instead
	 */
	public void setOrderNo(String orderNo) {
		setTradingNo(orderNo);
	}

	public void setTradingNo(String orderNo) {
		this.tradingNo = orderNo;
	}

	public String getSellDate() {
		return sellDate;
	}

	public void setSellDate(String sellDate) {
		sellDate = DateFormatter.toView(sellDate);
		this.sellDate = sellDate;
	}

	public Money getSellAmount() {
		return sellAmount;
	}

	public void setSellAmount(Money sellAmount) {
		this.sellAmount = sellAmount;
	}

	public Money getSellExcise() {
		return sellExcise;
	}

	public void setSellExcise(Money sellExcise) {
		this.sellExcise = sellExcise;
	}

	public Long getGrossMargin() {
		return grossMargin;
	}

	public void setGrossMargin(Long grossMargin) {
		this.grossMargin = grossMargin;
	}

	public Money getGrossProfit() {
		return grossProfit;
	}

	public void setGrossProfit(Money grossProfit) {
		this.grossProfit = grossProfit;
	}

	public Money getReceivedAmount() {
		return receivedAmount;
	}

	public void setReceivedAmount(Money receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		deliveryDate = DateFormatter.toView(deliveryDate);
		this.deliveryDate = deliveryDate;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPayAmounts() {
		return payAmounts;
	}

	public void setPayAmounts(String payAmounts) {
		this.payAmounts = payAmounts;
	}

	public String getPayDates() {
		return payDates;
	}

	public void setPayDates(String payDates) {
		this.payDates = payDates;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

}
