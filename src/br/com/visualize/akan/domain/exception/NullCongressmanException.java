/*
 * File: NullCongressmanException.java
 * Purpose: Implement the exception of lack of Congressman.
 */
package br.com.visualize.akan.domain.exception;


/**
 * Represents an exception to a lack of Congressman.
 */
public class NullCongressmanException extends Exception {
	private static final long serialVersionUID = -652180129137381807L;
	
	public NullCongressmanException() {
		/*! Empty Constructor. */
	}
	
	public NullCongressmanException( String message ) {
		super( message );
	}
}
