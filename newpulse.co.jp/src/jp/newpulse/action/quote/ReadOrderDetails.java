package jp.newpulse.action.quote;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.order.biz.invoice.OrderSheetMgr;
import com.np.order.models.OrderDetail;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class ReadOrderDetails extends BaseJsonAction {
	private static Log logger = LogFactory.getLog(ReadOrderDetails.class);
	private List<OrderDetail> list;
	private String OrderId;

	private String lanCd = "";

	public String getLanCd() {
		return lanCd;
	}

	public void setLanCd(String lanCd) {
		this.lanCd = lanCd;
	}

	public String execute() throws Exception {
		logger.debug("================ReadOrderDetails S===================");
		list = OrderSheetMgr.fetchDetails(Long.parseLong(OrderId), lanCd);
		logger.debug("===============ReadOrderDetails E====================");
		response(list);
		return "success";
	}

	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
}
