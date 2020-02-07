package com.codex.ecam.exception.inventory.stock;


public class StockException extends Exception {
 
	private static final long serialVersionUID = -784861597413002552L;

	public StockException() {
    }

    public StockException(String message) {
        super(message);
    }

    public StockException(Throwable cause) {
        super(cause);
    }

    public StockException(String message, Throwable cause) {
        super(message, cause);
    }
}
