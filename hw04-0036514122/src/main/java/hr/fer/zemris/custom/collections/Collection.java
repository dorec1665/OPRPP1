package hr.fer.zemris.custom.collections;



public interface Collection<T> {
	
	
	/**
	 * Provjerava ima li u kolekciji ijedan element.
	 * @return <code>true</code> ako kolekcija sadrži barem jedan element,
	 * inače <code>false</code>
	 */
	default boolean isEmpty() {
		return size() <= 0;
	}
	
	int size();
	
	void add(T value);
	
	boolean contains(Object value);
	
	void remove(int index);
	
	Object[] toArray();
	
	default void forEach(Processor<? super T> processor) {
		IElementsGetter<T> getter = this.createElementsGetter();
		T element;
		while(getter.hasNextElement()) {
			element = getter.getNextElement();
			processor.process(element);
		}
	}
	
	/**
	 * Prekopira elemente jedne kolekcije u drugu.
	 * @param other kolekcija u koju se kopiraju elementi
	 */
	default void addAll(Collection<? extends T> other) {
		other.forEach((value) -> add(value));
	}
	
	void clear();
	
	/**
	 * Stvara iterator kolekcije.
	 * @return iterator
	 */
	IElementsGetter<T> createElementsGetter();
	
	/**
	 * Dodaje samo one elemente kolekcije u drugu ako zadovoljavaju uvjet.
	 * @param col kolekcija iz koje se dodaju elementi
	 * @param tester uvjet koji element mora zadovoljit
	 */
	void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester);

}
