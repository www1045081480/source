package jp.newpulse.action.user;

import java.util.List;

import com.np.order.biz.mast.UserMgr;
import com.np.order.models.Users;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class SearchUserAction extends BaseJsonAction {

	private List<Users> userList;
	private String cnName;
	private String enName;
	private String jpName;
	private String account;
	private String role;

	public String execute() throws Exception {
		Users users = new Users();
		users.setCnName(cnName);
		users.setEnName(enName);
		users.setJpName(jpName);
		users.setAccount(account);

		if (role != null && !"".equals(role))
			users.setRole(Long.parseLong(role));

		userList = UserMgr.searchUsers(users);
		response(userList);
		return "success";
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getJpName() {
		return jpName;
	}

	public void setJpName(String jpName) {
		this.jpName = jpName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Users> getUserList() {
		return userList;
	}

	public void setUserList(List<Users> userList) {
		this.userList = userList;
	}

}
