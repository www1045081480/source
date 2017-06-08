package com.np.order.objects;

import com.np.order.Money;
import com.np.order.action.DateFormatter;

/*
 * 发货一览表
 */
public class ReceivableAmount {
	private Long detailId;
	private String currency;
	/*
	 * 发货日期
	 */
	private String deliveryDate = "";
	/*
	 * 订单号
	 */
	private String orderNo = "";
	/*
	 * 新安发票号
	 */
	private String xinanOrderNo = "";
	/*
	 * 内容
	 */
	private String content = "";
	/*
	 * 新增销售
	 */
	private Money earningAmount = new Money(0);
	/*
	 * 己収回款金额
	 */
	private Money receivedAmount = new Money(0);
	/*
	 * 回款金额
	 */
	private Money receiveAmount = new Money(0);
	/*
	 * 期末余额
	 */
	private Money endingBalance = new Money(0);
	/*
	 * 取引No
	 */
	private String tradingNo = "";
	/*
	 * 备注
	 */
	private String note = "";

	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		deliveryDate = DateFormatter.toView(deliveryDate);
		this.deliveryDate = deliveryDate;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getXinanOrderNo() {
		return xinanOrderNo;
	}

	public void setXinanOrderNo(String xinanOrderNo) {
		if (xinanOrderNo != null)
			this.xinanOrderNo = xinanOrderNo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Money getEarningAmount() {
		return earningAmount;
	}

	public void setEarningAmount(Money earningAmount) {
		this.earningAmount = earningAmount;
	}

	public Money getEndingBalance() {
		return endingBalance;
	}

	public void setEndingBalance(Money endingBalance) {
		this.endingBalance = endingBalance;
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

	public Money getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(Money receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	public Money getReceivedAmount() {
		return receivedAmount;
	}

	public void setReceivedAmount(Money receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
