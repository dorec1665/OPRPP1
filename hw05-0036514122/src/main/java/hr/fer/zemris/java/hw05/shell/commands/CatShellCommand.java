package hr.fer.zemris.java.hw05.shell.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Enviroment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * Naredba cat otvara datoteku, čita i ispisuje njen sadržaj na konzolu.
 * @author dario
 *
 */
public class CatShellCommand implements ShellCommand{
	
	private List<String> description;
	
	/**
	 * Konstruktor sa kojim se stvara opis naredbe.
	 */
	public CatShellCommand() {
		description = new ArrayList<>();
		description.add("Cat command opens given file and writes its content to console.");
		description.add("This command takes 1 or 2 arguments. The first argument is path to file and is mandatory");
		description.add("The second argument is charset name that should be used to interpret chars from bytes.");
		description.add("If second argument is not provided, default one is used.");
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
		
		Charset c = null;
		if(splitArgumets.length == 1) {
			c = StandardCharsets.UTF_8;
		} else if(splitArgumets.length == 2) {
			if(!Charset.isSupported(splitArgumets[1])) {
				env.writeln("Charset not supported.");
				return ShellStatus.CONTINUE;
			}
			c = Charset.forName(splitArgumets[1]);
		} else {
			env.writeln("Invalid number of arguments. Command cat takes 1 or 2 arguments.");
			return ShellStatus.CONTINUE;
		}
		
		File f = new File(splitArgumets[0]);
		if(f.exists() && f.isFile()) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(splitArgumets[0])), c));
				String line;
				while((line = br.readLine()) != null) {
					env.writeln(line);
				}
				
			} catch (IOException e) {
				env.writeln("Error occured. Could not read file.");
				return ShellStatus.CONTINUE;
			}
		} else if(f.exists() && f.isDirectory()) {
			env.writeln(splitArgumets[0] + " is directory.");
		} else {
			env.writeln("File does not exists.");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
