package hr.fer.oprpp1.hw08.jnotepadpp.local;


/**
 * Klasa dekorator za nekog drugog {@link ILocalizationProvider}-a.
 * @author dario
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider{

	private ILocalizationListener listener;
	private ILocalizationProvider parent;
	private boolean connected;
	private String previousLanguage;
	
	/**
	 * Konstruktor.
	 * @param parent
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = parent;
		this.listener = new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				fire();
			}
		};
	}
	
	
	
	/**
	 * Pozivom ove metode registrira se instanca anonimne
	 * klase koja implementira {@link ILocalizationListener} kao
	 * slušač na dekoratora.
	 */
	public void connect() {
		if(connected) return;
		
		connected = true;
		parent.addLocalizationListener(listener);
	}
	
	/**
	 * Pozivom metode instanca anonimne
	 * klase koja implementira {@link ILocalizationListener} se
	 * odjavljiva kao slušač nad dekoratorom.
	 */
	public void disconnect() {
		connected = false;
		parent.removeLocalizationListener(listener);
		previousLanguage = parent.getCurrentLanguage();
	}
	
	
	
	
	@Override
	public String getString(String s) {
		return parent.getString(s);
	}




	@Override
	public String getCurrentLanguage() {
		return parent.getCurrentLanguage();
	}
}
