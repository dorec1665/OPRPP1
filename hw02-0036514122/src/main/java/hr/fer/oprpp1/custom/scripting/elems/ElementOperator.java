package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Razred koji sprema simbol kao String.
 * @author dario
 *
 */
public class ElementOperator extends Element{

	private String symbol;
	
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	@Override
	public String asText() {
		return symbol;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ElementOperator)) {
			return false;
		}
		ElementOperator other = (ElementOperator) obj;
		return this.symbol.equals(other.symbol);
	}
}
