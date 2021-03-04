package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Razred koji modelira akciju lokalizacije.
 * @author dario
 *
 */
public abstract class LocalizableAction extends AbstractAction {


	private static final long serialVersionUID = 1L;
	private String key;
	
	public LocalizableAction(String key, ILocalizationProvider lp) {
		this.key = key;
		putValue(NAME, lp.getString(key));
		
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				putValue(NAME, lp.getString(key));
			}
		});
	}
}
