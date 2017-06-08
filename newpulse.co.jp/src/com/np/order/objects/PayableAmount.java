package com.np.order.objects;

import com.np.order.Money;
import com.np.order.action.DateFormatter;

/*
 * 買掛金一覧
 */
public class PayableAmount {
	private Long detailId;
	private Long supplierId;

	private String currency;
	/*
	 * 入荷日・支払日
	 */
	private String payDate = "";
	/*
	 * 品名
	 */
	private String modelCd = "";
	/*
	 * 注文番号
	 */
	private String orderNo = "";
	/*
	 * 購入金額
	 */
	private Money purchaseAmount = new Money(0);
	/*
	 * 消費税
	 */
	private Money exciseAmount = new Money(0);
	/*
	 * 小計
	 */
	private Money totalAmount = new Money(0);
	/*
	 * 支払金額
	 */
	private Money payAmount = new Money(0);
	/*
	 * 残高
	 */
	private Money balance = new Money(0);
	/*
	 * 取引No.
	 */
	private String tradingNo = "";
	/*
	 * 備考
	 */
	private String note = "";

	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		payDate = DateFormatter.toView(payDate);
		this.payDate = payDate;
	}

	public String getModelCd() {
		return modelCd;
	}

	public void setModelCd(String modelCd) {
		this.modelCd = modelCd;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Money getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(Money purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	public Money getExciseAmount() {
		return exciseAmount;
	}

	public void setExciseAmount(Money exciseAmount) {
		this.exciseAmount = exciseAmount;
	}

	public Money getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Money totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Money getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Money payAmount) {
		this.payAmount = payAmount;
	}

	public Money getBalance() {
		return balance;
	}

	public void setBalance(Money balance) {
		this.balance = balance;
	}

	public String getTradingNo() {
		return tradingNo;
	}

	public void setTradingNo(String tradingNo) {
		this.tradingNo = tradingNo;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
