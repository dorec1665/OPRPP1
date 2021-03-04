package hr.fer.zemris.java.hw05.shell.commands;

import java.io.BufferedInputStream;
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
 * Naredba hexdump stvara heksadecimalni ispis datoteke.
 * @author dario
 *
 */
public class HexDumpShellCommand implements ShellCommand{
	
	
	private List<String> description;
	
	
	/**
	 * Konstruktor sa kojim se stvara opis naredbe.
	 */
	public HexDumpShellCommand() {
		description = new ArrayList<>();
		description.add("Hexdump command takes one argument and it must be a file.");
		description.add("This command produces hex-output.");
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
			env.writeln("Invalid number of arguments. Command hexdump takes 1 argument.");
			return ShellStatus.CONTINUE;
		}
		
		File f = new File(splitArguments[0]);
		if(f.exists() && f.isDirectory()) {
			env.writeln("Command hexdump must take file as argument.");
			return ShellStatus.CONTINUE;
		}
		
		if(f.exists() && f.isFile()) {
			int line = 0;
			try {
				BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(Paths.get(f.getPath())));
				byte[] arr = new byte[16];
				while(true) {
					int readReturn = bis.read(arr);
					if(readReturn == -1) {
						break;
					}
					String hexLine = Integer.toHexString(line);
					env.write("0".repeat(8-hexLine.length()) + hexLine + ": ");
					String original = "";
					for(int i = 0; i < readReturn; i++) {
						int decimal = 0;
						if(i == 7) {
							decimal = (arr[i] & 0xFF);
							env.write(decimalToHex(decimal).toUpperCase() + "|");
						} else {
							decimal = (arr[i] & 0xFF);
							env.write(decimalToHex(decimal).toUpperCase() + " ");
						}
						if(decimal < 32 || decimal > 127) {
							original += ".";
						} else {
							original += Character.toString((char) arr[i]);
						}
					}
					line += 16;
					if(readReturn < 16) {
						env.write(" ".repeat((16-readReturn)*3));
					}
					env.write("| ");
					env.writeln(original);
					
				}
				bis.close();
			} catch (IOException e) {
				env.writeln("An error occured.");
				return ShellStatus.CONTINUE;
			}
		} else {
			env.writeln("Could not find the file.");
		}
		
		return ShellStatus.CONTINUE;
	}
	
	
	
	private String decimalToHex(int decimal) {
		StringBuilder rez = new StringBuilder();
		while(true) {
			if(decimal % 16 >= 10) {
				rez.append((char) ((decimal % 16) - 10 + 'a'));
			} else if (decimal % 16 < 10) {
				rez.append((char) ((decimal % 16) + '0'));
			}
			decimal = decimal / 16;
			if(decimal == 0) {
				break;
			}
		}
		if(rez.length() == 1) {
			rez.append("0");
		}
		return rez.reverse().toString();
	}
	
	
	@Override
	public String getCommandName() {
		return "hexdump";
	}
	
	
	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
