package jp.newpulse.action.quote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.utils.UDate;
import com.np.base.utils.UJson;
import com.np.order.action.SessionMgr;
import com.np.order.biz.invoice.EstmationSheetMgr;
import com.np.order.biz.invoice.OrderSheetMgr;
import com.np.order.models.Users;
import com.np.order.objects.NoApproveEstimation;
import com.np.order.objects.NoApproveOrder;

import jp.newpulse.action.BaseExcelAction;

@SuppressWarnings("serial")
public class DownloadUnapprovedList extends BaseExcelAction {
	private static Log logger = LogFactory.getLog(DownloadUnapprovedList.class);
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

		Map<String, Object> context = new HashMap<String, Object>();
		context.put("estimations", noApproveEstimation);
		context.put("orders", noApproveOrder);

		Users user = SessionMgr.getLoginUser();
		context.put("user", user);
		context.put("createDate", UDate.getDate());

		return response("UnapprovedList.xml", context);

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
