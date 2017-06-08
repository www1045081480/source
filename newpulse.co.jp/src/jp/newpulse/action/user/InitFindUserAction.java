package jp.newpulse.action.user;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.view.ViewModelMapper;
import com.np.order.biz.mast.UserMgr;
import com.np.order.models.Users;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class InitFindUserAction extends ActionSupport {
	private static Log logger = LogFactory.getLog(InitFindUserAction.class);
	private List<Users> userList;

	public String execute() throws Exception {
		Users user = ViewModelMapper.toModel(Users.class);
		// 検索
		userList = UserMgr.searchUsers(user);
		logger.debug("------------------userList--------------");
		logger.debug(userList);
		return "success";
	}

	public List<Users> getUserList() {
		return userList;
	}

	public void setUserList(List<Users> userList) {
		this.userList = userList;
	}

}
