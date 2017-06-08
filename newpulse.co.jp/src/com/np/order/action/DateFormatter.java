package com.np.order.action;

public class DateFormatter {

	/*
	 * YYYYMMDD -> YYYY-MM-DD
	 */
	public static String toView(String ymd) {
		if (ymd == null)
			return ymd;
		if (ymd.length() != 8)
			return ymd;

		StringBuffer view = new StringBuffer();
		view.append(ymd.substring(0, 4));
		view.append("-");
		view.append(ymd.substring(4, 6));
		view.append("-");
		view.append(ymd.substring(6, 8));

		return view.toString();
	}

	/*
	 * YYYY-MM-DD -> YYYYMMDD
	 */
	public static String toModel(String ymd) {
		if (ymd == null)
			return ymd;
		if (ymd.length() != 10)
			return ymd;

		StringBuffer view = new StringBuffer();
		view.append(ymd.substring(0, 4));
		view.append(ymd.substring(5, 7));
		view.append(ymd.substring(8, 10));

		return view.toString();
	}

}
