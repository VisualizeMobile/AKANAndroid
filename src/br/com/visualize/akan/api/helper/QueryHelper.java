package br.com.visualize.akan.api.helper;

public class QueryHelper {
	private QueryStrategy queryStrategy;
	
	public QueryHelper( QueryStrategy strategy ) {
		this.queryStrategy = strategy;
	}
	
	public String executeQuery( String tableName, String[] columnNames,
			String[] values, String columnReference, String valueReference,
			String valueComparison, String columnOrdered, String orderType ) {
		
		String query = queryStrategy.doOperation(tableName, columnNames, 
				values, columnReference, valueReference, valueComparison, 
				columnOrdered, orderType);
		
		return query;
	}
}
