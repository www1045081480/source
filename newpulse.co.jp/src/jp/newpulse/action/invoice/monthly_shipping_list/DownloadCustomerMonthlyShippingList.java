package jp.newpulse.action.invoice.monthly_shipping_list;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.utils.UDate;
import com.np.base.utils.UDebugger;
import com.np.order.action.SessionMgr;
import com.np.order.biz.invoice.PayReceiveAmountMgr;
import com.np.order.models.Users;
import com.np.order.objects.ReceivableAmount;
import com.np.order.objects.ReceivableAmountFilter;

import jp.newpulse.action.BaseExcelAction;

@SuppressWarnings("serial")
public class DownloadCustomerMonthlyShippingList extends BaseExcelAction {
	private static Log logger = LogFactory.getLog(DownloadCustomerMonthlyShippingList.class);
	private String year;
	private String month;
	private String customerID;
	private String customerIDName;
	private String Currency;
	private List<ReceivableAmount> receivableAmount;

	public String execute() throws Exception {

		System.out.println("------------------------");
		ReceivableAmountFilter receivableAmountFilter = new ReceivableAmountFilter();
		receivableAmountFilter.setBalanceYear(year);
		receivableAmountFilter.setBalanceMonth(month);
		logger.debug("------------------------------customerID111111111-------------------------");
		logger.debug(customerID);
		if (customerID != null && !customerID.equals("")) {
			logger.debug("------------------------------customerID-------------------------");
			logger.debug(customerID);
			receivableAmountFilter.setCustomerId(Long.parseLong(customerID));
		}
		try {
			receivableAmountFilter.setCurrency("JP");
			receivableAmount = PayReceiveAmountMgr.getReceivableAmounts(receivableAmountFilter);
			receivableAmountFilter.setCurrency("US");
			receivableAmount.addAll(PayReceiveAmountMgr.getReceivableAmounts(receivableAmountFilter));
			receivableAmountFilter.setCurrency("CN");
			receivableAmount.addAll(PayReceiveAmountMgr.getReceivableAmounts(receivableAmountFilter));
		} catch (Exception e) {
			logger.trace(UDebugger.trace(e));
		}
		logger.debug("-------------------------ReceiveFindActionFind----------------------------");
		logger.debug(year);
		logger.debug(month);
		logger.debug(customerID);
		logger.debug(customerIDName);

		Map<String, Object> context = new HashMap<String, Object>();
		context.put("shippings", receivableAmount);

		Users user = SessionMgr.getLoginUser();
		context.put("user", user);
		context.put("createDate", UDate.getDate());
		context.put("yyyymm", year + "年" + month + "月");
		context.put("customerName", customerIDName);

		return response("CustomerMonthlyShippingList.xml", context);
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
