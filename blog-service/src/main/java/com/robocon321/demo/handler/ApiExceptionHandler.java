package com.robocon321.demo.handler;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.robocon321.demo.dto.response.ErrorResponse;
import com.robocon321.demo.exception.CannotSaveBlogException;
import com.robocon321.demo.exception.CannotSaveImageException;
import com.robocon321.demo.exception.NotFoundException;

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
	
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorResponse notFoundExeptionHandler(Exception ex, WebRequest request) {
		ErrorResponse response = new ErrorResponse();
		response.setStatus(HttpStatus.NOT_FOUND.value());
		response.setError("Not found");
		response.setMessage(ex.getMessage());
		return response;
	}

	@ExceptionHandler(BindException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorResponse argumentNotValidMultipartExceptionHandler(BindException ex, WebRequest request) {
		String message = "";
		for (FieldError error : ex.getFieldErrors()) {
			message += error.getDefaultMessage() + ". \n";
		}

		ErrorResponse response = new ErrorResponse();
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setError("Request invalid");
		response.setMessage(message);

		return response;
	}
}
