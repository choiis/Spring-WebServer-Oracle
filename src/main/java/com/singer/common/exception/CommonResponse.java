package com.singer.common.exception;

public record CommonResponse<T>(Header header,
                                T data) {

    public record Header(boolean isSuccessful,
                         int resultCode,
                         String resultMessage) {

        public static final Header OK = new Header(true, 0, "");
    }

    public static <T> CommonResponse<T> ok(T data) {
        return new CommonResponse<>(Header.OK, data);
    }

    public static CommonResponse<Void> ok() {
        return new CommonResponse<>(Header.OK, null);
    }

    public static CommonResponse<Void> error(int resultCode, String resultMessage) {
        return new CommonResponse<>(new Header(false, resultCode, resultMessage), null);
    }

    public static CommonResponse<Object> error(int resultCode, String resultMessage, Object object) {
        return new CommonResponse<>(new Header(false, resultCode, resultMessage), object);
    }
}
