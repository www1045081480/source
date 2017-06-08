package jp.newpulse.action.customer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.order.biz.mast.CustomerMgr;

public class CustomerDelAction {
	private static Log logger = LogFactory.getLog(CustomerDelAction.class);
	private String customerid;

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String execute() throws Exception {
		Long id = Long.parseLong(customerid);
		logger.debug("**************删除用户****************");
		logger.debug("id=" + id);
		CustomerMgr.removeCustomer(id);
		logger.debug("**************删除成功****************");
		return "success";
	}
}
