package com.singer.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ClientException extends Exception {

    private static final long serialVersionUID = -2649662521414292331L;

    private HttpStatus httpStatusCode;

    public ClientException(String msg) {
        super(msg);
    }
}
