package jp.newpulse.action.customer;

import java.util.List;

import com.np.order.biz.mast.CustomerMgr;
import com.np.order.models.Customer;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class CustomerAction extends ActionSupport {
	/**
	 * 
	 */
	private List<Customer> customers;

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public String execute() throws Exception {
		Customer cus = new Customer();
		customers = CustomerMgr.searchCustomers(cus);
		return "success";
	}

}
