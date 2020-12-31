package com.codex.ecam.exception.asset;


public class AssetException extends Exception {

	private static final long serialVersionUID = -9217922628713301709L;

	public AssetException() {
	}

	public AssetException(String message) {
		super(message);
	}

	public AssetException(Throwable cause) {
		super(cause);
	}

	public AssetException(String message, Throwable cause) {
		super(message, cause);
	}

}
