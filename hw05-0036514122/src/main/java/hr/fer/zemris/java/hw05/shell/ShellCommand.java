package hr.fer.zemris.java.hw05.shell;

import java.util.List;

public interface ShellCommand {

	
	
	/**
	 * Izvršava naredbu.
	 * @param env Parametar pomoću kojeg se ispisuje korisniku rezultat naredbe
	 * @param arguments Argumenti naredbe(ako postoje)
	 * @return status koji govori trebali program nastaviti rad
	 */
	ShellStatus executeCommand(Enviroment env, String arguments);
	
	
	
	/**
	 * Dohvaća ime naredbe.
	 * @return Ime naredbe
	 */
	String getCommandName();
	
	
	
	/**
	 * Dohvaća opis naredbe.
	 * @return Opis naredbe
	 */
	List<String> getCommandDescription();
}
