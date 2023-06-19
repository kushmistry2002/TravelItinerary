package com.kush.travel_api.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthException extends RuntimeException{
	String message;
	public AuthException(String message) {
		super(String.format("%s",message));
		this.message = message;
	}
}
