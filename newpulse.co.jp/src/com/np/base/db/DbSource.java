package com.np.base.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class DbSource {
	private static String driver = "org.postgresql.Driver";
	// private static String url =
	// "jdbc:postgresql://192.168.1.4:5432/order?charSet=UTF-8";
	private static String url = "jdbc:postgresql://localhost:5432/join?charSet=UTF-8";
	private static boolean usePool = false;

	public static Connection getConnection() {
		if (usePool)
			return getConnectionFromPool();
		else
			return getConnectionByPureJDBC();
	}

	public static Connection getConnectionByPureJDBC() {

		String username = "postgres";
		String password = "sa";

		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, username, password);
			return con;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Connection getConnectionFromPool() {
		String username = "postgres";
		String password = "sa";

		BasicDataSource ds = setupDataSource();
		ds.setUsername(username);
		ds.setPassword(password);

		try {
			return ds.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public static BasicDataSource setupDataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(url);

		ds.setInitialSize(10);
		ds.setMaxTotal(100);
		return ds;

	}

}
