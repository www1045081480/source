package com.np.base.dml.cmn;

import java.util.Comparator;

public class IntComparator implements Comparator<Integer> {
	public int compare(Integer v1, Integer v2) {
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
