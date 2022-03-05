package com.componentprocessing.exceptions;

public class ValidationFailedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValidationFailedException(String message) {
		super(message);

	}

}