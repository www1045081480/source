package jp.newpulse.action.quote;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.utils.UJson;
import com.np.order.biz.invoice.EstmationSheetMgr;
import com.np.order.biz.invoice.OrderSheetMgr;
import com.np.order.objects.NoApproveEstimation;
import com.np.order.objects.NoApproveOrder;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class FetchNoApproveList extends ActionSupport {
	private static Log logger = LogFactory.getLog(FetchNoApproveList.class);
	/**
	 * 
	 */
	private List<NoApproveEstimation> noApproveEstimation;
	private List<NoApproveOrder> noApproveOrder;

	public String execute() throws Exception {

		noApproveEstimation = EstmationSheetMgr.fetchNotApproveList();

		if (!noApproveEstimation.isEmpty()) {
			logger.debug(noApproveEstimation.size());
			String jsonEs = UJson.toJsonString(noApproveEstimation.get(0));
			logger.debug(jsonEs);
		}

		noApproveOrder = OrderSheetMgr.fetchNotApproveList();

		if (!noApproveOrder.isEmpty()) {
			logger.debug(noApproveOrder.size());
			String jsonOr = UJson.toJsonString(noApproveOrder.get(0));
			logger.debug(jsonOr);
		}
		return "success";
	}

	public List<NoApproveEstimation> getNoApproveEstimation() {
		return noApproveEstimation;
	}

	public void setNoApproveEstimation(List<NoApproveEstimation> noApproveEstimation) {
		this.noApproveEstimation = noApproveEstimation;
	}

	public List<NoApproveOrder> getNoApproveOrder() {
		return noApproveOrder;
	}

	public void setNoApproveOrder(List<NoApproveOrder> noApproveOrder) {
		this.noApproveOrder = noApproveOrder;
	}
}
