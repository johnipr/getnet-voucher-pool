package com.getnet.app.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class GetnetException {

	private final String message;
	private final int statusCode;

	public GetnetException(String message, HttpStatus httpStatus) {
		this.message = message;
		this.statusCode = httpStatus.value();
	}

}