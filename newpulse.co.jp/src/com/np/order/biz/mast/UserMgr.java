package com.np.order.biz.mast;

import java.util.List;

import com.np.base.db.SequenceMgr;
import com.np.base.db.TxMgr;
import com.np.base.orm.Criteria;
import com.np.base.orm.Restrictions;
import com.np.base.utils.MD5;
import com.np.base.utils.UString;
import com.np.order.action.SessionMgr;
import com.np.order.models.Users;

public class UserMgr {

	public static boolean registerUser(Users user) throws Exception {
		/*
		 * TODO:存在チェック
		 */

		/*
		 * 自動採番
		 */
		Long userId = SequenceMgr.nextSeq(Users.class);
		user.setUserId(userId);

		/*
		 * 暗号化
		 */
		user.setPassword(MD5.digest(user.getPassword()));

		/*
		 * DBにInsert
		 */
		TxMgr.insert(user);
		return true;
	}

	public static boolean modifyUser(Users user) throws Exception {
		/*
		 * TODO:存在チェック
		 */
		user.setPassword(MD5.digest(user.getPassword()));
		TxMgr.update(user);
		return true;
	}

	public static boolean removeUser(Long userId) throws Exception {
		Users user = new Users();
		user.setUserId(userId);
		TxMgr.delete(user);
		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Users> searchUsers(Users user) throws Exception {
		Criteria<Users> criteria = new Criteria(Users.class);

		/*
		 * 管理者以外、自分のみ見える
		 */
		if (SessionMgr.isAdministrator() == false)
			user.setUserId(SessionMgr.getLoginUserId());

		if (user.getUserId() != null)
			criteria.and(Restrictions.eq(Users.UserId, user.getUserId()));
		if (UString.notEmpty(user.getCnName()))
			criteria.and(Restrictions.eq(Users.CnName, user.getCnName()));
		if (UString.notEmpty(user.getJpName()))
			criteria.and(Restrictions.eq(Users.JpName, user.getJpName()));
		if (UString.notEmpty(user.getEnName()))
			criteria.and(Restrictions.eq(Users.EnName, user.getEnName()));
		if (UString.notEmpty(user.getAccount()))
			criteria.and(Restrictions.eq(Users.Account, user.getAccount()));
		// if (UString.notEmpty(user.getRole().toString()))
		// criteria.and(Restrictions.eq(Users.Role, user.getRole()));

		criteria.addSelectedColumn(Users.UserId);
		criteria.addSelectedColumn(Users.EnName);
		criteria.addSelectedColumn(Users.JpName);
		criteria.addSelectedColumn(Users.CnName);
		criteria.addSelectedColumn(Users.Role);
		criteria.addSelectedColumn(Users.Account);

		return criteria.list();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Users searchUser(Long userId) throws Exception {
		Criteria<Users> criteria = new Criteria(Users.class);
		criteria.and(Restrictions.eq(Users.UserId, userId));

		criteria.addSelectedColumn(Users.UserId);
		criteria.addSelectedColumn(Users.EnName);
		criteria.addSelectedColumn(Users.JpName);
		criteria.addSelectedColumn(Users.CnName);
		criteria.addSelectedColumn(Users.Role);

		Users user = criteria.get();
		return user;
	}

	/*
	 * 印鑑イメージを取得
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static byte[] getSeal(Long userId) throws Exception {
		Criteria<Users> criteria = new Criteria(Users.class);
		criteria.and(Restrictions.eq(Users.UserId, userId));

		criteria.addSelectedColumn(Users.Seal);

		Users user = criteria.get();
		return (user == null) ? null : user.getSeal();
	}

	/*
	 * サインイメージを取得
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static byte[] getSign(Long userId) throws Exception {
		Criteria<Users> criteria = new Criteria(Users.class);
		criteria.and(Restrictions.eq(Users.UserId, userId));

		criteria.addSelectedColumn(Users.Sign);

		Users user = criteria.get();
		return (user == null) ? null : user.getSign();
	}

}
