package hr.fer.oprpp1.hw08.jnotepadpp.model;

/**
 * Predstavlja slušača nad razredom {@link SingleDocumentModel}.
 * @author dario
 *
 */
public interface SingleDocumentListener {

	/**
	 * Obavještava sve prijavljene slušače
	 * da je dokument modificiran.
	 * @param model dokument koji je modificiran
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	/**
	 * Obavještava sve prijavljene slušače da je
	 * dokumentu promjenjena putanja.
	 * @param model dokument kojemu je promjenjena putanja
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
