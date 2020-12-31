package com.codex.ecam.exception.asset;


public class PartException extends Exception {

	private static final long serialVersionUID = 2824908989804344998L;

	public PartException() {
	}

	public PartException(String message) {
		super(message);
	}

	public PartException(Throwable cause) {
		super(cause);
	}

	public PartException(String message, Throwable cause) {
		super(message, cause);
	}

}
