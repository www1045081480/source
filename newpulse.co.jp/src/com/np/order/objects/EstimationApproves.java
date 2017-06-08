package com.np.order.objects;

/*
 * 見積書承認状態
 */
public class EstimationApproves {
	/*
	 * 社長
	 */
	private Long president;
	/*
	 * 副社長
	 */
	private Long vicePresident;
	/*
	 * 承認者
	 */
	private Long[] approvers;

	public boolean isPresidentApproved() {
		return president != null;
	}

	public boolean isVicePresidentApproved() {
		return vicePresident != null;
	}

	public Long getPresident() {
		return president;
	}

	public void setPresident(Long president) {
		this.president = president;
	}

	public Long getVicePresident() {
		return vicePresident;
	}

	public void setVicePresident(Long vicePresident) {
		this.vicePresident = vicePresident;
	}

	public Long[] getApprovers() {
		return approvers;
	}

	public void setApprovers(Long[] approvers) {
		this.approvers = approvers;
	}
}
