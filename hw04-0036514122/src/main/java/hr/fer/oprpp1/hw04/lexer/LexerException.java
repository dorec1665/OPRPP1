package hr.fer.oprpp1.hw04.lexer;

import hr.fer.oprpp1.hw04.parser.ParserException;


/**
 *Greška koja nastupi pri pogrešnom tokeniziranje Lexera. 
 * @author dario
 *
 */
public class LexerException extends ParserException {

	public LexerException(String message) {
		super(message);
	}
	
	public LexerException() {
		super();
	}
}
