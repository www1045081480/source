package jp.newpulse.action.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.db.TxMgr;
import com.np.base.utils.MD5;
import com.np.order.models.Users;

public class ChangeSubUserAction {
	private static Log logger = LogFactory.getLog(ChangeSubUserAction.class);
	private String cname;
	private String ename;
	private String jname;
	private String role;
	private String password;
	private String sign;
	private String seal;
	private String id;

	public String execute() throws Exception {
		logger.debug(cname);
		logger.debug(ename);
		logger.debug(jname);
		logger.debug(role);
		logger.debug(password);
		// 角色类型转换
		// Long userId = SequenceMgr.nextSeq(Users.class);
		Users users = new Users();
		Long setid = Long.parseLong(id);
		Long setrole = Long.parseLong(role);
		// 电子签名和印章类型转换
		byte[] setsign = sign.getBytes("utf-8");
		byte[] setseal = seal.getBytes("utf-8");
		logger.debug(setsign);
		logger.debug(setseal);
		users.setUserId(setid);
		users.setPassword(MD5.digest(password));
		users.setCnName(cname);
		users.setEnName(ename);
		users.setJpName(jname);
		users.setRole(setrole);
		users.setSeal(setsign);
		users.setSign(setseal);

		TxMgr.update(users);

		return "success";

	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
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

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSeal() {
		return seal;
	}

	public void setSeal(String seal) {
		this.seal = seal;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
