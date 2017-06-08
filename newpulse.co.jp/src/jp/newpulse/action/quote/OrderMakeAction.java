package jp.newpulse.action.quote;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class OrderMakeAction extends ActionSupport {
	private static Log logger = LogFactory.getLog(OrderMakeAction.class);
	private String EstimationId;
	private String reqStr;
	private String langFlg;

	public String getLangFlg() {
		return langFlg;
	}

	public void setLangFlg(String langFlg) {
		this.langFlg = langFlg;
	}

	public String getReqStr() {
		return reqStr;
	}

	public void setReqStr(String reqStr) {
		this.reqStr = reqStr;
	}

	public String getEstimationId() {
		return EstimationId;
	}

	public void setEstimationId(String estimationId) {
		EstimationId = estimationId;
	}

	public String execute() {
		logger.debug("EstimationId=" + EstimationId);
		logger.debug("reqStr=" + reqStr);
		logger.debug("langFlg=" + langFlg);
		if ("EN".equals(langFlg)) {
			return "successEN";
		} else if ("CN".equals(langFlg)) {
			return "successCN";
		}
		return "successJP";
	}
}
