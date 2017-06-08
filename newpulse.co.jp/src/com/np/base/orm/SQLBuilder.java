package com.np.base.orm;

import org.apache.log4j.Logger;

import com.np.base.reflect.PojoUtils;

public class SQLBuilder {
	private static final Logger logger = Logger.getLogger(SQLBuilder.class);

	public static String createSelectSQL(Object obj) {
		ModelMapper mapper = PojoUtils.getMapper(obj.getClass());

		StringBuffer sql = new StringBuffer();

		sql.append("SELECT ");

		boolean first = true;
		for (String column : mapper.getColumns()) {
			if (!first) {
				sql.append(",");
			}
			sql.append(column);
			first = false;
		}
		sql.append(" FROM ");
		sql.append(mapper.getTablename());

		first = true;
		for (String name : mapper.getNames()) {
			Object value = mapper.getValue(obj, name);
			if (value == null)
				continue;

			if (!first)
				sql.append(" AND ");
			else
				sql.append(" WHERE ");

			sql.append(mapper.toColumnName(name));
			sql.append("=");
			sql.append("?");
			first = false;
		}

		logger.debug(sql);
		return sql.toString();
	}

	public static String createInserSQL(Object obj) {
		ModelMapper mapper = PojoUtils.getMapper(obj.getClass());

		StringBuilder sql = new StringBuilder();
		StringBuilder sqlValues = new StringBuilder();
		StringBuilder sqlColumns = new StringBuilder();

		sql.append("INSERT INTO ");
		sql.append(mapper.getTablename());

		boolean first = true;
		for (String name : mapper.getNames()) {

			if (!first) {
				sqlValues.append(",");
				sqlColumns.append(",");
			}
			sqlColumns.append(mapper.toColumnName(name));
			first = false;
			if (mapper.isBinary(name)) {
				/*
				 * TODO: Blob
				 */
				sqlValues.append("?");
			} else {
				sqlValues.append("?");
			}

		}

		sql.append("(");
		sql.append(sqlColumns);
		sql.append(")VALUES(");
		sql.append(sqlValues);
		sql.append(")");

		logger.debug(sql);
		return sql.toString();
	}

	public static String createUpdateSQL(Object obj) {
		ModelMapper mapper = PojoUtils.getMapper(obj.getClass());

		StringBuffer sql = new StringBuffer();
		StringBuilder sqlSet = new StringBuilder();
		StringBuilder sqlWhere = new StringBuilder();

		sql.append("UPDATE ");
		sql.append(mapper.getTablename());

		boolean first = true;
		for (String pk : mapper.getPrimaryKeys()) {
			Object value = mapper.getValue(obj, pk);
			if (value == null)
				continue;

			if (!first)
				sqlWhere.append(" AND ");
			first = false;
			sqlWhere.append(mapper.toColumnName(pk));
			sqlWhere.append("=");
			sqlWhere.append("?");
		}

		first = true;
		for (String column : mapper.getNonPKNames()) {
			Object value = mapper.getValue(obj, column);
			if (value == null) {
				continue;
			}

			if (!first)
				sqlSet.append(",");
			first = false;

			sqlSet.append(mapper.toColumnName(column));
			sqlSet.append("=");
			sqlSet.append("?");
		}

		sql.append(" SET ");
		sql.append(sqlSet);
		sql.append(" WHERE ");
		sql.append(sqlWhere);

		logger.debug(sql);
		return sql.toString();
	}

	public static String createDeleteSQL(Object obj) {
		ModelMapper mapper = PojoUtils.getMapper(obj.getClass());

		StringBuffer sql = new StringBuffer();

		sql.append("DELETE  FROM ");
		sql.append(mapper.getTablename());

		sql.append(" WHERE ");
		boolean first = true;
		for (String pk : mapper.getPrimaryKeys()) {
			Object value = mapper.getValue(obj, pk);
			if (value == null)
				continue;

			if (!first)
				sql.append(" AND ");
			sql.append(mapper.toColumnName(pk));
			sql.append("=");
			sql.append("?");
			first = false;
		}

		logger.debug(sql);
		return sql.toString();
	}

}
