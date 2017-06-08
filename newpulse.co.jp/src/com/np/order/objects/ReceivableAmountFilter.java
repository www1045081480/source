package com.np.order.objects;

public class ReceivableAmountFilter {
	private Long customerId;
	private String balanceYear;
	private String balanceMonth;
	private String currency;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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
