package jp.newpulse.action.quote;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.order.biz.invoice.OrderSheetMgr;
import com.np.order.models.OrderSheet;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class ReadOrderSheet extends BaseJsonAction {
	private static Log logger = LogFactory.getLog(ReadOrderSheet.class);
	private OrderSheet orderSheet;
	private String OrderId;

	public String execute() {
		logger.debug("================orderSheet b===================" + OrderId);
		try {
			orderSheet = OrderSheetMgr.fetchSheet(Long.parseLong(OrderId));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug(orderSheet);
		logger.debug("================orderSheet e===================");

		response(orderSheet);
		return "success";
	}

	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	public OrderSheet getOrderSheet() {
		return orderSheet;
	}

	public void setOrderSheet(OrderSheet orderSheet) {
		this.orderSheet = orderSheet;
	}
}
