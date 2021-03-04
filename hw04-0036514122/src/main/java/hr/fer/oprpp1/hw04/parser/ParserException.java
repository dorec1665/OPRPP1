package hr.fer.oprpp1.hw04.parser;


/**
 * Greška koja nastupi pri pogrešnom parsiranju.
 * @author dario
 *
 */
public class ParserException extends RuntimeException{

	public ParserException() {
		
	}
	
	public ParserException(String message) {
		super(message);
	}
}
