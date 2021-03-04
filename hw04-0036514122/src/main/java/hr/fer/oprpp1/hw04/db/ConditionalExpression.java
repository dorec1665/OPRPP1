package hr.fer.oprpp1.hw04.db;

/**
 * Predstavlja jedan uvjet upita nad bazom.
 * @author dario
 *
 */
public class ConditionalExpression {

	/**
	 * Služi za dohvaćanje članske varijable trenutnog studenta.
	 */
	private IFieldValueGetter field;
	/**
	 * Znakovni niz kojeg je korisnik upisao nakon operatora usporedbe.
	 */
	private String literal;
	/**
	 * Predstavlja vrstu usporedbe koja će se koristiti.
	 */
	private IComparisonOperator operator;
	
	public ConditionalExpression(IFieldValueGetter field, String literal, IComparisonOperator operator) {
		if(field == null || literal == null || operator == null) {
			throw new NullPointerException();
		}
		this.field = field;
		this.literal = literal;
		this.operator = operator;
	}

	public IFieldValueGetter getField() {
		return field;
	}

	public String getLiteral() {
		return literal;
	}

	public IComparisonOperator getOperator() {
		return operator;
	}
	
	
	
}
