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
			String clauseWhere = buildClauseWhere(columnReference, valueReference);
			
			query.append(clauseWhere);
			query.append(BLANK);
			
		} else {
			/*! Nothing To Do */
		}
		
		if( valueComparison.equals(null) ) {
			String clauseLike = buildClauseLike(valueComparison);
			
			query.append(clauseLike);
			query.append(BLANK);
			
		} else {
			/*! Nothing To Do */
		}
		
		if( columnOrdered.equals(null) || orderType.equals(null) ) {
			String clauseOrderBy = buildClauseOrderBy(columnOrdered, orderType);
			
			query.append(clauseOrderBy);
			
		} else {
			/*! Nothing To Do */
		}
		
		return query.toString();
	}
}
