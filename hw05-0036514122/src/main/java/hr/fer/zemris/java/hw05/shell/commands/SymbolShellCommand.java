package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Enviroment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;


/**
 * Pomoću naredbe symbol moguće je promjeniti zadane(izvorne) simbole PROMPT,
 * MORELINES ili MULTILINE. Također je moguće uvidjeti koji su trenutno simboli
 * zapisani u PROMPT, MORELINES ili MULTILINE.
 * @author dario
 *
 */
public class SymbolShellCommand implements ShellCommand{

	private List<String> description;
	
	
	/**
	 * Konstruktor sa kojim se stvara opis naredbe.
	 */
	public SymbolShellCommand() {
		description = new ArrayList<>();
		description.add("Command symbol changes default symbols PROMPT(>), MULTILINE(|) and MORELINES(|)");
		description.add("It takes 1 or 2 arguments.");
		description.add("When it takes one argument, that argument can be PROMPT, MULTILINE or MORELINES, it writes adequate symbol for given argument.");
		description.add("When it takes two arguments, first argument can be PROMPT, MULTILINE or MORELINES, while second argument can be"
				+ "symbol that will chage default symbol for first argument.");
		description = Collections.unmodifiableList(description);
	}
	
	@Override
	public ShellStatus executeCommand(Enviroment env, String arguments) {
		
		String[] splitArgumets = null;
		splitArgumets = Splitter.split(arguments);
		if(splitArgumets == null) {
			env.writeln("Did you close quotes?");
			return ShellStatus.CONTINUE;
		}
		
		if(splitArgumets.length == 1) {
			if(splitArgumets[0].equals("PROMPT")) {
				env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
			} else if(splitArgumets[0].equals("MULTILINE")) {
				env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'");
			} else if(splitArgumets[0].equals("MORELINES")) {
				env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
			} else {
				env.writeln("Incorrect argument. Try writing help symbol to see correct arguments for symbol command.");
			} 
		} else if(splitArgumets.length == 2) {
			if(splitArgumets[0].equals("PROMPT") && splitArgumets[1].length() == 1) {
				char tmp = env.getPromptSymbol();
				env.setPromptSymbol(splitArgumets[1].charAt(0));
				env.writeln("Symbol for PROMPT changed from '" + tmp + "' to '" + env.getPromptSymbol() + "'");
			} else if(splitArgumets[0].equals("MULTILINE") && splitArgumets[1].length() == 1) {
				char tmp = env.getMultilineSymbol();
				env.setMultilineSymbol(splitArgumets[1].charAt(0));
				env.writeln("Symbol for MULTILINE changed from '" + tmp + "' to '" + env.getMultilineSymbol() + "'");
			} else if(splitArgumets[0].equals("MORELINES") && splitArgumets[1].length() == 1) {
				char tmp = env.getMorelinesSymbol();
				env.setMorelinesSymbol(splitArgumets[1].charAt(0));
				env.writeln("Symbol for MORELINES changed from '" + tmp + "' to '" + env.getMorelinesSymbol() + "'");
			} else {
				env.writeln("Incorrect arguments. Try writing help symbol to see correct arguments for symbol command.");
			}
		} else {
			env.writeln("Invalid number of arguments. Try writing help symbol to see correct arguments for symbol command.");
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

	
}
