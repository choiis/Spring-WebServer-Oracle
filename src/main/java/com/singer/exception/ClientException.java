package com.singer.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ClientException extends Exception {

	private static final long serialVersionUID = -2649662521414292331L;

	private HttpStatus httpStatusCode;

	public ClientException(HttpStatus status) {
		super();
		this.httpStatusCode = status;
	}

	public ClientException(String msg) {
		super(msg);
	}
}
