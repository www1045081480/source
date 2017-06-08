package jp.newpulse.action.quote;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.order.biz.invoice.OrderSheetMgr;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class DeleteOrder extends ActionSupport {
	private static Log logger = LogFactory.getLog(DeleteEstimation.class);
	private String OrderId;

	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	public String execute() throws Exception {
		logger.debug("=========DELETE:" + OrderId + "===========");
		Boolean tag = OrderSheetMgr.cancel(Long.parseLong(OrderId));
		logger.debug("=========DELETE:" + tag + "===========");
		if (tag) {
			return "success";
		} else {
			return "false";
		}
	}
}
