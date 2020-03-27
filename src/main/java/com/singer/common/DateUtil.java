package com.singer.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DateUtil {

	private static SimpleDateFormat sdfDay_8 = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat sdfDay_14 = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * 오늘 날짜를 반환한다
	 */
	public static String getToday() {
		Calendar cal = Calendar.getInstance();

		return sdfDay_8.format(cal.getTime());
	}

	/**
	 * 오늘 상세날짜를 반환한다
	 */
	public static String getTodayTime() {
		Calendar cal = Calendar.getInstance();

		return sdfDay_14.format(cal.getTime());
	}

	private static Date stringToDayTime(String date) {
		try {
			return sdfDay_8.parse(date);
		} catch (ParseException e) {

		}
		return null;
	}

	private static Date stringDetailToDate(String date) {
		try {
			return sdfDay_14.parse(date);
		} catch (ParseException e) {

		}
		return null;
	}

	private static String addMonth_8(String str, int month) {
		Date date = stringToDayTime(str);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, month);

		return sdfDay_8.format(cal.getTime());
	}

	private static String addMonth_14(String str, int month) {
		Date date = stringDetailToDate(str);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, month);

		return sdfDay_14.format(cal.getTime());
	}

	/**
	 * 날짜에 월을 더한다
	 */
	public static String addMonth(String str, int month) {
		if (str.length() == 8) {
			return addMonth_8(str, month);
		} else {
			return addMonth_14(str, month);
		}
	}

	private static String addDay_8(String str, int day) {
		Date date = stringToDayTime(str);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);

		return sdfDay_8.format(cal.getTime());
	}

	private static String addDay_14(String str, int day) {
		Date date = stringDetailToDate(str);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);

		return sdfDay_14.format(cal.getTime());
	}

	/**
	 * 날짜에 일을 더한다
	 */
	public static String addDay(String str, int day) {
		if (str.length() == 8) {
			return addDay_8(str, day);
		} else {
			return addDay_14(str, day);
		}
	}

	/**
	 * 상세날짜형식을 날짜 형식으로 반환한다
	 */
	public static String getDateFormat(String str) {
		if (str == null) {
			return "";
		} else if ("".equals(str)) {
			return "";
		} else {
			return str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8);
		}
	}
}
