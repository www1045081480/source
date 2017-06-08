package jp.newpulse.action.invoice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.utils.UDebugger;
import com.np.order.biz.invoice.CodeAutoIncrement;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class CheckInvoiceCd extends BaseJsonAction {
	private static Log logger = LogFactory.getLog(CheckInvoiceCd.class);
	private String InvoiceCd;

	public String execute() {
		boolean ret = false;
		try {
			ret = CodeAutoIncrement.checkInvoiceCode(InvoiceCd);
		} catch (Exception e) {
			logger.trace(UDebugger.trace(e));
		}
		response(ret);
		return ret ? "success" : "error";
	}

	public String getInvoiceCd() {
		return InvoiceCd;
	}

	public void setInvoiceCd(String invoiceCD) {
		this.InvoiceCd = invoiceCD;
	}

}
