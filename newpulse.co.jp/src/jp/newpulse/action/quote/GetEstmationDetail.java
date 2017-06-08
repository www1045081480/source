package jp.newpulse.action.quote;

import java.util.List;

import com.np.order.biz.invoice.EstmationSheetMgr;
import com.np.order.models.EstiomationDetail;

import jp.newpulse.action.BaseJsonAction;

public class GetEstmationDetail extends BaseJsonAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String EstimationId;
	private List<EstiomationDetail> result;

	public String getEstimationId() {
		return EstimationId;
	}

	public void setEstimationId(String estimationId) {
		EstimationId = estimationId;
	}

	public String execute() throws Exception {
		result = EstmationSheetMgr.fetchDetails(Long.parseLong(EstimationId));
		response(result);
		return "success";
	}
}
