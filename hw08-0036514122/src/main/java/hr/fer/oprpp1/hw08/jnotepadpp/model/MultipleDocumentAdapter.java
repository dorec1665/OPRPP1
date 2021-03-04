package hr.fer.oprpp1.hw08.jnotepadpp.model;

/**
 * Sve metode ove klase su prazne, tj. ova klasa
 * služi za stvaranje razreda koji implementiraju {@link MultipleDocumentListener},
 * ali ne trebaju nadjačati sve metode koje to sučelje nudi.
 * @author dario
 *
 */
public class MultipleDocumentAdapter implements MultipleDocumentListener{

	@Override
	public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {}

	@Override
	public void documentAdded(SingleDocumentModel model) {}

	@Override
	public void documentRemoved(SingleDocumentModel model) {}

}
