package jp.newpulse.action.quote;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class QuoteLoad extends ActionSupport {
	private String langflg;

	public String getLangflg() {
		return langflg;
	}

	public void setLangflg(String langflg) {
		this.langflg = langflg;
	}

	public String execute() throws Exception {
		if ("CN".equals(langflg)) {
			return "successCn";
		}
		return "successJp";

	}

}
