package com.singer.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DateUtil {

    protected static DateTimeFormatter sdfDay_14 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    protected static DateTimeFormatter sdfDay_8 = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * 오늘 날짜를 반환한다
     */
    public static String getToday() {
        LocalDate now = LocalDate.now();
        return now.format(sdfDay_8);
    }

    /**
     * 오늘 상세날짜를 반환한다
     */
    public static String getTodayTime() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(sdfDay_14);
    }

    public static LocalDate stringToDayTime(@NonNull String date) {
        return LocalDate.parse(date, sdfDay_8);
    }

    public static LocalDateTime stringDetailToDate(@NonNull String date) {
        return LocalDateTime.parse(date, sdfDay_14);
    }

    protected static String addMonth_8(int month) {
        LocalDate now = LocalDate.now();
        return now.plusMonths(month).format(sdfDay_8);
    }

    protected static String addMonth_14(int month) {
        LocalDateTime now = LocalDateTime.now();
        return now.plusMonths(month).format(sdfDay_14);
    }

    /**
     * 날짜에 월을 더한다
     */
    public static String getAddMonth(int month) {
        return addMonth_8(month);
    }

    /**
     * 날짜에 월을 더한다
     */
    public static String getAddMonthTime(int month) {
        return addMonth_14(month);
    }

    protected static String addDay_8(int day) {
        LocalDate now = LocalDate.now();
        return now.plusDays(day).format(sdfDay_8);
    }

    protected static String addDay_14(int day) {
        LocalDateTime now = LocalDateTime.now();
        return now.plusDays(day).format(sdfDay_14);
    }

    /**
     * 날짜에 일을 더한다
     */
    public static String getAddDay(int day) {
        return addDay_8(day);
    }

    /**
     * 날짜에 일을 더한다
     */
    public static String getAddDayTime(int day) {
        return addDay_14(day);
    }

    /**
     * 상세날짜형식을 날짜 형식으로 반환한다
     */
    public static String getDateFormat(String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        } else {
            return str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8);
        }
    }
}
