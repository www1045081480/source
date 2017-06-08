package jp.newpulse.action.quote;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class ReadOrder extends BaseJsonAction {

	private String OrderId;
	private String langFlg;

	public String execute() throws Exception {
		if ("EN".equals(langFlg)) {
			return "successEN";
		} else if ("CN".equals(langFlg)) {
			return "successCN";
		}
		return "successJP";
	}

	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	public String getLangFlg() {
		return langFlg;
	}

	public void setLangFlg(String langFlg) {
		this.langFlg = langFlg;
	}
}
