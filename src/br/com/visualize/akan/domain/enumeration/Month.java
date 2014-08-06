/*
 * File: 	Month.java 
 * Purpose: Brings the implementation of a enumeration to support
 * the development.
 */
package br.com.visualize.akan.domain.enumeration;


/**
 * This enumeration serves to support the development. Through this enumeration 
 * is possible to easily identify the months of the year.
 */
public enum Month {
	WITHOUT_MONTH( 0 ),
	
	JANUARY( 1 ),
	FEBRUARY( 2 ),
	MARCH( 3 ),
	
	APRIL( 4 ),
	MAY( 5 ),
	JUNE( 6 ),
	
	JULY( 7 ),
	AUGUST( 8 ),
	SEPTEMBER( 9 ),
	
	OCTOBER( 10 ),
	NOVEMBER( 11 ),
	DECEMBER( 12 );
	
	private int valueMonth;
	
	Month( int value ) {
		valueMonth = value;
	}
	
	/**
	 * Return the numerical value of the month.
	 * 
	 * @return The numerical value of the month.
	 */
	public int getvalueMonth() {
		return valueMonth;
	}
}
