package com.singer.common;

public class Constants {

	public enum YES_NO {
		/** NO */
		NO(0),
		/** YES */
		YES(1);

		private final int value;

		private YES_NO(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	public enum RESULT_CODE {
		/** 성공 코드 */
		FAIL(0),
		/** 실패 코드 */
		SUCCESS(1);
		private final int value;

		private RESULT_CODE(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	/** 기본 페이징 숫자 */
	public static final int ROW_PER_PAGE = 10;

	/** 로그인 실패 코드 */
	public static final int ERROR_LOGIN_FAIL = 1001;
	/** 메일전송 실패 코드 */
	public static final int ERROR_MAIL_FAIL = 1011;
	/** 유저등급 관리자 코드 */
	public static final String USER_CODE_ADMIN = "01";
	/** 유저등급 특별회원 코드 */
	public static final String USER_CODE_SPECIAL = "02";
	/** 유저등급 우수회원 코드 */
	public static final String USER_CODE_GOOD = "03";
	/** 유저등급 사용자 코드 */
	public static final String USER_CODE_NORMAL = "04";
}
