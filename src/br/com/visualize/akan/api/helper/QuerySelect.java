package br.com.visualize.akan.api.helper;

public class QuerySelect extends Query implements QueryStrategy {

	@Override
	public String doOperation(String tableName, String[] columnNames,
			String[] values, String columnReference, String valueReference,
			String valueComparison, String columnOrdered, String orderType) {

		StringBuilder query = new StringBuilder( "SELECT" );
		
		query.append(BLANK);
		query.append(columnNames);
		query.append(BLANK);
		
		query.append(FROM);
		query.append(BLANK);
		query.append(tableName);
		query.append(BLANK);
		
		if( columnReference.equals(null) || valueReference.equals(null) ) {
			query.append(WHERE);
			query.append(BLANK);
			query.append(columnReference);
			query.append(BLANK);
			query.append(EQUAL);
			query.append(BLANK);
			query.append(valueReference);
			query.append(BLANK);
			
		} else {
			/*! Nothing To Do */
		}
		
		if( valueComparison.equals(null) ) {
			query.append(LIKE);
			query.append(BLANK);
			query.append(QUOTES);
			query.append(PERCENT);
			query.append(valueComparison);
			query.append(PERCENT);
			query.append(QUOTES);
			query.append(BLANK);
			
		} else {
			/*! Nothing To Do */
		}
		
		if( columnOrdered.equals(null) || orderType.equals(null) ) {
			query.append(ORDER_BY);
			query.append(BLANK);
			query.append(columnOrdered);
			query.append(BLANK);
			query.append(orderType);
			
		} else {
			/*! Nothing To Do */
		}
		
		return query.toString();
	}
}
