package com.singer.common.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilTest {

    @Test
    @DisplayName("getToday() - 오늘 날짜를 yyyyMMdd 형식으로 반환")
    void getToday_returnsCurrentDateInYyyyMMddFormat() {
        String today = DateUtil.getToday();

        assertNotNull(today);
        assertEquals(8, today.length());

        // 실제 오늘 날짜와 비교
        String expected = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        assertEquals(expected, today);
    }

    @Test
    @DisplayName("getTodayTime() - 현재 시간을 yyyyMMddHHmmss 형식으로 반환")
    void getTodayTime_returnsCurrentDateTimeIn14CharFormat() {
        String todayTime = DateUtil.getTodayTime();

        assertNotNull(todayTime);
        assertEquals(14, todayTime.length());

        // 형식 검증 (숫자만 포함)
        assertTrue(todayTime.matches("\\d{14}"));
    }

    @Test
    @DisplayName("stringToDayTime() - yyyyMMdd 문자열을 LocalDate로 변환")
    void stringToDayTime_convertsStringToLocalDate() {
        String dateString = "20231225";

        LocalDate result = DateUtil.stringToDayTime(dateString);

        assertNotNull(result);
        assertEquals(2023, result.getYear());
        assertEquals(12, result.getMonthValue());
        assertEquals(25, result.getDayOfMonth());
    }

    @Test
    @DisplayName("stringDetailToDate() - yyyyMMddHHmmss 문자열을 LocalDateTime으로 변환")
    void stringDetailToDate_convertsStringToLocalDateTime() {
        String dateTimeString = "20231225143059";

        LocalDateTime result = DateUtil.stringDetailToDate(dateTimeString);

        assertNotNull(result);
        assertEquals(2023, result.getYear());
        assertEquals(12, result.getMonthValue());
        assertEquals(25, result.getDayOfMonth());
        assertEquals(14, result.getHour());
        assertEquals(30, result.getMinute());
        assertEquals(59, result.getSecond());
    }

    @Test
    @DisplayName("getAddMonth() - 현재 날짜에 월을 더한 결과 반환")
    void getAddMonth_addsMonthsToCurrentDate() {
        String result = DateUtil.getAddMonth(1);

        assertNotNull(result);
        assertEquals(8, result.length());

        // 예상 결과와 비교
        String expected = LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("getAddMonth() - 음수 월을 더하면 월이 감소")
    void getAddMonth_withNegativeValue_subtractsMonths() {
        String result = DateUtil.getAddMonth(-1);

        String expected = LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("getAddMonthTime() - 현재 시간에 월을 더한 상세 시간 반환")
    void getAddMonthTime_addsMonthsToCurrentDateTime() {
        String result = DateUtil.getAddMonthTime(2);

        assertNotNull(result);
        assertEquals(14, result.length());
    }

    @Test
    @DisplayName("getAddDay() - 현재 날짜에 일을 더한 결과 반환")
    void getAddDay_addsDaysToCurrentDate() {
        String result = DateUtil.getAddDay(7);

        assertNotNull(result);
        assertEquals(8, result.length());

        String expected = LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("getAddDay() - 음수 일을 더하면 일이 감소")
    void getAddDay_withNegativeValue_subtractsDays() {
        String result = DateUtil.getAddDay(-3);

        String expected = LocalDate.now().minusDays(3).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("getAddDayTime() - 현재 시간에 일을 더한 상세 시간 반환")
    void getAddDayTime_addsDaysToCurrentDateTime() {
        String result = DateUtil.getAddDayTime(5);

        assertNotNull(result);
        assertEquals(14, result.length());
    }

    @Test
    @DisplayName("getDateFormat() - yyyyMMdd 형식을 yyyy-MM-dd로 변환")
    void getDateFormat_convertsToHyphenatedFormat() {
        String input = "20231225";

        String result = DateUtil.getDateFormat(input);

        assertEquals("2023-12-25", result);
    }

    @Test
    @DisplayName("getDateFormat() - 빈 문자열 입력시 빈 문자열 반환")
    void getDateFormat_withEmptyString_returnsEmptyString() {
        String result = DateUtil.getDateFormat("");

        assertEquals("", result);
    }

    @Test
    @DisplayName("getDateFormat() - null 입력시 빈 문자열 반환")
    void getDateFormat_withNull_returnsEmptyString() {
        String result = DateUtil.getDateFormat(null);

        assertEquals("", result);
    }

    @Test
    @DisplayName("getDateFormat() - 상세 날짜 문자열도 날짜 부분만 변환")
    void getDateFormat_withLongerString_extractsDatePart() {
        String input = "20231225143059";

        String result = DateUtil.getDateFormat(input);

        assertEquals("2023-12-25", result);
    }
}
