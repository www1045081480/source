package com.np.order.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.np.order.biz.mast.RolerMgr;
import com.np.order.models.Rolers;
import com.np.order.models.Users;

public class SessionMgr {
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(SessionMgr.class);

	private static final String mySessionKey = "MY_SESSION";
	private static ThreadLocal<OrderSessionObj> threadLocal = new ThreadLocal<OrderSessionObj>();
	private static boolean testMode = false;
	private static String webRoot = ".";

	/*
	 * ログイン情報をセッションに保存
	 * 
	 * -> Session
	 */
	public static void login(Users user) {
		login(ServletActionContext.getRequest(), user);
	}

	public static void login(HttpServletRequest req, Users user) {
		webRoot = req.getServletContext().getRealPath("/");
		OrderSessionObj myObj = new OrderSessionObj();
		myObj.setUser(user);
		req.getSession(true).setAttribute(mySessionKey, myObj);
		begin(req);
	}

	/*
	 * ログイン情報をセッションから取得して、Requestスレッドに設定
	 * 
	 * Session -> Request
	 */
	public static boolean begin(HttpServletRequest req) {
		OrderSessionObj myObj = (OrderSessionObj) req.getSession().getAttribute(mySessionKey);

		webRoot = req.getServletContext().getRealPath("/");
		threadLocal.set(myObj);
		/*
		 * login URI:/newpulse/xinan/xinan/login.action
		 */
		String uri = req.getRequestURI();
		if (myObj == null && uri.contains(".action")) {
			if (uri.endsWith("/login.action") || uri.endsWith("/logout.action")) {

			} else {
				/*
				 * Session Timeout
				 */
				return false;
			}
		}
		return true;
	}

	public static boolean isLogined() {
		return getLoginUserId() != null;
	}

	public static Long getLoginUserId() {
		if (testMode)
			return Long.valueOf(1L);

		OrderSessionObj myObj = threadLocal.get();
		if (myObj == null) {
			/*
			 * TODO
			 */
			return null;
		}
		return myObj.getUserId();
	}

	public static Long getLoginRoleId() {
		if (testMode)
			return Long.valueOf(1L);

		OrderSessionObj myObj = threadLocal.get();
		if (myObj == null) {
			/*
			 * TODO
			 */
			return null;
		}
		return myObj.getUser().getRole();
	}

	public static Users getLoginUser() {
		OrderSessionObj myObj = threadLocal.get();
		if (myObj == null) {
			/*
			 * TODO
			 */
			return null;
		}
		return myObj.getUser();
	}

	public static String getWebRoot() {
		return webRoot;
	}

	/*
	 * 営業
	 */
	public static boolean isSaller() {
		return validation(RolerMgr.getRolerForSeller()) || validation(RolerMgr.getRolerForDistribution())
				|| isSellerDistributer();
	}

	/*
	 * 物流
	 */
	public static boolean isDistributer() {
		return validation(RolerMgr.getRolerForDistribution()) || validation(RolerMgr.getRolerForSeller())
				|| isSellerDistributer();
	}

	/*
	 * 営業物流
	 */
	public static boolean isSellerDistributer() {
		return validation(RolerMgr.getRolerForSellerDistribution());
	}

	/*
	 * 財務
	 */
	public static boolean isFinacer() {
		return validation(RolerMgr.getRolerForAccount());
	}

	/*
	 * 副社長
	 */
	public static boolean isVicePresident() {
		return validation(RolerMgr.getRolerForVicePresident());
	}

	/*
	 * 社長
	 */
	public static boolean isPresident() {
		return validation(RolerMgr.getRolerForPresident());
	}

	/*
	 * 管理员
	 */
	public static boolean isAdministrator() {
		return validation(RolerMgr.getRolerForAdministrator());
	}

	private static boolean validation(Rolers roler) {
		Long rolerId = SessionMgr.getLoginRoleId();
		if (rolerId == null)
			return false;

		if (roler != null && roler.getRolerId().equals(rolerId))
			return true;
		return false;
	}
}
