package jp.newpulse.action.invoice.invoice_list;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.utils.UDebugger;
import com.np.order.biz.invoice.ResultListMgr;
import com.np.order.objects.DeliveryResult;
import com.np.order.objects.DeliveryResultFilter;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class InvoiceallLoadAction extends ActionSupport {
	private static Log logger = LogFactory.getLog(InvoiceallLoadAction.class);
	List<DeliveryResult> deliveryResult;
	// 权限
	private String roleID;

	public String execute() throws Exception {
		DeliveryResultFilter deliveryResultFilter = new DeliveryResultFilter();
		String Form = "";
		String To = "";

		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int mouth = now.get(Calendar.MONTH) + 1;
		String mouths = "";
		if (mouth == 1) {
			Form = (year - 1) + "1201";
			To = year + "0131";
		} else {
			if (mouth < 10) {
				mouths = "0" + (mouth - 1);
				Form = year + "" + mouths + "01";
				To = year + "0" + mouth + "31";
			} else if (mouth == 10) {
				Form = year + "0901";
				To = year + "1031";
			} else {
				Form = year + "" + (mouth - 1) + "01";
				To = year + "" + mouth + "31";
			}
		}
		deliveryResultFilter.setDateFrom(Form);
		deliveryResultFilter.setDateTo(To);
		logger.debug("-----------------------------Form Time----------------------------");
		logger.debug(Form);
		logger.debug("-----------------------------To Time----------------------------");
		logger.debug(To);

		try {
			deliveryResult = ResultListMgr.getDeliveryResult(deliveryResultFilter);

			if (false) {
				logger.debug("-----------------------------invoive Star----------------------------");

				for (int i = 0; i < deliveryResult.size(); i++) {
					logger.debug("-----------------------------invoive 番号----------------------------");
					logger.debug(deliveryResult.get(i).getInvoiceCd());
					logger.debug("-----------------------------販売先----------------------------");
					logger.debug(deliveryResult.get(i).getCustomerName());
					logger.debug("-----------------------------販売先注文番号---------------------------");
					logger.debug(deliveryResult.get(i).getOrderCd());
					logger.debug("-----------------------------invoice作成日----------------------------");
					logger.debug(deliveryResult.get(i).getIssueDate());
					logger.debug("-----------------------------品名----------------------------");
					logger.debug(deliveryResult.get(i).getItemName());
					logger.debug("-----------------------------型番----------------------------");
					logger.debug(deliveryResult.get(i).getModelCd());
					logger.debug("-----------------------------数量----------------------------");
					logger.debug(deliveryResult.get(i).getQuantity());
					logger.debug("-----------------------------販売金額----------------------------");
					logger.debug(deliveryResult.get(i).getAmount());
					logger.debug("-----------------------------貿易条件----------------------------");
					logger.debug(deliveryResult.get(i).getShippingType());
					logger.debug("-----------------------------通費----------------------------");
					logger.debug(deliveryResult.get(i).getCarringAmount());
				}
			}

		} catch (Exception e) {
			logger.trace(UDebugger.trace(e));
		}
		return "success";
	}

	public List<DeliveryResult> getDeliveryResult() {
		return deliveryResult;
	}

	public void setDeliveryResult(List<DeliveryResult> deliveryResult) {
		this.deliveryResult = deliveryResult;
	}

	public String getRoleID() {
		return roleID;
	}

	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}

}
