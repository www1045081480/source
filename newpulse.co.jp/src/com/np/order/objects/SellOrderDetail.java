package com.np.order.objects;

import java.util.List;

public class SellOrderDetail {
	private List<SellOrderInfo> details;
	private SellOrderSummary summary;

	public List<SellOrderInfo> getDetails() {
		return details;
	}

	public void setDetails(List<SellOrderInfo> details) {
		this.details = details;
	}

	public SellOrderSummary getSummary() {
		return summary;
	}

	public void setSummary(SellOrderSummary summary) {
		this.summary = summary;
	}
}
