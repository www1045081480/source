package com.np.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UDate {

	public static String getLastDay(String yearMonth) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		Calendar calc = Calendar.getInstance();
		calc.setTime(format.parse(yearMonth));
		calc.set(Calendar.DAY_OF_MONTH, calc.getActualMaximum(Calendar.DAY_OF_MONTH));

		format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(calc.getTime());
	}

	public static String getDateByAdjustMonth(int adjustMonth) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar calc = Calendar.getInstance();
		calc.setTime(new Date());
		calc.add(Calendar.MONTH, adjustMonth);

		return format.format(calc.getTime());
	}

	public static String getDateByAdjustDay(int adjustDay) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar calc = Calendar.getInstance();
		calc.setTime(new Date());
		calc.add(Calendar.DAY_OF_MONTH, adjustDay);

		return format.format(calc.getTime());
	}

	public static String getDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(new Date());
	}

	public static String getYearYYYY() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		return format.format(new Date());
	}

	public static String getYearYYYYMM() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		return format.format(new Date());
	}

	public static String getYearYY() {
		SimpleDateFormat format = new SimpleDateFormat("yy");
		return format.format(new Date());
	}

	public static String getMonthDay() {
		SimpleDateFormat format = new SimpleDateFormat("MMdd");
		return format.format(new Date());
	}

}
