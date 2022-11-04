package com.singer.common.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMsg {
    /** 아이디를 필수 입력해야 합니다! */
    public static final String EXT_MSG_INF_1 = "아이디를 필수 입력해야 합니다!";

    /** 비밀번호를 필수 입력해야 합니다! */
    public static final String EXT_MSG_INF_2 = "비밀번호를 필수 입력해야 합니다!";

    /** 이름을 입력해주세요! */
    public static final String EXT_MSG_INF_3 = "이름을 입력해주세요!";

    /** 제목을 필수 입력해야 합니다 */
    public static final String EXT_MSG_INPUT_1 = "제목을 필수 입력해야 합니다";

    /** 내용을 필수 입력해야 합니다 */
    public static final String EXT_MSG_INPUT_2 = "내용을 필수 입력해야 합니다";

    /** 파일 업로드를 해주세요 */
    public static final String EXT_MSG_INPUT_3 = "파일 업로드를 해주세요!";

    /** 사진 파일만 업로드 가능합니다! */
    public static final String EXT_MSG_INPUT_4 = "사진 파일만 업로드 가능합니다!";

    /** 동영상 또는 오디오 파일만 업로드 가능합니다 */
    public static final String EXT_MSG_INPUT_5 = "동영상 또는 오디오 파일만 업로드 가능합니다!";

    /** 가게명을 필수 입력해야 합니다 */
    public static final String EXT_MSG_INPUT_6 = "가게명을 필수 입력해야 합니다";

    /** 유효한 점수를 입력해야 합니다 */
    public static final String EXT_MSG_INPUT_7 = "유효한 점수를 입력해야 합니다";

    /** 투표항목을 한개 이상 입력해야 합니다 */
    public static final String EXT_MSG_INPUT_8 = "투표항목을 한개 이상 입력해야 합니다";

    /** 투표항목 내용을 필수 입력해야 합니다 */
    public static final String EXT_MSG_INPUT_9 = "투표항목 내용을 필수 입력해야 합니다";

    /** 검색조건을 입력하세요 */
    public static final String EXT_MSG_INPUT_10 = "검색조건을 입력하세요";

    /** 검색구분을 선택하세요 */
    public static final String EXT_MSG_INPUT_11 = "검색구분을 선택하세요";

    /** 8자리 생년월일을 입력하세요 */
    public static final String EXT_MSG_INPUT_12 = "8자리 생년월일을 입력하세요";

    /** 이메일 형식에 맞아야 합니다 */
    public static final String EXT_MSG_INPUT_13 = "이메일 형식에 맞아야 합니다";

    /** SQL Injection 위험이 있습니다 */
    public static final String EXT_MSG_SECURE_1 = "SQL Injection 위험이 있습니다";

    /** 존재 하지않는 계정입니다 */
    public static final String EXT_MSG_LOGIN_1 = "존재 하지않는 계정입니다";

    /** 비밀번호가 틀렸습니다" */
    public static final String EXT_MSG_LOGIN_2 = "비밀번호가 틀렸습니다";

}
