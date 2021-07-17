package com.singer.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClientException extends Exception {

	private static final long serialVersionUID = -2649662521414292331L;

	private HttpStatus httpStatusCode;

	public ClientException(String msg) {
		super(msg);
	}
}
