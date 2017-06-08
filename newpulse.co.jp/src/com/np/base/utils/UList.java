package com.np.base.utils;

import java.util.List;

public class UList {

	public static boolean isEmpty(Object[] list) {
		if (list == null)
			return true;
		return list.length == 0;
	}

	@SuppressWarnings("rawtypes")
	public static boolean notEmpty(List list) {
		return isEmpty(list) == false;
	}

	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(List list) {
		if (list == null)
			return true;
		return list.isEmpty();
	}

	public static <T> T first(List<T> list) {
		if (isEmpty(list))
			return null;
		return list.get(0);
	}

	public static <T> T last(List<T> list) {
		if (isEmpty(list))
			return null;
		return list.get(list.size() - 1);
	}

}
