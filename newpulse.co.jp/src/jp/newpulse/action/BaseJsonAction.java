package jp.newpulse.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.np.base.utils.UJson;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public abstract class BaseJsonAction extends ActionSupport {
	private Object jsonData;

	public void response(Object result) {
		try {
			HttpServletResponse res = ServletActionContext.getResponse();
			res.setCharacterEncoding("application/json;charset=utf-8");
			res.setHeader("Cache-Control", "no-cache");
			res.reset();

			if (result != null)
				jsonData = UJson.toJsonString(result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void responseForView(Object result) {
		try {
			HttpServletResponse res = ServletActionContext.getResponse();
			res.setCharacterEncoding("application/json;charset=utf-8");
			res.setHeader("Cache-Control", "no-cache");
			res.reset();

			if (result != null)
				jsonData = UJson.toJsonString(result, true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Object getJsonData() {
		return jsonData;
	}
}
