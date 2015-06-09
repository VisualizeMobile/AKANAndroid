package br.com.visualize.akan.domain.model;

import br.com.visualize.akan.domain.enumeration.Month;
import br.com.visualize.akan.domain.enumeration.SubQuota;

public class Statistic {
	private SubQuota subquota;
	
	private int idStatistic;
	private int year;
	private Month month;
	
	private double stdDeviation;
	private double average;
	private double maxValue;
	
	public int getIdStatistic() {
		return idStatistic;
	}

	public void setIdStatistic(int idStatistic) {
		this.idStatistic = idStatistic;
	}

	
	public SubQuota getSubquota() {
		return subquota;
	}
	
	public void setSubquota( SubQuota subquota ) {
		this.subquota = subquota;
	}
	
public void setSubquotaByNumber (int type){
		
		switch(type){
				
		case 1:
			setSubquota(SubQuota.OFFICE);
			break;
		case 3:
			setSubquota(SubQuota.FUEL);
			break;
		case 4:
			setSubquota(SubQuota.TECHNICAL_WORK_AND_CONSULTING);
			break;
		case 5:
			setSubquota(SubQuota.DISCLOSURE_PARLIAMENTARY_ACTIVITY);
			break;
		case 8:
			setSubquota(SubQuota.SAFETY);
			break;
		case 9:
			setSubquota(SubQuota.ISSUANCE_OF_AIR_TICKETS);
			break;
		case 10:
			setSubquota(SubQuota.TELEPHONY);
			break;
		case 11:
			setSubquota(SubQuota.POSTAL_SERVICES);
			break;
		case 12:
			setSubquota(SubQuota.SIGNATURE_OF_PUBLICATION);
			break;
		case 13:
			setSubquota(SubQuota.ALIMENTATION);
			break;
		case 14:
			setSubquota(SubQuota.ACCOMMODATION);
			break;
		case 15:
			setSubquota(SubQuota.LEASE_OF_VEHICLES);
			break;
		case 119:
			setSubquota(SubQuota.AIR_FREIGHT);
			break;
		default:
			setSubquota(SubQuota.WITHOUT_TYPE);
		}
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear( int year ) {
		this.year = year;
	}
	
	public Month getMonth() {
		return month;
	}
	
public void setMonthByNumber (int type){
		
		switch(type){
		case 1:
			setMonth(Month.JANUARY);
			break;
		case 2:
			setMonth(Month.FEBRUARY);
			break;
		case 3:
			setMonth(Month.MARCH);
			break;
		case 4:
			setMonth(Month.APRIL);
			break;
		case 5:
			setMonth(Month.MAY);
			break;
		case 6:
			setMonth(Month.JUNE);
			break;
		case 7:
			setMonth(Month.JULY);
			break;
		case 8:
			setMonth(Month.AUGUST);
			break;
		case 9:
			setMonth(Month.SEPTEMBER);
			break;
		case 10:
			setMonth(Month.OCTOBER);
			break;
		case 11:
			setMonth(Month.NOVEMBER);
			break;
		case 12:
			setMonth(Month.DECEMBER);
			break;
		default:
			//nothing to do
		}
	}
	
	public void setMonth( Month month ) {
		this.month = month;
	}
	
	public double getStdDeviation() {
		return stdDeviation;
	}
	
	public void setStdDeviation( double stdDeviation ) {
		this.stdDeviation = stdDeviation;
	}
	
	public double getAverage() {
		return average;
	}
	
	public void setAverage( double average ) {
		this.average = average;
	}
	
	public double getMaxValue() {
		return maxValue;
	}
	
	public void setMaxValue( double maxValue ) {
		this.maxValue = maxValue;
	}
}
