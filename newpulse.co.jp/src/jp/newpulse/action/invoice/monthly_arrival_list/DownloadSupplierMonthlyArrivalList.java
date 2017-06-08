package jp.newpulse.action.invoice.monthly_arrival_list;

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
import com.np.order.objects.PayableAmount;
import com.np.order.objects.PayableAmountFilter;

import jp.newpulse.action.BaseExcelAction;;

@SuppressWarnings("serial")
public class DownloadSupplierMonthlyArrivalList extends BaseExcelAction {
	private static Log logger = LogFactory.getLog(DownloadSupplierMonthlyArrivalList.class);
	private String year;
	private String month;
	private String customerID;
	private String customerIDName;
	private String Currency;
	private List<PayableAmount> payableAmount;

	public String execute() throws Exception {

		System.out.println("------------------------");
		PayableAmountFilter payableAmountFilter = new PayableAmountFilter();
		payableAmountFilter.setBalanceYear(year);
		payableAmountFilter.setBalanceMonth(month);
		logger.debug("------------------------------仕入先ID-----------------------------");
		logger.debug(customerID);
		if (customerID != null && !customerID.equals("")) {
			logger.debug("------------------------------customerID-------------------------");
			logger.debug(customerID);
			payableAmountFilter.setSupplierId(Long.parseLong(customerID));
		}
		try {
			payableAmountFilter.setCurrency("JP");
			payableAmount = PayReceiveAmountMgr.getPayableAmounts(payableAmountFilter);
			payableAmountFilter.setCurrency("US");
			payableAmount.addAll(PayReceiveAmountMgr.getPayableAmounts(payableAmountFilter));
			payableAmountFilter.setCurrency("CN");
			payableAmount.addAll(PayReceiveAmountMgr.getPayableAmounts(payableAmountFilter));
		} catch (Exception e) {
			logger.trace(UDebugger.trace(e));
		}
		logger.debug("-------------------------ReceiveFindActionFind----------------------------");
		logger.debug(year);
		logger.debug(month);
		logger.debug(customerID);
		logger.debug(customerIDName);

		Map<String, Object> context = new HashMap<String, Object>();
		context.put("arrivals", payableAmount);

		Users user = SessionMgr.getLoginUser();
		context.put("user", user);
		context.put("createDate", UDate.getDate());
		context.put("yyyymm", year + "年" + month + "月");
		context.put("supplierName", customerIDName);

		return response("SupplierMonthlyArrivalList.xml", context);
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

	public void setMonth(String month) {
		this.month = month;
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
