package com.neolith.focus.exception.setting.business;

public class BusinessException extends Exception {

    private static final long serialVersionUID = 7795713039163148438L;

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

}
