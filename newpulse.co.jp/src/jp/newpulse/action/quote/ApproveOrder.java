package jp.newpulse.action.quote;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.order.biz.invoice.OrderSheetMgr;

import jp.newpulse.action.BaseAction;

@SuppressWarnings("serial")
public class ApproveOrder extends BaseAction {
	private static Log logger = LogFactory.getLog(ApproveOrder.class);
	private String OrderId;

	public String execute() throws Exception {
		logger.debug("=========Approve:" + OrderId + "===========");
		OrderSheetMgr.approve(Long.parseLong(OrderId));
		return "success";
	}

	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
}
