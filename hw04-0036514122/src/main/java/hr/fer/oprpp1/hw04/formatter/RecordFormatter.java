package hr.fer.oprpp1.hw04.formatter;

import java.util.ArrayList;
import java.util.List;

import hr.fer.oprpp1.hw04.db.StudentRecord;

/**
 * Ispisiva rezultat upita nad bazom podataka.
 * @author dario
 *
 */
public class RecordFormatter {

	/**
	 * Ispisiva rezultat upita nad bazom podataka.
	 * Ispis sliči tablicama u bazi.
	 * @param records Svi studenti koji zadovoljavaju upit
	 * @return Formatirana lista studenata spremna za ipisat.
	 */
	public static List<String> format(List<StudentRecord> records) {
		List<String> outputs = new ArrayList<>();
		int maxNameSize = 0;
		int maxLastNameSize = 0;
		
		for(StudentRecord r : records) {
			if(maxNameSize < r.getFirstName().length()) {
				maxNameSize = r.getFirstName().length();
			}
			if(maxLastNameSize < r.getLastName().length()) {
				maxLastNameSize = r.getLastName().length();
			}
		}
		
		for(StudentRecord r : records) {
			int namePadding = maxNameSize+1;
			int lastNamePadding = maxLastNameSize+1;
			String record = "| " + r.getJmbag() + " | " + 
			String.format("%-" + lastNamePadding + "s", r.getLastName()) + "| " +
			String.format("%-" + namePadding + "s", r.getFirstName()) + "| " +
			r.getFinalGrade() + " |";
			outputs.add(record);
		}
		
		String nameEqualSigns = "=";
		String lastNameEqualSigns = "=";
		String header = "+============+" + lastNameEqualSigns.repeat(maxLastNameSize+2) +
				"+" + nameEqualSigns.repeat(maxNameSize+2) + "+" + "===+";
		String footer = "+============+" + lastNameEqualSigns.repeat(maxLastNameSize+2) +
				"+" + nameEqualSigns.repeat(maxNameSize+2) + "+" + "===+";
		
		if(records.size() > 0) {
			outputs.add(0, header);
			outputs.add(footer);
		}
		
		return outputs;
	}
}
