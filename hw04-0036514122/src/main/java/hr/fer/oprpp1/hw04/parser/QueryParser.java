package hr.fer.oprpp1.hw04.parser;

import java.util.ArrayList;
import java.util.List;

import hr.fer.oprpp1.hw04.db.ComparisonOperators;
import hr.fer.oprpp1.hw04.db.ConditionalExpression;
import hr.fer.oprpp1.hw04.db.FieldValueGetter;
import hr.fer.oprpp1.hw04.db.IComparisonOperator;
import hr.fer.oprpp1.hw04.db.IFieldValueGetter;
import hr.fer.oprpp1.hw04.lexer.Lexer;
import hr.fer.oprpp1.hw04.lexer.Token;
import hr.fer.oprpp1.hw04.lexer.TokenType;

/**
 * Razred koji obavlja parsiranje jednog jednostavnog SQL upita.
 * @author dario
 *
 */
public class QueryParser {

	private Lexer lexer;
	private List<ConditionalExpression> query;
	private String jmbag;
	private String text;
	private Token t;
	
	public QueryParser(String text) {
		if(text == null) {
			throw new NullPointerException();
		}
		
		this.text = text;
		lexer = new Lexer(text);
		query = new ArrayList<>();
	}
	
	
	/**
	 * Provjerava jeli upit direkt upit. Upit je direktan ako je tipa
	 * jmbag="xxx".
	 * @return true ako je upit direktan, inače false.
	 */
	public boolean isDirectQuery() {
		Lexer l = new Lexer(text);
		Token t = l.nextToken();
		if(t.getType().equals(TokenType.ATTRIBUTE) && t.getValue().equals("jmbag")) {
			t = l.nextToken();
			if(t.getType().equals(TokenType.OPERATOR) && t.getValue().equals("=")) {
				t = l.nextToken();
				if(t.getType().equals(TokenType.LITERAL)) {
					jmbag = (String) t.getValue();
					t = l.nextToken();
					if(t.getType().equals(TokenType.EOF)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
	/**
	 * Dohvaća jmbag iz upita samo ako je upit direktan.
	 * @return jmbag
	 * @throws IllegalStateException ako upit nije direktan, a pozove se ova metoda.
	 */
	public String getQueriedJMBAG() {
		if(isDirectQuery()) {
			return jmbag;
		} else {
			throw new IllegalStateException("Upit nije direktan!");
		}
	}
	
	
	/**
	 * Dovaća sve uvjete jednog upita.
	 * @return Listu svih uvjeta
	 * @throws ParserException ako ime atributa nije valjano ili
	 * ako nakon logičkog oepratora and nema uvjeta.
	 */
	public List<ConditionalExpression> getQuery() {
		ConditionalExpression expr;
		t = lexer.nextToken();
		while(!t.getType().equals(TokenType.EOF)) {

			if(validateAttributeName()) {
				expr = getExpression();
				query.add(expr);
			} else {
				throw new ParserException("Ime atributa nije valjano!");
			}
			
			if(t.getValue() != null && t.getValue().equals("and")) {
				t = lexer.nextToken();
				if(t.getType().equals(TokenType.EOF)){
					throw new ParserException("Nakon operatora AND mora postojati novi uvjet!");
				}
			}
		}
		return query;
	}
	
	
	/**
	 * Obrađiva jedan po jedan uvjet upita.
	 * @return novoobrađeni uvjet upita
	 * @throws ParserException ako nakon atributa ne dolazi operator ili
	 * ako nakon operatora ne dolazi literal.
	 */
	private ConditionalExpression getExpression() {
		String attribute, operator, literal;

		attribute = (String) t.getValue();
		t = lexer.nextToken();
		if(t.getType().equals(TokenType.OPERATOR)) {
			operator = (String) t.getValue();
			t = lexer.nextToken();
			if(t.getType().equals(TokenType.LITERAL)) {
				literal = (String) t.getValue();
				t = lexer.nextToken();
			} else {
				throw new ParserException("Nakon operatora mora biti literal!");
			}
		} else {
			throw new ParserException("Nakon atributa mora biti operator!");
		}
		
		IFieldValueGetter valueGetter = toFieldValueGetter(attribute);
		IComparisonOperator comparisonOperator = toComparisonOperator(operator);
		ConditionalExpression expr = new ConditionalExpression(valueGetter, literal, comparisonOperator);
		return expr;
		
	}
	
	
	/**
	 * Provjerava ispravnost imena atributa.
	 * @return true ako je ime ispravno, false inače.
	 */
	private boolean validateAttributeName() {
		if(t.getType().equals(TokenType.ATTRIBUTE)) {
			if(t.getValue().equals("firstName") ||
			   t.getValue().equals("lastName")  ||
			   t.getValue().equals("jmbag")) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Pretvara ime atributa u njegovo stavrnu vrijednost za
	 * konkretnog studenta.
	 * @param attribute ime atributa
	 * @return stvarnu vrijednost atributa
	 */
	private IFieldValueGetter toFieldValueGetter(String attribute) {
		if(attribute.equals("firstName")) {
			return FieldValueGetter.FIRST_NAME;
		} else if(attribute.equals("lastName")) {
			return FieldValueGetter.LAST_NAME;
		} else {
			return FieldValueGetter.JMBAG;
		}
	}
	
	
	/**
	 * Pretvara znak operatora usporedbe u njegovu stvarnu funkcionalnost.
	 * @param operator znak operatora usporedbe
	 * @return funkcionalnost operatora
	 */
	private IComparisonOperator toComparisonOperator(String operator) {
		if(operator.equals("<")) {
			return ComparisonOperators.LESS;
		} else if(operator.equals("<=")) {
			return ComparisonOperators.LESS_OR_EQUALS;
		} else if(operator.equals(">")) {
			return ComparisonOperators.GREATER;
		} else if(operator.equals(">=")) {
			return ComparisonOperators.GREATER_OR_EQUALS;
		} else if(operator.equals("=")) {
			return ComparisonOperators.EQUALS;
		} else if(operator.equals("!=")) {
			return ComparisonOperators.NOT_EQUALS;
		} else {
			return ComparisonOperators.LIKE;
		}
	}
	
	
}
