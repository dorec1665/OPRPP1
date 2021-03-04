package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Enviroment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * Naredba help ispisuje sve podržane naredbe u programu MyShell.
 * Također se može koristiti da detaljnije opiše bilo koju naredbu(uključujući i sebe).
 * @author dario
 *
 */
public class HelpShellCommand implements ShellCommand{

	private List<String> description;
	
	
	/**
	 * Konstruktor sa kojim se stvara opis naredbe.
	 */
	public HelpShellCommand() {
		description = new ArrayList<>();
		description.add("Help command takes one or no argument.");
		description.add("If this command is started with no arguments, it lists all available commands.");
		description.add("if this command is started with sinlge argument, it prints name and description of argument which represents one command.");
		description = Collections.unmodifiableList(description);
	}
	
	
	@Override
	public ShellStatus executeCommand(Enviroment env, String arguments) {
		if(arguments.length() > 0) {
			if(env.commands().get(arguments) == null) {
				env.writeln("Argument is not any known shell command. Try writing just help to see list of available commands.");
				return ShellStatus.CONTINUE;
			}
			env.writeln(env.commands().get(arguments).getCommandName());
			env.commands().get(arguments).getCommandDescription().forEach(env::writeln);
		} else {
			for(String s : env.commands().keySet()) {
				env.writeln(s);
			}
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
