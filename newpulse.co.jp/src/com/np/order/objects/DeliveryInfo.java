package com.np.order.objects;

import com.np.order.Money;
import com.np.order.action.DateFormatter;

/*
 * 配送一覧
 */
public class DeliveryInfo {
	private Long orderId;
	private Long estimationId;
	/*
	 * 注文詳細ID
	 */
	private Long detailId;
	/*
	 * 販売先注文番号 为estimation_confirm_tbl表中对应customer_order_no字段和
	 */
	private String customerOrderNo;
	/*
	 * 販売先
	 */
	private Long customerId;
	private String customerName;
	/*
	 * 纳期为estmation_sheet_tbl表中delivery_limit_days字段
	 */
	/*
	 * 仕入納期
	 */
	private String deliveryDate;

	/* 品ID */
	private Long itemId;
	/*
	 * 品名
	 */
	private String itemName;
	/*
	 * 型品
	 */
	private String modelCd;
	/*
	 * 品番
	 */
	private String partsCd;
	/*
	 * 数量
	 */
	private Integer quantity;
	private Money unitPrice;
	/*
	 * 販売金額
	 */
	private Money amount;

	/*
	 * 幣種
	 */
	private String currency;
	/*
	 * 注文番号
	 */
	private String orderCd;
	/*
	 * 仕入先
	 */
	private String supplierName;

	/*
	 * 変更納期
	 */
	private String newDeliveryDate;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getModelCd() {
		return modelCd;
	}

	public void setModelCd(String modelCd) {
		this.modelCd = modelCd;
	}

	public Money getAmount() {
		return amount;
	}

	public void setAmount(Money amount) {
		this.amount = amount;
	}

	public String getOrderCd() {
		return orderCd;
	}

	public void setOrderCd(String orderCd) {
		this.orderCd = orderCd;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		deliveryDate = DateFormatter.toView(deliveryDate);
		this.deliveryDate = deliveryDate;
	}

	public String getNewDeliveryDate() {
		return newDeliveryDate;
	}

	public void setNewDeliveryDate(String newDeliveryDate) {
		this.newDeliveryDate = newDeliveryDate;
	}

	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public String getPartsCd() {
		return partsCd;
	}

	public void setPartsCd(String partsCd) {
		this.partsCd = partsCd;
	}

	public String getName() {
		return itemName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getCustomerOrderNo() {
		return customerOrderNo;
	}

	public void setCustomerOrderNo(String customerOrderNo) {
		this.customerOrderNo = customerOrderNo;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getEstimationId() {
		return estimationId;
	}

	public void setEstimationId(Long estimationId) {
		this.estimationId = estimationId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Money getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Money unitPrice) {
		this.unitPrice = unitPrice;
	}

}
