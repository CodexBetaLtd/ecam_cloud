package com.codex.ecam.exception.inventory;


public class StockQuantityExceedException extends RuntimeException { 

	private static final long serialVersionUID = 929800769679621242L;

	public StockQuantityExceedException() {
    }

    public StockQuantityExceedException(String message) {
        super(message);
    }

    public StockQuantityExceedException(Throwable cause) {
        super(cause);
    }

    public StockQuantityExceedException(String message, Throwable cause) {
        super(message, cause);
    }

}
