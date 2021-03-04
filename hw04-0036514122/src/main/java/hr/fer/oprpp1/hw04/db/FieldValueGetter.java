package hr.fer.oprpp1.hw04.db;

/**
 * Dohvaća člansku varijablu razreda {@link StudentRecord}.
 * @author dario
 *
 */
public class FieldValueGetter {

	/**
	 * Dohvaća člansku varijabli firstName razreda {@link StudentRecord}.
	 */
	public static final IFieldValueGetter FIRST_NAME = (record) -> record.getFirstName();
	
	/**
	 * Dohvaća člansku varijabli lastName razreda {@link StudentRecord}.
	 */
	public static final IFieldValueGetter LAST_NAME = (record) -> record.getLastName();

	/**
	 * Dohvaća člansku varijabli jmbag razreda {@link StudentRecord}.
	 */
	public static final IFieldValueGetter JMBAG = (record) -> record.getJmbag();
}
