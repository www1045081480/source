package jp.newpulse.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class BaseAction extends ActionSupport implements SessionAware {

	protected Map<String, Object> session;

	public void setSession(Map<String, Object> arg0) {
		session = arg0;
	}

	public String execute() throws Exception {
		return "success";
	}

}
