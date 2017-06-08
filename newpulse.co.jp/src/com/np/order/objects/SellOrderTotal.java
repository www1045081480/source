package com.np.order.objects;

import com.np.order.Money;

public class SellOrderTotal {
	/*
	 * 仕入合計
	 */
	private Money requireAmount;
	/*
	 * 売上金額
	 */
	private Money sellAmount;
	/*
	 * 消費税
	 */
	private Money sellExcise;
	/*
	 * 粗利益率
	 */
	private long grossMargin;
	/*
	 * 粗利
	 */
	private Money grossProfit;

	public SellOrderTotal(String currency) {
		requireAmount = new Money(currency, 0);
		sellAmount = new Money(currency, 0);
		sellExcise = new Money(currency, 0);
		grossMargin = 0;
		grossProfit = new Money(currency, 0);
	}

	public void sum(SellOrderInfo obj) {
		requireAmount.add(obj.getRequireAmount());
		sellAmount.add(obj.getSellAmount());
		sellExcise.add(obj.getSellExcise());

		grossMargin = obj.getGrossMargin();
		grossProfit.add(obj.getGrossProfit());

	}

	public Money getRequireAmount() {
		return requireAmount;
	}

	public void setRequireAmount(Money requireAmount) {
		this.requireAmount = requireAmount;
	}

	public Money getSellAmount() {
		return sellAmount;
	}

	public void setSellAmount(Money sellAmount) {
		this.sellAmount = sellAmount;
	}

	public Money getSellExcise() {
		return sellExcise;
	}

	public void setSellExcise(Money sellExcise) {
		this.sellExcise = sellExcise;
	}

	public long getGrossMargin() {
		return grossMargin;
	}

	public void setGrossMargin(long grossMargin) {
		this.grossMargin = grossMargin;
	}

	public Money getGrossProfit() {
		return grossProfit;
	}

	public void setGrossProfit(Money grossProfit) {
		this.grossProfit = grossProfit;
	}
}
