package com.np.order.objects;

import com.np.order.Money;
import com.np.order.action.DateFormatter;

/*
 * 見積実績
 * 
 * 検索条件：
 * 見積番号
 * 得意先
 * 期間
 * 商品種別
 */
public class EstimationResult {

	private Long estimationId;
	/*
	 * 見積書番号
	 */
	private String estimationCd;

	/*
	 * 作成日付
	 */
	private String issueDate;
	/* 言語 */
	private String langFlg;
	/*
	 * 得意先名
	 */
	private String customerName;
	private Long customerId;

	/*
	 * 品名/数量 EstiomationDetail : modelCd + quantity
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
	private String estimationOkDays;

	/*
	 * 支払
	 */
	private String payment;

	/*
	 * 進捗: 承認済み 受注確定 注文済み 出荷完了 納品済み 支払済み
	 * 
	 */
	private String status;

	private Long regTime;
	private Long updTime;

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

	public String getEstimationOkDays() {
		return estimationOkDays;
	}

	public void setEstimationOkDays(String estimationOkDays) {
		this.estimationOkDays = estimationOkDays;
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

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getLangFlg() {
		return langFlg;
	}

	public void setLangFlg(String langFlg) {
		this.langFlg = langFlg;
	}

	public Long getRegTime() {
		return regTime;
	}

	public void setRegTime(Long regTime) {
		this.regTime = regTime;
	}

	public Long getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Long updTime) {
		this.updTime = updTime;
	}
}
