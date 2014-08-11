/*
 * File: 	SubQuota.java 
 * Purpose: Brings the implementation of a enumeration to support
 * the development.
 */
package br.com.visualize.akan.domain.enumeration;


/**
 * This enumeration serves to support the development. Through this enumeration 
 * is possible to easily identify the sub-quotas that congressman has.
 */
public enum SubQuota {
	WITHOUT_TYPE( 0 ),
	OFFICE( 1 ),
	FUEL( 3 ),
	TECHNICAL_WORK_AND_CONSULTING( 4 ),
	DISCLOSURE_PARLIAMENTARY_ACTIVITY( 5 ),
	SAFETY( 8 ),
	AIR_FREIGHT( 9 ),
	TELEPHONY( 10 ),
	POSTAL_SERVICES( 11 ),
	SIGNATURE_OF_PUBLICATION( 12 ),
	ALIMENTATION( 13 ),
	ACCOMMODATION( 14 ),
	LEASE_OF_VEHICLES( 15 ),
	ISSUANCE_OF_AIR_TICKETS( 999 );
	
	private int valueSubQuota;
	
	SubQuota( int value ) {
		valueSubQuota = value;
	}
	
	/**
	 * Return the numerical value of the sub-quota that represent it in database.
	 * 
	 * @return The numerical value of the sub-quota that represent it in database.
	 */
	public int getValueSubQuota() {
		return valueSubQuota;
	}
}
