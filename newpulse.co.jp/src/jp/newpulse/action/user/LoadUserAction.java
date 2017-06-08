package jp.newpulse.action.user;

import java.util.List;

import com.np.base.view.ViewModelMapper;
import com.np.order.biz.mast.UserMgr;
import com.np.order.models.Users;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class LoadUserAction extends ActionSupport {

	private List<Users> userList;

	public String execute() throws Exception {

		// req.setParam(Users.JpName, "田中");
		Users user = ViewModelMapper.toModel(Users.class);
		// 検索
		userList = UserMgr.searchUsers(user);
		return "success";
	}

	public List<Users> getUserList() {
		return userList;
	}

	public void setUserList(List<Users> userList) {
		this.userList = userList;
	}

}
