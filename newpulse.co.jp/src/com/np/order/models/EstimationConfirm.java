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
@Table(name = "ESTIMATION_CONFIRM_TBL")
public class EstimationConfirm {
	public static final String TABLE_NAME = "ESTIMATION_CONFIRM_TBL";

	/*
	 * ---------------------- Public Field Names Definition
	 * ----------------------------
	 */
	public static final String EstimationId = "EstimationId";
	public static final String ConfirmDate = "ConfirmDate";
	public static final String CustomerOrderNo = "CustomerOrderNo";
	public static final String RegTime = "RegTime";
	public static final String UpdTime = "UpdTime";

	/*
	 * ---------------------- Private Fields Definition
	 * ----------------------------
	 */
	@Id
	/*  */
	@Column(name = "ESTIMATION_ID", length = 0, nullable = false)
	@JoinTable(name = "EstmationSheet")
	@JoinColumn(name = "EstimationId")
	private Long estimationId;

	/*  */
	@Column(name = "CONFIRM_DATE", length = 8, nullable = true)
	private String confirmDate;

	/*  */
	@Column(name = "CUSTOMER_ORDER_NO", length = 16, nullable = true)
	private String customerOrderNo;

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
	public Long getEstimationId() {
		return estimationId;
	}

	/*
	 * Set the value of
	 * 
	 * @param estimationId the new value for $col.ViewName
	 */
	public void setEstimationId(Long estimationId) {
		this.estimationId = estimationId;
	}

	/*
	 * Get the
	 * 
	 * @return the value of $col.ViewName
	 */
	public String getConfirmDate() {
		return confirmDate;
	}

	/*
	 * Set the value of
	 * 
	 * @param confirmDate the new value for $col.ViewName
	 */
	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}

	/*
	 * Get the
	 * 
	 * @return the value of $col.ViewName
	 */
	public String getCustomerOrderNo() {
		return customerOrderNo;
	}

	/*
	 * Set the value of
	 * 
	 * @param customerOrderNo the new value for $col.ViewName
	 */
	public void setCustomerOrderNo(String customerOrderNo) {
		this.customerOrderNo = customerOrderNo;
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
		sb.append("EstimationId");
		sb.append("=");
		sb.append(estimationId);
		sb.append("\n");
		sb.append("\t");
		sb.append("ConfirmDate");
		sb.append("=");
		sb.append(confirmDate);
		sb.append("\n");
		sb.append("\t");
		sb.append("CustomerOrderNo");
		sb.append("=");
		sb.append(customerOrderNo);
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
