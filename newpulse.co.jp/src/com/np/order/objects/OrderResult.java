package com.np.order.objects;

import com.np.order.Money;
import com.np.order.action.DateFormatter;

/*
 * 注文実績
 * 
 * 検索条件：
 * 注文番号
 * 仕入先
 * 期間
 * 商品種別
 */
public class OrderResult {
	private Long orderId;
	/*
	 * 注文書番号
	 */
	private String orderCd;

	/*
	 * 作成日付
	 */
	private String issueDate;
	/* 言語 */
	private String langFlg;
	/*
	 * 仕入先名
	 */
	private String supplierName;
	private Long supplierId;
	/*
	 * 品名/数量
	 */
	private String modelCd;

	/*
	 * 商品種別 Item: categoryType
	 */
	private String categoryType;

	/*
	 * 合計金額
	 */
	private Money amount;
	/*
	 * 納期
	 */
	private String deliveryDate;
	/*
	 * 支払
	 */
	private String payment;
	/*
	 * 進捗:承認済み 入荷完了 支払済み
	 * 
	 */
	private String status;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderCd() {
		return orderCd;
	}

	public void setOrderCd(String orderCd) {
		this.orderCd = orderCd;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		issueDate = DateFormatter.toView(issueDate);
		this.issueDate = issueDate;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getModelCd() {
		return modelCd;
	}

	public void setModelCd(String modelCd) {
		this.modelCd = modelCd;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public Money getAmount() {
		return amount;
	}

	public void setAmount(Money amount) {
		this.amount = amount;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		deliveryDate = DateFormatter.toView(deliveryDate);
		this.deliveryDate = deliveryDate;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getLangFlg() {
		return langFlg;
	}

	public void setLangFlg(String langFlg) {
		this.langFlg = langFlg;
	}
}
