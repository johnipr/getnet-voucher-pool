package com.getnet.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GetnetExceptionHandler {

	@ExceptionHandler(value = { ConflictException.class })
	public ResponseEntity<Object> handleConflictException(ConflictException exception) {

		GetnetException getnetException = new GetnetException(exception.getMessage(), HttpStatus.CONFLICT);

		return new ResponseEntity<>(getnetException, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = { NotFoundException.class })
	public ResponseEntity<Object> handleNotFoundException(NotFoundException exception) {

		GetnetException getnetException = new GetnetException(exception.getMessage(), HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(getnetException, HttpStatus.NOT_FOUND);
	}
	
}

