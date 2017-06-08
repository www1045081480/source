package jp.newpulse.dao.login;

import jp.newpulse.dao.DAOException;
import jp.newpulse.entity.Users;

public class LoginDaoImpl implements ILoginDao {

	@Override
	public Users findByCodeAndPassword(String code, String password) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * private String findByCodeAndPasswordSql =
	 * "select * from ADMIN_INFO where " + "ADMIN_CODE=? AND PASSWORD=?";
	 * 
	 * @Override public Admin findByCodeAndPassword(String code, String
	 * password) throws DAOException { if (code == null || password == null)
	 * return null; Connection con = DBUtil.getConnection(); try {
	 * PreparedStatement ps = con .prepareStatement(findByCodeAndPasswordSql);
	 * ps.setString(1, code); ps.setString(2, password); ResultSet rs =
	 * ps.executeQuery(); while (rs.next()) { Admin u = createUser(rs); return
	 * u; } } catch (SQLException e) { e.printStackTrace(); throw new
	 * DAOException("��ѯ�û�ʧ��", e); } finally { DBUtil.closeConnection(); }
	 * return null; }
	 * 
	 * private Admin createUser(ResultSet rs) throws SQLException { Admin u =
	 * new Admin(); u.setId(rs.getInt("id"));
	 * u.setAdminCode(rs.getString("admin_code"));
	 * u.setPassword(rs.getString("password")); u.setName(rs.getString("name"));
	 * return u; }
	 * 
	 * public static void main(String[] args) throws Exception { Admin user =
	 * new LoginDaoImpl().findByCodeAndPassword("lhh", "123");
	 * System.out.println(user); }
	 */

}
