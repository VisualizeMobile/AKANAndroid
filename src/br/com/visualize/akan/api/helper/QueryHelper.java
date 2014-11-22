package br.com.visualize.akan.api.helper;

public class QueryHelper {
	private QueryStrategy queryStrategy;
	
	public QueryHelper( QueryStrategy strategy ) {
		this.queryStrategy = strategy;
	}
	
	public String executeQuery( String tableName, String columnName  ) {
		String query = queryStrategy.doOperation( tableName, columnName );
		
		return query;
	}
	
	public String executeQuery( String tableName, String columnName,
			String value  ) {
		
		String query = queryStrategy.doOperation( tableName, columnName,
				value );
		
		return query;
	}
	
	public String executeQuery( String tableName, String[] columnNames,
			String[] values  ) {
		
		String query = queryStrategy.doOperation( tableName, columnNames,
				values );
		
		return query;
	}
}
