package hr.fer.oprpp1.custom.collections;


/**
 * Kolekcija implementirana poljem razreda {@link Object} promjenjive veličine.
 * @author Dario
 *
 */
public class ArrayIndexedCollection extends Collection{
	
	private int size;
	private Object[] elements;
	
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
	public ArrayIndexedCollection(int initialCapacity) {		
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("Velicina nove kolekcije mora biti barem 1 a zadana je: "
					+ initialCapacity);
		}
																	
		
		elements = new Object[initialCapacity];
		size = 0;
	}
	
	/**
	 * Konstruktor koji kopira elemente predane kolekcije u novu kolekciju.
	 * @param collection kolekcija čiji se elementi kopiraju
	 * @throws NullPointerException ako je predana kolekcija <code>null</code>
	 */
	public ArrayIndexedCollection(Collection collection) {
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
	public ArrayIndexedCollection(Collection collection, int initialCapacity) {
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("Velicina nove kolekcije mora biti barem 1 a zadana je:"
					+ initialCapacity);
		}
																	
		if(collection == null) {
			throw new NullPointerException("Kolekcija mora sadržavati barem 1 član!");
		}
		
		if(initialCapacity < collection.size()) {
			elements = new Object[collection.size()];
		} else {
			elements = new Object[initialCapacity];
		}
		
		this.addAll(collection);

	}
	
	/**
	 * Getter za varijablu elements.
	 * @return elements
	 */
	public Object[] getElements() {
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
	public Object get(int index) {
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
	 * Poziva metodu <code>process</code> klase <code>Processor</code>
	 * za svaki element kolekcije.
	 */
	@Override
	public void forEach(Processor processor) {
		for(int i = 0, h = size; i < h; i++) {
			processor.process(elements[i]);
		}
	}
	
	/**
	 * Povećava veličinu polja na dva puta veću veličinu.
	 */
	public void doubleTheArraySize() {
		Object[] tmpElements = new Object[elements.length*2];
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
	public void add(Object value) {
		if(value == null) {
			throw new NullPointerException("Kolekcija ne može spremati null vrijednosti");
		}
		
		if(elements.length == size) {
			doubleTheArraySize();
		}

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
	}
	
	/**
	 * Ubaciva dani objekt na danu poziciju kolekcije.
	 * @param value objekt koji se ubacuje u kolekciju
	 * @param position mjesto na koje se ubaciva objekt
	 * @throws NullPointerException ako je objekt <code>null</code>
	 * @throws IndexOutOfBoundsException ako je pozicija veća od trenutnog broja članova
	 * ili manja od 0
	 */
	public void insert(Object value, int position) {
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
	}

}
