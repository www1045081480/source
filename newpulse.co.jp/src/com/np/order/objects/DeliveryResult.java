package com.np.order.objects;

import com.np.order.Money;
import com.np.order.action.DateFormatter;

/*
 * 配送実績
 * 
 * 検索条件：
 * Invoice番号
 * 得意先
 * 期間
 */
public class DeliveryResult {
	private Long invoiceId;
	private Long estimationId;
	/*
	 * Invoice番号
	 * 
	 */
	private String invoiceCd;
	/*
	 * 作成日付
	 */
	private String issueDate;
	/*
	 * 配送先
	 */
	private String customerName;
	/*
	 * 見積番号 EstimationInvoice:estimationCd
	 */
	private String estimationCd;
	/*
	 * お客様注文番号 OrderInvoice : orderCd
	 */
	private String orderCd;
	/*
	 * 配送方式:EMS/ 港運/ 航空
	 * 
	 */
	private String deliveryType;
	/*
	 * 渡し条件:FOB/ CIF
	 */
	private String shippingType;
	/*
	 * 配送会社
	 */
	private String shippingCompany;
	/*
	 * 梱包数 PackingLists
	 */
	private Integer packingNumber;
	private String itemName;
	/*
	 * 品名/数量 InvoiceDetail : modelCd + quantity
	 */
	private String modelCd;
	/*
	 * 品番
	 */
	private String partsCd;

	/*
	 * 品名
	 */
	private Integer quantity;

	/*
	 * 総金額（JPN/USD）
	 */
	private Money amount;
	/*
	 * 到着日付
	 */
	private String arrivalDate;
	/*
	 * 運賃金額
	 */
	private Money carringAmount;
	/*
	 * 承認者
	 */
	private Long approvedId;

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getInvoiceCd() {
		return invoiceCd;
	}

	public void setInvoiceCd(String invoiceCd) {
		this.invoiceCd = invoiceCd;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		issueDate = DateFormatter.toView(issueDate);
		this.issueDate = issueDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getEstimationCd() {
		return estimationCd;
	}

	public void setEstimationCd(String estimationCd) {
		this.estimationCd = estimationCd;
	}

	public String getOrderCd() {
		return orderCd;
	}

	public void setOrderCd(String orderCd) {
		this.orderCd = orderCd;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getShippingType() {
		return shippingType;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}

	public String getShippingCompany() {
		return shippingCompany;
	}

	public void setShippingCompany(String shippingCompany) {
		this.shippingCompany = shippingCompany;
	}

	public Integer getPackingNumber() {
		return packingNumber;
	}

	public void setPackingNumber(Integer packingNumber) {
		this.packingNumber = packingNumber;
	}

	public Money getAmount() {
		return amount;
	}

	public void setAmount(Money amount) {
		this.amount = amount;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		arrivalDate = DateFormatter.toView(arrivalDate);
		this.arrivalDate = arrivalDate;
	}

	public Money getCarringAmount() {
		return carringAmount;
	}

	public void setCarringAmount(Money carringAmount) {
		this.carringAmount = carringAmount;
	}

	public String getModelCd() {
		return modelCd;
	}

	public void setModelCd(String modelCd) {
		this.modelCd = modelCd;
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

	public Long getEstimationId() {
		return estimationId;
	}

	public void setEstimationId(Long estimationId) {
		this.estimationId = estimationId;
	}

	public Long getApprovedId() {
		return approvedId;
	}

	public void setApprovedId(Long approvedId) {
		this.approvedId = approvedId;
	}

	public String getPartsCd() {
		return partsCd;
	}

	public void setPartsCd(String partsCd) {
		this.partsCd = partsCd;
	}

}
