package br.com.visualize.akan.api.helper;

import java.util.Arrays;

public class Query {
	protected final String VALUES = "VALUES";
	protected final String SET = "SET";
	protected final String FROM = "FROM";
	
	
	protected final String BLANK = " ";
	protected final String OPEN_PARENTEHSESIS = "(";
	protected final String CLOSED_PARENTEHSESIS = ")";
	protected final String COMMA = ",";
	protected final String EQUAL = "=";
	protected final String PERCENT = "%";
	protected final String QUOTES = "'";
	protected final String SEMICOLON = ";";
	
	
	private final String WHERE = "WHERE";
	private final String LIKE = "LIKE";
	private final String ORDER_BY = "ORDER BY";
	
	
	protected String buildStringList( String[] elements ) {
		StringBuilder elementsList = new StringBuilder();
		
		int qtdElements = Arrays.asList(elements).size();
		
		for( int index = 0; index < qtdElements; index++ ) {
			elementsList.append( elements[index] );
			
			int elementNumber = index + 1;
			
			if( elementNumber > qtdElements ) {
				elementsList.append( COMMA );
				elementsList.append( BLANK );
				
			} else {
				/*! Nothing To Do */
			}
		}
		
		return elementsList.toString();
	}
	
	protected String buildSetList( String[] elements, String[] values ) {
		StringBuilder clause = new StringBuilder();
		
		int qtdElements = Arrays.asList(elements).size();
		int qtdValues = Arrays.asList(values).size();
		
		equalizeQuantities(qtdElements, qtdValues);
		
		for( int index = 0; index < qtdElements; index++ ) {
			clause.append( elements[index] );
			
			int elementNumber = index + 1;
			
			if( elementNumber > qtdElements ) {
				clause.append( BLANK );
				clause.append( EQUAL );
				clause.append( BLANK );
				
				clause.append(values[index]);
				clause.append(COMMA);
				
			} else {
				/*! Nothing To Do */
			}
		}
		
		return clause.toString();
	}
	
	protected String buildClauseWhere( String columnReference, 
			String valueReference ) {
		
		StringBuilder clause = new StringBuilder(WHERE);
		
		clause.append(BLANK);
		clause.append(columnReference);
		clause.append(BLANK);
		clause.append(EQUAL);
		clause.append(BLANK);
		clause.append(valueReference);
		
		return clause.toString();
	}
	
	protected String buildClauseLike( String valueComparison ) {
		StringBuilder clause = new StringBuilder(LIKE);
		
		clause.append(BLANK);
		clause.append(QUOTES);
		clause.append(PERCENT);
		clause.append(valueComparison);
		clause.append(PERCENT);
		clause.append(QUOTES);
		
		return clause.toString();
	}
	
	protected String buildClauseOrderBy( String columnOrdered, 
			String orderType ) {
		
		StringBuilder clause = new StringBuilder(ORDER_BY);
		
		clause.append(BLANK);
		clause.append(columnOrdered);
		clause.append(BLANK);
		clause.append(orderType);
		
		return clause.toString();
	}
	
	private void equalizeQuantities( int qtdColumns, int qtdValues ) {
		if( qtdColumns == qtdValues ) {
			/*! Nothing To Do */
			
		} else if ( qtdColumns > qtdValues ) {
			qtdColumns = qtdValues;
			
		} else {
			qtdValues = qtdColumns;
		}
	}
}
