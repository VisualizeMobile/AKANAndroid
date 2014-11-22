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
}
