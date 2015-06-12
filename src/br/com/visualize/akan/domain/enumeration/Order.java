package br.com.visualize.akan.domain.enumeration;

public enum Order {
	RANKING( 0, "ranking" ),
	ALPHABETIC( 1, "aphabetic" ),
	STATE( 2, "state" ),
	PARTY( 3, "party" );
	
	private int valueOrder;
	private String representativeName;
	
	Order( int value, String name ) {
		this.valueOrder = value;
		this.representativeName = name;
	}
	
	/**
	 * Return the numerical value of the ordenation.
	 * 
	 * @return The numerical value of the ordenation.
	 */
	public int getValueOrder() {
		return valueOrder;
	}
	
	/**
	 * Return a name that represent the ordenation.
	 * 
	 * @return The name that represent this ordenation.
	 */
	public String getOrderName() {
		return representativeName; 
	}

}