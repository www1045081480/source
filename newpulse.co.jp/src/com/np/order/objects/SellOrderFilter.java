package com.np.order.objects;

public class SellOrderFilter {
	/*
	 * 年月
	 */

	private String shipYear;
	private String shipMonth;

	public String getShipYear() {
		return shipYear;
	}

	public void setShipYear(String shipYear) {
		this.shipYear = shipYear;
	}

	public String getShipMonth() {
		return shipMonth;
	}

	public void setShipMonth(String shipMonth) {
		if (shipMonth != null && shipMonth.length() == 1)
			shipMonth = "0" + shipMonth;
		this.shipMonth = shipMonth;
	}
}
