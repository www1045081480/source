package jp.newpulse.action.invoice.monthly_shipping_list;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.utils.UDebugger;
import com.np.order.biz.invoice.PayReceiveAmountMgr;
import com.np.order.objects.ReceivableAmount;
import com.np.order.objects.ReceivableAmountFilter;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class ReceiveFindAction extends BaseJsonAction {
	private static Log logger = LogFactory.getLog(ReceiveFindAction.class);
	private String year;
	private String month;
	private String customerID;
	private String customerIDName;
	private String Currency;
	private List<ReceivableAmount> receivableAmount;

	public String execute() {

		System.out.println("------------------------");
		ReceivableAmountFilter receivableAmountFilter = new ReceivableAmountFilter();
		receivableAmountFilter.setBalanceYear(year);
		receivableAmountFilter.setBalanceMonth(month);
		receivableAmountFilter.setCurrency(Currency);
		logger.debug("------------------------------customerID111111111-------------------------");
		logger.debug(customerID);
		if (customerID != null && !customerID.equals("")) {
			logger.debug("------------------------------customerID-------------------------");
			logger.debug(customerID);
			receivableAmountFilter.setCustomerId(Long.parseLong(customerID));
		}
		try {
			receivableAmount = PayReceiveAmountMgr.getReceivableAmounts(receivableAmountFilter);
		} catch (Exception e) {
			logger.trace(UDebugger.trace(e));
		}
		logger.debug("-------------------------ReceiveFindActionFind----------------------------");
		logger.debug(year);
		logger.debug(month);
		logger.debug(customerID);
		logger.debug(customerIDName);
		responseForView(receivableAmount);
		return "success";
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public static Log getLogger() {
		return logger;
	}

	public static void setLogger(Log logger) {
		ReceiveFindAction.logger = logger;
	}

	public String getCustomerIDName() {
		return customerIDName;
	}

	public void setCustomerIDName(String customerIDName) {
		this.customerIDName = customerIDName;
	}

	public List<ReceivableAmount> getReceivableAmount() {
		return receivableAmount;
	}

	public void setReceivableAmount(List<ReceivableAmount> receivableAmount) {
		this.receivableAmount = receivableAmount;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

}
