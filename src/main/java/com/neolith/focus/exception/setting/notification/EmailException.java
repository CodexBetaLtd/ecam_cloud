package com.neolith.focus.exception.setting.notification;

public class EmailException extends Exception {

	private static final long serialVersionUID = -3524645129335471712L;

	public EmailException() {
    }

    public EmailException(String message) {
        super(message);
    }

    public EmailException(Throwable cause) {
        super(cause);
    }

    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }

}
