package br.com.visualize.akan.api.helper;


public class QuerySelect extends Query implements QueryStrategy {

	@Override
	public String doOperation(String tableName, String[] columnNames,
			String[] values, String columnReference, String valueReference,
			String valueComparison, String columnOrdered, String orderType) {

		StringBuilder query = new StringBuilder( "SELECT" );
		
		query.append(BLANK);
		
		String columns = buildStringList(columnNames);
		query.append(columns);
		
		query.append(BLANK);
		query.append(FROM);
		query.append(BLANK);
		query.append(tableName);
		
		if( ( columnReference == null ) || ( valueReference == null ) ) {
			/*! Nothing To Do */
			
		} else {
			String clauseWhere = buildClauseWhere(columnReference, 
					valueReference);
			
			query.append(BLANK);
			query.append(clauseWhere);
		}
		
		if( valueComparison == null ) {
			/*! Nothing To Do */
			
		} else {
			String clauseLike = buildClauseLike(valueComparison);
			
			query.append(BLANK);
			query.append(clauseLike);
		}
		
		if( ( columnOrdered == null ) || ( orderType == null ) ) {
			/*! Nothing To Do */
			
		} else {
			String clauseOrderBy = buildClauseOrderBy(columnOrdered, orderType);
			
			query.append(BLANK);
			query.append(clauseOrderBy);
		}
		
		query.append(SEMICOLON);
		
		return query.toString();
	}
}
