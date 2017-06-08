package jp.newpulse.action.quote;

import java.util.List;

import com.np.order.biz.invoice.OrderSheetMgr;
import com.np.order.objects.NoApproveOrder;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class FetchNoApproveOrderList extends ActionSupport {
	/**
	 * 
	 */
	private List<NoApproveOrder> noApproveOrder;

	public String execute() throws Exception {
		noApproveOrder = OrderSheetMgr.fetchNotApproveList();
		return "success";
	}

	public List<NoApproveOrder> getNoApproveOrder() {
		return noApproveOrder;
	}

	public void setNoApproveOrder(List<NoApproveOrder> noApproveOrder) {
		this.noApproveOrder = noApproveOrder;
	}
}
