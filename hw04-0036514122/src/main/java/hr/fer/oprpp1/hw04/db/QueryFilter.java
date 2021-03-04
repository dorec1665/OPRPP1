package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**
 * Filtrira bazu podataka na temelju upita.
 * @author dario
 *
 */
public class QueryFilter implements IFilter {

	/**
	 * Predstavlja upit kojeg je korisnik zatražio.
	 */
	List<ConditionalExpression> expressions;
	
	public QueryFilter(List<ConditionalExpression> expressions) {
		this.expressions = expressions;
	}
	
	

	@Override
	public boolean accepts(StudentRecord record) {
		int currentElement = 0;
		boolean[] array = new boolean[expressions.size()];
		for(ConditionalExpression expr : expressions) {
			array[currentElement] = expr.getOperator().satisfied(expr.getField().get(record), expr.getLiteral());
			currentElement++;
		}
		for(int i = 0; i < array.length; i++) {
			if(!array[i]) {
				return false;
			}
		}
		
		return true;
	}

}
