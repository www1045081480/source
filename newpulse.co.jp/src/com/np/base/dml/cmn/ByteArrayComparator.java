package com.np.base.dml.cmn;

import java.util.Comparator;

public class ByteArrayComparator implements Comparator<byte[]> {
	public int compare(byte[] v1, byte[] v2) {
		if (v1 == null)
			return -1;
		if (v2 == null)
			return 1;

		int l1 = v1.length;
		int l2 = v2.length;

		if (l1 == l2) {
			COMPARE: for (int i = 0; i < l2; i++) {
				if (v1[i] == v2[i])
					continue COMPARE;
				return v1[i] - v2[i];
			}
			return 0;
		} else if (l2 < l1) {
			COMPARE: for (int i = 0; i < l2; i++) {
				if (v1[i] == v2[i])
					continue COMPARE;
				return v1[i] - v2[i];
			}
			return 1;
		} else // if ( l2 > l1 )
		{
			COMPARE: for (int i = 0; i < l1; i++) {
				if (v1[i] == v2[i])
					continue COMPARE;
				return v1[i] - v2[i];
			}
			return -1;
		}
	}

}