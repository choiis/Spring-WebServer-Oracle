package com.singer.application.dto.comm;

import org.springframework.http.HttpStatus;

public record ErrorResponse(
        HttpStatus errorCode,
        String errorMsg
) {

    public HttpStatus getErrorCode() {
        return errorCode();
    }

    public String getErrorMsg() {
        return errorMsg();
    }

}
