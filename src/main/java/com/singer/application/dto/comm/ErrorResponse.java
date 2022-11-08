package com.singer.application.dto.comm;

import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
public class ErrorResponse {

    HttpStatus errorCode;

    String errorMsg;
}
