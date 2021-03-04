package hr.fer.oprpp1.hw08.jnotepadpp.custom;

import javax.swing.JMenu;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;

/**
 * Menu izbornik prilagođen za lokalizaciju.
 * @author dario
 *
 */
public class LJMenu extends JMenu{

	private static final long serialVersionUID = 1L;

	private String key;
	private ILocalizationListener listener;
	private ILocalizationProvider provider;
	
	public LJMenu(String s, ILocalizationProvider prov) {
		this.key = s;
		this.provider = prov;
		
		listener = new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				LJMenu.this.setText(provider.getString(key));
			}
		};
		
		provider.addLocalizationListener(listener);
	}
	
}
