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
	
	private Statistic statisticQuota = null;
	
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
			break;
		case 3:
			setTypeQuota(SubQuota.FUEL);
			break;
		case 4:
			setTypeQuota(SubQuota.TECHNICAL_WORK_AND_CONSULTING);
			break;
		case 5:
			setTypeQuota(SubQuota.DISCLOSURE_PARLIAMENTARY_ACTIVITY);
			break;
		case 8:
			setTypeQuota(SubQuota.SAFETY);
			break;
		case 9:
			setTypeQuota(SubQuota.ISSUANCE_OF_AIR_TICKETS);
			break;
		case 10:
			setTypeQuota(SubQuota.TELEPHONY);
			break;
		case 11:
			setTypeQuota(SubQuota.POSTAL_SERVICES);
			break;
		case 12:
			setTypeQuota(SubQuota.SIGNATURE_OF_PUBLICATION);
			break;
		case 13:
			setTypeQuota(SubQuota.ALIMENTATION);
			break;
		case 14:
			setTypeQuota(SubQuota.ACCOMMODATION);
			break;
		case 15:
			setTypeQuota(SubQuota.LEASE_OF_VEHICLES);
			break;
		case 119:
			setTypeQuota(SubQuota.AIR_FREIGHT);
			break;
		default:
			setTypeQuota(SubQuota.WITHOUT_TYPE);
			
			
		}
	}
public void setTypeMonthByNumber (int type){
		
		switch(type){
				
		case 1:
			setMonthReferenceQuota(Month.JANUARY);
			break;
		case 2:
			setMonthReferenceQuota(Month.FEBRUARY);
			break;
		case 3:
			setMonthReferenceQuota(Month.MARCH);
			break;
		case 4:
			setMonthReferenceQuota(Month.APRIL);
			break;
		case 5:
			setMonthReferenceQuota(Month.MAY);
			break;
		case 6:
			setMonthReferenceQuota(Month.JUNE);
			break;
		case 7:
			setMonthReferenceQuota(Month.JULY);
			break;
		case 8:
			setMonthReferenceQuota(Month.AUGUST);
			break;
		case 9:
			setMonthReferenceQuota(Month.SEPTEMBER);
			break;
		case 10:
			setMonthReferenceQuota(Month.OCTOBER);
			break;
		case 11:
			setMonthReferenceQuota(Month.NOVEMBER);
			break;
		case 12:
			setMonthReferenceQuota(Month.DECEMBER);
			break;
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

	public Statistic getStatisticQuota() {
		return statisticQuota;
	}

	public void setStatisticQuota( Statistic statisticQuota ) {
		this.statisticQuota = statisticQuota;
	}
}
