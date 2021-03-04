package hr.fer.zemris.java.hw05.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Enviroment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;


/**
 * Naredba mkdir stara adekvatnu strukturu direktorija.
 * @author dario
 *
 */
public class MkdirShellCommand implements ShellCommand{

	private List<String> description;
	
	
	/**
	 * Konstruktor sa kojim se stvara opis naredbe.
	 */
	public MkdirShellCommand() {
		description = new ArrayList<>();
		description.add("Mkdir command takes one argument and it must be a directory.");
		description.add("This command creates appropiate directory structure.");
		description = Collections.unmodifiableList(description);
	}
	
	@Override
	public ShellStatus executeCommand(Enviroment env, String arguments) {
		
		String[] splitArguments = null;
		splitArguments = Splitter.split(arguments);
		if(splitArguments == null) {
			env.writeln("Did you close quotes?");
			return ShellStatus.CONTINUE;
		}
		
		if(splitArguments.length != 1) {
			env.writeln("Invalid number of arguments. Command mkdir takes 1 argument.");
			return ShellStatus.CONTINUE;
		}
		
		File f = new File(splitArguments[0]);
		
		try {
			Files.createDirectories(Paths.get(f.getPath()));
		} catch (IOException e) {
			env.writeln("Could not create directory/directories.");
			return ShellStatus.CONTINUE;
		}
		
		env.writeln("Directory structure created.");
		return ShellStatus.CONTINUE;
		
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
