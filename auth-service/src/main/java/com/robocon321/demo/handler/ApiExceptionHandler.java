package com.robocon321.demo.handler;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.robocon321.demo.dto.response.ErrorResponse;
import com.robocon321.demo.exception.ConflictException;
import com.robocon321.demo.exception.ExpiredRefreshTokenException;
import com.robocon321.demo.exception.NotFoundRefreshToken;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@RestControllerAdvice
public class ApiExceptionHandler {
	@ExceptionHandler(ConflictException.class)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public ErrorResponse conflictExceptionHandler(Exception ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setError("Conflict");
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setStatus(HttpStatus.CONFLICT.value());
		return errorResponse;
	}
	
	@ExceptionHandler(ExpiredJwtException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public ErrorResponse expiredJwtExceptionHandler(Exception ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setError("Expired token");
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		return errorResponse;		
	}

	@ExceptionHandler(UnsupportedJwtException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse unsupportedJwtExceptionHandler(Exception ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setError("Unsupported Jwt");
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return errorResponse;		
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public ErrorResponse accessDeniedExceptionHandler(Exception ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setError("Access denied");
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setStatus(HttpStatus.FORBIDDEN.value());
		return errorResponse;		
	}
	
	@ExceptionHandler(NotFoundRefreshToken.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorResponse notFoundRefreshTokenHandler(Exception ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setError("Refresh token invalid");
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
		return errorResponse;		
	}

	@ExceptionHandler(ExpiredRefreshTokenException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public ErrorResponse expiredRefreshTokenExceptionHandler(Exception ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setError("Expire refresh token");
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
		return errorResponse;		
	}

}
