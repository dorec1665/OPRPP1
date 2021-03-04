package hr.fer.oprpp1.hw02.prob1;


/**
 * Razred kojeg Lexer vraća ako je tokeniziranje uspješno odrađeno.
 * @author dario
 *
 */
public class Token {

	private TokenType type;
	private Object value;
	
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	public Object getValue() {
		return value;
	}
	
	public TokenType getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return "(" + type + ", " + value + ")";
	}
}
