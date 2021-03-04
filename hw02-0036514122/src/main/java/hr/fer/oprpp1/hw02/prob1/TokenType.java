package hr.fer.oprpp1.hw02.prob1;

public enum TokenType {

	/**
	 * Označava prazan tekst ili kraj teksta.
	 */
	EOF,
	
	/**
	 * Označava niz znakova koji su slova.
	 */
	WORD,
	
	/**
	 * Označa niz znakova koji su brojevi.
	 */
	NUMBER,
	
	/**
	 * Označava znak koji je simbol(nije riječ, broj, praznina).
	 */
	SYMBOL;
	
}
