package hr.fer.oprpp1.hw04.db;

/**
 * Razred koji predstavlja zapis jednog studenta.
 * @author dario
 *
 */
public class StudentRecord {

	private String jmbag;
	private String lastName;
	private String firstName;
	private int finalGrade;
	
	
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		super();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}


	public String getJmbag() {
		return jmbag;
	}


	public String getLastName() {
		return lastName;
	}


	public String getFirstName() {
		return firstName;
	}


	public int getFinalGrade() {
		return finalGrade;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "StudentRecord [jmbag=" + jmbag + ", lastName=" + lastName + ", firstName=" + firstName + ", finalGrade="
				+ finalGrade + "]";
	}
	
	
	
	
	
}
