package jp.newpulse.action.invoice.invoice_list;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.utils.UDate;
import com.np.base.utils.UDebugger;
import com.np.order.action.SessionMgr;
import com.np.order.biz.invoice.ResultListMgr;
import com.np.order.models.Users;
import com.np.order.objects.DeliveryResult;
import com.np.order.objects.DeliveryResultFilter;

import jp.newpulse.action.BaseExcelAction;;

@SuppressWarnings("serial")
public class DownloadInvoiceList extends BaseExcelAction {
	private static Log logger = LogFactory.getLog(DownloadInvoiceList.class);
	private String invoiceCD;
	private String customerID;
	private String customerNO;
	private String InForm;
	private String InTo;
	List<DeliveryResult> deliveryResult;

	public String execute() throws Exception {
		try {
			DeliveryResultFilter deliveryResultFilter = new DeliveryResultFilter();
			deliveryResultFilter.setInvoiceCd(invoiceCD);
			if (!customerID.equals(null) && !customerID.equals("")) {
				deliveryResultFilter.setCustomerId(Long.parseLong(customerID));
			}
			// DeliveryResult deliveryResult = new DeliveryResult();
			String yyyymm = "";
			String yyyymm1 = "";
			if (!InForm.equals("") && !InTo.equals("")) {

				String[] yyyy = InForm.split("-");
				String year = yyyy[0];
				String month = yyyy[1];
				String day = yyyy[2];
				yyyymm = year + month + day;
				String[] yyyy1 = InTo.split("-");
				String year1 = yyyy1[0];
				String month1 = yyyy1[1];
				String day1 = yyyy1[2];
				yyyymm1 = year1 + month1 + day1;
				deliveryResultFilter.setDateFrom(yyyymm);
				logger.debug("--------------------------yyyymm---------------------------");

				logger.debug(yyyymm);
				deliveryResultFilter.setDateTo(yyyymm1);
				logger.debug("--------------------------yyyymm1---------------------------");

				logger.debug(yyyymm1);

			}

			deliveryResult = ResultListMgr.getDeliveryResult(deliveryResultFilter);
		} catch (Exception e) {
			logger.trace(UDebugger.trace(e));
		}

		Map<String, Object> context = new HashMap<String, Object>();
		context.put("invoices", deliveryResult);

		Users user = SessionMgr.getLoginUser();
		context.put("user", user);
		context.put("createDate", UDate.getDate());

		return response("InvoiceList.xml", context);

	}

	public List<DeliveryResult> getDeliveryResult() {
		return deliveryResult;
	}

	public void setDeliveryResult(List<DeliveryResult> deliveryResult) {
		this.deliveryResult = deliveryResult;
	}

	public String getInvoiceCD() {
		return invoiceCD;
	}

	public void setInvoiceCD(String invoiceCD) {
		this.invoiceCD = invoiceCD;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getCustomerNO() {
		return customerNO;
	}

	public void setCustomerNO(String customerNO) {
		this.customerNO = customerNO;
	}

	public String getInForm() {
		return InForm;
	}

	public void setInForm(String inForm) {
		InForm = inForm;
	}

	public String getInTo() {
		return InTo;
	}

	public void setInTo(String inTo) {
		InTo = inTo;
	}

}