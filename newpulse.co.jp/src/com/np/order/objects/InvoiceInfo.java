package com.np.order.objects;

import com.np.order.Money;
import com.np.order.action.DateFormatter;

/*
 * INVOICE一覧
 * 
 * 検索条件：
 */
public class InvoiceInfo {
	private Long invoiceId;
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
	/*
	 * 品名/数量 InvoiceDetail : modelCd + quantity
	 */
	private String modelCd;
	/*
	 * 総金額（JPN/USD）
	 */
	private Money amount;

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

	public String getModelCd() {
		return modelCd;
	}

	public void setModelCd(String modelCd) {
		this.modelCd = modelCd;
	}

}
