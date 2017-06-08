package jp.newpulse.action.quote;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.order.biz.invoice.EstmationSheetMgr;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class DeleteEstimation extends ActionSupport {
	private static Log logger = LogFactory.getLog(DeleteEstimation.class);
	private String EstimationId;

	public String execute() throws Exception {
		logger.debug("=========DELETE:" + EstimationId + "===========");
		Boolean tag = EstmationSheetMgr.cancel(Long.parseLong(EstimationId));
		logger.debug("=========DELETE:" + tag + "===========");
		if (tag) {
			return "success";
		} else {
			return "false";
		}
	}

	public String getEstimationId() {
		return EstimationId;
	}

	public void setEstimationId(String estimationId) {
		EstimationId = estimationId;
	}
}
