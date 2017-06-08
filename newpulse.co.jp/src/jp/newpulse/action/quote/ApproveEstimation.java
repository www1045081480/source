package jp.newpulse.action.quote;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.order.biz.invoice.EstmationSheetMgr;

import jp.newpulse.action.BaseAction;

@SuppressWarnings("serial")
public class ApproveEstimation extends BaseAction {
	private static Log logger = LogFactory.getLog(ApproveEstimation.class);
	private String EstimationId;

	public String execute() throws Exception {
		logger.debug("=========Approve:" + EstimationId + "===========");
		EstmationSheetMgr.approve(Long.parseLong(EstimationId));
		return "success";
	}

	public String getEstimationId() {
		return EstimationId;
	}

	public void setEstimationId(String estimationId) {
		EstimationId = estimationId;
	}
}
