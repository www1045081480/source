package com.np.base.dml.cmn;

import java.util.Comparator;

public class DoubleComparator implements Comparator<Double> {
	public int compare(Double v1, Double v2) {
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
