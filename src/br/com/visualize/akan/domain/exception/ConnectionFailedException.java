/*
 * File: ConnectionFailedException.java
 * Purpose: Implement the exception of connection failure with Internet.
 */
package br.com.visualize.akan.domain.exception;


/**
 * Represents an exception to connection failure with Internet.
 */
public class ConnectionFailedException extends Exception {
	private static final long serialVersionUID = 834455534735333354L;
	
	public ConnectionFailedException() {
		/*! Empty Constructor. */
	}
	
	public ConnectionFailedException( String message ) {
		super( message );
	}
}
