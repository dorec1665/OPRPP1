package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstraktna implementacija {@link ILocalizationProvider} sučelja
 * @author dario
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider{

	private List<ILocalizationListener> listeners;
	
	/**
	 * Konstruktor.
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}
	
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}
	
	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}
	
	
	/**
	 * Metoda okidač, tj. javlja svim zainteresiranim slušateljima da je došlo
	 * do promjene u lokalizaciji.
	 */
	public void fire() {
		for(ILocalizationListener listener : listeners) {
			listener.localizationChanged();
		}
	}
	
}
