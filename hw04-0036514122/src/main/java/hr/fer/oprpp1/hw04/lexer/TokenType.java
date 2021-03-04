package hr.fer.oprpp1.hw04.lexer;

public enum TokenType {

	/**
	 * Označava operator usoredbe. Mogući operatori su: <, <=, >, >=, =, != i LIKE.
	 */
	OPERATOR,
	/**
	 * Označava ime članske varijable. Moguća imena su firstName, lastName, jmbag.
	 */
	ATTRIBUTE,
	/**
	 * Predstavlja bilo koji niz znakova unutar navodnika.
	 */
	LITERAL,
	/**
	 * Označava prazan tekst ili kraj teksta.
	 */
	EOF;
}
