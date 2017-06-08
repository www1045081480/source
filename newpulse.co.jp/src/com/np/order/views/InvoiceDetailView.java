package com.np.order.views;

public class InvoiceDetailView {
	private Long detailId;

	private Long invoiceId;

	private Long itemId;

	private String name;

	private String currency;
	private String type;

	private String partsCd;

	private String modelCd;

	private Integer quantity;

	private String unit;

	private Object unitPrice;

	private Object amount;

	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPartsCd() {
		return partsCd;
	}

	public void setPartsCd(String partsCd) {
		this.partsCd = partsCd;
	}

	public String getModelCd() {
		return modelCd;
	}

	public void setModelCd(String modelCd) {
		this.modelCd = modelCd;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Object getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Object unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Object getAmount() {
		return amount;
	}

	public void setAmount(Object amount) {
		this.amount = amount;
	}
}
