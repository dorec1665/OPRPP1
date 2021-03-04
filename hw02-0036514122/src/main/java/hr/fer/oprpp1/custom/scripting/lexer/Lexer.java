package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementOperator;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.Node;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;


/**
 * Razred Lexer kao ulaz prima tekst, te ga obrađuje i na izlaz šalje
 * niz tokena. Lexer ima 4 stanja: TEXT, FOR, ECHO, NODE. Lexer se pridržava sljedećih pravila:
 * Ako se nalazi u stanju TEXT tada čita bilo koji znak osim
 * znakova { i $ ako se nalaze jedan iza drugog. Tada prelazi u drugo odgovarajuće
 * stanje. U ovom stanju je dopušteno escapaenje:
 * \\ se tretira kao \, a \{ se tretira kao {, sve ostalo nije ispravno.
 * U stanju END Lexer samo provjerava da je tag ispravno napisan.
 * U stanju FOR Lexer provjerava ispravnost taga. FOR tag mora sadržavati
 * ime varijable i još 2 ili 3 dodatna elementa, inače će nastupiti LexerException.
 * U stanju ECHO Lexer provjerava ispravnost taga. Tag echo može sadržavati n elemenata
 * koji moraju biti ispravno napisani kako bi se mogli tokenizirati.
 * @author dario
 *
 */
public class Lexer {

	private Token token;
	private LexerState state;
	private char[] data;
	private int currentIndex;
	
	
	/**
	 * Konstruktor koji zadajemo tekst koji lekser treba obraditi.
	 * @param text koji treba obraditit
	 * @throws NullPointerException ako je tekst <code>null</code>
	 */
	public Lexer(String text) {
		if(text == null) {
			throw new NullPointerException("Tekst mora biti zadan, a ne null!");
		}
		
		data = text.toCharArray();
		currentIndex = 0;
		state = LexerState.TEXT;
	}
	
	
	/**
	 * Obrađuje novi element u tekstu na način da provjeri da li
	 * je element jedan od tipova (TEXT, FOR, ECHO, END) i na temelju toga
	 * vrši odgovarajuću metodu obrade elementa.
	 * @return novoobrađeni element
	 */
	public Token nextToken() {
		
		if(currentIndex == data.length) {
			return new Token(null, NodeType.EOF);
		}
		
		if(data[currentIndex] == '{') {
			if(data[currentIndex+1] == '$') {
				checkTagAndSetState();
			} else {
				setState(LexerState.TEXT);
			}
		} else {
			setState(LexerState.TEXT);
		}
		
		if(state.equals(LexerState.TEXT)) {
			token = parseText();
			return token;
		}
		
		if(state.equals(LexerState.FOR)) {
			token = parseFor();
			return token;
		}
		
		if(state.equals(LexerState.END)) {
			token = parseEnd();
			return token;
		}
		
		if(state.equals(LexerState.ECHO)) {
			token = parseEcho();
			return token;
		}
		return null;
	}
	
	public Token getToken() {
		return token;
	}
	
	
	/**
	 * Prolazi kroz sve razmake i zanemaruje ih.
	 */
	private void readAndIgnoreBlank() {
		for(; currentIndex < data.length; currentIndex++) {
			if(Character.isWhitespace(data[currentIndex])) {
				continue;
			} else {
				return;
			}
		}
	}
	
	
	/**
	 * Čita znak po znak i vraća ime varijable. Ime se može sastojati
	 * od malih ili velikih slova abecede, brojeva(ali ne na prvom mjestu!), te znaka "_".
	 * @return ime varijable
	 * @throws LexerException ako ime nije validno
	 */
	private ElementVariable readVariable() {
		if(!(Character.isLetter(data[currentIndex]))) {
			throw new RuntimeException();
		}
		String name = "";
		for(; currentIndex < data.length; currentIndex++) {
			if(state.equals(LexerState.ECHO) && (data[currentIndex] == '@'  ||
					data[currentIndex] == '+' || data[currentIndex] == '/' ||
					data[currentIndex] == '*' || data[currentIndex] == '^' ||
					(data[currentIndex] == '$' && data[currentIndex+1] == '}'))) {
				break;
			}
			if(data[currentIndex] == '-' || data[currentIndex] == '"' || 
					Character.isWhitespace(data[currentIndex])) {
				break;
			} else if(Character.isLetterOrDigit(data[currentIndex]) || data[currentIndex] == '_') {
				name += data[currentIndex];
			} else {
				throw new LexerException();
			}
		}
		
		return new ElementVariable(name);
	}
	
	
	/**
	 * Čita prvi izraz FOR taga koji može biti varijabla,
	 * integer, ili pak String koji sadrži integer npr. "15".
	 * @return prvi izraz FOR taga
	 * @throws LexerException ako izraz nije validan
	 */
	private Element readFirstExpression() {
		Element e;
		if(data[currentIndex] == '-') {
			currentIndex++;
			e = firstExpressionStartsWithMinusOrDigit(true);
		} else if(Character.isDigit(data[currentIndex])) {
			e = firstExpressionStartsWithMinusOrDigit(false);
		} else if(Character.isLetter(data[currentIndex])) {
			e = readVariable();
		} else if(data[currentIndex] == '"') {
			currentIndex++;
			e = firstExpressionStartsWithQoutes();
		} else {
			throw new LexerException();
		}
		
		return e;
	}
	
	
	/**
	 * Čita drugi(opcionalni) izraz FOR taga koji može biti varijabla,
	 * integer, ili pak String koji sadrži integer npr. "15".
	 * @return drugi izraz FOR taga
	 * @throws LexerException ako izraz nije validan
	 */
	private Element readStepExpression() {
		Element e;
		if(data[currentIndex] == '-') {
			currentIndex++;
			e = stepExpressionStartsWithMinusOrDigit(true);
		} else if(Character.isDigit(data[currentIndex])) {
			e = stepExpressionStartsWithMinusOrDigit(false);
		} else if(Character.isLetter(data[currentIndex])) {
			e = stepExpressionStartsWithLetter();
		} else if(data[currentIndex] == '"') {
			currentIndex++;
			e = stepExpressionStartsWithQoutes();
		} else {
			throw new LexerException();
		}
		
		return e;
	}
	
	
	
	/**
	 * Čita treći i posljednji izraz FOR taga koji može biti varijabla,
	 * integer, ili pak String koji sadrži integer npr. "15".
	 * @return posljednji izraz FOR taga
	 * @throws LexerException ako izraz nije validan
	 */
	private Element readLastExpression() {
		Element e;
		if(data[currentIndex] == '-') {
			currentIndex++;
			e = lastExpressionStartsWithMinusOrDigit(true);
		} else if(Character.isDigit(data[currentIndex])) {
			e = lastExpressionStartsWithMinusOrDigit(false);
		} else if(Character.isLetter(data[currentIndex])) {
			e = lastExpressionStartsWithLetter();
		} else if(data[currentIndex] == '"') {
			currentIndex++;
			e = lastExpressionStartsWithQoutes();
		} else {
			throw new LexerException();
		}
		
		return e;
	}
	
	
	/**
	 * Provjera jeli posljednji izraz FOR taga cijeli broj.
	 * @param minus provjerava jeli broj negativan ili pozitivan
	 * @return cijeli broj
	 * @throws LexerException ako broj nije validan
	 */
	private Element lastExpressionStartsWithMinusOrDigit(boolean minus) {
		String expression = minus ? "-" : "";
		if(!Character.isDigit(data[currentIndex])) {
			throw new RuntimeException();
		}
		for(; currentIndex < data.length; currentIndex++) {
			if(Character.isDigit(data[currentIndex])) {
				expression += data[currentIndex];

			} else if((data[currentIndex] == '$' && data[currentIndex+1] == '}') ||
					Character.isWhitespace(data[currentIndex])) {
				break;
				
			} else {
				throw new LexerException();
			}
		}
		
		int value = Integer.parseInt(expression);
		return new ElementConstantInteger(value);
	}
	
	
	/**
	 * Provjerava jeli posljednji izraz FOR taga varijabla.
	 * @return ime varijable
	 * @throws LexerException ako ime varijable nije valjano
	 */
	private Element lastExpressionStartsWithLetter() {
		if(!(Character.isLetter(data[currentIndex]))) {
			throw new RuntimeException();
		}
		String name = "";
		for(; currentIndex < data.length; currentIndex++) {
			if((data[currentIndex] == '$' && data[currentIndex+1] == '}') ||
					Character.isWhitespace(data[currentIndex])) {
				break;
			} else if(Character.isLetterOrDigit(data[currentIndex]) || data[currentIndex] == '_') {
				name += data[currentIndex];
			} else {
				throw new LexerException();
			}
		}
		
		return new ElementVariable(name);
	}
	
	
	/**
	 * Provjerava jeli posljednji izraz FOR taga počinje sa navodnim znakom.
	 * Ako da onda se u tim navodnim znacima mora nalaziti samo brojevi i to cijeli.
	 * @return cijeli broj
	 * @throws LexerException ako broj nije valjan
	 */
	private Element lastExpressionStartsWithQoutes() {
		Element e;
		if(data[currentIndex] == '-') {
			e = lastExpressionStartsWithMinusOrDigit(true);
		} else {
			e = lastExpressionStartsWithMinusOrDigit(false);
		}
		if(data[currentIndex] != '"') {
			throw new LexerException();
		}
		currentIndex++;
		
		return e;
	}
	
	
	/**
	 * Provjerava jeli drugi izraz FOR taga počinje sa navodnim znakom.
	 * Ako da onda se u tim navodnim znacima mora nalaziti samo brojevi i to cijeli.
	 * @return cijeli broj
	 * @throws LexerException ako broj nije valjan
	 */
	private Element stepExpressionStartsWithQoutes() {
		Element e;
		if(data[currentIndex] == '-') {
			e = stepExpressionStartsWithMinusOrDigit(true);
		} else {
			e = stepExpressionStartsWithMinusOrDigit(false);
		}
		if(data[currentIndex] != '"') {
			throw new RuntimeException();
		}
		currentIndex++;
		
		return e;
	}
	
	
	/**
	 * Provjerava jeli drugi izraz FOR taga varijabla.
	 * @return ime varijable
	 * @throws LexerException ako ime varijable nije valjano
	 */
	private Element stepExpressionStartsWithLetter() {
		if(!(Character.isLetter(data[currentIndex]))) {
			throw new LexerException();
		}
		String name = "";
		for(; currentIndex < data.length; currentIndex++) {
			if(data[currentIndex] == '-' || data[currentIndex] == '"' || 
					Character.isWhitespace(data[currentIndex]) ||
					(data[currentIndex] == '$' && data[currentIndex+1] == '}')) {
				break;
			} else if(Character.isLetterOrDigit(data[currentIndex]) || data[currentIndex] == '_') {
				name += data[currentIndex];
			} else {
				throw new LexerException();
			}
		}
		
		return new ElementVariable(name);
	}
	
	
	/**
	 * Provjera jeli drugi izraz FOR taga cijeli broj.
	 * @param minus provjerava jeli broj negativan ili pozitivan
	 * @return cijeli broj
	 * @throws LexerException ako broj nije validan
	 */
	private Element stepExpressionStartsWithMinusOrDigit(boolean minus) {
		String expression = minus ? "-" : "";
		if(!Character.isDigit(data[currentIndex])) {
			throw new LexerException();
		}
		for(; currentIndex < data.length; currentIndex++) {
			if(Character.isDigit(data[currentIndex])) {
				expression += data[currentIndex];

			} else if(data[currentIndex] == '-' || data[currentIndex] == '"' || 
					Character.isWhitespace(data[currentIndex]) ||
					Character.isLetter(data[currentIndex]) || 
					(data[currentIndex] == '$') && data[currentIndex+1] == '}') {
				break;
				
			} else {
				throw new LexerException();
			}
		}
		
		int value = Integer.parseInt(expression);
		return new ElementConstantInteger(value);

	}
	
	
	/**
	 * Provjerava jeli prvi izraz FOR taga počinje sa navodnim znakom.
	 * Ako da onda se u tim navodnim znacima mora nalaziti samo brojevi i to cijeli.
	 * @return cijeli broj
	 * @throws LexerException ako broj nije valjan
	 */
	private Element firstExpressionStartsWithQoutes() {
		Element e;
		if(data[currentIndex] == '-') {
			e = firstExpressionStartsWithMinusOrDigit(true);
		} else {
			e = firstExpressionStartsWithMinusOrDigit(false);
		}
		if(data[currentIndex] != '"') {
			throw new LexerException();
		}
		currentIndex++;
		
		return e;
	}
	
	
	/**
	 * Provjera jeli prvi izraz FOR taga cijeli broj.
	 * @param minus provjerava jeli broj negativan ili pozitivan
	 * @return cijeli broj
	 * @throws LexerException ako broj nije validan
	 */
	private Element firstExpressionStartsWithMinusOrDigit(boolean minus) {
		String expression = minus ? "-" : "";
		if(!Character.isDigit(data[currentIndex])) {
			throw new LexerException();
		}
		for(; currentIndex < data.length; currentIndex++) {
			if(Character.isDigit(data[currentIndex])) {
				expression += data[currentIndex];

			} else if(data[currentIndex] == '-' || data[currentIndex] == '"' || 
					Character.isWhitespace(data[currentIndex]) ||
					Character.isLetter(data[currentIndex])) {
				break;
				
			} else {
				throw new LexerException();
			}
		}
		
		int value = Integer.parseInt(expression);
		return new ElementConstantInteger(value);
		
	}
	
	
	/**
	 * Provjera jeli izraz u ECHO tagu broj.
	 * @para	@Test
	public void testNoActualContent() {
		Lexer lexer = new Lexer("   \r\n\t    ");
		
		assertEquals(NodeType.EOF, lexer.nextToken().getType());
	}m minus provjera za predznak broja
	 * @return niz koji se na odgovarajući način parsira u Integer ili Double
	 * @throws LexerException ako broj nije valjan
	 */
	private String readNumber(boolean minus) {
		String number = minus ? "-" : "";
		int dotCounter = 0;
		for(; currentIndex < data.length; currentIndex++) {
			if(Character.isWhitespace(data[currentIndex]) || data[currentIndex] == '-' ||
					data[currentIndex] == '+' || data[currentIndex] == '/' ||
					data[currentIndex] == '*' || data[currentIndex] == '^' ||
					(data[currentIndex] == '$' && data[currentIndex+1] == '}') ||
					data[currentIndex] == '@' || data[currentIndex] == '"' ||
					Character.isLetter(data[currentIndex])) {
						break;
			} else if(Character.isDigit(data[currentIndex])) {
				number += data[currentIndex];
			} else if(dotCounter == 0 && data[currentIndex] == '.') {
				number += '.';
			} else {
				throw new LexerException();
			}
		}
		
		return number;
		
	}
	
	
	/**
	 * Provjera jeli izraz u ECHO tagu funkcija. Funkcija je ako
	 * izraz započinje s @.
	 * @return ime funkcije
	 * @throws LexerException ako ime funkcije nije valjano
	 */
	private ElementFunction readFunction() {
		if(!Character.isLetter(data[currentIndex+1])) {
			throw new RuntimeException();
		}
		ElementFunction function;
		String functionName = "@";
		currentIndex++;
		
		for(; currentIndex < data.length; currentIndex++) {
			if(Character.isLetterOrDigit(data[currentIndex]) || data[currentIndex] == '_') {
				functionName += data[currentIndex];
			} else if(Character.isWhitespace(data[currentIndex]) || data[currentIndex] == '-' ||
					data[currentIndex] == '+' || data[currentIndex] == '/' ||
					data[currentIndex] == '*' || data[currentIndex] == '^' ||
					(data[currentIndex] == '$' && data[currentIndex+1] == '}') ||
					data[currentIndex] == '@' || data[currentIndex] == '"'){
				break;
			} else {
				throw new LexerException();
			}
		}
		
		function = new ElementFunction(functionName);
		return function;
	}
	
	
	/**
	 * Provjerava jeli izraz u ECHO tagu započinje s navodnim znakom.
	 * @return niz koji je sadržan u navodnim znacima
	 */
	private ElementString readQuotes() {
		ElementString value;
		String str = "\"";
		for(; currentIndex < data.length; currentIndex++) {
			if(data[currentIndex] == '"') {
				str += "\"";
				break;
			} else {
				str += data[currentIndex];
			}
		}
		value = new ElementString(str);
		return value;
	}
	
	/**
	 * Provjerava jeli izraz u ECHO tagu neki od dozvoljenih
	 * operatora. Dozvoljeni operatori su -, +, ^, / i *.
	 * @return operator
	 * @throws LexerException ako operator nije valjan
	 */
	private ElementOperator readOperator() {
		ElementOperator operator;
		if(data[currentIndex] == '-') {
			operator = new ElementOperator("-");
		} else if(data[currentIndex] == '+') {
			operator = new ElementOperator("+");
		} else if(data[currentIndex] == '*') {
			operator = new ElementOperator("*");
		} else if(data[currentIndex] == '/') {
			operator = new ElementOperator("/");
		} else if(data[currentIndex] == '^') {
			operator = new ElementOperator("^");
		} else {
			throw new LexerException();
		}
		
		return operator;
	}
	
	
	
	
	public void setState(LexerState state) {
		this.state = state;
	}
	
	
	/**
	 * Metoda u kojoj se Lexer nalazi u stanju ECHO, te čita
	 * tag echo i obrađuje elemente taga echo. Elementi taga echo mogu biti:
	 * Varijabla(počinje slovom i sadrži slova brojeve ili "_"),
	 * funkcija(počinje s @ i ponaša se kao varijabla),
	 * integer, double, string, te simbol(+, -, *, / ili ^).
	 * @return token u kojem se nalazi cijeli obrađeni tag echo
	 */
	private Token parseEcho() {
		EchoNode node;
		Element[] elements = new Element[20];
		int elementsIndex = 0;
		
		readAndIgnoreBlank();
		
		while(data[currentIndex] != '$' || data[currentIndex+1] != '}') {
			if(Character.isWhitespace(data[currentIndex])) {
				currentIndex++;
				continue;
			} else if(Character.isLetter(data[currentIndex])) {
				ElementVariable variable = readVariable();
				elements[elementsIndex] = variable;
				elementsIndex++;
			} else if(data[currentIndex] == '@') {
				ElementFunction function = readFunction();
				elements[elementsIndex] = function;
				elementsIndex++;
			} else if(Character.isDigit(data[currentIndex]) || (data[currentIndex] == '-' && Character.isDigit(data[currentIndex+1]))) {
				String numAsString;
				if(data[currentIndex] == '-') {
					numAsString = readNumber(true);
				} else {
					numAsString = readNumber(false);
				}
				if(numAsString.contains(".")) {
					ElementConstantDouble num = new ElementConstantDouble(Double.parseDouble(numAsString));
					elements[elementsIndex] = num;
					elementsIndex++;
				} else {
					ElementConstantInteger num = new ElementConstantInteger(Integer.parseInt(numAsString));
					elements[elementsIndex] = num;
					elementsIndex++;
				}
			} else if(data[currentIndex] == '"') {
				currentIndex++;
				ElementString string = readQuotes();
				currentIndex++;
				elements[elementsIndex] = string;
				elementsIndex++;
			} else {
				ElementOperator operator = readOperator();
				currentIndex++;
				elements[elementsIndex] = operator;
				elementsIndex++;
			}
		}
		
		currentIndex += 2;
		node = new EchoNode(elements);
		token = new Token(node, NodeType.ECHO);
		return token;
		
	}
	
	
	/**
	 * Metoda u kojoj se Lexer nalazi u stanju END, te obrađuje
	 * tag end na odgovarajući način.
	 * @return token
	 * @throws LexerException ako tag END nije pravilno zatvoren
	 */
	private Token parseEnd() {		
		readAndIgnoreBlank();
		
		if(data[currentIndex] == '$') {
			currentIndex++;
			if(data[currentIndex] == '}') {
				currentIndex++;
			} else {
				throw new LexerException();
			}
		} else {
			throw new LexerException();
		}
		
		token = new Token(null, NodeType.END);
		return token;
	}
	
	
	

	/**
	 * Metoda u kojoj se Lexer nalazi u stanju FOR, te čita
	 * tag for i obrađuje elemente. Elementi taga for mogu biti:
	 * Varijabla(počinje slovom i sadrži slova brojeve ili "_"),
	 * integer, string koji se mora moći parsirati u integer.
	 * @return token u kojem se nalazi cijeli obrađeni tag for
	 * @throws LexerException ako nakon drugog elementa nije treći element ili
	 * niz $}, te ako iza trećeg elementa ne dolazi niz $}
	 */
	private Token parseFor() {
		ForLoopNode node;
		readAndIgnoreBlank();
		
		//Čitam ime varijable
		ElementVariable variableName = readVariable();
		readAndIgnoreBlank();
		
		//Čitam prvi izraz petlje
		Element startExpression = readFirstExpression();
		readAndIgnoreBlank();
		
		//Čitam drugi izraz petlje
		Element stepExpression = readStepExpression();
		readAndIgnoreBlank();
		
		if(data[currentIndex] == '$') {
			currentIndex++;
			if(currentIndex == data.length) {
				throw new LexerException();
			}
			if(data[currentIndex] != '}') {
				throw new LexerException();
			} else {
				node = new ForLoopNode(variableName, startExpression, null, stepExpression);
				token = new Token(node, NodeType.FOR);
				return token;
			}
		}
		
		//Čitam zadnji izraz petlje
		Element lastExpression = readLastExpression();
		readAndIgnoreBlank();
		
		if(data[currentIndex] == '$') {
			currentIndex++;
		}
		
		if(currentIndex == data.length) {
			throw new LexerException();
		}
		if(data[currentIndex] != '}') {
			throw new LexerException();
		}
		
		currentIndex++;
		node = new ForLoopNode(variableName, startExpression, lastExpression, stepExpression);
		token = new Token(node, NodeType.FOR);
		return token;
	}
	
	
	/**
	 * Metoda koja prije tokeniziranja teksta provjerava
	 * treba li mijenjati stanje Lexera.
	 * @return obrađeni token metode readText
	 */
	private Token parseText() {
		if(data[currentIndex] == '{') {
			if(data[currentIndex+1] != '$') {
				token = readText();
				return token;
			} else {
				checkTagAndSetState();
			}
		} else {
			token = readText();
			return token;
		}
		return null;
	}
	
	
	/**
	 * Metoda u kojoj je Lexer u stanje TEXT, te obrađuje tekst.
	 * Svaki niz se smatra tekstom osim nailaska na {$ kada se Lexer void u
	 * drugo stanje.
	 * @return token u kojem se nalazi obrađeni tekst
	 * @throws LexerException ako tekst nije valjan
	 */
	private Token readText() {
		Token t;
		TextNode node;
		String word = "";
		
				
		for(; currentIndex < data.length; currentIndex++) {
			if(data[currentIndex] == '{') {
				if(data[currentIndex+1] != '$') {
					word += data[currentIndex];
				} else {
					node = new TextNode(word);
					t = new Token(node, NodeType.TEXT);
					return t;
				}
			} else if(data[currentIndex] == '\\') {
				if(data[currentIndex+1] == '\\') {
					continue;
				}
				if(data[currentIndex+1] == '{') {
					word += '{';
					currentIndex++;
				} else if(Character.isWhitespace(data[currentIndex+1])) {
					word += data[currentIndex];
				} else {
					throw new LexerException();
				}
			} else {
				word += data[currentIndex];
			}
		}
		
		node = new TextNode(word);
		t = new Token(node, NodeType.TEXT);
		return t;
	}
	
	
	/**
	 * Provjerava trenutni tag i ako utvrdi da je tag postojeći
	 * postavlja stanje leksera u stanje za obradu navedenog taga.
	 * @throws LexerException ako tag ne postoji
	 */
	private void checkTagAndSetState() {
		for(; currentIndex < data.length; currentIndex++) {
			if(data[currentIndex] == '{') {
				currentIndex++;
				if(data[currentIndex] == '$') {
					continue;
				}
			} else if(data[currentIndex] == '=') {
				currentIndex++;
				setState(LexerState.ECHO);
				return;
			} else if(Character.isWhitespace(data[currentIndex])) {
				continue;
			} else if(Character.toUpperCase(data[currentIndex]) == 'F') {
				if(Character.toUpperCase(data[currentIndex+1]) == 'O' &&
						Character.toUpperCase(data[currentIndex+2]) == 'R') {
					currentIndex += 3;
					setState(LexerState.FOR);
					return;
				} else {
					throw new LexerException();
				}
			} else if(Character.toUpperCase(data[currentIndex]) == 'E') {
				if(Character.toUpperCase(data[currentIndex+1]) == 'N' &&
						Character.toUpperCase(data[currentIndex+2]) == 'D') {
					currentIndex += 3;
					setState(LexerState.END);
					return;
				} else {
					throw new LexerException();
				}
				
			} else {
				throw new LexerException();
			}
		}
	}
	
	
	
	
	
	
	
	
}
