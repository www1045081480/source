package com.np.order.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import com.np.order.Money;

/*
 * $table.ViewName Model
 *
 * This class was autogenerated on:
 * [Mon Jan 11 18:47:34 JST 2016]
 *
 * [WARNING]
 * Don't edit this class directly, because it may be regenerated late.
 */
@Entity
@Table(name = "PRE_PAYMENT_TBL")
public class PrePayment {
	public static final String TABLE_NAME = "PRE_PAYMENT_TBL";

	/*
	 * ---------------------- Public Field Names Definition
	 * ----------------------------
	 */
	public static final String PrePaymentId = "PrePaymentId";
	public static final String OrderCd = "OrderCd";
	public static final String PrepayAmount = "PrepayAmount";
	public static final String RegTime = "RegTime";
	public static final String UpdTime = "UpdTime";

	/*
	 * ---------------------- Private Fields Definition
	 * ----------------------------
	 */
	@Id
	/*  */
	@Column(name = "PRE_PAYMENT_ID", length = 0, nullable = false)
	private Long prePaymentId;

	/*  */
	@Column(name = "ORDER_CD", length = 0, nullable = false)
	@JoinTable(name = "OrderSheet")
	@JoinColumn(name = "OrderCd")
	private Long orderCd;

	/* 財務前払金 */
	@Column(name = "PREPAY_AMOUNT", length = 0, nullable = true)
	private Money prepayAmount;

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
	public Long getPrePaymentId() {
		return prePaymentId;
	}

	/*
	 * Set the value of
	 * 
	 * @param prePaymentId the new value for $col.ViewName
	 */
	public void setPrePaymentId(Long prePaymentId) {
		this.prePaymentId = prePaymentId;
	}

	/*
	 * Get the
	 * 
	 * @return the value of $col.ViewName
	 */
	public Long getOrderCd() {
		return orderCd;
	}

	/*
	 * Set the value of
	 * 
	 * @param orderCd the new value for $col.ViewName
	 */
	public void setOrderCd(Long orderCd) {
		this.orderCd = orderCd;
	}

	/*
	 * Get the 財務前払金
	 * 
	 * @return the value of 財務前払金
	 */
	public Money getPrepayAmount() {
		return prepayAmount;
	}

	/*
	 * Set the value of 財務前払金
	 * 
	 * @param prepayAmount the new value for 財務前払金
	 */
	public void setPrepayAmount(Money prepayAmount) {
		this.prepayAmount = prepayAmount;
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
		sb.append("PrePaymentId");
		sb.append("=");
		sb.append(prePaymentId);
		sb.append("\n");
		sb.append("\t");
		sb.append("OrderCd");
		sb.append("=");
		sb.append(orderCd);
		sb.append("\n");
		sb.append("\t");
		sb.append("PrepayAmount");
		sb.append("=");
		sb.append(prepayAmount);
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
