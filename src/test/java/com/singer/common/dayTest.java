package com.singer.common;

public class dayTest {

	public static void main(String[] args) {

		String day = "20180806";
		String dayTime = "20180806230012";

		System.out.println(DateUtil.addDay(day, -2));
		System.out.println(DateUtil.addDay(dayTime, -1));
		System.out.println(DateUtil.addMonth(day, 2));
		System.out.println(DateUtil.addMonth(dayTime, 3));
	}
}