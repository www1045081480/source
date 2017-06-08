package jp.newpulse.action.quote;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class LoadEstmationConfirm extends ActionSupport {
	private static Log logger = LogFactory.getLog(LoadEstmationConfirm.class);
	private String EstimationId;

	public String getEstimationId() {
		return EstimationId;
	}

	public void setEstimationId(String estimationId) {
		EstimationId = estimationId;
	}

	public String execute() {
		logger.debug("EstimationId=" + EstimationId);
		return "success";
	}
}
