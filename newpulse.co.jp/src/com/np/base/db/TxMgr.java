package com.np.base.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.np.base.orm.ColumnField;
import com.np.base.orm.ModelMapper;
import com.np.base.orm.SQLBuilder;
import com.np.base.reflect.PojoUtils;
import com.np.base.utils.UList;
import com.np.order.Money;
import com.np.order.action.DateFormatter;

public class TxMgr {
	private static final Logger logger = Logger.getLogger(TxMgr.class);

	public static void fetch(ITxProc iTxProc) {
		Connection con = DbSource.getConnection();
		try {
			con.setAutoCommit(false);
			con.setReadOnly(true);
			iTxProc.process(con);
			con.commit();
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				throw new RuntimeException(e);
			}
			throw new RuntimeException(e);
		} finally {
			try {
				con.close();
			} catch (SQLException e1) {
				throw new RuntimeException(e1);
			}
		}
	}

	public static void update(ITxProc iTxProc) throws Exception {
		Connection con = DbSource.getConnection();
		try {
			con.setAutoCommit(false);
			con.setReadOnly(false);
			iTxProc.process(con);
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
			throw e;
		} finally {
			con.close();
		}
	}

	public static void insert(final Object obj) throws Exception {
		update(new ITxProc() {

			@Override
			public void process(Connection con) throws Exception {
				insert(con, obj);
			}

		});
	}

	public static void insert(Connection con, final Object obj) throws Exception {
		if (con == null) {
			insert(obj);
		} else {
			_insert(con, obj);
		}
	}

	public static void _insert(Connection con, final Object obj) throws Exception {
		ModelMapper mapper = PojoUtils.getMapper(obj.getClass());
		if (mapper.containsProperty(ModelMapper.RegTime)) {
			mapper.setValue(obj, ModelMapper.RegTime, System.currentTimeMillis());
		}
		String sql = SQLBuilder.createInserSQL(obj);
		PreparedStatement pstmt = con.prepareStatement(sql);

		int index = 1;
		StringBuffer params = new StringBuffer();
		for (String name : mapper.getNames()) {
			Object value = mapper.getValue(obj, name);
			ColumnField field = mapper.getField(name);
			if (value == null) {
				/*
				 * 必須をチェック
				 */
				if (field.isNullable() == false)
					throw new NullPointerException(name);
				pstmt.setNull(index, mapper.getSqlType(name));
			} else {
				/*
				 * 最大サイズをチェック
				 */
				if (field.getLength() > 0 && String.class.isInstance(value)) {
					String str = String.class.cast(value);
					if (field.getColumn().endsWith("DATE"))
						str = DateFormatter.toModel(str);
					if (str.length() > field.getLength()) {
						str = str.substring(0, field.getLength());
					}
					value = str;
				}
				if (Money.class.isInstance(value))
					value = Integer.valueOf(Money.class.cast(value).intValue());
				pstmt.setObject(index, value);
			}
			index++;
			if (params.length() == 0)
				params.append("(");
			else
				params.append(", ");

			params.append(value);
		}
		params.append(")");
		logger.debug(params.toString());

		pstmt.executeUpdate();
	}

	@SuppressWarnings("rawtypes")
	public static void insertList(final List objs) throws Exception {
		if (UList.isEmpty(objs))
			return;

		update(new ITxProc() {

			@Override
			public void process(Connection con) throws Exception {
				insertList(con, objs);
			}

		});
	}

	@SuppressWarnings("rawtypes")
	public static void insertList(Connection con, List objs) throws Exception {
		if (UList.isEmpty(objs))
			return;

		Object first = objs.get(0);
		ModelMapper mapper = PojoUtils.getMapper(first.getClass());
		String sql = SQLBuilder.createInserSQL(first);
		PreparedStatement pstmt = con.prepareStatement(sql);

		StringBuffer params = new StringBuffer();
		for (Object obj : objs) {
			int index = 1;
			params.delete(0, params.length());

			if (mapper.containsProperty(ModelMapper.RegTime)) {
				mapper.setValue(obj, ModelMapper.RegTime, System.currentTimeMillis());
			}

			for (String column : mapper.getColumns()) {
				String name = mapper.toPropertyName(column);
				Object value = mapper.getValue(obj, name);
				ColumnField field = mapper.getField(name);
				if (value == null) {
					/*
					 * 必須をチェック
					 */
					if (field.isNullable() == false)
						throw new NullPointerException(name);
					pstmt.setNull(index++, mapper.getSqlType(name));
				} else {
					/*
					 * 最大サイズをチェック
					 */
					if (field.getLength() > 0 && String.class.isInstance(value)) {
						String str = String.class.cast(value);
						if (field.getColumn().endsWith("DATE"))
							str = DateFormatter.toModel(str);
						if (str.length() > field.getLength()) {
							str = str.substring(0, field.getLength());
						}
						value = str;
					}
					if (Money.class.isInstance(value))
						value = Integer.valueOf(Money.class.cast(value).intValue());
					pstmt.setObject(index++, value);
				}
				if (params.length() == 0)
					params.append("(");
				else
					params.append(", ");

				params.append(value);
			}
			params.append(")");
			logger.debug(params.toString());

			pstmt.addBatch();
		}
		pstmt.executeBatch();

	}

	public static void update(final Object obj) throws Exception {
		update(new ITxProc() {

			@Override
			public void process(Connection con) throws Exception {
				update(con, obj);
			}

		});
	}

	public static void update(Connection con, Object obj) throws Exception {
		ModelMapper mapper = PojoUtils.getMapper(obj.getClass());
		if (mapper.containsProperty(ModelMapper.UpdTime)) {
			mapper.setValue(obj, ModelMapper.UpdTime, System.currentTimeMillis());
		}
		String sql = SQLBuilder.createUpdateSQL(obj);
		PreparedStatement pstmt = con.prepareStatement(sql);

		int index = 1;

		for (String column : mapper.getNonPKNames()) {
			Object value = mapper.getValue(obj, column);
			if (value == null)
				continue;

			/*
			 * 最大サイズをチェック
			 */
			String name = mapper.toPropertyName(column);
			ColumnField field = mapper.getField(name);
			if (field.getLength() > 0 && String.class.isInstance(value)) {
				String str = String.class.cast(value);
				if (field.getColumn().endsWith("DATE"))
					str = DateFormatter.toModel(str);
				if (str.length() > field.getLength()) {
					str = str.substring(0, field.getLength());
				}
				value = str;
			}
			if (Money.class.isInstance(value))
				value = Integer.valueOf(Money.class.cast(value).intValue());

			pstmt.setObject(index++, value);
		}

		for (String column : mapper.getPrimaryKeys()) {
			Object value = mapper.getValue(obj, column);
			if (value == null)
				continue;
			/*
			 * 最大サイズをチェック
			 */
			String name = mapper.toPropertyName(column);
			ColumnField field = mapper.getField(name);
			if (field.getLength() > 0 && String.class.isInstance(value)) {
				String str = String.class.cast(value);
				if (field.getColumn().endsWith("DATE"))
					str = DateFormatter.toModel(str);
				if (str.length() > field.getLength()) {
					str = str.substring(0, field.getLength());
				}
				value = str;
			}
			if (Money.class.isInstance(value))
				value = Integer.valueOf(Money.class.cast(value).intValue());
			pstmt.setObject(index++, value);
		}
		pstmt.executeUpdate();

	}

	@SuppressWarnings("rawtypes")
	public static void updateList(final List objs) throws Exception {
		update(new ITxProc() {

			@Override
			public void process(Connection con) throws Exception {
				updateList(con, objs);
			}

		});
	}

	@SuppressWarnings("rawtypes")
	public static void updateList(Connection con, List objs) throws Exception {
		for (Object obj : objs)
			update(con, obj);
	}

	public static void delete(final Object obj) throws Exception {
		update(new ITxProc() {

			@Override
			public void process(Connection con) throws Exception {
				delete(con, obj);
			}

		});
	}

	public static void delete(Connection con, Object obj) throws Exception {

		String sql = SQLBuilder.createDeleteSQL(obj);
		PreparedStatement pstmt = con.prepareStatement(sql);

		ModelMapper mapper = PojoUtils.getMapper(obj.getClass());
		int index = 1;
		for (String column : mapper.getPrimaryKeys()) {
			Object value = mapper.getValue(obj, column);
			if (value == null)
				continue;
			pstmt.setObject(index++, value);
		}
		pstmt.executeUpdate();

	}
}
