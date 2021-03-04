package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Singleton klasa koja implementira sučelje {@link ILocalizationProvider}.
 * @author dario
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider{

	
	private static LocalizationProvider instance = new LocalizationProvider();
	private String language;
	private ResourceBundle bundle;
	
	/**
	 * Konstruktor.
	 */
	private LocalizationProvider() {
		language = "en";
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", locale);

	}
	
	
	@Override
	public String getString(String s) {
		return bundle.getString(s);
	}
	
	
	/**
	 * Vraća instancu klase(singleton).
	 * @return instancu klase
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}
	
	
	/**
	 * Postavlja jezik na zadani.
	 * @param language novi jezik
	 */
	public void setLanguage(String language) {
		this.language = language;
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", locale);
		fire();
	}


	@Override
	public String getCurrentLanguage() {
		return language;
	}




}
