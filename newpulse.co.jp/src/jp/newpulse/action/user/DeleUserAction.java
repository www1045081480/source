package jp.newpulse.action.user;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.np.base.utils.UJson;
import com.np.order.biz.mast.UserMgr;
import com.np.order.models.Users;

public class DeleUserAction {
	private static Log logger = LogFactory.getLog(DeleUserAction.class);
	private String account;

	public String execute() throws Exception {
		logger.debug(account);
		Users users = new Users();
		Long userId = Long.parseLong(account);
		users.setUserId(userId);
		logger.debug(users);
		boolean f = UserMgr.removeUser(userId);
		HttpServletResponse res = ServletActionContext.getResponse();
		res.reset();
		PrintWriter pw = res.getWriter();
		String json = UJson.toJsonString(f);
		logger.debug(json);
		pw.print(json);
		pw.flush();
		pw.close();
		return "success";
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

}
