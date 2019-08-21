package com.neolith.focus.exception.admin;

public class UserException extends Exception {

    private static final long serialVersionUID = -1728992476229464624L;

    public UserException() {
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(Throwable cause) {
        super(cause);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

}
