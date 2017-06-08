package com.np.base.dml.cmn;

import java.lang.reflect.Method;
import java.util.Comparator;

public class AccessMethod {
	public Method invoker;
	@SuppressWarnings("rawtypes")
	public Comparator comparator;

	@SuppressWarnings("rawtypes")
	public Comparator getComparator() {
		return comparator;
	}

	@SuppressWarnings("rawtypes")
	public void setComparator(Comparator comparator) {
		this.comparator = comparator;
	}

	public Method getInvoker() {
		return invoker;
	}

	public void setInvoker(Method invoker) {
		this.invoker = invoker;
	}
}
