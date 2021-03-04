package hr.fer.zemris.java.hw05.shell;

import java.util.SortedMap;

/**
 * Predstavlja sučelje koje komunicira sa naredbama shella.
 * @author dario
 *
 */
public interface Enviroment {

	/**
	 * Čita korisnikov upis u naredbeni redak.
	 * @return Upisani korisnikov niz znakova
	 * @throws ShellIOException
	 */
	String readLine() throws ShellIOException;
	
	
	
	/**
	 * Ispisuje na tekst na konzolu.
	 * @param text Tekst koji se ispisuje
	 * @throws ShellIOException
	 */
	void write(String text) throws ShellIOException;
	
	
	
	/**
	 * Kao i metoda write ispisuje tekst na konzolu, te se prebacuje
	 * u novi redak.
	 * @param text Tekst koji se ispisuje
	 * @throws ShellIOException
	 */
	void writeln(String text) throws ShellIOException;
	
	
	
	/**
	 * Vraća sortiranu mapu naredbi koju nije moguće modificirat.
	 * @return Sortiranu mapu naredbi
	 */
	SortedMap<String, ShellCommand> commands();
	
	
	
	/**
	 * Dohvaća MULTILINE simbol.
	 * @return MULTILINE simbol
	 */
	Character getMultilineSymbol();
	
	
	
	/**
	 * Postavlja MULTILINE simbol na novu vrijednost.
	 * @param symbol Nova vrijednost na koju se MULTILINE simbol postavlja
	 */
	void setMultilineSymbol(Character symbol);
	
	
	
	/**
	 * Dohvaća PROMPT simbol.
	 * @return PROMPT simbol
	 */
	Character getPromptSymbol();
	
	
	
	/**
	 * Postavlja PROMPT simbol na novu vrijednost.
	 * @param symbol Nova vrijednost na koju se PROMPT simbol postavlja
	 */
	void setPromptSymbol(Character symbol);
	
	
	
	/**
	 * Dohvaća MORELINES simbol.
	 * @return MORELINES simbol
	 */
	Character getMorelinesSymbol();
	
	
	
	/**
	 * Postavlja MORELINES simbol na novu vrijednost.
	 * @param symbol Nova vrijednost na koju se MORELINES simbol postavlja
	 */
	void setMorelinesSymbol(Character symbol);
}
