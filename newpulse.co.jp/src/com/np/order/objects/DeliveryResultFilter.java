package com.np.order.objects;

import com.np.order.action.DateFormatter;

public class DeliveryResultFilter {
	/*
	 * Invoice番号
	 */
	private String invoiceCd;
	/*
	 * 得意先名
	 */
	private Long customerId;

	/*
	 * 期間
	 */
	private String dateFrom;
	private String dateTo;

	public String getInvoiceCd() {
		return invoiceCd;
	}

	public void setInvoiceCd(String invoiceCd) {
		this.invoiceCd = invoiceCd;
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

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

}
