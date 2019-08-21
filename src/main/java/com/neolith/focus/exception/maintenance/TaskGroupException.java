package com.neolith.focus.exception.maintenance;


public class TaskGroupException extends Exception {

    private static final long serialVersionUID = -7830114276930533527L;

    public TaskGroupException() {
    }

    public TaskGroupException(String message) {
        super(message);
    }

    public TaskGroupException(Throwable cause) {
        super(cause);
    }

    public TaskGroupException(String message, Throwable cause) {
        super(message, cause);
    }

}
