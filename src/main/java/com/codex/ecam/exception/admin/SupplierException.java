package com.codex.ecam.exception.admin;

public class SupplierException extends Exception {

    private static final long serialVersionUID = 1190945245661420555L;

    public SupplierException() {
    }

    public SupplierException(String message) {
        super(message);
    }

    public SupplierException(Throwable cause) {
        super(cause);
    }

    public SupplierException(String message, Throwable cause) {
        super(message, cause);
    }

}
