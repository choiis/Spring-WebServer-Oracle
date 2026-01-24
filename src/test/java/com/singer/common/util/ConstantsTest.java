package com.singer.common.util;

import com.singer.common.util.Constants.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ConstantsTest {

    @Test
    @DisplayName("ROW_PER_PAGE 상수 값 확인")
    void rowPerPage_hasCorrectValue() {
        assertEquals(10, Constants.ROW_PER_PAGE);
    }

    @Test
    @DisplayName("ERROR_LOGIN_FAIL 상수 값 확인")
    void errorLoginFail_hasCorrectValue() {
        assertEquals(1001, Constants.ERROR_LOGIN_FAIL);
    }

    @Test
    @DisplayName("ERROR_MAIL_FAIL 상수 값 확인")
    void errorMailFail_hasCorrectValue() {
        assertEquals(1011, Constants.ERROR_MAIL_FAIL);
    }

    @Nested
    @DisplayName("YES_NO enum 테스트")
    class YesNoTest {

        @Test
        @DisplayName("NO의 value는 0")
        void no_hasValueZero() {
            assertEquals(0, YES_NO.NO.getValue());
        }

        @Test
        @DisplayName("YES의 value는 1")
        void yes_hasValueOne() {
            assertEquals(1, YES_NO.YES.getValue());
        }

        @Test
        @DisplayName("YES_NO enum은 2개의 값을 가짐")
        void yesNo_hasTwoValues() {
            assertEquals(2, YES_NO.values().length);
        }
    }

    @Nested
    @DisplayName("RESULT_CODE enum 테스트")
    class ResultCodeTest {

        @Test
        @DisplayName("FAIL의 value는 0")
        void fail_hasValueZero() {
            assertEquals(0, RESULT_CODE.FAIL.getValue());
        }

        @Test
        @DisplayName("SUCCESS의 value는 1")
        void success_hasValueOne() {
            assertEquals(1, RESULT_CODE.SUCCESS.getValue());
        }
    }

    @Nested
    @DisplayName("USER_CODE enum 테스트")
    class UserCodeTest {

        @ParameterizedTest
        @CsvSource({
            "NONE, 0",
            "ADMIN, 1",
            "SPECIAL, 2",
            "GOOD, 3",
            "NORMAL, 4"
        })
        @DisplayName("USER_CODE enum의 value 확인")
        void userCode_hasCorrectValues(String name, int expectedValue) {
            USER_CODE code = USER_CODE.valueOf(name);
            assertEquals(expectedValue, code.getValue());
        }

        @ParameterizedTest
        @CsvSource({
            "0, NONE",
            "1, ADMIN",
            "2, SPECIAL",
            "3, GOOD",
            "4, NORMAL"
        })
        @DisplayName("valueOf(int)로 USER_CODE 조회")
        void userCode_valueOfInt_returnsCorrectEnum(int value, String expectedName) {
            USER_CODE result = USER_CODE.valueOf(value);
            assertEquals(expectedName, result.name());
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, 5, 100})
        @DisplayName("잘못된 value로 valueOf 호출시 예외 발생")
        void userCode_valueOfInt_withInvalidValue_throwsException(int invalidValue) {
            assertThrows(IllegalArgumentException.class, () -> USER_CODE.valueOf(invalidValue));
        }

        @Test
        @DisplayName("USER_CODE enum은 5개의 값을 가짐")
        void userCode_hasFiveValues() {
            assertEquals(5, USER_CODE.values().length);
        }
    }

    @Nested
    @DisplayName("PHONE_INFO_CODE enum 테스트")
    class PhoneInfoCodeTest {

        @ParameterizedTest
        @CsvSource({
            "NONE, 0",
            "CELL, 1",
            "HOME, 2",
            "COMPANY, 3",
            "OTHER, 4"
        })
        @DisplayName("PHONE_INFO_CODE enum의 value 확인")
        void phoneInfoCode_hasCorrectValues(String name, int expectedValue) {
            PHONE_INFO_CODE code = PHONE_INFO_CODE.valueOf(name);
            assertEquals(expectedValue, code.getValue());
        }

        @ParameterizedTest
        @CsvSource({
            "0, NONE",
            "1, CELL",
            "2, HOME",
            "3, COMPANY",
            "4, OTHER"
        })
        @DisplayName("valueOf(int)로 PHONE_INFO_CODE 조회")
        void phoneInfoCode_valueOfInt_returnsCorrectEnum(int value, String expectedName) {
            PHONE_INFO_CODE result = PHONE_INFO_CODE.valueOf(value);
            assertEquals(expectedName, result.name());
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, 5, 100})
        @DisplayName("잘못된 value로 valueOf 호출시 예외 발생")
        void phoneInfoCode_valueOfInt_withInvalidValue_throwsException(int invalidValue) {
            assertThrows(IllegalArgumentException.class, () -> PHONE_INFO_CODE.valueOf(invalidValue));
        }
    }

    @Nested
    @DisplayName("BROWSER_CODE enum 테스트")
    class BrowserCodeTest {

        @ParameterizedTest
        @CsvSource({
            "CHROME, 0",
            "IE, 1",
            "WHALE, 2",
            "OPERA, 3",
            "FIREFOX, 4",
            "SAFARI, 5",
            "IPHONE, 6",
            "ANDROID, 7"
        })
        @DisplayName("BROWSER_CODE enum의 value 확인")
        void browserCode_hasCorrectValues(String name, int expectedValue) {
            BROWSER_CODE code = BROWSER_CODE.valueOf(name);
            assertEquals(expectedValue, code.getValue());
        }

        @ParameterizedTest
        @CsvSource({
            "0, CHROME",
            "1, IE",
            "2, WHALE",
            "3, OPERA",
            "4, FIREFOX",
            "5, SAFARI",
            "6, IPHONE",
            "7, ANDROID"
        })
        @DisplayName("valueOf(int)로 BROWSER_CODE 조회")
        void browserCode_valueOfInt_returnsCorrectEnum(int value, String expectedName) {
            BROWSER_CODE result = BROWSER_CODE.valueOf(value);
            assertEquals(expectedName, result.name());
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, 8, 100})
        @DisplayName("잘못된 value로 valueOf 호출시 예외 발생")
        void browserCode_valueOfInt_withInvalidValue_throwsException(int invalidValue) {
            assertThrows(IllegalArgumentException.class, () -> BROWSER_CODE.valueOf(invalidValue));
        }

        @Test
        @DisplayName("BROWSER_CODE enum은 8개의 값을 가짐")
        void browserCode_hasEightValues() {
            assertEquals(8, BROWSER_CODE.values().length);
        }
    }
}
