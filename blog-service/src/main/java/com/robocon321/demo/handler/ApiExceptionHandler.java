package com.robocon321.demo.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.robocon321.demo.exception.CannotSaveBlogException;
import com.robocon321.demo.exception.CannotSaveImageException;

@RestControllerAdvice
@ControllerAdvice
public class ApiExceptionHandler {
	@ExceptionHandler(CannotSaveImageException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public String cannotSaveImageHandler(Exception ex, WebRequest request) {
		return ex.getMessage();
	}

	@ExceptionHandler(CannotSaveBlogException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public String cannotSaveBlogHandler(Exception ex, WebRequest request) {
		return ex.getMessage();
	}
	

}
