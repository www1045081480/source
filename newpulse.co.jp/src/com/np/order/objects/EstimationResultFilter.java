package com.np.order.objects;

import com.np.order.action.DateFormatter;

public class EstimationResultFilter {
	/*
	 * 見積番号
	 */
	private String estimationCd;
	/*
	 * 得意先
	 */
	private String customerName;
	/*
	 * 商品種別
	 */
	private String categoryType;
	/*
	 * 期間
	 */
	private String dateFrom;
	private String dateTo;

	public String getEstimationCd() {
		return estimationCd;
	}

	public void setEstimationCd(String estimationCd) {
		this.estimationCd = estimationCd;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		dateFrom = DateFormatter.toModel(dateFrom);
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		dateTo = DateFormatter.toModel(dateTo);
		this.dateTo = dateTo;
	}

}
