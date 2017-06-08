package com.np.order.objects;

import com.np.order.Money;
import com.np.order.action.DateFormatter;

/*
 * 未承認見積書
 */
public class NoApproveEstimation {
	private Long estimationId;

	/* 見積番号 */
	private String estimationCd;
	/* 言語 */
	private String langFlg;

	/* 作成日期 */
	private String issueDate;

	/* 得意先簡名 */
	private String customerName;

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
	private Integer patment;
	/* 承認状態 */
	private String approvedStates;

	/*  */
	private String paymentCondition;

	private String paymentMethord;

	public Long getEstimationId() {
		return estimationId;
	}

	public void setEstimationId(Long estimationId) {
		this.estimationId = estimationId;
	}

	public String getEstimationCd() {
		return estimationCd;
	}

	public void setEstimationCd(String estimationCd) {
		this.estimationCd = estimationCd;
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
		this.arriveDate = arriveDate;
	}

	public Integer getPatment() {
		return patment;
	}

	public void setPatment(Integer patment) {
		this.patment = patment;
	}

	public String getApprovedStates() {
		return approvedStates;
	}

	public void setApprovedStates(String approvedStates) {
		this.approvedStates = approvedStates;
	}

	public String getPaymentCondition() {
		return paymentCondition;
	}

	public void setPaymentCondition(String paymentCondition) {
		this.paymentCondition = paymentCondition;
	}

	public String getPaymentMethord() {
		return paymentMethord;
	}

	public void setPaymentMethord(String paymentMethord) {
		this.paymentMethord = paymentMethord;
	}

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
