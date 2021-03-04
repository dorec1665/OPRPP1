package hr.fer.oprpp1.hw02.prob1;

/**
 *Greška koja nastupi pri pogrešnom tokeniziranje Lexera. 
 * @author dario
 *
 */
public class LexerException extends RuntimeException{

	public LexerException(String message) {
		super(message);
	}
	
	public LexerException() {
		super();
	}
}
