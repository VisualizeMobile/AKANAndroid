package br.com.visualize.akan.api.helper;


public class QueryDelete extends Query implements QueryStrategy {

	@Override
	public String doOperation(String tableName, String[] columnNames,
			String[] values, String columnReference, String valueReference,
			String valueComparison, String columnOrdered, String orderType) {

		StringBuilder query = new StringBuilder( "DELETE FROM" );
		
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
		
		query.append(SEMICOLON);
		
		return query.toString();
	}
}
