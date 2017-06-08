package jp.newpulse.action.alerm;

public class Alarm{
	private boolean hasDeliveries;
	private boolean hasNoPayableAmounts;
	private boolean hasNoReceivableAmounts;
	public boolean isHasDeliveries() {
		return hasDeliveries;
	}
	public void setHasDeliveries(boolean hasDeliveries) {
		this.hasDeliveries = hasDeliveries;
	}
	public boolean isHasNoPayableAmounts() {
		return hasNoPayableAmounts;
	}
	public void setHasNoPayableAmounts(boolean hasNoPayableAmounts) {
		this.hasNoPayableAmounts = hasNoPayableAmounts;
	}
	public boolean isHasNoReceivableAmounts() {
		return hasNoReceivableAmounts;
	}
	public void setHasNoReceivableAmounts(boolean hasNoReceivableAmounts) {
		this.hasNoReceivableAmounts = hasNoReceivableAmounts;
	}
}
