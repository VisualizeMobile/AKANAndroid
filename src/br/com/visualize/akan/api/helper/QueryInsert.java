package br.com.visualize.akan.api.helper;


public class QueryInsert extends Query implements QueryStrategy {
	
	@Override
	public String doOperation( String tableName, String[] columnNames,
			String[] values, String columnReference, String valueReference,
			String valueComparison, String columnOrdered, String orderType ) {

		StringBuilder query = new StringBuilder( "INSERT INTO" );
		
		query.append(BLANK);
		query.append(tableName);
		query.append(BLANK);
		query.append(OPEN_PARENTEHSESIS);
		
		String columns = buildStringList(columnNames);
		query.append(columns);
		
		query.append(CLOSED_PARENTEHSESIS);
		query.append(BLANK);
		
		query.append(VALUES);
		query.append(BLANK);
		query.append(OPEN_PARENTEHSESIS);
		
		String elements = buildStringList(values);
		query.append(elements);
		
		query.append(CLOSED_PARENTEHSESIS);
		
		
		return query.toString();
	}
}
