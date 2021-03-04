package hr.fer.oprpp1.custom.collections;



public interface Collection {
	
	
	/**
	 * Provjerava ima li u kolekciji ijedan element.
	 * @return <code>true</code> ako kolekcija sadrži barem jedan element,
	 * inače <code>false</code>
	 */
	default boolean isEmpty() {
		return size() <= 0;
	}
	
	int size();
	
	void add(Object value);
	
	boolean contains(Object value);
	
	void remove(int index);
	
	Object[] toArray();
	
	default void forEach(Processor processor) {
		IElementsGetter getter = this.createElementsGetter();
		Object element;
		while(getter.hasNextElement()) {
			element = getter.getNextElement();
			processor.process(element);
		}
	}
	
	/**
	 * Prekopira elemente jedne kolekcije u drugu.
	 * @param other kolekcija u koju se kopiraju elementi
	 */
	default void addAll(Collection other) {
		Collection collection = this;
		
		class addToOtherCollectionProcessor implements Processor{
			
			@Override
			public void process(Object value) {
				collection.add(value);
			}
			
		}
		
		other.forEach(new addToOtherCollectionProcessor());
	}
	
	void clear();
	
	IElementsGetter createElementsGetter();
	
	void addAllSatisfying(Collection col, Tester tester);

}
