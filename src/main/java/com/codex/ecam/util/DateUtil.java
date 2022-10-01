package com.codex.ecam.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	private static SimpleDateFormat COMMON_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	private static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

	public static String getCommonDateString(Date date) {
		if (date != null) {
			return COMMON_DATE_FORMAT.format(date);
		} else {
			return "";
		}
	}

	public static String getSimpleDateString(Date date) {
		if (date != null) {
			return SIMPLE_DATE_FORMAT.format(date);
		} else {
			return "";
		}
	}

	public static Date getDateObj(String date) {
		try {
			return SIMPLE_DATE_FORMAT.parse(date);
		} catch (final ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date getDateTimeObj(String date) {
		try {
			return COMMON_DATE_FORMAT.parse(date);
		} catch (final ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Calendar getDayStartCalandar(Date date) {

		final Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal;
	}

	public static Calendar getDayEndCalandar(Date date) {

		final Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 59);

		return cal;
	}

}
