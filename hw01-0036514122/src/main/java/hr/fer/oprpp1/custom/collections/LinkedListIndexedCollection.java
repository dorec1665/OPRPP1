package hr.fer.oprpp1.custom.collections;

/**
 * Kolekcija implementirana dvostruko povezanom listom objekata.
 * @author Dario
 *
 */
public class LinkedListIndexedCollection extends Collection{
	
	private ListNode first;
	private ListNode last;
	private int size;
	
	private static class ListNode {
		private ListNode next;
		private ListNode prev;
		private Object value;
		
		public ListNode() {
			next = null;
			prev = null;
			value = null;
		}
		
		public ListNode(Object value) {
			this(value, null, null);
		}
		
		public ListNode(Object value, ListNode next, ListNode prev) {
			if(value == null) {
				throw new NullPointerException("Kolekcija ne može spremati null vrijednosti u ListNode");
			}
			this.value = value;
			this.next = next;
			this.prev = prev;
		}
		
	}
	
	/**
	 * Deafultni konstruktor.
	 */
	public LinkedListIndexedCollection() {
		first = null;
		last = null;
		size = 0;
	}
	
	/**
	 * Konstruktor koji kopira elemente predane kolekcije u novu kolekciju.
	 * @param collection kolekcija čiji se elementi kopiraju
	 * @throws NullPointerException ako je predana kolekcija <code>null</code>
	 */
	public LinkedListIndexedCollection(Collection collection) {
		this();
		if(collection == null) {
			throw new NullPointerException("Kolekcija mora sadržavati barem 1 član!");
		}
		
		this.addAll(collection);
		
	}
	
	/**
	 * Vraća trenutni broj elemenata u kolekciji.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Provjerava da li je zadani element u kolekciji.
	 * @return <code>true</code> ako je element u kolekciji, inače <code>false</code>
	 */
	public boolean contains(Object value) {
		ListNode node = new ListNode();
		node = first;
		for(int i = 0, h = size; i < h; i++) {
			if(node.value.equals(value)) {
				return true;
			}
			node = node.next;
		}
		
		return false;
		
	}
	
	/**
	 * Alocira novo polje veličine ove kolekcije, puni ga elementima kolekcije
	 * te ga vraća.
	 * @return alocirano novo polje
	 */
	public Object[] toArray() {
		Object[] array = new Object[size];
		ListNode node = new ListNode();
		
		node = first;
		for(int i = 0, h = size; i < h; i++) {
			array[i] = node.value;
			node = node.next;
		}
		
		return array;
	}
	
	/**
	 * Poziva metodu <code>process</code> klase <code>Processor</code>
	 * za svaki element kolekcije.
	 */
	public void forEach(Processor processor) {
		ListNode node = new ListNode();
		
		node = first;
		for(int i = 0, h = size; i < h; i++) {
			processor.process(node.value);
			node = node.next;
		}
	}
	
	/**
	 * Dodaje novi objekt u kolekciju.
	 * @throws NullPointerException ako je vrijednost predanog objekta <code>null</code>
	 */
	public void add(Object value) {
		if(value == null) {
			throw new NullPointerException("Kolekcija ne može spremati null vrijednosti u add metodi");
		}
		
		ListNode node = new ListNode(value);
		
		if(size == 0) {
			first = node;
			last = node;
			size++;
		} else {		
			node.prev = last;
			last.next = node;
			last = node;
			size++;
		}
	}
	
	/**
	 * Vraća element liste na danoj poziciji.
	 * @param index pozicija elementa
	 * @return element liste na danoj poziciji
	 * @throws IndexOutOfBoundsException ako je pozicija veća od trenutnog broja članova
	 * ili manja od 0
	 */
	public Object get(int index) {
		if(index > size-1 || index < 0) {
			throw new IndexOutOfBoundsException("Zadani indeks" + index + "je veci od veličine polja");
		}
		
		ListNode node = new ListNode();
		if(index > (size-1)/2) {
			node = last;
			for(int i = size-1; i > 0; i--) {
				if(i == index) {
					return node.value;
				}
				node = node.prev;
			}
		} else {
			node = first;
			for(int i = 0; i < size; i++) {
				if(index == i) {
					return node.value;
				}
				node = node.next;
			}
		}
		
		return null;
		
	}

	/**
	 * Uklanja sve elemente iz kolekcije.
	 */
	public void clear() {
		first = null;
		last = null;
		size = 0;
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
		
		ListNode node = new ListNode();
		ListNode insertingNode = new ListNode(value);
		
		if(size == 0) {
			first = insertingNode;
			last = insertingNode;
			size++;
			return;
		}
		
		if(position == 0) {
			first.prev = insertingNode;
			insertingNode.next = first;
			first = insertingNode;
			size++;
			return;
		}
		
		if(position == size) {
			last.next = insertingNode;
			insertingNode.prev = last;
			last = insertingNode;
			size++;
			return;
		}
		
		if(position > size/2) {
			node = last;
			for(int i = size-1; i >= position; i--) {
				if(i == position) {
					insertingNode.prev = node.prev;
					insertingNode.next = node;
					insertingNode.next.prev = insertingNode;
					insertingNode.prev.next = insertingNode;
					size++;
				}
				node = node.prev;
			}
		} else {
			node = first;
			for(int i = 0; i <= position; i++) {
				if(i == position) {
					insertingNode.prev = node.prev;
					insertingNode.next = node;
					insertingNode.next.prev = insertingNode;
					insertingNode.prev.next = insertingNode;
					size++;
				}
				node = node.next;
			}
		}
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
		
		ListNode node = new ListNode();
		node = first;
		for(int i = 0; i < size; i++) {
			if(node.value.equals(value)) {
				return i;
			}
			node = node.next;
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
		
		ListNode node = new ListNode();
		
		if(index == 0) {
			first.next.prev = null;
			first = first.next;
			size--;
			return;
		}
		
		if(index == size-1) {
			last.prev.next = null;
			last = last.prev;
			size--;
			return;
		}
		
		if(index > (size-1)/2) {
			node = last;
			for(int i = size-1; i > 0; i--) {
				if(i == index) {
					node.next.prev = node.prev;
					node.prev.next = node.next;
					size--;
					break;
				}
				node = node.prev;
			}
		} else {
			node = first;
			for(int i = 0; i < size-1; i++) {
				if(i == index) {
					node.next.prev = node.prev;
					node.prev.next = node.next;
					size--;
					break;
				}
				node = node.next;
			}
		}
	}
	
	

}
