package com.np.base.db;

import java.sql.Connection;

public interface ITxProc {
	void process(Connection con) throws Exception;
}
