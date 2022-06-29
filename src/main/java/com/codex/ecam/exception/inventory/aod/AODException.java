package com.codex.ecam.exception.inventory.aod;


public class AODException extends Exception {

	private static final long serialVersionUID = -5631360358528350263L;

	public AODException() {
    }

    public AODException(String message) {
        super(message);
    }

    public AODException(Throwable cause) {
        super(cause);
    }

    public AODException(String message, Throwable cause) {
        super(message, cause);
    }

}
