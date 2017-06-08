package jp.newpulse.action.quote;

import java.util.List;

import com.np.order.biz.invoice.EstmationSheetMgr;
import com.np.order.objects.NoApproveEstimation;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class NoApproveEstimationAction extends ActionSupport {
	private List<NoApproveEstimation> noApproveEstimation;

	public String execute() throws Exception {
		noApproveEstimation = EstmationSheetMgr.fetchNotApproveList();
		/*
		 * WriterRespone w = new WriterRespone();
		 * w.response(noApproveEstimation);
		 */
		return "success";
	}

	public List<NoApproveEstimation> getNoApproveEstimation() {
		return noApproveEstimation;
	}

	public void setNoApproveEstimation(List<NoApproveEstimation> noApproveEstimation) {
		this.noApproveEstimation = noApproveEstimation;
	}

}
