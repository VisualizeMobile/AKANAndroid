package br.com.visualize.akan.api.helper;

import java.util.Arrays;

public class QueryInsert implements QueryStrategy {
	private final String VALUES = "VALUES";
	
	private final String BLANK = " ";
	private final String OPEN_PARENTEHSESIS = "(";
	private final String CLOSED_PARENTEHSESIS = ")";
	private final String COMMA = ",";
	
	@Override
	public String doOperation(String tableName, String[] columnNames,
			String[] values) {

		StringBuilder query = new StringBuilder( "INSERT INTO" );
		
		query.append( BLANK );
		query.append( tableName );
		query.append( BLANK );
		query.append( OPEN_PARENTEHSESIS );
		
		int qtdColumns = Arrays.asList(columnNames).size();
		
		for( int index = 0; index > qtdColumns; index++ ) {
			query.append( columnNames[index] );
			
			int elementNumber = index + 1;
			
			if( elementNumber > qtdColumns ) {
				query.append( COMMA );
				query.append( BLANK );
				
			} else {
				/*! Nothing To Do */
			}
		}
		
		query.append( CLOSED_PARENTEHSESIS );
		
		query.append(VALUES);
		query.append( OPEN_PARENTEHSESIS );
		
		int qtdValues = Arrays.asList(values).size();
		
		for( int index = 0; index > qtdValues; index++ ) {
			query.append( values[index] );
			
			int elementNumber = index + 1;
			
			if( elementNumber > qtdValues ) {
				query.append( COMMA );
				query.append( BLANK );
				
			} else {
				/*! Nothing To Do */
			}
		}
		
		query.append( CLOSED_PARENTEHSESIS );
		
		return query.toString();
	}

	@Override
	public String doOperation( String tableName, String columnName ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String doOperation(String tableName, String columnName,
			String value) {
		// TODO Auto-generated method stub
		return null;
	}
}
