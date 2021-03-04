package hr.fer.oprpp1.custom.collections;


/**
 * Neka generalna kolekcija objekata.
 * @author Dario
 *
 */
public class Collection {
	
	/**
	 * Defaultni konstruktor.
	 */
	protected Collection() {
		
	}
	
	/**
	 * Provjerava ima li u kolekciji ijedan element.
	 * @return <code>true</code> ako kolekcija sadrži barem jedan element,
	 * inače <code>false</code>
	 */
	public boolean isEmpty() {
		if(this.size() > 0) return false;
		
		return true;
	}
	
	public int size() {
		return 0;
	}
	
	public void add(Object value) {
		
	}
	
	public boolean contains(Object value) {
		return false;
	}
	
	public boolean remove(Object value) {
		return false;
	}
	
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	public void forEach(Processor processor) {
		
	}
	
	/**
	 * Prekopira elemente jedne kolekcije u drugu.
	 * @param other kolekcija u koju se kopiraju elementi
	 */
	public void addAll(Collection other) {
		Collection collection = this;
		
		class addToOtherCollectionProcessor extends Processor{
			
			@Override
			public void process(Object value) {
				collection.add(value);
			}
			
		}
		
		other.forEach(new addToOtherCollectionProcessor());
	}
	
	public void clear() {
		
	}

}
