package com.np.order.objects;

import com.np.order.Money;
import com.np.order.action.DateFormatter;

/*
 * 出荷済み一覧(出荷済（売掛金）統計一覧)
 * 
 * 検索条件：
 *  平成27年度5月
 */
public class ShipResult {
	private Long estimationDetailId;
	private Long invoiceDetailId;
	/*
	 * 日付
	 */
	private String date;
	/*
	 * 円／ドル
	 */
	private String currency;
	/*
	 * 得意先
	 */
	private Long customerId;
	private String customerName;
	/*
	 * 商品種別
	 * 
	 * 合計: 得意先、日本円・ドル、商品種別
	 */
	private String categoryType = "";

	/*
	 * 今月販売金額
	 */
	private Money sellAmountOfThisMonth = new Money(0);
	/*
	 * 入金金額
	 */
	private Money depositAmount = new Money(0);
	/*
	 * 期末残高（日本円）
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
	 * 四ヶ月超未回収（見積番号/金額）
	 */
	private String estimationCdAmount = "";
	/*
	 * 備考
	 */
	private String note;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public Money getSellAmountOfThisMonth() {
		return sellAmountOfThisMonth;
	}

	public void setSellAmountOfThisMonth(Money sellAmountOfThisMonth) {
		this.sellAmountOfThisMonth = sellAmountOfThisMonth;
	}

	public Money getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(Money depositAmount) {
		this.depositAmount = depositAmount;
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

	public String getEstimationCdAmount() {
		return estimationCdAmount;
	}

	public void setEstimationCdAmount(String estimationCdAmount) {
		this.estimationCdAmount = estimationCdAmount;
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

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getEstimationDetailId() {
		return estimationDetailId;
	}

	public void setEstimationDetailId(Long estimationDetailId) {
		this.estimationDetailId = estimationDetailId;
	}

	public Long getInvoiceDetailId() {
		return invoiceDetailId;
	}

	public void setInvoiceDetailId(Long invoiceDetailId) {
		this.invoiceDetailId = invoiceDetailId;
	}

	public Money getBalanceOfChinese() {
		return balanceOfChinese;
	}

	public void setBalanceOfChinese(Money balanceOfChinese) {
		this.balanceOfChinese = balanceOfChinese;
	}
}
