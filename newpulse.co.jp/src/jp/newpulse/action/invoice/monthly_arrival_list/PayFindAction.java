package jp.newpulse.action.invoice.monthly_arrival_list;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.utils.UDebugger;
import com.np.order.biz.invoice.PayReceiveAmountMgr;
import com.np.order.objects.PayableAmount;
import com.np.order.objects.PayableAmountFilter;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class PayFindAction extends BaseJsonAction {
	private static Log logger = LogFactory.getLog(PayFindAction.class);
	private String year;
	private String month;
	private String customerID;
	private String customerIDName;
	private String Currency;
	private List<PayableAmount> payableAmount;

	public String execute() {

		System.out.println("------------------------");
		PayableAmountFilter payableAmountFilter = new PayableAmountFilter();
		payableAmountFilter.setBalanceYear(year);
		payableAmountFilter.setBalanceMonth(month);
		payableAmountFilter.setCurrency(Currency);
		logger.debug("------------------------------仕入先ID-----------------------------");
		logger.debug(customerID);
		if (customerID != null && !customerID.equals("")) {
			logger.debug("------------------------------customerID-------------------------");
			logger.debug(customerID);
			payableAmountFilter.setSupplierId(Long.parseLong(customerID));
		}
		try {
			payableAmount = PayReceiveAmountMgr.getPayableAmounts(payableAmountFilter);
		} catch (Exception e) {
			logger.trace(UDebugger.trace(e));
		}
		logger.debug("-------------------------ReceiveFindActionFind----------------------------");
		logger.debug(year);
		logger.debug(month);
		logger.debug(customerID);
		logger.debug(customerIDName);
		// response(true);
		responseForView(payableAmount);
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

	public String getCustomerIDName() {
		return customerIDName;
	}

	public void setCustomerIDName(String customerIDName) {
		this.customerIDName = customerIDName;
	}

	public static Log getLogger() {
		return logger;
	}

	public static void setLogger(Log logger) {
		PayFindAction.logger = logger;
	}

	public List<PayableAmount> getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(List<PayableAmount> payableAmount) {
		this.payableAmount = payableAmount;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

}
