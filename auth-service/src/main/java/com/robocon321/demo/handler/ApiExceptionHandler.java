package com.robocon321.demo.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.robocon321.demo.exception.ConflictException;

@RestControllerAdvice
public class ApiExceptionHandler {
	@ExceptionHandler(ConflictException.class)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public String conflictExceptionHandler(Exception ex, WebRequest request) {
		return ex.getMessage();
	}
}
