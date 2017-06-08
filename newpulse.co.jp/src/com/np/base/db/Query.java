package com.np.base.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.np.base.dml.Matcher;
import com.np.base.dml.PojoCollections;
import com.np.base.models.JoinKey;
import com.np.base.models.JoinedModels;
import com.np.base.orm.Criteria;
import com.np.base.orm.ModelMapper;
import com.np.base.orm.Restrictions;
import com.np.base.orm.SQLBuilder;
import com.np.base.reflect.PojoUtils;
import com.np.base.utils.UList;

public class Query {
	private static final Logger logger = Logger.getLogger(Query.class);

	@SuppressWarnings("rawtypes")
	public static <T> List<JoinedModels> queryWithDependencies(T filter) {
		Class type = filter.getClass();
		ModelMapper mapper = PojoUtils.getMapper(type);
		List<JoinedModels> result = queryWithDependencies(filter, mapper.getJoinTables());
		return result;
	}

	@SuppressWarnings("rawtypes")
	public static <T> List<JoinedModels> queryWithDependencies(T filter, Class... joinTypes) {
		List<T> tras = query(filter);
		return queryWithDependencies(tras, joinTypes);
	}

	@SuppressWarnings("rawtypes")
	public static <T> List<JoinedModels> queryWithDependencies(Criteria<T> criteria, Class... joinTypes) {
		List<T> tras = criteria.list();
		return queryWithDependencies(tras, joinTypes);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<JoinedModels> queryWithDependencies(List<T> tras, Class... joinTypes) {
		T first = UList.first(tras);
		if (first == null)
			return new ArrayList<JoinedModels>();

		Class mainType = first.getClass();
		List result = tras;
		ModelMapper mapper = PojoUtils.getMapper(mainType);
		for (int i = 0; i < joinTypes.length; i++) {
			/*
			 * 関連テーブル
			 */
			Class joinType = joinTypes[i];

			/*
			 * 関連キー
			 */
			JoinKey joinKey = PojoUtils.getMapper(joinType).getJoinKey(mapper);
			if (joinKey == null) {
				logger.debug("[ERROR] joinKey[" + joinType.getSimpleName() + "] = " + joinKey);
				continue;
			}

			/*
			 * Join Keys
			 */
			List refKeys = PojoCollections.getUniqueKeyList(result, joinKey.getLeftKey());
			if (refKeys.isEmpty()) {
				logger.debug("[ERROR] refKeys[" + joinType.getSimpleName() + "] = " + refKeys);
				continue;
			}

			/*
			 * 関連テーブルを検索
			 */
			Criteria criteria = new Criteria(joinType);
			criteria.and(Restrictions.in(joinKey.getRightKey(), refKeys));
			List joinList = criteria.list();

			/*
			 * Join
			 */
			result = Matcher.innerJoin(result, joinList, joinKey);
		}

		return result;
	}

	public static <T> List<T> query(final T filter) {
		final List<T> result = new ArrayList<T>();

		TxMgr.fetch(new ITxProc() {

			@Override
			public void process(Connection con) throws Exception {
				ModelMapper mapper = PojoUtils.getMapper(filter.getClass());
				String sql = SQLBuilder.createSelectSQL(filter);
				PreparedStatement stmt = con.prepareStatement(sql);

				int index = 1;
				for (String col : mapper.getColumns()) {
					Object value = mapper.getValue(filter, mapper.toPropertyName(col));
					if (value == null)
						continue;
					stmt.setObject(index++, value);
				}

				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					@SuppressWarnings("unchecked")
					T obj = (T) mapper.toModel(rs);
					result.add(obj);
				}
				rs.close();
				stmt.close();
			}

		});

		return result;
	}

}