package com.singer.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
		/** 실패 코드 */
		FAIL(0),
		/** 성공 코드 */
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

	public enum USER_CODE {
		/** 유저등급 관리자 코드 */
		ADMIN(1),
		/** 유저등급 특별회원 코드 */
		SPECIAL(2),
		/** 유저등급 우수회원 코드 */
		GOOD(3),
		/** 유저등급 사용자 코드 */
		NORMAL(4),;
		private final int value;

		private USER_CODE(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	public enum PHONE_INFO_CODE {
		/** 핸드폰코드 */
		CELL(1),
		/** 집번호코드 */
		HOME(2),
		/** 회사번호코드 */
		COMPANY(3),
		/** 그외번호코드 */
		OTHER(4),;
		private final int value;

		private PHONE_INFO_CODE(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
}
