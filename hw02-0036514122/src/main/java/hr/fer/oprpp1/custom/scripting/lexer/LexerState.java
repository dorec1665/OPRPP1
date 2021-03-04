package hr.fer.oprpp1.custom.scripting.lexer;

public enum LexerState {
	
	/**
	 * Stanje u kojem se čitaju elementi razreda TextNode.
	 */
	TEXT,
	
	/**
	 * Stanje u kojem se čitaju elementi razreda ForLoopNode.
	 */
	FOR,
	
	/**
	 * Stanje u kojem se čitaju elementi razreda EchoNode.
	 */
	ECHO,
	
	/**
	 * Stanje u kojem se čitaju elementi razreda EndNode.
	 */
	END;

}
