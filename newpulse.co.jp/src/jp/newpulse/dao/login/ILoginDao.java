package jp.newpulse.dao.login;

import jp.newpulse.dao.DAOException;
import jp.newpulse.entity.Users;

public interface ILoginDao {

	Users findByCodeAndPassword(String code, String password)
			throws DAOException;

}
