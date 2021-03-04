package hr.fer.oprpp1.custom.collections;

/**
 * Razred koji predstavlja strukturu podataka stog.
 * @author Dario
 *
 */
public class ObjectStack<T> {

	private ArrayIndexedCollection<T> array;
	
	/**
	 * Defaultni konstruktor.
	 */
	public ObjectStack() {
		array = new ArrayIndexedCollection<T>();
	}
	
	/**
	 * Kontruktor koji inicijalizira početnu veličinu stoga.
	 * @param initialCapacity početna veličina stoga
	 */
	public ObjectStack(int initialCapacity) {
		array = new ArrayIndexedCollection<T>(initialCapacity);
	}
	
	/**
	 * Provjerava ima li elemenata na stogu.
	 * @return <code>true</code> ako ima elemenata, inače <code>false</code>
	 */
	public boolean isEmpty() {
		return array.isEmpty();
	}
	
	/**
	 * Provjerava trenutni broj elemenata na stogu.
	 * @return trenutni broj elemenata na stogu
	 */
	public int size() {
		return array.size();
	}
	
	public ArrayIndexedCollection<T> getArray() {
		return array;
	}
	
	/**
	 * Stavlja predanu vrijednost na vrh stoga.
	 * @param value vrijednsot koja se stavlja na vrh stoga
	 * @throws NullPointerException ako je predana vrijednost <code>null</code>
	 */
	public void push(T value) {
		if(value == null) {
			throw new NullPointerException("Kolekcija ne može spremati null vrijednosti");
		}
		
		array.add(value);
	}
	
	/**
	 * Uklanja posljednju postavljenu vrijednost s vrha stoga.
	 * @return posljednju postavljenu vrijednost
	 * @throws EmptyStackException ako na stogu nema elemenata
	 */
	public T pop() throws EmptyStackException{
		if(array.size() == 0) {
			throw new EmptyStackException("Ne može se ukloniti element sa stoga jer je stog prazan.");
		}
		
		T stackTop = array.get(array.size()-1);
		array.remove(array.size()-1);
		
		return stackTop;
	}
	
	/**
	 * vraća zadnju postavljenu vrijednost na stogu, ali je ne uklanja;
	 * @return posljednu postavljenu vrijednost
	 * @throws EmptyStackException ako na stogu nema elemenata
	 */
	public T peek() throws EmptyStackException{
		if(array.size() == 0) {
			throw new EmptyStackException("Ne može se dohvatit element sa stoga jer je stog prazan.");
		}
		T stackTop = array.get(array.size()-1);
		return stackTop;
	}
	
	/**
	 * Uklanja sve elemente stoga.
	 */
	public void clear() {
		array.clear();
	}
	
}
