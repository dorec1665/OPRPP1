package hr.fer.zemris.java.hw05.shell.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Enviroment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;


/**
 * Naredba tree ispisuje stablo zadanog direktorija.
 * @author dario
 *
 */
public class TreeShellCommand implements ShellCommand{

	
	private List<String> description;
	
	
	/**
	 * Konstruktor sa kojim se stvara opis naredbe.
	 */
	public TreeShellCommand() {
		description = new ArrayList<>();
		description.add("Tree command takes one argument and it must be a directory.");
		description.add("This command prints a tree.");
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
			env.writeln("Invalid number of arguments. Command tree takes 1 argument.");
			return ShellStatus.CONTINUE;
		}
		
		File f = new File(splitArguments[0]);
		if(f.exists() && f.isFile()) {
			env.writeln("Cannot print tree of a file.");
			return ShellStatus.CONTINUE;
		}
		
		if(f.exists() && f.isDirectory()) {
			try {
				printTree(0, f, env);
			} catch(NullPointerException e) {
				return ShellStatus.CONTINUE;
			}
		} else {
			env.writeln("Could not print tree of given directory.");
		}
		
		return ShellStatus.CONTINUE;
	}

	private void printTree(int n, File f, Enviroment env) {
		env.write(" ".repeat(n));
		env.writeln(f.getName());
		if(f.isDirectory()) {
			File[] files = f.listFiles();
			if(files == null) {
				env.writeln("An error occured while reading " + f.getName());
				throw new NullPointerException();
			}
			for(int i = 0; i < files.length; i++) {
				printTree(n+2, files[i], env);
			}
		}
		
	}


	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
