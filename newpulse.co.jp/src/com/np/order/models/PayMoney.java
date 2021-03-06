package com.np.order.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import com.np.order.Money;

/*
 * 入荷済み一覧 Model
 *
 * This class was autogenerated on:
 * [Mon Jan 11 18:47:34 JST 2016]
 *
 * [WARNING]
 * Don't edit this class directly, because it may be regenerated late.
 */
@Entity
@Table(name = "PAY_MONEY_TBL")
public class PayMoney {
	public static final String TABLE_NAME = "PAY_MONEY_TBL";

	/*
	 * ---------------------- Public Field Names Definition
	 * ----------------------------
	 */
	public static final String PayId = "PayId";
	public static final String OrderId = "OrderId";
	public static final String SupplierId = "SupplierId";
	public static final String Date = "Date";
	public static final String Currency = "Currency";
	public static final String Amount = "Amount";
	public static final String Note = "Note";
	public static final String TradingNo = "TradingNo";
	public static final String Name = "Name";
	public static final String RegTime = "RegTime";
	public static final String UpdTime = "UpdTime";

	/*
	 * ---------------------- Private Fields Definition
	 * ----------------------------
	 */
	@Id
	/*  */
	@Column(name = "PAY_ID", length = 0, nullable = false)
	private Long payId;

	/*  */
	@Column(name = "ORDER_ID", length = 0, nullable = true)
	@JoinTable(name = "OrderSheet")
	@JoinColumn(name = "OrderId")
	private Long orderId;

	/*  */
	@Column(name = "SUPPLIER_ID", length = 0, nullable = true)
	@JoinTable(name = "Supplier")
	@JoinColumn(name = "SupplierId")
	private Long supplierId;

	/*  */
	@Column(name = "DATE", length = 8, nullable = true)
	private String date;

	/* 幣種 */
	@Column(name = "CURRENCY", length = 64, nullable = true)
	private String currency;

	/*  */
	@Column(name = "AMOUNT", length = 0, nullable = true)
	private Money amount;

	/* 備考 */
	@Column(name = "NOTE", length = 64, nullable = true)
	private String note;

	/* 取引No. */
	@Column(name = "TRADING_NO", length = 64, nullable = true)
	private String tradingNo;

	/* 品名 */
	@Column(name = "NAME", length = 64, nullable = true)
	private String name;

	/*  */
	@Column(name = "REG_TIME", length = 0, nullable = true)
	private Long regTime;

	/*  */
	@Column(name = "UPD_TIME", length = 0, nullable = true)
	private Long updTime;

	/*
	 * ---------------------- Getter / Setter Methods
	 * ----------------------------
	 */

	/*
	 * Get the
	 * 
	 * @return the value of $col.ViewName
	 */
	public Long getPayId() {
		return payId;
	}

	/*
	 * Set the value of
	 * 
	 * @param payId the new value for $col.ViewName
	 */
	public void setPayId(Long payId) {
		this.payId = payId;
	}

	/*
	 * Get the
	 * 
	 * @return the value of $col.ViewName
	 */
	public Long getOrderId() {
		return orderId;
	}

	/*
	 * Set the value of
	 * 
	 * @param orderId the new value for $col.ViewName
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	/*
	 * Get the
	 * 
	 * @return the value of $col.ViewName
	 */
	public Long getSupplierId() {
		return supplierId;
	}

	/*
	 * Set the value of
	 * 
	 * @param supplierId the new value for $col.ViewName
	 */
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	/*
	 * Get the
	 * 
	 * @return the value of $col.ViewName
	 */
	public String getDate() {
		return date;
	}

	/*
	 * Set the value of
	 * 
	 * @param date the new value for $col.ViewName
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/*
	 * Get the 幣種
	 * 
	 * @return the value of 幣種
	 */
	public String getCurrency() {
		return currency;
	}

	/*
	 * Set the value of 幣種
	 * 
	 * @param currency the new value for 幣種
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/*
	 * Get the
	 * 
	 * @return the value of $col.ViewName
	 */
	public Money getAmount() {
		return amount;
	}

	/*
	 * Set the value of
	 * 
	 * @param amount the new value for $col.ViewName
	 */
	public void setAmount(Money amount) {
		this.amount = amount;
	}

	/*
	 * Get the 備考
	 * 
	 * @return the value of 備考
	 */
	public String getNote() {
		return note;
	}

	/*
	 * Set the value of 備考
	 * 
	 * @param note the new value for 備考
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/*
	 * Get the 取引No.
	 * 
	 * @return the value of 取引No.
	 */
	public String getTradingNo() {
		return tradingNo;
	}

	/*
	 * Set the value of 取引No.
	 * 
	 * @param tradingNo the new value for 取引No.
	 */
	public void setTradingNo(String tradingNo) {
		this.tradingNo = tradingNo;
	}

	/*
	 * Get the 品名
	 * 
	 * @return the value of 品名
	 */
	public String getName() {
		return name;
	}

	/*
	 * Set the value of 品名
	 * 
	 * @param name the new value for 品名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * Get the
	 * 
	 * @return the value of $col.ViewName
	 */
	public Long getRegTime() {
		return regTime;
	}

	/*
	 * Set the value of
	 * 
	 * @param regTime the new value for $col.ViewName
	 */
	public void setRegTime(Long regTime) {
		this.regTime = regTime;
	}

	/*
	 * Get the
	 * 
	 * @return the value of $col.ViewName
	 */
	public Long getUpdTime() {
		return updTime;
	}

	/*
	 * Set the value of
	 * 
	 * @param updTime the new value for $col.ViewName
	 */
	public void setUpdTime(Long updTime) {
		this.updTime = updTime;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\t");
		sb.append("PayId");
		sb.append("=");
		sb.append(payId);
		sb.append("\n");
		sb.append("\t");
		sb.append("OrderId");
		sb.append("=");
		sb.append(orderId);
		sb.append("\n");
		sb.append("\t");
		sb.append("SupplierId");
		sb.append("=");
		sb.append(supplierId);
		sb.append("\n");
		sb.append("\t");
		sb.append("Date");
		sb.append("=");
		sb.append(date);
		sb.append("\n");
		sb.append("\t");
		sb.append("Currency");
		sb.append("=");
		sb.append(currency);
		sb.append("\n");
		sb.append("\t");
		sb.append("Amount");
		sb.append("=");
		sb.append(amount);
		sb.append("\n");
		sb.append("\t");
		sb.append("Note");
		sb.append("=");
		sb.append(note);
		sb.append("\n");
		sb.append("\t");
		sb.append("TradingNo");
		sb.append("=");
		sb.append(tradingNo);
		sb.append("\n");
		sb.append("\t");
		sb.append("Name");
		sb.append("=");
		sb.append(name);
		sb.append("\n");
		sb.append("\t");
		sb.append("RegTime");
		sb.append("=");
		sb.append(regTime);
		sb.append("\n");
		sb.append("\t");
		sb.append("UpdTime");
		sb.append("=");
		sb.append(updTime);
		sb.append("\n");
		return sb.toString();
	}

}
