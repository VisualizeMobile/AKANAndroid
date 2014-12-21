/*
 * File: Quota.java 
 * Purpose: Bringing the implementation of model Quota, a class that directly
 * references the business domain.
 */
package br.com.visualize.akan.domain.model;


import br.com.visualize.akan.domain.enumeration.Month;
import br.com.visualize.akan.domain.enumeration.SubQuota;


/**
 * This class represents the model of quota for application. It contains the
 * essential data to represent and maintain a quota.
 */
public class Quota {
	private int idQuota = 0;
	private int congressman_id = 0;
	private int idUpdateQuota = 0;
	private SubQuota typeQuota = SubQuota.WITHOUT_TYPE;
	
	private Month monthReferenceQuota = Month.WITHOUT_MONTH;
	private int yearReferenceQuota = 0;
	private String descriptionQuota = "";
	private double valueQuota = 0;
	
	public Quota( int id, int idCongressman, SubQuota subquota, double value ) {
		this.idQuota = id;
		this.congressman_id = idCongressman;
		this.typeQuota = subquota;
		this.valueQuota = value;
	}
	
	public int getIdQuota() {
		return idQuota;
	}
	
	public void setIdQuota( int idQuota ) {
		this.idQuota = idQuota;
	}
	
	public int getIdCongressmanQuota() {
		return congressman_id;
	}
	
	public void setIdCongressmanQuota( int idCongressmanQuota ) {
		this.congressman_id = idCongressmanQuota;
	}
	
	public int getIdUpdateQuota() {
		return idUpdateQuota;
	}
	
	public void setIdUpdateQuota( int idUpdateQuota ) {
		this.idUpdateQuota = idUpdateQuota;
	}
	
	public SubQuota getTypeQuota() {
		return typeQuota;
	}
	
	public void setTypeQuota( SubQuota typeQuota ) {
		this.typeQuota = typeQuota;
	}
	
	public Month getMonthReferenceQuota() {
		return monthReferenceQuota;
	}
	
	public void setMonthReferenceQuota( Month monthReferenceQuota ) {
		this.monthReferenceQuota = monthReferenceQuota;
	}
	
	public int getYearReferenceQuota() {
		return yearReferenceQuota;
	}
	
	public void setYearReferenceQuota( int yearReferenceQuota ) {
		this.yearReferenceQuota = yearReferenceQuota;
	}
	
	public String getDescriptionQuota() {
		return descriptionQuota;
	}
	
	public void setDescriptionQuota( String descriptionQuota ) {
		this.descriptionQuota = descriptionQuota;
	}
	
	public double getValueQuota() {
		return valueQuota;
	}
	
	public void setValueQuota( double valueQuota ) {
		this.valueQuota = valueQuota;
	}
}
