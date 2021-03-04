package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

public class LexerException extends SmartScriptParserException{

	public LexerException(String message) {
		super(message);
	}
	
	public LexerException() {
		super();
	}
}
