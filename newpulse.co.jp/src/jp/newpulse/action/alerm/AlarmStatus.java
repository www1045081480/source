package jp.newpulse.action.alerm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.order.biz.invoice.AlarmMgr;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class AlarmStatus extends BaseJsonAction {
	@SuppressWarnings("unused")
	private static Log logger = LogFactory.getLog(AlarmStatus.class);
	private Alarm alarm = new Alarm();

	public Alarm getAlarm() {
		return alarm;
	}

	public void setAlarm(Alarm alarm) {
		this.alarm = alarm;
	}

	public String execute() throws Exception {
		alarm.setHasDeliveries(AlarmMgr.hasDeliveries());
		alarm.setHasNoPayableAmounts(AlarmMgr.hasNoPayableAmounts());
		alarm.setHasNoReceivableAmounts(AlarmMgr.hasNoReceivableAmounts());
		return "success";
	}

}
