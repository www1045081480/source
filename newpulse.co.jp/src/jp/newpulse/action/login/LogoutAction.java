package jp.newpulse.action.login;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.newpulse.action.BaseAction;

@SuppressWarnings("serial")
public class LogoutAction extends BaseAction {
	private static Log logger = LogFactory.getLog(LogoutAction.class);

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String execute() throws Exception {
		logger.debug("############clear session#########");
		this.session.clear();
		return "success";
	}

}
