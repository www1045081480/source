package jp.newpulse.action.quote;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.db.TxMgr;
import com.np.order.models.EstimationConfirm;
import com.np.order.models.EstmationSheet;
import com.np.order.models.OrderSheet;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class AddEstmationConfirm extends ActionSupport {
	private static Log logger = LogFactory.getLog(AddEstmationConfirm.class);
	private String EstimationId;
	@SuppressWarnings("unused")
	private EstmationSheet from;
	private String confirmDate;

	public String getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}

	@SuppressWarnings("unused")
	private OrderSheet to;

	public String execute() throws Exception {
		logger.debug("``````````````````````````````@");
		Long id = Long.parseLong(EstimationId);
		EstimationConfirm transfer = new EstimationConfirm();
		transfer.setEstimationId(id);
		transfer.setCustomerOrderNo(OrderCd);
		transfer.setConfirmDate(confirmDate.replace("-", ""));
		TxMgr.insert(transfer);
		return "success";
	}

	public String getEstimationId() {
		return EstimationId;
	}

	public void setEstimationId(String estimationId) {
		EstimationId = estimationId;
	}

	public String getOrderCd() {
		return OrderCd;
	}

	public void setOrderCd(String orderCd) {
		OrderCd = orderCd;
	}

	private String OrderCd;
}
