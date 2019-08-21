package com.neolith.focus.exception.setting.notification;

public class NotificationException extends Exception {

    private static final long serialVersionUID = 8127772523697381681L;

    public NotificationException() {
    }

    public NotificationException(String message) {
        super(message);
    }

    public NotificationException(Throwable cause) {
        super(cause);
    }

    public NotificationException(String message, Throwable cause) {
        super(message, cause);
    }

}
