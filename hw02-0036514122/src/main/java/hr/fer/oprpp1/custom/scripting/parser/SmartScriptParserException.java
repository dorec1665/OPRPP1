package hr.fer.oprpp1.custom.scripting.parser;


/**
 * Iznimka koja nastupi pro pogrešnom parsiranju teksta.
 * @author dario
 *
 */
public class SmartScriptParserException extends RuntimeException {

	public SmartScriptParserException(String message) {
		super(message);
	}
	
	public SmartScriptParserException() {
		super();
	}
}
