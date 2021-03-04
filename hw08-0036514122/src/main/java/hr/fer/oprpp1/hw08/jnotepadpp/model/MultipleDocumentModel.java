package hr.fer.oprpp1.hw08.jnotepadpp.model;

import java.nio.file.Path;

/**
 * Predstavlja model koji može sadržavati 0, 1 ili više dokumenata.
 * @author dario
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {

	/**
	 * Stvara novi dokument.
	 * @return novostvoreni dokument
	 */
	SingleDocumentModel createNewDocument();
	
	/**
	 * Dohvaća trenutno otvoren i prikazan dokument korisniku.
	 * @return trenutno otvoren i prikazan dokument korisniku
	 */
	SingleDocumentModel getCurrentDocument();
	
	/**
	 * Učitava dokument iz memorije.
	 * @param path putanja dokumenta kojeg korisnik želi učitati
	 * @return učitani dokument
	 */
	SingleDocumentModel loadDocument(Path path);
	
	/**
	 * Snima dokument u memoriju.
	 * @param model dokument kojeg treba snimiti
	 * @param path putanja na koju korisnik želi snimiti dokument
	 */
	void saveDocument(SingleDocumentModel model, Path path);
	
	/**
	 * Zatvara trenutno otvoreni i prikazan korisniku dokument.
	 * @param model trenutno otvoreni i prikazan korisniku dokument
	 */
	void closeDocument(SingleDocumentModel model);
	
	/**
	 * Dodaje novog slušača nad cijelim {@link MultipleDocumentModel} modelom.
	 * @param l novi slušač
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Odjavljuje slušača.
	 * @param l slušač koji se odjvljuje
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Vraća broj otvorenih dokuemata.
	 * @return
	 */
	int getNumberOfDocuments();
	
	/**
	 * Vraća dokument na zadanoj poziciji.
	 * @param index pozicija
	 * @return dokument na zadanoj poziciji
	 */
	SingleDocumentModel getDocument(int index);
}
