package com.robocon321.demo.exception;

public class NotFoundRefreshToken extends RuntimeException {
	public NotFoundRefreshToken(String message) {
		super(message);
	}
}
