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
	OFFICE( 1, "office" ),
	FUEL( 3, "fuel" ),
	TECHNICAL_WORK_AND_CONSULTING( 4, "technical_work_and_consulting" ),
	DISCLOSURE_PARLIAMENTARY_ACTIVITY( 5, "disclosure_parliamentary_activity" ),
	SAFETY( 8, "safety" ),
	AIR_FREIGHT( 9, "air_freight" ),
	TELEPHONY( 10, "telephony" ),
	POSTAL_SERVICES( 11, "postal_services" ),
	SIGNATURE_OF_PUBLICATION( 12, "signature_of_publication" ),
	ALIMENTATION( 13, "alimentation" ),
	ACCOMMODATION( 14, "accommodation" ),
	LEASE_OF_VEHICLES( 15, "lease_of_vehicles" ),
	ISSUANCE_OF_AIR_TICKETS( 999, "issuance_of_air_tickets" );
	
	private int valueSubQuota;
	private String representativeName;
	
	SubQuota( int value ) {
		valueSubQuota = value;
	}
	
	SubQuota( int value, String name ) {
		this.valueSubQuota = value;
		this.representativeName = name;
	}
	
	/**
	 * Return the numerical value of the sub-quota that represent it in database.
	 * 
	 * @return The numerical value of the sub-quota that represent it in database.
	 */
	public int getValueSubQuota() {
		return valueSubQuota;
	}
	
	/**
	 * Return a name that represent the sub-quota.
	 * 
	 * @return The name that represent this sub-quota.
	 */
	public String getRepresentativeNameQuota() {
		return representativeName; 
	}
}
