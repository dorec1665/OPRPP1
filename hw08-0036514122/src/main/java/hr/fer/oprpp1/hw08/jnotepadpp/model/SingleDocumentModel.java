package hr.fer.oprpp1.hw08.jnotepadpp.model;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Predstavlja model jednog dokumenta.
 * @author dario
 *
 */
public interface SingleDocumentModel {

	/**
	 * Vraća komponentu za unos i manipulaciju tekstom.
	 * @return komponentu za unos i manipulaciju tekstom
	 */
	JTextArea getTextComponent();
	
	/**
	 * Vraća putanju datoteke iz koje je datoteka učitana.
	 * Putanja može biti null.
	 * @return putanju datoteke
	 */
	Path getFilePath();
	
	/**
	 * Postavlja putanju datoteke.
	 * Ne smije biti null.
	 * @param path nova putanja datoteke.
	 */
	void setFilePath(Path path);
	
	/**
	 * Vraća odgovor jeli dokument modificiran.
	 * @return jeli dokument modificiran.
	 */
	boolean isModified();
	
	/**
	 * Postavlja modifikaciju dokumenta.
	 * True dokument je modificiran, false dokument nije modificiran.
	 * @param modified postavlja modifikaciju dokumenta
	 */
	void setModified(boolean modified);
	
	/**
	 * Dodaje novog slušača nad dokumentom.
	 * @param l novi slušač
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	
	
	/**
	 * Odjavljuje slušača sa dokumenta.
	 * @param l slušač koji se odjvljuje
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
