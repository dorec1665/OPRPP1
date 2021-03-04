package hr.fer.oprpp1.hw08.jnotepadpp.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Implementacija sučelja {@link SingleDocumentModel}.
 * @author dario
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel{

	private JTextArea editor;
	private Path filePath;
	private boolean modified;
	private List<SingleDocumentListener> listeners;
	
	/**
	 * Konstrktor.
	 * @param text tekst koji se nalazi u komponenti za tekst(može biti prazan)
	 * @param filePath putanja dokumenta(ako se učitava iz memorije onda nije null)
	 */
	public DefaultSingleDocumentModel(String text, Path filePath) {
		this.editor = new JTextArea(text);
		this.filePath = filePath;
		modified = false;
		listeners = new ArrayList<>();
		
		editor.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {		
				
			}
		});
		
	}
	
	
	@Override
	public JTextArea getTextComponent() {
		return editor;
	}

	@Override
	public Path getFilePath() {
		return filePath;
	}

	@Override
	public void setFilePath(Path path) {
		this.filePath = path;
		filePathChanged();
		
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		modifyFlagChanged();
		
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}
	
	/**
	 * Pomoćna metoda za obaještavanje svih slušača o 
	 * modifikaciji dokumenta.
	 */
	private void modifyFlagChanged() {
		for(SingleDocumentListener listener : listeners) {
			listener.documentModifyStatusUpdated(this);
		}
	}
	
	/**
	 * Pomoćna metoda za obavještavanje svih slušača o
	 * promjeni putanje dokumenta.
	 */
	private void filePathChanged() {
		for(SingleDocumentListener listener : listeners) {
			listener.documentFilePathUpdated(this);
		}
	}
	
	
	
	

}
