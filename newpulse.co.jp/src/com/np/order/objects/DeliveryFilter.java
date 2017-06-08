package com.np.order.objects;

public class DeliveryFilter {
	private String deliveryYear;
	private String deliveryMonth;
	private Long supplierId;
	private String orderNo;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getDeliveryYear() {
		return deliveryYear;
	}

	public void setDeliveryYear(String deliveryYear) {
		this.deliveryYear = deliveryYear;
	}

	public String getDeliveryMonth() {
		return deliveryMonth;
	}

	public void setDeliveryMonth(String deliveryMonth) {
		if (deliveryMonth != null && deliveryMonth.length() == 1)
			deliveryMonth = "0" + deliveryMonth;
		this.deliveryMonth = deliveryMonth;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

}
