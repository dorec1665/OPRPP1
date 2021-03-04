package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Enviroment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;


/**
 * Naredba exit terminira rad MyShell programa.
 * @author dario
 *
 */
public class ExitShellCommand implements ShellCommand{

	private List<String> description;
	
	
	/**
	 * Konstruktor sa kojim se stvara opis naredbe.
	 */
	public ExitShellCommand() {
		description = new ArrayList<>();
		description.add("Command exit takes no arguments and it terminates shell program.");
		description = Collections.unmodifiableList(description);
	}
	
	@Override
	public ShellStatus executeCommand(Enviroment env, String arguments) {
		if(arguments.length() > 0) {
			env.writeln("Command exit takes no arguments");
			return ShellStatus.CONTINUE;
		}
		env.writeln("Goodbye!");
		return ShellStatus.TERMINATE;
	}
	
	@Override
	public List<String> getCommandDescription() {
		return description;
	}
	
	@Override
	public String getCommandName() {
		return "exit";
	}
}
