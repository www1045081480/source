package com.np.base.orm;

import java.util.List;
import java.util.concurrent.Callable;

public class QueryTask<R> implements Callable<List<R>> {
	private Criteria<R> criteria;

	public QueryTask(Criteria<R> criteria) {
		this.criteria = criteria;
	}

	@Override
	public List<R> call() throws Exception {
		List<R> result = criteria.list();
		return result;
	}

}
