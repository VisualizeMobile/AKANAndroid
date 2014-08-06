/*
 * File: 	Url.java
 * Purpose: Bringing the implementation of modelUrl, a class that 
 * directly references the business domain.
 */
package br.com.visualize.akan.domain.model;

/**
 * This class represents the model for an URL in application.
 */
public class Url {
	private int idUpdateUrl = 0;
	private int updateVerifierUrl = 0;
	
	private String defaultUrl = "";
	private String firstAlternativeUrl = "";
	private String secondAlternativeUrl = "";
	
	public int getIdUpdateUrl() {
		return idUpdateUrl;
	}
	
	public void setIdUpdateUrl( int idUpdateUrl ) {
		this.idUpdateUrl = idUpdateUrl;
	}
	
	public int getUpdateVerifierUrl() {
		return updateVerifierUrl;
	}
	
	public void setUpdateVerifierUrl( int updateVerifierUrl ) {
		this.updateVerifierUrl = updateVerifierUrl;
	}
	
	public String getDefaultUrl() {
		return defaultUrl;
	}
	
	public void setDefaultUrl( String defaultUrl ) {
		this.defaultUrl = defaultUrl;
	}
	
	public String getFirstAlternativeUrl() {
		return firstAlternativeUrl;
	}
	
	public void setFirstAlternativeUrl( String firstAlternativeUrl ) {
		this.firstAlternativeUrl = firstAlternativeUrl;
	}
	
	public String getSecondAlternativeUrl() {
		return secondAlternativeUrl;
	}
	
	public void setSecondAlternativeUrl( String secondAlternativeUrl ) {
		this.secondAlternativeUrl = secondAlternativeUrl;
	}
}
