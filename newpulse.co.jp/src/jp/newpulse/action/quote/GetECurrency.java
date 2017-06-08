package jp.newpulse.action.quote;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.order.biz.invoice.EstmationSheetMgr;
import com.np.order.models.EstmationSheet;

import jp.newpulse.action.BaseJsonAction;

public class GetECurrency extends BaseJsonAction {
	@SuppressWarnings("unused")
	private static Log logger = LogFactory.getLog(GetECurrency.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String EstimationId;
	private String reqStr;
	private String currency;

	public String execute() throws Exception {
		if (reqStr.length() > 0) {
			String[] str1 = reqStr.split(";");
			String[] str2 = str1[0].split(",");
			EstimationId = str2[0];
		}
		EstmationSheet es = EstmationSheetMgr.search(Long.parseLong(EstimationId));
		response(es);
		return "success";
	}

	public String getEstimationId() {
		return EstimationId;
	}

	public void setEstimationId(String estimationId) {
		EstimationId = estimationId;
	}

	public String getReqStr() {
		return reqStr;
	}

	public void setReqStr(String reqStr) {
		this.reqStr = reqStr;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
