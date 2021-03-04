package hr.fer.zemris.java.hw05.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw05.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.HexDumpShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.TreeShellCommand;

/**
 * Predstavlja pojednostavljeni command-line program. Naredbe koje podržava su:
 * exit, help, symbol, charsets, cat, ls, tree, copy, mkdir, hexdump.
 * @author dario
 *
 */
public class MyShell {

	private static Scanner sc = new Scanner(System.in);
	private static SortedMap<String, ShellCommand> commands;
	
	public static void main(String[] args) {
				
		commands = fillMap();
		
		Enviroment env = new EnviromentImpl();
		env.writeln("Welcome to MyShell v 1.0");
		
		ShellStatus status = ShellStatus.CONTINUE;
		ShellCommand command;
		
		while(status != ShellStatus.TERMINATE) {
			env.write(env.getPromptSymbol() + " ");
			String l = readLineOrLines(env);
			String commandName = extractCommandName(l);
			String arguments = extractArguments(l, commandName);
			command = commands.get(commandName);
			if(command == null) {
				env.writeln("Command does not exists! Try writing help to check available commands.");
				continue;
			}
			status = command.executeCommand(env, arguments);
		}
		
		sc.close();
		
	}

	
	
	/**
	 * Izvlači argumente za unesenu naredbu(ako postoje) iz korisnikovog unosa.
	 * @param l Korisnikov unos
	 * @param commandName Unesena naredba
	 * @return argumente naredbe(ako postoje)
	 */
	private static String extractArguments(String l, String commandName) {
		if(l.equals(commandName)) {
			return "";
		}
		int firstApp = l.indexOf(" ");
		return l.substring(firstApp).trim();
	}

	
	
	/**
	 * Izvači ime naredbe iz korisnikova unosa.
	 * @param l Korisnikov unos
	 * @return Ime nareedbe
	 */
	private static String extractCommandName(String l) {
		int firstApp = l.indexOf(" ");
		
		return firstApp < 0 ? l.trim() : l.substring(0, firstApp).trim();
	}

	
	
	/**
	 * Čita jednu ili više liniju korisnikova unosa.
	 * @param env Parametar pomoću koje se čita unos
	 * @return Cijeli korisnikov unos
	 */
	private static String readLineOrLines(Enviroment env) {
		String rez = "";
		while(true) {
			rez += env.readLine();
			if(rez.isEmpty()) {
				break;
			}
			if(rez.lastIndexOf(env.getMorelinesSymbol()) == rez.length()-1 && rez.charAt(rez.length()-2) == ' ') {
				rez = rez.substring(0, rez.lastIndexOf(env.getMorelinesSymbol()));
				env.write(env.getMultilineSymbol() + " ");
				continue;
			} else {
				break;
			}
		}
		return rez;
	}

	
	
	/**
	 * Puni mapu sa svim podržanim naredbama.
	 * @return Napunjena mapa.
	 */
	private static SortedMap<String, ShellCommand> fillMap() {
		SortedMap<String, ShellCommand> map = new TreeMap<>();
		map.put("exit", new ExitShellCommand());
		map.put("help", new HelpShellCommand());
		map.put("symbol", new SymbolShellCommand());
		map.put("charsets", new CharsetsShellCommand());
		map.put("cat", new CatShellCommand());
		map.put("ls", new LsShellCommand());
		map.put("tree", new TreeShellCommand());
		map.put("copy", new CopyShellCommand());
		map.put("mkdir", new MkdirShellCommand());
		map.put("hexdump", new HexDumpShellCommand());
		map = Collections.unmodifiableSortedMap(map);
		
		return map;
	}
	
	
	
	/**
	 * Implementacija sučelja {@link Enviroment}.
	 * @author dario
	 *
	 */
	private static class EnviromentImpl implements Enviroment {
		
		// defaultne postavke za PROMPT, MULTILINES i MORELINES simbole
		private char prompt = '>';
		private char morelines = '\\';
		private char multiline = '|';

		@Override
		public String readLine() throws ShellIOException {
			String s;
			try {
				s = sc.nextLine();
			} catch (Exception e) {
				throw new ShellIOException("Došlo je do greške.");
			}
			
			return s;
		}

		@Override
		public void write(String text) throws ShellIOException {
			System.out.print(text);
			
		}

		@Override
		public void writeln(String text) throws ShellIOException {
			System.out.println(text);
		}

		@Override
		public SortedMap<String, ShellCommand> commands() {
			return commands;
		}

		@Override
		public Character getMultilineSymbol() {
			return multiline;
		}

		@Override
		public void setMultilineSymbol(Character symbol) {
			multiline = symbol;
			
		}

		@Override
		public Character getPromptSymbol() {
			return prompt;
		}

		@Override
		public void setPromptSymbol(Character symbol) {
			prompt = symbol;
			
		}

		@Override
		public Character getMorelinesSymbol() {
			return morelines;
		}

		@Override
		public void setMorelinesSymbol(Character symbol) {
			morelines = symbol;
		}
		
	}
	
	
	
}
