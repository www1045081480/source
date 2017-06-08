package jp.newpulse.action.invoice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.utils.UDebugger;
import com.np.order.biz.invoice.InvoiceSheetMgr;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class InvoiceApprove extends BaseJsonAction {
	private static Log logger = LogFactory.getLog(InvoiceApprove.class);
	private String InvoiceID;

	public String execute() {
		try {
			InvoiceSheetMgr.approve(Long.parseLong(InvoiceID));
		} catch (NumberFormatException e) {
			logger.trace(UDebugger.trace(e));
		} catch (Exception e) {
			logger.trace(UDebugger.trace(e));
		}
		response(true);
		return "success";
	}

	public String getInvoiceID() {
		return InvoiceID;
	}

	public void setInvoiceID(String invoiceID) {
		InvoiceID = invoiceID;
	}

}
