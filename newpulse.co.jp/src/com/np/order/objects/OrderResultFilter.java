package com.np.order.objects;

import com.np.order.action.DateFormatter;

public class OrderResultFilter {
	/*
	 * 注文番号
	 */
	private String orderCd;
	/*
	 * 仕入先
	 */
	private String supplierName;
	/*
	 * 商品種別
	 */
	private String categoryType;
	/*
	 * 期間
	 */
	private String dateFrom;
	private String dateTo;

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
