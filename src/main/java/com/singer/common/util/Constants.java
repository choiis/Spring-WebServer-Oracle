package com.singer.common.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public enum YES_NO {
        /** NO */
        NO(0),
        /** YES */
        YES(1);

        private final int value;

    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public enum RESULT_CODE {
        /** 실패 코드 */
        FAIL(0),
        /** 성공 코드 */
        SUCCESS(1);
        private final int value;

    }

    /** 기본 페이징 숫자 */
    public static final int ROW_PER_PAGE = 10;

    /** 로그인 실패 코드 */
    public static final int ERROR_LOGIN_FAIL = 1001;
    /** 메일전송 실패 코드 */
    public static final int ERROR_MAIL_FAIL = 1011;

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public enum USER_CODE {
        /** 유저등급 안쓰는 코드 */
        NONE(0),
        /** 유저등급 관리자 코드 */
        ADMIN(1),
        /** 유저등급 특별회원 코드 */
        SPECIAL(2),
        /** 유저등급 우수회원 코드 */
        GOOD(3),
        /** 유저등급 사용자 코드 */
        NORMAL(4);
        private final int value;

        public static USER_CODE valueOf(int value) {
            switch (value) {
                case 0:
                    return NONE;
                case 1:
                    return ADMIN;
                case 2:
                    return SPECIAL;
                case 3:
                    return GOOD;
                case 4:
                    return NORMAL;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public enum PHONE_INFO_CODE {
        /** 안쓰는 코드 */
        NONE(0),
        /** 핸드폰코드 */
        CELL(1),
        /** 집번호코드 */
        HOME(2),
        /** 회사번호코드 */
        COMPANY(3),
        /** 그외번호코드 */
        OTHER(4),;
        private final int value;

        public static PHONE_INFO_CODE valueOf(int value) {
            switch (value) {
                case 0:
                    return NONE;
                case 1:
                    return CELL;
                case 2:
                    return HOME;
                case 3:
                    return COMPANY;
                case 4:
                    return OTHER;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public enum BROWSER_CODE {
        CHROME(0), IE(1), WHALE(2), OPERA(3), FIREFOX(4), SAFARI(5), IPHONE(6), ANDROID(7);
        private final int value;

        public static BROWSER_CODE valueOf(int value) {
            switch (value) {
                case 0:
                    return CHROME;
                case 1:
                    return IE;
                case 2:
                    return WHALE;
                case 3:
                    return OPERA;
                case 4:
                    return FIREFOX;
                case 5:
                    return SAFARI;
                case 6:
                    return IPHONE;
                case 7:
                    return ANDROID;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }
}
