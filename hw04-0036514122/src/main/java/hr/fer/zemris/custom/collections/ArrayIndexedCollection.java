package hr.fer.zemris.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Kolekcija implementirana poljem razreda {@link Object} promjenjive veličine.
 * @author Dario
 *
 */
public class ArrayIndexedCollection<T> implements List<T> {
	
	private int size;
	private long modificationCount = 0;
	private T[] elements;
	
	private static class ElementsGetter<T> implements IElementsGetter<T>{
		private long savedModificationCount;
		private ArrayIndexedCollection<T> collection;
		private int currentIndex;
		
		private ElementsGetter(ArrayIndexedCollection<T> collection, long savedModificationCount) {
			this.collection = collection;
			this.savedModificationCount = savedModificationCount;
			currentIndex = 0;
			
		}

		/**
		 * Provjerava sadrži li kolekcija još jedan element.
		 * @throws ConcurrentModificationException ako je kolekcija modificirana
		 */
		@Override
		public boolean hasNextElement() {
			if(savedModificationCount != collection.modificationCount) {
				throw new ConcurrentModificationException("Kolekcija je modificirana!");
			}
			return currentIndex != collection.size;
		}

		/**
		 * Dohvaća sljedeći element kolekcije.
		 * @throws ConcurrentModificationException ako je kolekcija modificirana
		 * @throws NoSuchElementException ako kolekcija nema više elemenata za dohvatiti
		 */
		@Override
		public T getNextElement() {
			if(!hasNextElement()) {
				throw new NoSuchElementException("Nema više elemenata!");
			}
			if(savedModificationCount != collection.modificationCount) {
				throw new ConcurrentModificationException("Kolekcija je modificirana!");
			}
			
			return collection.elements[currentIndex++];
		}


		@Override
		public void processRemaining(Processor<? super T> p) {
			for(int i = currentIndex; i < collection.size; i++) {
				p.process(collection.elements[i]);
			}
			
		}
		
	}
	
	/**
	 * Defaultni konstruktor koji alocira polje na veličinu 16.
	 */
	public ArrayIndexedCollection() {		
		this(16);
	}
	
	/**
	 * Konstruktor koji alocira polje veličine initialCapacity.
	 * @param initialCapacity veličina na koju se polje alocira
	 * @throws IllegalArgumentException ako je predan initialCapacitiy manji od 1
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(int initialCapacity) {		
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("Velicina nove kolekcije mora biti barem 1 a zadana je: "
					+ initialCapacity);
		}
																	
		elements = (T[]) new Object[initialCapacity];
		size = 0;
	}
	
	/**
	 * Konstruktor koji kopira elemente predane kolekcije u novu kolekciju.
	 * @param collection kolekcija čiji se elementi kopiraju
	 * @throws NullPointerException ako je predana kolekcija <code>null</code>
	 */
	public ArrayIndexedCollection(Collection<T> collection) {
		this(collection, collection.size());
	}
	
	/**
	 * Konstruktor koji kopira elemente predane kolekcije u novu kolekciju
	 * veličine initialCapacity. Ako je veličina predane kolekcije veća od
	 * initialCapacity nova kolekcija će biti veličine predane kolekcije.
	 * @param collection kolekcija čiji se elementi kopiraju
	 * @param initialCapacity veličina na koju se alocira polje ako je veličina
	 * predane kolekcije manja ili jednaka veličini initialCapacity
	 * @throws IllegalArgumentException ako je predan initialCapacity manji od 1
	 * @throws NullPointerException ako je predana kolekcija <code>null</code>
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(Collection<T> collection, int initialCapacity) {
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("Velicina nove kolekcije mora biti barem 1 a zadana je:"
					+ initialCapacity);
		}
																	
		if(collection == null) {
			throw new NullPointerException("Kolekcija mora sadržavati barem 1 član!");
		}
		
		if(initialCapacity < collection.size()) {
			elements = (T[])  new Object[collection.size()];
		} else {
			elements = (T[])  new Object[initialCapacity];
		}
		
		this.addAll(collection);

	}
	
	/**
	 * Getter za varijablu elements.
	 * @return elements
	 */
	public T[] getElements() {
		return elements;
	}
	
	/**
	 * Vraca veličinu alociranog polja
	 * @return veličina alociranog polja
	 */
	public int elementsCapacity() {
		return elements.length;
	}
	
	/**
	 * Vraća element polja na danoj poziciji.
	 * @param index pozicija elementa
	 * @return element polja na danoj poziciji
	 * @throws IndexOutOfBoundsException ako je pozicija veća od trenutnog broja članova
	 * ili manja od 0
	 */
	public T get(int index) {
		if(index > size-1 || index < 0) {
			throw new IndexOutOfBoundsException("Zadani indeks" + index + "je veci od veličine polja");
		}
		
		return elements[index];
	}
	

	/**
	 * Vraća trenutni broj elemenata u kolekciji.
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Provjerava da li je zadani element u kolekciji.
	 * @return <code>true</code> ako je element u kolekciji, inače <code>false</code>
	 */
	@Override
	public boolean contains(Object value) {
		for(int i = 0, h = size; i < h; i++) {
			if(elements[i].equals(value)) return true;
		}
		
		return false;
	}
	
	/**
	 * Alocira novo polje veličine ove kolekcije, puni ga elementima kolekcije
	 * te ga vraća.
	 * @return alocirano novo polje
	 */
	@Override
	public Object[] toArray() {
		
		Object[] array = new Object[size];
		for(int i = 0, h = size(); i < h; i++) {
			array[i] = elements[i];
		}
		
		return array;
	}
	
	
	/**
	 * Povećava veličinu polja na dva puta veću veličinu.
	 */
	@SuppressWarnings("unchecked")
	public void doubleTheArraySize() {
		T[] tmpElements = (T[]) new Object[elements.length*2];
		for(int i = 0, h = elements.length; i < h; i++) {
			tmpElements[i] = elements[i];
		}
		elements = tmpElements;
	}
	
	/**
	 * Dodaje novi objekt u kolekciju.
	 * @throws NullPointerException ako je vrijednost predanog objekta <code>null</code>
	 */
	@Override
	public void add(T value) {
		if(value == null) {
			throw new NullPointerException("Kolekcija ne može spremati null vrijednosti");
		}
		
		if(elements.length == size) {
			doubleTheArraySize();
		}

		modificationCount++;
		elements[size] = value;
		size++;

	}
	
	/**
	 * Uklanja sve elemente iz kolekcije.
	 */
	public void clear() {
		for(int i = 0; i < elements.length; i++) {
			elements[i] = null;
		}
		size = 0;
		modificationCount++;
	}
	
	/**
	 * Ubaciva dani objekt na danu poziciju kolekcije.
	 * @param value objekt koji se ubacuje u kolekciju
	 * @param position mjesto na koje se ubaciva objekt
	 * @throws NullPointerException ako je objekt <code>null</code>
	 * @throws IndexOutOfBoundsException ako je pozicija veća od trenutnog broja članova
	 * ili manja od 0
	 */
	public void insert(T value, int position) {
		if(value == null) {
			throw new NullPointerException("Kolekcija ne može spremati null vrijednosti");
		}
		if(position > size() || position < 0) {
			throw new IndexOutOfBoundsException("Zadana pozicija" + position + "se ne nalazi u polju");
		}
		
		if(elements.length == size) {
			doubleTheArraySize();
		}
		
		for(int i = size-1; i >= position; i--) {
			elements[i+1] = elements[i];
		}
		
		elements[position] = value;
		size++;
		modificationCount++;
		
	}
	
	/**
	 * Za dani objekt vraća mjesto gdje se nalazi prva pojava tog objekta
	 * utvđeno metodom <code>equals</code>.
	 * @param value objekt kojeg želimo pronaći
	 * @return -1 ako nema objekta ili se kao vrijednost pošalje <code>null</code>,
	 * poziciju danog objekta u kolekciji ako ga pronađe
	 */
	public int indexOf(Object value) {
		if(value == null) return -1;
		
		for(int i = 0, h = size; i < h; i++) {
			if(value.equals(elements[i])) return i;
		}
		
		return -1;
	}
	
	/**
	 * Uklanja element kolekcije na danom mjestu.
	 * @param index pozicija elementa kojeg želimo ukloniti
	 * @throws IndexOutOfBoundsException ako je pozicija manja od 0 ili
	 * veća od trenutnog broja članova kolekcije umanjeno za 1
	 */
	public void remove(int index) {
		if(index > size - 1 || index < 0) {
			throw new IndexOutOfBoundsException("Zadana pozicija" + index + "se ne nalazi u polju");
		}
		
		elements[index] = null;
		
		for(int i = index, h = size-1; i < h; i++) {
			elements[i] = elements[i+1];
		}
		size--;
		modificationCount++;
	}

	@Override
	public IElementsGetter<T> createElementsGetter() {
		return new ElementsGetter<T>(this, modificationCount);
	}

	@Override
	public void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {
		IElementsGetter<? extends T> getter = col.createElementsGetter();
		while(getter.hasNextElement()) {
			T element = getter.getNextElement();
			if(tester.test(element)) {
				this.add(element);
			}
		}
		
	}

}
