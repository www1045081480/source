package com.np.order.objects;

import com.np.order.Money;
import com.np.order.action.DateFormatter;

/*
 * 未注文見積書
 */
public class NoOrderEstimation {
	private Long estimationId;
	private Long detailId;

	/* 未承認見積書 */
	private String estimationCd;

	/*
	 * 販売先 EstimationSheet : CustomerName
	 */
	private String sellerName;
	/*
	 * 受注日 EstimationConfirm : ConfirmDate
	 */
	private String orderDate;
	/*
	 * 納期 EstimationSheet : DeliveryLimitDays
	 */
	private String acceptDate;
	/* 品ID */
	private Long itemId;
	/* 品名 */
	private String itemName;
	/* 仕様・寸法 */
	private String partCd;
	/* 数量 */
	private Integer quantity;
	/* 型番 */
	private String modelCd;
	/* 販売金額 */
	private Money amount;
	/* 販売金額 */
	private String currency;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Long getEstimationId() {
		return estimationId;
	}

	public void setEstimationId(Long estimationId) {
		this.estimationId = estimationId;
	}

	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public String getEstimationCd() {
		return estimationCd;
	}

	public void setEstimationCd(String estimationCd) {
		this.estimationCd = estimationCd;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		orderDate = DateFormatter.toView(orderDate);
		this.orderDate = orderDate;
	}

	public String getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(String acceptDate) {
		acceptDate = DateFormatter.toView(acceptDate);
		this.acceptDate = acceptDate;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getPartCd() {
		return partCd;
	}

	public void setPartCd(String partCd) {
		this.partCd = partCd;
	}

}
