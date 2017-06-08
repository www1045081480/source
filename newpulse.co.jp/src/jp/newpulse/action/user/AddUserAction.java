package jp.newpulse.action.user;

import java.io.File;
import java.util.List;

import com.np.base.utils.UFile;
import com.np.order.biz.mast.UserMgr;
import com.np.order.models.Users;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class AddUserAction extends ActionSupport {
	private String userID;
	private String ename;
	private String jname;
	private String cname;
	private String role;
	private String password;
	private String id;
	private File sign;
	private String signFileName;
	private String signContentType;

	private File seal;
	private String sealFileName;
	private String sealContentType;
	private List<Users> userList;

	public String execute() throws Exception {
		if ((null == userID) || userID.equals("")) {
			// 角色类型转换
			Users users = new Users();
			Long setrole = Long.parseLong(role);
			// 电子签名和印章类型转换
			byte[] setsign = UFile.read(sign).array();
			byte[] setseal = UFile.read(seal).array();
			users.setPassword(password);
			users.setEnName(ename);
			users.setJpName(jname);
			users.setCnName(cname);
			users.setAccount(id);
			users.setRole(setrole);
			users.setSeal(setseal);
			users.setSign(setsign);
			UserMgr.registerUser(users);
			// 插入后检索
			Users users1 = new Users();
			userList = UserMgr.searchUsers(users1);
		} else {
			Long userId = Long.parseLong(userID);
			@SuppressWarnings("unused")
			boolean f = UserMgr.removeUser(userId);
			Users users = new Users();
			Long setrole = Long.parseLong(role);
			// 电子签名和印章类型转换
			byte[] setsign = UFile.read(sign).array();
			byte[] setseal = UFile.read(seal).array();
			users.setPassword(password);
			users.setEnName(ename);
			users.setJpName(jname);
			users.setCnName(cname);
			users.setAccount(id);
			users.setRole(setrole);
			users.setSeal(setseal);
			users.setSign(setsign);
			UserMgr.registerUser(users);
			// 插入后检索
			Users users1 = new Users();
			userList = UserMgr.searchUsers(users1);
		}
		return "success";
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getJname() {
		return jname;
	}

	public void setJname(String jname) {
		this.jname = jname;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public File getSign() {
		return sign;
	}

	public void setSign(File sign) {
		this.sign = sign;
	}

	public File getSeal() {
		return seal;
	}

	public void setSeal(File seal) {
		this.seal = seal;
	}

	public String getSignFileName() {
		return signFileName;
	}

	public void setSignFileName(String signFileName) {
		this.signFileName = signFileName;
	}

	public String getSignContentType() {
		return signContentType;
	}

	public void setSignContentType(String signContentType) {
		this.signContentType = signContentType;
	}

	public String getSealFileName() {
		return sealFileName;
	}

	public void setSealFileName(String sealFileName) {
		this.sealFileName = sealFileName;
	}

	public String getSealContentType() {
		return sealContentType;
	}

	public void setSealContentType(String sealContentType) {
		this.sealContentType = sealContentType;
	}

	public List<Users> getUserList() {
		return userList;
	}

	public void setUserList(List<Users> userList) {
		this.userList = userList;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}
}
