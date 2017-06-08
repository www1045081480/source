package jp.newpulse.action.approve;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.order.biz.invoice.OrderSheetMgr;
import com.np.order.objects.EstimationApproves;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class OrderApproveStatus extends BaseJsonAction {
	private static Log logger = LogFactory.getLog(EstimationApproveStatus.class);
	private String OrderId;

	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	public String execute() throws NumberFormatException, Exception {
		logger.debug("===============OrderApproveStatus S===================");
		EstimationApproves ea;
		ea = OrderSheetMgr.getApproves(Long.parseLong(OrderId));
		ApproveStatus as = new ApproveStatus();
		as.setPresidentId(ea.getPresident());
		logger.debug(as.getPresidentId());
		logger.debug(as.getVicePresidentId());
		as.setVicePresidentId(ea.getVicePresident());
		logger.debug("===============OrderApproveStatus E===================");
		response(as);
		return "success";
	}

}
