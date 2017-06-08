package jp.newpulse.action.quote;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.order.biz.invoice.EstmationSheetMgr;
import com.np.order.objects.NoApproveEstimation;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class FetchNoApproveEstimationList extends ActionSupport {
	private static Log logger = LogFactory.getLog(FetchNoApproveEstimationList.class);
	/**
	 * 
	 */
	private List<NoApproveEstimation> noApproveEstimation;

	public String execute() throws Exception {
		logger.debug("==================FetchNoApproveEstimationList======================");
		noApproveEstimation = EstmationSheetMgr.fetchNotApproveList();

		return "success";
	}

	public List<NoApproveEstimation> getNoApproveEstimation() {
		return noApproveEstimation;
	}

	public void setNoApproveEstimation(List<NoApproveEstimation> noApproveEstimation) {
		this.noApproveEstimation = noApproveEstimation;
	}
}
