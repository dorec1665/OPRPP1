package hr.fer.zemris.custom.collections;

public interface IElementsGetter<T> {
	
	boolean hasNextElement();
	
	T getNextElement();
	
	/**
	 * Obrađuje preostale elemente kolekcije.
	 */
	void processRemaining(Processor<? super T> p);

}
