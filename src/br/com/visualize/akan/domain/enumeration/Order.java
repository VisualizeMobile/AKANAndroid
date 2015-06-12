package br.com.visualize.akan.domain.enumeration;

public enum Order {
	RANKING( 0, "RANKING_CONGRESSMAN" ),
	ALPHABETIC( 1, "NAME_CONGRESSMAN" ),
	STATE( 2, "UF_CONGRESSMAN" ),
	PARTY( 3, "PARTY" );
	
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