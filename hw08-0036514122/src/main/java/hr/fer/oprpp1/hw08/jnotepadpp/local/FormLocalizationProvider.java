package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;

/**
 * Razred koji kad se {@link JNotepadPP} prozor otvori poziva
 * metodu connect, a kada se prozor zatvori poziva metodu disconnect.
 * @author dario
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge{
	
	public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
		super(parent);
		frame.addWindowListener(new WindowAdapter() {
			
			
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
		});
		
	}

}
