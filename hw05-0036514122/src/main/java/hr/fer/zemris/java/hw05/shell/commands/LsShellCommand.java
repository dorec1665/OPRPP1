package hr.fer.zemris.java.hw05.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Enviroment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;


/**
 * Naredba ls ispisuje svu djecu direktorija.
 * @author dario
 *
 */
public class LsShellCommand implements ShellCommand{

	
	private List<String> description;
	
	
	/**
	 * Konstruktor sa kojim se stvara opis naredbe.
	 */
	public LsShellCommand() {
		description = new ArrayList<>();
		description.add("Ls command takes one argument and it must be a directory.");
		description.add("This command lists all children of given directory.");
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
			env.writeln("Invali number of arguments. Command ls takes 1 argument.");
			return ShellStatus.CONTINUE;
		}
		
		File f = new File(splitArguments[0]);
		if(f.exists() && f.isFile()) {
			env.writeln("Cannot list children of a file.");
			return ShellStatus.CONTINUE;
		}
		
		if(f.exists() && f.isDirectory()) {
			String[] children = f.list();
			if(children == null) {
				env.writeln("Path does not denote a directory.");
				return ShellStatus.CONTINUE;
			}
			
			String output;;
			for(String s : children) {
				output = "";
				File child = splitArguments[0].lastIndexOf("/") == splitArguments[0].length()-1 ? new File(splitArguments[0] + s) 
																: new File(splitArguments[0] + "/" + s); 
				
				output += child.isDirectory() ? "d" : "-";
				output += child.canRead() ? "r" : "-";
				output += child.canWrite() ? "w" : "-";
				output += child.canExecute() ? "x " : "- ";
				
				Long size = child.length();
				output += " ".repeat(10-Long.toString(size).length());
				output += Long.toString(size);
				output += " ";
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Path path = Paths.get(child.getPath());
				BasicFileAttributeView faView = Files.getFileAttributeView(
						path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
				BasicFileAttributes attributes = null;
				try {
					attributes = faView.readAttributes();
				} catch (IOException e) {
					e.printStackTrace();
				}
				FileTime fileTime = attributes.creationTime();
				String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
				output += formattedDateTime;
				output += " ";
				
				output += child.getName();
				env.writeln(output);
			}
		} else {
			env.writeln("No such directory.");
		}
		
		return ShellStatus.CONTINUE;
		
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
