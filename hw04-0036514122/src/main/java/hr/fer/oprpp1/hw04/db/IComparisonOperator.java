package hr.fer.oprpp1.hw04.db;

/**
 * Funkcijsko sučelje koje provjerava jeli dva niza znakova zadovoljavaju uvjet.
 * @author dario
 *
 */
public interface IComparisonOperator {

	/**
	 * Provjerava jeli dva niza znakova zadovoljavaju uvjet.
	 * @param value1 Prvi niz
	 * @param value2 Drugi niz
	 * @return true ako zadovoljavaju, false inače
	 */
	boolean satisfied(String value1, String value2);
}
