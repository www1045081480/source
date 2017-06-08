package jp.newpulse.action.approve;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.order.biz.invoice.EstmationSheetMgr;
import com.np.order.objects.EstimationApproves;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class EstimationApproveStatus extends BaseJsonAction {
	private static Log logger = LogFactory.getLog(EstimationApproveStatus.class);
	private String EstimationId;

	public String execute() throws Exception {
		logger.debug("===============EstimationApproveStatus S===================");
		EstimationApproves ea = EstmationSheetMgr.getApproves(Long.parseLong(EstimationId));
		boolean isConfirmed = EstmationSheetMgr.isConfirmed(Long.parseLong(EstimationId));
		ApproveStatus as = new ApproveStatus();
		as.setPresidentId(ea.getPresident());
		logger.debug(as.getPresidentId());
		as.setVicePresidentId(ea.getVicePresident());
		logger.debug(as.getVicePresidentId());
		as.setIsConfirm(isConfirmed);
		response(as);
		logger.debug("===============EstimationApproveStatus E===================");
		return "success";
	}

	public String getEstimationId() {
		return EstimationId;
	}

	public void setEstimationId(String estimationId) {
		EstimationId = estimationId;
	}

}
