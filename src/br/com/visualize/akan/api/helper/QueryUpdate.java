package br.com.visualize.akan.api.helper;

public class QueryUpdate extends Query implements QueryStrategy {

	@Override
	public String doOperation(String tableName, String[] columnNames,
			String[] values, String columnReference, String valueReference,
			String valueComparison, String columnOrdered, String orderType) {
		
		StringBuilder query = new StringBuilder( "UPDATE" );
		
		query.append(BLANK);
		query.append(tableName);
		query.append(BLANK);
		query.append(SET);
		query.append(BLANK);
		
		String setList = buildSetList(columnNames, columnNames);
		query.append(setList);
		
		if( columnReference.equals(null) || valueReference.equals(null) ) {
			/*! Nothing To Do */
			
		} else {
			String clauseWhere = buildClauseWhere(columnReference, 
					valueReference);
			
			query.append(clauseWhere);
		}
		
		return query.toString();
	}
}
