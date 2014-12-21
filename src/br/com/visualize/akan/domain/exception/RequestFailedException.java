/*
 * File: RequestFailedException.java
 * Purpose: Implement the exception of failed request.
 */
package br.com.visualize.akan.domain.exception;


/**
 * Represents an exception to a failed request.
 */
public class RequestFailedException extends Exception {
	private static final long serialVersionUID = 7622241146095348692L;
	
	public RequestFailedException() {
		/*! Empty Constructor. */
	}
	
	public RequestFailedException( String message ) {
		super( message );
	}
	
}
