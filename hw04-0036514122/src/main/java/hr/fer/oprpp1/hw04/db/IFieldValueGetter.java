package hr.fer.oprpp1.hw04.db;

/**
 * Funkcijsko sučelje koje dohvaća traženu člansku varijablu razreda {@link StudentRecord}.
 * @author dario
 *
 */
public interface IFieldValueGetter {

	/**
	 * Dohvaća traženu člansku varijablu razreda {@link StudentRecord}.
	 * @param record Studentski zapis čije članske varijable želimo dohvatiti.
	 *
	 */
	String get(StudentRecord record);
}
