package hr.fer.zemris.java.hw05.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Enviroment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;


/**
 * Naredba copy kopira sadržaj jedne datoteke u drugu datoteku ili direktorij.
 * @author dario
 *
 */
public class CopyShellCommand implements ShellCommand{
	
	private List<String> description;
	
	
	/**
	 * Konstruktor sa kojim se stvara opis naredbe.
	 */
	public CopyShellCommand() {
		description = new ArrayList<>();
		description.add("Copy command takes two arguments. First argument must be a file.");
		description.add("Second argument can be file or directory. If it is a file, content of first argument(file) will be copied to second file.");
		description.add("If it is directory, new file will be created in that directory containing content of first file.");
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
		
		if(splitArguments.length != 2) {
			env.writeln("Invalid number of arguments. Command copy takes 2 arguments.");
			return ShellStatus.CONTINUE;
		}
		
		File f1 = new File(splitArguments[0]);
		File f2 = new File(splitArguments[1]);
		if(f1.exists() && f1.isDirectory()) {
			env.writeln("Cannot copy directory!");
			return ShellStatus.CONTINUE;
		}
		
		if(f1.exists() && f1.isFile() && f2.exists()) {
			boolean copy = false;
				try {
					Path newFile = null;
					if(f2.isFile()) {
						env.write("Are you seure you want to overwrite " + f2.getName() + "? [Y/n]: ");
						String decide = env.readLine();
						if(decide.equalsIgnoreCase("y") || decide.equalsIgnoreCase("yes")) {
							Files.delete(Paths.get(f2.getPath()));
							newFile = Files.createFile(Paths.get(f2.getPath()));
							copy = true;
						} else if(decide.equalsIgnoreCase("n") || decide.equalsIgnoreCase("no")) {
							env.writeln("Copying stopped.");
							copy = false;
						} else {
							env.writeln("Unsupported argument!");
							copy = false;
						}
					} else if(f2.isDirectory()) {
						newFile = Files.createFile(Paths.get(f2.getPath() + "/" + f1.getName()));
						copy = true;
					}
					if(copy) {
						BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(Paths.get(f1.getPath())));
						BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(newFile, StandardOpenOption.WRITE));
						byte[] arr = new byte[4096];
						
						while(true) {
							int readReturn = bis.read(arr);
							if(readReturn == -1) {
								break;
							}
							bos.write(arr, 0, readReturn);
						}
						bis.close();
						bos.close();
						env.writeln("Copying done.");
					}
				} catch (Exception e) {
					env.writeln("An error occured!");
					env.writeln("Maybe " + "\"" + f2.getName() + "\"" + " directory already has file named " + "\"" + f1.getName() + "\".");
					return ShellStatus.CONTINUE;
				}
		} else {
			env.writeln("Could not execute copy command.");
		}
		
		return ShellStatus.CONTINUE;
		
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
