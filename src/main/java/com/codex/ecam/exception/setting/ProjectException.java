package com.codex.ecam.exception.setting;

public class ProjectException extends Exception {

    private static final long serialVersionUID = 399025744596974448L;

    public ProjectException() {
    }

    public ProjectException(String message) {
        super(message);
    }

    public ProjectException(Throwable cause) {
        super(cause);
    }

    public ProjectException(String message, Throwable cause) {
        super(message, cause);
    }

}
