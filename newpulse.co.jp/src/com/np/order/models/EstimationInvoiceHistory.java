package com.np.order.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

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
@Table(name = "ESTIMATION_INVOICE_HISTORY_TBL")
public class EstimationInvoiceHistory {
	public static final String TABLE_NAME = "ESTIMATION_INVOICE_HISTORY_TBL";

	/*
	 * ---------------------- Public Field Names Definition
	 * ----------------------------
	 */
	public static final String EstimationCd = "EstimationCd";
	public static final String InvoiceCd = "InvoiceCd";
	public static final String RegTime = "RegTime";
	public static final String UpdTime = "UpdTime";

	/*
	 * ---------------------- Private Fields Definition
	 * ----------------------------
	 */
	@Id
	/*  */
	@Column(name = "ESTIMATION_CD", length = 64, nullable = false)
	@JoinTable(name = "EstmationSheet")
	@JoinColumn(name = "EstimationCd")
	private String estimationCd;

	@Id
	/*  */
	@Column(name = "INVOICE_CD", length = 64, nullable = false)
	@JoinTable(name = "InvoiceSheet")
	@JoinColumn(name = "InvoiceCd")
	private String invoiceCd;

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
	public String getEstimationCd() {
		return estimationCd;
	}

	/*
	 * Set the value of
	 * 
	 * @param estimationCd the new value for $col.ViewName
	 */
	public void setEstimationCd(String estimationCd) {
		this.estimationCd = estimationCd;
	}

	/*
	 * Get the
	 * 
	 * @return the value of $col.ViewName
	 */
	public String getInvoiceCd() {
		return invoiceCd;
	}

	/*
	 * Set the value of
	 * 
	 * @param invoiceCd the new value for $col.ViewName
	 */
	public void setInvoiceCd(String invoiceCd) {
		this.invoiceCd = invoiceCd;
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
		sb.append("EstimationCd");
		sb.append("=");
		sb.append(estimationCd);
		sb.append("\n");
		sb.append("\t");
		sb.append("InvoiceCd");
		sb.append("=");
		sb.append(invoiceCd);
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
