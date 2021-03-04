package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Razred koji predstavlja bazu podataka u kojoj se nalaze studenti.
 * @author dario
 *
 */
public class StudentDatabase {

	/**
	 * Lista svih studenata.
	 */
	private List<StudentRecord> students;
	/**
	 * Mapa koja služi za brz pronalazak studenta ako je jmbag poznat.
	 */
	private Map<String, StudentRecord> index;
	
	/**
	 * Konstruktro koji jedan niz pretvara u jedan studentski zapis.
	 * @param database Lista studenata zapisanih kao niz.
	 * @throws NullPointerException kada je database null
	 * @throws IllegalArgumentException ako se u database nalaze duplikati
	 */
	public StudentDatabase(List<String> database) {
		if(database == null) {
			throw new NullPointerException("Baza ne smije biti null!");
		}
		if(checkForDuplicates(database)) {
			throw new IllegalArgumentException();
		}
		
		this.students = toStudentRecord(database);
		index = toIndex(this.students);
		
	}
	
	
	/**
	 * Dohvaća studentski zapis za zadani jmbag.
	 * @param jmbag Jmbag studenta kojeg tražimo
	 * @return studentski zapis ako takav postoji, inače null
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return index.get(jmbag);
	}
	
	
	/**
	 * Metoda koja filtrira studentske zapise.
	 * @param filter Način na koji se studenti filtriraju
	 * @return Novu filtriranu listu studenata
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> tmp = new ArrayList<>();
		
		for(StudentRecord student : students) {
			if(filter.accepts(student)) {
				tmp.add(student);
			}
		}
		
		return tmp;
	}
	
	
	/**
	 * Provjerava ima li u bazi podataka duplikata.
	 * @param database Lista svih studenata
	 * @return true ako ima duplikata, inače false
	 */
	private boolean checkForDuplicates(List<String> database) {
		Set<String> set = new HashSet<>(database);
		if(set.size() < database.size()) {
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * Metoda koja bazu podataka koja je lista s elementima {@link String} pretvara u
	 * listu s elementima {@link StudentRecord}.
	 * @param database Lista svih studenata koji su tipa {@link String}
	 * @return Lista svih studenata koji su tipa {@link StudentRecord}
	 */
	private List<StudentRecord> toStudentRecord(List<String> database) {
		List<StudentRecord> students = new ArrayList<>();
		StudentRecord sr;
		
		for(String str: database) {
			String[] student = str.split("\\s+");
			if(student.length == 4) {
				Integer grade = Integer.parseInt(student[3]);
				if(grade < 1 || grade > 5) {
					throw new IllegalArgumentException();
				}
				sr = new StudentRecord(student[0], student[1], student[2], grade);
				students.add(sr);
			} else if(student.length == 5) {
				Integer grade = Integer.parseInt(student[4]);
				if(grade < 1 || grade > 5) {
					throw new IllegalArgumentException();
				}
				sr = new StudentRecord(student[0], student[1] + "-" + student[2], student[3], grade);
				students.add(sr);

			}
		}
		
		return students;
	}
	
	
	/**
	 * Stvara kolekciju(mapu) koja služi za brzi dohvat studenata po jmbag-u.
	 * @param students Lista svih studenata.
	 * @return Mapa svih studenata.
	 */
	private Map<String, StudentRecord> toIndex(List<StudentRecord> students) {
		Map<String, StudentRecord> map = new HashMap<>();
		
		for(StudentRecord student : students) {
			map.put(student.getJmbag(), student);
		}
		
		return map;
	}
	
	
	
	
	
	
	
	
}
