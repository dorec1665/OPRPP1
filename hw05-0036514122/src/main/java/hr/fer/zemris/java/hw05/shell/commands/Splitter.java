package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.List;


/**
 * Klasa koja služi za razdvajanje argumenata prije nego se
 * pošalju pojedinoj naredbi programa MyShell.
 * @author dario
 *
 */
public class Splitter {

	/**
	 * Metoda koja obavlja razdvajanje argumenata.
	 * @param arguments Argumenti zadani kao jedan niz(String)
	 * @return String[] čiji su članovi razdvojeni argumenti.
	 */
	public static String[] split(String arguments) {
		arguments = arguments.trim();
		char[] arr = arguments.toCharArray();
		List<String> rez = new ArrayList<>();
		String tmp = "";
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] == '"') {
				i++;
				if(i == arr.length) {
					return null;
				}
				while(arr[i] != '"') {
					if(arr[i] == '\\') {
						if(arr[i+1] == '"' || arr[i+1] == '\\') {
							tmp += arr[i+1];
							i += 2;
							continue;
						}
					}
					tmp += arr[i];
					i++;
					if(i == arr.length) {
						return null;
					}
				}
				rez.add(tmp);
				tmp = "";
				continue;
			}
			
			if(Character.isWhitespace(arr[i])) {
				if(!tmp.isEmpty()) {
					rez.add(tmp);
					tmp = "";
				}
			} else {
				tmp += arr[i];
			}
		}
		if(!tmp.isEmpty()) {
			rez.add(tmp);
		}
		return rez.toArray(new String[rez.size()]);
	}
}
