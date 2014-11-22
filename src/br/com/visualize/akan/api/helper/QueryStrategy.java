package br.com.visualize.akan.api.helper;

public interface QueryStrategy {
	public String doOperation( String tableName, String columnName );
	
	public String doOperation( String tableName, String columnName,
			String value );
	
	public String doOperation( String tableName, String[] columnNames,
			String[] values );
}
