package com.np.order.objects;

public class PayableAmountFilter {
	private Long supplierId;
	private String balanceYear;
	private String balanceMonth;
	private String currency;

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getBalanceYear() {
		return balanceYear;
	}

	public void setBalanceYear(String balanceYear) {
		this.balanceYear = balanceYear;
	}

	public String getBalanceMonth() {
		return balanceMonth;
	}

	public void setBalanceMonth(String balanceMonth) {
		this.balanceMonth = balanceMonth;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
