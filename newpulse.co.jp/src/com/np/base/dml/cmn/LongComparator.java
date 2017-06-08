package com.np.base.dml.cmn;

import java.util.Comparator;

public class LongComparator implements Comparator<Long> {
	public int compare(Long v1, Long v2) {
		int comp;
		if (v2 < v1)
			comp = 1;
		else if (v2 > v1)
			comp = -1;
		else
			comp = 0;

		return comp;
	}
}
