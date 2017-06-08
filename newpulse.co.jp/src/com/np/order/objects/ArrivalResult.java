package com.np.order.objects;

import com.np.order.Money;
import com.np.order.action.DateFormatter;

/*
 * 　入荷済み一覧(入荷済（買掛金)統計一覧)
 * 
 * 検索条件：
 * 平成27年度5月
 * 
 */
public class ArrivalResult {
	private Long payId;
	/*
	 * 日付
	 */
	private String date;
	/*
	 * 円／ドル
	 */
	private String currency;
	/*
	 * 仕入先
	 * 
	 */
	private Long supplierId;
	private String supplierName;
	/*
	 * 商品種別
	 */
	private String categoryType = "";
	/*
	 * 今月注文金額
	 */
	private Money orderAmountOfThisMonth = new Money(0);
	/*
	 * 出金金額
	 */
	private Money payAmount = new Money(0);
	/*
	 * 期末残高
	 */
	private Money balance = new Money(0);
	/*
	 * ドル残高
	 */
	private Money balanceOfDollar = new Money(Currency.US.getCode(), 0);
	/*
	 * 人民元残高
	 */
	private Money balanceOfChinese = new Money(Currency.China.getCode(), 0);
	/*
	 * 期限超未払い（注文番号/金額）
	 */
	private String orderCdAmount = "";
	/*
	 * 備考
	 */
	private String note;

	public Long getPayId() {
		return payId;
	}

	public void setPayId(Long payId) {
		this.payId = payId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public Money getOrderAmountOfThisMonth() {
		return orderAmountOfThisMonth;
	}

	public void setOrderAmountOfThisMonth(Money orderAmountOfThisMonth) {
		this.orderAmountOfThisMonth = orderAmountOfThisMonth;
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

	public Money getBalanceOfDollar() {
		return balanceOfDollar;
	}

	public void setBalanceOfDollar(Money balanceOfDollar) {
		this.balanceOfDollar = balanceOfDollar;
	}

	public String getOrderCdAmount() {
		return orderCdAmount;
	}

	public void setOrderCdAmount(String orderCdAmount) {
		this.orderCdAmount = orderCdAmount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		date = DateFormatter.toView(date);
		this.date = date;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Money getBalanceOfChinese() {
		return balanceOfChinese;
	}

	public void setBalanceOfChinese(Money balanceOfChinese) {
		this.balanceOfChinese = balanceOfChinese;
	}
}
