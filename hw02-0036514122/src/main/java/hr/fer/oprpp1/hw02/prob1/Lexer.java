package hr.fer.oprpp1.hw02.prob1;


/**
 * Razred Lexer kao ulaz prima tekst, te ga obrađuje i na izlaz šalje
 * niz tokena. Lexer se pridržava sljedećih pravila:
 * Tekst se sastoji od riječi, brojeva te simbola. Riječ je
 * svaki niz znakova nad kojim metoda Character.isLetter vraća <code>true</code>.
 * Broj je savki niz znakova koji je prikaziv sa {@link Long}.
 * Simbol je svaki znak koji se dobije kada se iz teksta izbace riječi , brojevi
 * te praznine.
 * Također je dopušteno escapeanje: ako se ispred znamenke nađe \, tada se ta znamenka
 * tretira kao slovo, tj. dio riječi. Znak \ ispred samog sebe je također ispravno,
 * te se tada znak \ smatra dijelom riječi. Sva ostala escapeanja nisu dozvoljena.
 * Lexer se može u naći dva stanja: BASIC i EXTENDED.
 * U stanju BASIC Lexer radi kako je već pojašnjeno, no nailaskom na znak #
 * Lexer prelazi u stanje EXTENDED gdje vrijede nova pravila:
 * Svaki znak, bilo slovo, broj ili simbol su dio riječi, te nema escapeanja.
 * Ponovnim nailaskom na znak # Lexer se vraća u BASIC stanje.
 * @author dario
 *
 */
public class Lexer {

	private char[] data;
	private Token token;
	private int currentIndex;
	private LexerState state;
	
	/**
	 * Konstruktor koji prima tekst koji je potrebno tokenizirati.
	 * @param text tekst koji se tokenizira
	 * @throws NullPointerException ako je tekst null
	 */
	public Lexer(String text) {
		if(text == null) {
			throw new NullPointerException("Tekst mora biti zadan, a ne null!");
		}
		
		data = text.toCharArray();
		currentIndex = 0;
		state = LexerState.BASIC;
	}
	
	
	
	/**
	 * Metoda koja provjerava koje vrste je token koji se trenutno
	 * obrađuje. Ako u tekstu nema više tokena za obradit, metoda vraća
	 * token EOF kao znak kraja teksta.
	 * @return novoobrađeni token
	 * @throws LexerException ako je token null ili ako je zadnji obrađeni
	 * token bio tipa EOF
	 */
	public Token nextToken() throws LexerException {
		if(token != null && getToken().getType().equals(TokenType.EOF)) {
			throw new LexerException("Nema više elemenata za obradit!");
		}
		
		if(data.length == 0) {
			token = new Token(TokenType.EOF, null);
			return token;
		}
		
		if(state.equals(LexerState.BASIC)) {
			for(; currentIndex < data.length; currentIndex++) {
				if(Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
					token = parseWord();
					return token;
				} else if(Character.isWhitespace(data[currentIndex])){
					continue;
				} else if(Character.isDigit(data[currentIndex])) {
					token = parseNumber();
					return token;
				} else {
					if(data[currentIndex] == '#') {
						setState(LexerState.EXTENDED);
					}
					token = new Token(TokenType.SYMBOL, data[currentIndex]);
					currentIndex++;
					return token;
				}
			}
		} else {
			for(; currentIndex < data.length; currentIndex++) {
				if(Character.isWhitespace(data[currentIndex])) {
					continue;
				} else {
					token = parseWordExtended();
					return token;
				}
			}
		}
		
		if(currentIndex == data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}
		
		return null;
	}
	
	
	
	/**
	 * U ovoj metodi Lexer se nalazi u stanju EXTENDED, te
	 * na adekvatan način tokenizira tekst
	 * @return novonastali obrađeni token
	 */
	private Token parseWordExtended() {
		String word = "";
		Token t = null;
		for(int i = currentIndex; i < data.length; i++) {
			if(Character.isWhitespace(data[i])) {
				t = new Token(TokenType.WORD, word);
				return t;
			} else if(data[i] == '#') {
				setState(LexerState.BASIC);
				t = new Token(TokenType.WORD, word);
				return t;
			} else {
				word += data[i];
				currentIndex++;
			}
		}
		
		t = new Token(TokenType.WORD, word);
		return t;
		
	}
	
	
	/**
	 * U ovoj metodi Lexer se nalti u stanju BASIC, te
	 * na adekvatan način tokenizira tekst
	 * @return novoobrađeni token
	 * @throws LexerException ako se escapaenje koristi na pogrešan način
	 */
	private Token parseWord() {
		String word = "";
		Token t = null;
		for(int i = currentIndex; i < data.length; i++) {
			if(!Character.isLetter(data[i]) && data[i] != '\\') {
				t = new Token(TokenType.WORD, word);
				return t;
			} else if(data[i] == '\\' && (i != data.length-1 && !Character.isLetter(data[i+1]))) {
				word += data[i+1];
				i++;
				currentIndex += 2;
			} else if(data[i] == '\\' && (i == data.length-1 || Character.isLetter(data[i+1]))) {
				throw new LexerException("Pogrešno escapaenje");
			} else {
				word += data[i];
				currentIndex++;
			}
		}
		
		t = new Token(TokenType.WORD, word);
		return t;
	}
	
	
	
	/**
	 * U ovoj metodi Lexer ne može biti stanju EXTENDED, jer
	 * token koji generira je broj.
	 * @return cijeli broj
	 * @throws LexerException ako se dogodi overflow nad Longom
	 */
	private Token parseNumber() {
		Long number = 0L;
		Token t = null;
		for(int i = currentIndex; i < data.length; i++) {
			if(!Character.isDigit(data[i])) {
				t = new Token(TokenType.NUMBER, number);
				return t;
			} else {	
				
				number = number * 10L + data[i] - '0';
				if(number < 0) {
					throw new LexerException("Broj je prevelik!");
				}
				currentIndex++;
			}
		}
		
		t = new Token(TokenType.NUMBER, number);
		return t;
	}
	
	public Token getToken() {
		return token;
	}
	
	public char[] getData() {
		return data;
	}
	
	public int getCurrentIndex() {
		return currentIndex;
	}
	
	
	
	/**
	 * Postavlja stanje Lexera
	 * @param state novo stanje Lexera
	 */
	public void setState(LexerState state) {
		if(state == null) {
			throw new NullPointerException("Stanje mora biti zadano, a ne null!");
		}
		this.state = state;
	}
}
