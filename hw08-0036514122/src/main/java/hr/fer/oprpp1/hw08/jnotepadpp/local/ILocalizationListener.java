package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Sučelje koje modelira jednog slušača lokalizacije.
 * @author dario
 *
 */
public interface ILocalizationListener {

	/**
	 * Metoda koja dojavljuje slušačima da je dosšlo do
	 * promjene lokalizacije.
	 */
	void localizationChanged();
	
}
