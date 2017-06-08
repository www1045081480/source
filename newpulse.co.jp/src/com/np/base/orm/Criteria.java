package com.np.base.orm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.np.base.db.ITxProc;
import com.np.base.db.TxMgr;
import com.np.base.reflect.PojoUtils;
import com.np.base.utils.UList;
import com.np.base.utils.UString;

public class Criteria<T> {
	private static final Logger logger = Logger.getLogger(Criteria.class);

	private final static int MAX_SIZE_OF_IN = 1000;
	private final static int MAX_THREADS = Math.max(Runtime.getRuntime().availableProcessors(), 4);
	private final static ExecutorService threadPool;

	static {
		threadPool = Executors.newFixedThreadPool(MAX_THREADS);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				threadPool.shutdown();
			}
		});

	}

	private final Class<T> model;
	private List<Criterion> filters;
	private List<Criterion> orFilters;
	private List<String> selectedColumns;

	public Criteria(Class<T> model) {
		this.model = model;
		this.orFilters = new ArrayList<Criterion>();
		this.filters = new ArrayList<Criterion>();
		this.selectedColumns = new ArrayList<String>();
	}

	public Criteria<T> and(Criterion c) {
		if (c.numberOfValues() > 0 && UString.isEmpty(c.getValue(0)))
			return this;

		filters.add(c);
		c.setName(PojoUtils.getMapper(model).toColumnName(c.getName()));
		return this;
	}

	public Criteria<T> or(Criterion c) {
		if (c.numberOfValues() > 0 && UString.isEmpty(c.getValue(0)))
			return this;

		orFilters.add(c);
		c.setName(PojoUtils.getMapper(model).toColumnName(c.getName()));
		return this;
	}

	public void addSelectedColumn(String selectedColumn) {
		this.selectedColumns.add(selectedColumn);
	}

	public Class<T> getModel() {
		return model;
	}

	protected List<Criterion> getFilters() {
		return filters;
	}

	protected List<String> getSelectedColumns() {
		return selectedColumns;
	}

	/*
	 * 一件データを取得
	 */
	public T get() {
		List<T> result = list(1);
		return UList.first(result);
	}

	/*
	 * データリストを取得
	 */

	public List<T> list() {
		return list(Integer.MAX_VALUE);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> list(int maxRows) {
		/*
		 * IN検索じゃない、あるいは、IN条件は最大値以内 そのまま検索に行く
		 */
		int sizeOfInClause = getInQuerySize();
		if (sizeOfInClause == 0)
			return new ArrayList<T>();
		if (sizeOfInClause <= MAX_SIZE_OF_IN)
			return fetch();

		/*
		 * IN条件の数は最大値以上の場合 最大値で分割して、平行検索する
		 */
		Criteria<T>[] crits = split();
		List<T> result = new ArrayList<T>();

		Future<List<T>>[] futures = new Future[crits.length];
		for (int i = 0; i < crits.length; i++) {
			futures[i] = threadPool.submit(new QueryTask(crits[i]));
		}

		try {
			for (int i = 0; i < futures.length; i++) {
				result.addAll(futures[i].get());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return result;
	}

	public int delete() throws Exception {
		final StringBuilder sql = new StringBuilder();
		sql.append("DELETE ");

		final ModelMapper mapper = PojoUtils.getMapper(model);
		sql.append(" FROM ");
		sql.append(mapper.getTablename());

		boolean first = true;
		if (UList.isEmpty(filters) == false) {
			sql.append(" WHERE ");
			first = true;
			for (Criterion filter : filters) {
				if (!first)
					sql.append(" AND ");
				first = false;
				sql.append(filter.toSqlString());
			}
		}
		logger.debug(sql);

		// Fetch
		final int[] result = new int[1];
		TxMgr.update(new ITxProc() {
			@Override
			public void process(Connection con) throws Exception {
				Statement stmt = con.createStatement();
				result[0] = stmt.executeUpdate(sql.toString());
				stmt.close();
			}
		});

		return result[0];
	}

	public int delete(Connection con) throws Exception {
		final StringBuilder sql = new StringBuilder();
		sql.append("DELETE ");

		final ModelMapper mapper = PojoUtils.getMapper(model);
		sql.append(" FROM ");
		sql.append(mapper.getTablename());

		boolean first = true;
		if (UList.isEmpty(filters) == false) {
			sql.append(" WHERE ");
			first = true;
			for (Criterion filter : filters) {
				if (!first)
					sql.append(" AND ");
				first = false;
				sql.append(filter.toSqlString());
			}
		}
		logger.debug(sql);

		// Fetch
		final int[] result = new int[1];

		Statement stmt = con.createStatement();
		result[0] = stmt.executeUpdate(sql.toString());
		stmt.close();

		return result[0];
	}

	public String toQuerySQL() {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");

		final ModelMapper mapper = PojoUtils.getMapper(model);

		final List<String> fetchColumns = (selectedColumns.isEmpty()) ? mapper.getNames() : selectedColumns;
		boolean first = true;
		for (String col : fetchColumns) {
			if (!first)
				sql.append(",");
			first = false;
			sql.append(mapper.toColumnName(col));
		}

		sql.append(" FROM ");
		sql.append(mapper.getTablename());

		if (UList.notEmpty(filters) || UList.notEmpty(orFilters)) {
			sql.append(" WHERE ");
			first = true;
			for (Criterion filter : filters) {

				/*
				 * TODO
				 */
				if (filter.getOp().equalsIgnoreCase("IN") && filter.numberOfValues() == 0) {
					if (!first)
						sql.append(" AND ");
					first = false;
					sql.append("1 != 1");
					continue;
				}
				if (filter.getOp().equalsIgnoreCase("NOT IN") && filter.numberOfValues() == 0)
					continue;
				// if (filter.getOp().equalsIgnoreCase("IN") &&
				// filter.numberOfValues() == 0)
				// return null;
				// if (filter.getOp().equalsIgnoreCase("NOT IN") &&
				// filter.numberOfValues() == 0)
				// return null;

				if (!first)
					sql.append(" AND ");
				first = false;
				sql.append(filter.toSqlString());
			}

			for (Criterion filter : orFilters) {

				/*
				 * TODO
				 */
				if (filter.getOp().equalsIgnoreCase("IN") && filter.numberOfValues() == 0)
					return null;
				if (filter.getOp().equalsIgnoreCase("NOT IN") && filter.numberOfValues() == 0)
					return null;

				if (!first)
					sql.append(" OR ");
				first = false;
				sql.append(filter.toSqlString());
			}
		}
		logger.debug(sql);

		return sql.toString();
	}

	private List<T> fetch() {
		final String sql = toQuerySQL();

		if (sql == null)
			return new ArrayList<T>();

		final ModelMapper mapper = PojoUtils.getMapper(model);
		final List<String> fetchColumns = (selectedColumns.isEmpty()) ? mapper.getNames() : selectedColumns;

		// Fetch
		final List<T> result = new ArrayList<T>();
		TxMgr.fetch(new ITxProc() {

			@Override
			public void process(Connection con) throws Exception {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql.toString());
				while (rs.next()) {
					T obj = ModelMapper.toModel(rs, model, fetchColumns);
					result.add(obj);
				}
				rs.close();

			}

		});

		logger.debug("Fetch Result : " + result.size() + " 件");
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Criteria<T>[] split() {

		InCriterion inFilter = null;
		for (Criterion filter : filters) {
			if (filter.getOp().equalsIgnoreCase("IN")) {
				inFilter = (InCriterion) filter;
				filters.remove(filter);
				break;
			}
		}

		if (inFilter == null)
			return null;

		List inValues = inFilter.getValues();
		int numberOfParts = inValues.size() / MAX_SIZE_OF_IN;
		if (inValues.size() % MAX_SIZE_OF_IN > 0)
			numberOfParts++;

		Criteria<T>[] parts = new Criteria[numberOfParts];
		for (int i = 0; i < numberOfParts; i++) {
			Criteria<T> crit = new Criteria<T>(model);
			parts[i] = crit;

			crit.selectedColumns.addAll(this.selectedColumns);

			int fromIndex = i * MAX_SIZE_OF_IN;
			int toIndex = fromIndex + MAX_SIZE_OF_IN;
			if (toIndex > inValues.size())
				toIndex = inValues.size();
			crit.filters.addAll(this.filters.subList(fromIndex, toIndex));
		}

		logger.debug("split : " + parts.length);

		return parts;
	}

	private int getInQuerySize() {
		for (Criterion filter : filters) {
			if (filter.getOp().equalsIgnoreCase("IN"))
				return filter.numberOfValues();
		}
		return -1;
	}

}
