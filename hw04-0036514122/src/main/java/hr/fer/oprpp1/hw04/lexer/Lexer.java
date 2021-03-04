package hr.fer.oprpp1.hw04.lexer;


/**
 * Razred Lexer prima kao ulaz i tekst na izlaz šalje niz tokena.
 * Ovaj razred za tekst prima jedan jednostavniji SQL upit.
 * @author dario
 *
 */
public class Lexer {
	
	private char[] data;
	private Token token;
	private int currentIndex;
	
	/**
	 * Konstruktor koji prima tekst koji je potrebno tokenizirati.
	 * @param text tekst koji se tokenizira
	 * @throws NullPointerException ako je tekst null
	 */
	public Lexer(String text) {
		if(text == null) {
			throw new NullPointerException();
		}
		
		data = text.toCharArray();
		currentIndex = 0;
	}
	
	
	/**
	 * Metoda koja provjerava koje vrste je token koji se trenutno
	 * obrađuje. Ako u tekstu nema više tokena za obradit, metoda vraća
	 * token EOF kao znak kraja teksta.
	 * @return novoobrađeni token
	 */
	public Token nextToken() {
		
		ignoreBlank();
		
		if(currentIndex == data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}
		
		if(Character.isLetter(data[currentIndex])) {
			token = checkWord();
			return token;
		} else if(data[currentIndex] == '"') {
			token = parseLiteral();
			return token;
		} else {
			token = parseOperator();
			return token;
		}
		
		
	}
	
	/**
	 * dohvaća zadnje obrađeni token.
	 * @return zadjnje obrađeni token.
	 */
	public Token getToken() {
		return token;
	}
	
	
	/**
	 * Obrađuje riječ na odgovarajući način. Riječ može biti
	 * atribut, logički operator AND ili operator usporedbe LIKE.
	 * @return novoobrađeni token
	 */
	private Token checkWord() {
		boolean isAnd = checkForLogicalOperatorAnd();
		int tmp = currentIndex;
		if(isAnd) {
			token = new Token(TokenType.OPERATOR, "and");
			return token;
		}
		currentIndex = tmp;
		boolean isLIKE = checkForLIKE();
		if(isLIKE) {
			token = new Token(TokenType.OPERATOR, "LIKE");
			return token;
		}
		currentIndex = tmp;
		String attributeName = parseWord();
		token = new Token(TokenType.ATTRIBUTE, attributeName);
		return token;
	}
	
	
	/**
	 * Prealazi preko svih praznina.
	 */
	private void ignoreBlank() {
		for(; currentIndex < data.length; currentIndex++) {
			if(Character.isWhitespace(data[currentIndex])) {
				continue;
			} else {
				return;
			}
		}
	}
	
	
	/**
	 * Provjerava jeli sljedeći token logčki operator AND.
	 * @return true ako je sljedeći token AND, inače false
	 */
	private boolean checkForLogicalOperatorAnd() {
		boolean isAnd = false;
		String s = "and";
		for(int i = 0; i < s.length(); i++) {
			if(Character.toLowerCase(data[currentIndex]) != s.charAt(i)) {
				isAnd = false;
				break;
			}
			currentIndex++;
			isAnd = true;
		}
		
		return isAnd;
	}
	
	
	/**
	 * Provjerava jeli sljedeći token operator usporedbe LIKE.
	 * @return true ako je sljedeći token LIKE, inače false
	 */
	private boolean checkForLIKE() {
		boolean isLIKE = false;
		String s = "LIKE";
		for(int i = 0; i < s.length(); i++) {
			if(Character.toUpperCase(data[currentIndex]) != s.charAt(i)) {
				isLIKE = false;
				break;
			}
			currentIndex++;
			isLIKE = true;
		}
		
		return isLIKE;
	}
	
	
	/**
	 * Tokenizira riječ na odgovarajući način.
	 * @return novoobrađenu riječ
	 */
	private String parseWord() {
		String word = "";
		for(; currentIndex < data.length; currentIndex++) {
			if(Character.isLetter(data[currentIndex])) {
				word += data[currentIndex];
			} else {
				break;
			}	
		}
		
		return word;	
	}
	
	
	/**
	 * Tokenizira svaki niz koji se nalazi između navodnika.
	 * @return novoobrađeni token
	 */
	private Token parseLiteral() {
		String literal = "";
		currentIndex++;
		for(; currentIndex < data.length; currentIndex++) {
			if(data[currentIndex] != '"') {
				literal += data[currentIndex];
			} else {
				currentIndex++;
				break;
			}
		}
		
		return new Token(TokenType.LITERAL, literal);
	}
	
	
	/**
	 * Obrađuje operator usporedbe koji može biti <, <=, >, >=, =, !=.
	 * @return novoobrađeni token.
	 * @throws LexerException ako se iza znaka ! ne nalazi znak = ili
	 * ako se pojavi neki znak koji nije jedan od navedenih operatora usporedbe
	 * 
	 */
	private Token parseOperator() {
		if(data[currentIndex] == '<') {
			currentIndex++;
			if(data[currentIndex] == '=') {
				currentIndex++;
				return new Token(TokenType.OPERATOR, "<=");
			} else {
				return new Token(TokenType.OPERATOR, "<");
			}
		} else if(data[currentIndex] == '>') {
			currentIndex++;
			if(data[currentIndex] == '=') {
				currentIndex++;
				return new Token(TokenType.OPERATOR, ">=");
			} else {
				return new Token(TokenType.OPERATOR, ">");
			}
		} else if(data[currentIndex] == '!') {
			currentIndex++;
			if(data[currentIndex] == '=') {
				currentIndex++;
				return new Token(TokenType.OPERATOR, "!=");
			} else {
				throw new LexerException("Iza znaka \"!\" mora doći znak \"=\"!");
			}
		} else if(data[currentIndex] == '=') {
			currentIndex++;
			return new Token(TokenType.OPERATOR, "=");
		} else {
			throw new LexerException("Pogrešan operator usporedbe ili nisu stavljeni navodnici za literal!");
		}
		
	}

	
	
	
}
