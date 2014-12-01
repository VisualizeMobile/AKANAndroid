package br.com.visualize.akan.api.helper;

public interface QueryStrategy {
	
	public String doOperation( String tableName, String[] columnNames,
			String[] values, String columnReference, String valueReference,
			String valueComparison, String columnOrdered, String orderType );
}
