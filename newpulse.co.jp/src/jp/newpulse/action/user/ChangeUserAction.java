package jp.newpulse.action.user;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.view.ViewModelMapper;
import com.np.order.biz.mast.UserMgr;
import com.np.order.models.Users;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class ChangeUserAction extends BaseJsonAction {
	private static Log logger = LogFactory.getLog(ChangeUserAction.class);
	private List<Users> userchangeList;

	public String execute() throws Exception {
		Users user = ViewModelMapper.toModel(Users.class);
		// 検索
		userchangeList = UserMgr.searchUsers(user);

		logger.debug(userchangeList);
		response(userchangeList);
		return "success";
	}

	public List<Users> getUserchangeList() {
		return userchangeList;
	}

	public void setUserchangeList(List<Users> userchangeList) {
		this.userchangeList = userchangeList;
	}

}