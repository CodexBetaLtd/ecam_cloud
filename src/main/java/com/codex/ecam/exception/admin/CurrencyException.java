package com.codex.ecam.exception.admin;

public class CurrencyException extends Exception {

	private static final long serialVersionUID = -2774556153752768900L;

	public CurrencyException() {
	}

	public CurrencyException(String message) {
		super(message);
	}

	public CurrencyException(Throwable cause) {
		super(cause);
	}

	public CurrencyException(String message, Throwable cause) {
		super(message, cause);
	}

}
