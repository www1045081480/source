package com.np.order.objects;

import com.np.order.Money;
import com.np.order.action.DateFormatter;

/*
 * 未承認見注文
 */
public class NoApproveOrder {
	private Long orderId;

	/* 注文番号 */
	private String orderCd;

	/* 作成日期 */
	private String issueDate;
	/* 言語 */
	private String langFlg;
	/* 仕入先名 */
	private String supplierName;
	/* 品名 */
	private String name;
	private String modelCd;
	/* 数量 */
	private Integer quantity;
	/* 合計金額 */
	private Money amount;
	/* 納期 */
	private String arriveDate;
	/* 支払 */
	private Money patment;
	/* 承認状態 */
	private String approvedStates;

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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Money getAmount() {
		return amount;
	}

	public void setAmount(Money amount) {
		this.amount = amount;
	}

	public String getArriveDate() {
		return arriveDate;
	}

	public void setArriveDate(String arriveDate) {
		arriveDate = DateFormatter.toView(arriveDate);
		this.arriveDate = arriveDate;
	}

	public Money getPatment() {
		return patment;
	}

	public void setPatment(Money patment) {
		this.patment = patment;
	}

	public String getApprovedStates() {
		return approvedStates;
	}

	public void setApprovedStates(String approvedStates) {
		this.approvedStates = approvedStates;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLangFlg() {
		return langFlg;
	}

	public void setLangFlg(String langFlg) {
		this.langFlg = langFlg;
	}

}
