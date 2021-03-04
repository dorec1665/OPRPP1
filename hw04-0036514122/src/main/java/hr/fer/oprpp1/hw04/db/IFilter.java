package hr.fer.oprpp1.hw04.db;

/**
 * Funkcijsko sučelje koje prihvaća pojedini zapis ako
 * zadovoljava uvjete.
 * @author dario
 *
 */
public interface IFilter {

	/**
	 * Prihvaća studentski zapis ako zadovoljava propisane uvjete.
	 * @param record Studensti zapis koji se testira.
	 * @return true ako studentski zapis ispunjava uvjete, inače false.
	 */
	boolean accepts(StudentRecord record);
}
