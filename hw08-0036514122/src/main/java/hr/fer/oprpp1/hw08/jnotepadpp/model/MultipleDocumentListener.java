package hr.fer.oprpp1.hw08.jnotepadpp.model;

/**
 * Slušač nad razredom {@link MultipleDocumentModel}.
 * @author dario
 *
 */
public interface MultipleDocumentListener {

	/**
	 * Dojavljuje svim prijavljenim slušačima da je došlo do promjene
	 * prikazivanja dokumenta korisniku.
	 * @param previousModel dokument koji se prikazivo prije promjene(može biti null
	 * jedino ako currentModel nije null)
	 * @param currentModel dokument koje se prikazuje nakon promjene(može biti null
	 * jednino ako previousModel nije null)
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	
	/**
	 * Dojavljuje svim prijavljenim slušačima da je dodan novi dokument.
	 * @param model novododani dokument
	 */
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * Dojavljuje svim prijavljenim slušačima da je dokument ukonjen.
	 * @param model uklonjeni dokument
	 */
	void documentRemoved(SingleDocumentModel model);
}
