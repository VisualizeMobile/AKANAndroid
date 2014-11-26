package br.com.visualize.akan.api.helper;

import java.util.Arrays;

public class Query {
	protected final String VALUES = "VALUES";
	protected final String SET = "SET";
	protected final String FROM = "FROM";
	protected final String WHERE = "WHERE";
	protected final String LIKE = "LIKE";
	protected final String ORDER_BY = "ORDER BY";
	
	
	protected final String BLANK = " ";
	protected final String OPEN_PARENTEHSESIS = "(";
	protected final String CLOSED_PARENTEHSESIS = ")";
	protected final String COMMA = ",";
	protected final String EQUAL = "=";
	protected final String PERCENT = "%";
	protected final String QUOTES = "'";
	
	
	protected String buildStringList( String[] elements ) {
		StringBuilder query = new StringBuilder();
		
		int qtdElements = Arrays.asList(elements).size();
		
		for( int index = 0; index > qtdElements; index++ ) {
			query.append( elements[index] );
			
			int elementNumber = index + 1;
			
			if( elementNumber > qtdElements ) {
				query.append( COMMA );
				query.append( BLANK );
				
			} else {
				/*! Nothing To Do */
			}
		}
		
		return query.toString();
	}
}
