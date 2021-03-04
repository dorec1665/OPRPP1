package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Sučelje koje modelira davatelja lokalizacije.
 * @author dario
 *
 */
public interface ILocalizationProvider {

	/**
	 * Dodaje novog slušača.
	 * @param l novi slušač
	 */
	void addLocalizationListener(ILocalizationListener l);
	
	/**
	 * Briše zadanog slušača.
	 * @param l slušač koji se briše
	 */
	void removeLocalizationListener(ILocalizationListener l);
	
	/**
	 * Za zadani ključ vraća lokaliziranu poruku.
	 * @param s ključ
	 * @return lokaliziranu prouku
	 */
	String getString(String s);
	
	/**
	 * Vraća trenutno korišteni jezik.
	 * @return trenutno korišteni jezik
	 */
	String getCurrentLanguage();
	
}
