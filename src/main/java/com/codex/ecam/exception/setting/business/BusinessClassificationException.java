package com.codex.ecam.exception.setting.business;

public class BusinessClassificationException extends Exception {

	private static final long serialVersionUID = 6967321795268162046L;

	public BusinessClassificationException() {
	}

	public BusinessClassificationException(String message) {
		super(message);
	}

	public BusinessClassificationException(Throwable cause) {
		super(cause);
	}

	public BusinessClassificationException(String message, Throwable cause) {
		super(message, cause);
	}

}
