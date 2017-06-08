package com.np.base.dml.cmn;

import java.util.Comparator;

public class ClassComparator implements Comparator<Object> {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int compare(Object o1, Object o2) {
		if ((o1 == null) && (o2 == null))
			return 0;

		if ((o1 == null) && (o2 != null))
			return -1;

		if ((o1 != null) && (o2 == null))
			return 1;

		if ((o1 instanceof Comparable) == false)
			return -1;
		if ((o2 instanceof Comparable) == false)
			return 1;
		return ((Comparable) o1).compareTo(o2);
	}
}
