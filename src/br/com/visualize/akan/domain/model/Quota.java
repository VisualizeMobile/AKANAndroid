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
	private int idCongressman = 0;
	private int idUpdateQuota = 0;
	private SubQuota typeQuota = SubQuota.WITHOUT_TYPE;
	
	private Month monthReferenceQuota = Month.WITHOUT_MONTH;
	private int yearReferenceQuota = 0;
	private String descriptionQuota = "";
	private double valueQuota = 0;
	
	public Quota( int id, int idCongressman, SubQuota subquota, double value ) {
		this.idQuota = id;
		this.idCongressman = idCongressman;
		this.typeQuota = subquota;
		this.valueQuota = value;
	}
	
	public Quota(){};
	public int getIdQuota() {
		return idQuota;
	}
	
	public void setIdQuota( int idQuota ) {
		this.idQuota = idQuota;
	}
	
	public int getIdCongressmanQuota() {
		return idCongressman;
	}
	
	public void setIdCongressmanQuota( int idCongressmanQuota ) {
		this.idCongressman = idCongressmanQuota;
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
	
	private void setTypeQuota( SubQuota typeQuota ) {
		this.typeQuota = typeQuota;
	}
	
	public void setTypeQuotaByNumber (int type){
		
		switch(type){
				
		case 1:
			setTypeQuota(SubQuota.OFFICE);
		case 3:
			setTypeQuota(SubQuota.FUEL);
		case 4:
			setTypeQuota(SubQuota.TECHNICAL_WORK_AND_CONSULTING);
		case 5:
			setTypeQuota(SubQuota.DISCLOSURE_PARLIAMENTARY_ACTIVITY);
		case 8:
			setTypeQuota(SubQuota.SAFETY);
		case 9:
			setTypeQuota(SubQuota.ISSUANCE_OF_AIR_TICKETS);
		case 10:
			setTypeQuota(SubQuota.TELEPHONY);
		case 11:
			setTypeQuota(SubQuota.POSTAL_SERVICES);
		case 12:
			setTypeQuota(SubQuota.SIGNATURE_OF_PUBLICATION);
		case 13:
			setTypeQuota(SubQuota.ALIMENTATION);
		case 14:
			setTypeQuota(SubQuota.ACCOMMODATION);
		case 15:
			setTypeQuota(SubQuota.LEASE_OF_VEHICLES);
		default:
			setTypeQuota(SubQuota.WITHOUT_TYPE);
			
			
		}
	}
public void setTypeMonthByNumber (int type){
		
		switch(type){
				
		case 1:
			setMonthReferenceQuota(Month.JANUARY);
		case 2:
			setMonthReferenceQuota(Month.FEBRUARY);
		case 3:
			setMonthReferenceQuota(Month.MARCH);
		case 4:
			setMonthReferenceQuota(Month.APRIL);
		case 5:
			setMonthReferenceQuota(Month.MAY);
		case 6:
			setMonthReferenceQuota(Month.JUNE);
		case 7:
			setMonthReferenceQuota(Month.JULY);
		case 8:
			setMonthReferenceQuota(Month.AUGUST);
		case 9:
			setMonthReferenceQuota(Month.SEPTEMBER);
		case 10:
			setMonthReferenceQuota(Month.OCTOBER);
		case 11:
			setMonthReferenceQuota(Month.NOVEMBER);
		case 12:
			setMonthReferenceQuota(Month.DECEMBER);
		default:
			//nothing to do
			
			
		}
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
