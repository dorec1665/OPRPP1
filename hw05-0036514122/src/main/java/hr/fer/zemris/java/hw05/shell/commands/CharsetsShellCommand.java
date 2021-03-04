package hr.fer.zemris.java.hw05.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw05.shell.Enviroment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;


/**
 * Naredba charsets ispisuje imena svih podržanih charsetova.
 * @author dario
 *
 */
public class CharsetsShellCommand implements ShellCommand{
	
	private List<String> description;
	
	
	/**
	 * Konstruktor sa kojim se stvara opis naredbe.
	 */
	public CharsetsShellCommand() {
		description = new ArrayList<>();
		description.add("Command charsets takes no arguments and lists names of supported charsets for your Java platform.");
		description.add("A single charset name is written per line.");
		description = Collections.unmodifiableList(description);
	}

	@Override
	public ShellStatus executeCommand(Enviroment env, String arguments) {
		if(arguments.length() > 0) {
			env.writeln("Charsets command takes no arguments.");
		} else {
			Map<String, Charset> charsets = Charset.availableCharsets();
			Iterator<Charset> iterator = charsets.values().iterator();
			while(iterator.hasNext()) {
				env.writeln(iterator.next().displayName());
			}
		}
		
		return ShellStatus.CONTINUE;
		
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
