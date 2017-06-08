package jp.newpulse.action.approve;

public class ApproveStatus{
	private Long presidentId;
	private Long vicePresidentId;
	private String presidentStatus;
	private String vicePresidentIdStatus;
	private boolean isConfirm;
	public boolean getIsConfirm() {
		return isConfirm;
	}
	public void setIsConfirm(boolean isConfirm) {
		this.isConfirm = isConfirm;
	}
	public Long getPresidentId() {
		return presidentId;
	}
	public void setPresidentId(Long presidentId) {
		this.presidentId = presidentId;
	}
	public Long getVicePresidentId() {
		return vicePresidentId;
	}
	public void setVicePresidentId(Long vicePresidentId) {
		this.vicePresidentId = vicePresidentId;
	}
	public String getPresidentStatus() {
		return presidentStatus;
	}
	public void setPresidentStatus(String presidentStatus) {
		this.presidentStatus = presidentStatus;
	}
	public String getVicePresidentIdStatus() {
		return vicePresidentIdStatus;
	}
	public void setVicePresidentIdStatus(String vicePresidentIdStatus) {
		this.vicePresidentIdStatus = vicePresidentIdStatus;
	}
}
