package com.codex.ecam.exception.inventory.receiptOrder;


public class ReceiptOrderException extends Exception {

	private static final long serialVersionUID = 7847518188821271682L;

	public ReceiptOrderException() {
	}

	public ReceiptOrderException(String message) {
		super(message);
	}

	public ReceiptOrderException(Throwable cause) {
		super(cause);
	}

	public ReceiptOrderException(String message, Throwable cause) {
		super(message, cause);
	}

}
