package br.com.visualize.akan.domain.model;

import br.com.visualize.akan.domain.enumeration.Month;
import br.com.visualize.akan.domain.enumeration.SubQuota;

public class Statistic {
	private SubQuota subquota;
	
	private int year;
	private Month month;
	
	private double stdDeviation;
	private double average;
	private double maxValue;
	
	public SubQuota getSubquota() {
		return subquota;
	}
	
	public void setSubquota( SubQuota subquota ) {
		this.subquota = subquota;
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
