package com.np.order.biz.mast;

import com.np.order.action.SessionMgr;

public class PriorityMgr {

	/*
	 * 変更権限
	 */
	public static boolean canModifySheet() {
		/*
		 * 社長は変更不可
		 */
		if (SessionMgr.isPresident())
			return false;
		/*
		 * 副社長は変更不可
		 */
		if (SessionMgr.isVicePresident())
			return false;
		return true;
	}

	/*
	 * User変更権限
	 */
	public static boolean canModifyUser() {
		/*
		 * 社長は権限有り
		 */
		if (SessionMgr.isPresident())
			return true;
		/*
		 * 副社長は権限有り
		 */
		if (SessionMgr.isVicePresident())
			return true;

		/*
		 * 管理员は権限有り
		 */
		if (SessionMgr.isAdministrator())
			return true;

		return false;
	}

}
