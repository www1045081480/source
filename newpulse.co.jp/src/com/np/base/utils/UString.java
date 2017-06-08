package com.np.base.utils;

public class UString {

	public static boolean equals(String s1, String s2) {
		if (s1 == null && s2 == null)
			return true;
		if (s1 == null || s2 == null)
			return false;
		return s1.equals(s2);
	}

	public static boolean isEmpty(Object s) {
		if (s == null)
			return true;

		return isEmpty(String.valueOf(s));
	}

	public static boolean isEmpty(String s) {
		if (s == null)
			return true;
		if (s.isEmpty())
			return true;
		return false;
	}

	public static boolean notEmpty(String s) {
		return isEmpty(s) == false;
	}

	public static String format(int x, int size) {
		StringBuffer s = new StringBuffer(Integer.toString(x));
		while (s.length() < size) {
			s.insert(0, '0');
		}
		return s.toString();
	}
}
