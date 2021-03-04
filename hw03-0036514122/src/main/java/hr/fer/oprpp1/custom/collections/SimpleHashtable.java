package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Predstavlja tablicu raspršenog adresiranja koja omogućava pohranu uređenih
 * parova(ključ, vrijednost).
 * @author dario
 *
 * @param <K> tip ključa uređenog para
 * @param <V> tip vrijednosti uređenog para
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
	
	private int size;
	private int modificationCount;
	private TableEntry<K, V>[] table;
	
	/**
	 * Defaultni konstruktor koji stvara tablicu veličine 16.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		table = (TableEntry<K, V>[]) new Object[16];
		size = 0;
		modificationCount = 0;
	}
	
	/**
	 * Stvara veličinu tablice na predanu vrijednost na način da testira predanu vrijednost.
	 * Ako je predana vrijednost potencija broja 2 onda se stvara tablica veličine te vrijednosti,
	 * inače se stvara tablica veličine potencije broja 2 koja je prva veća od predane vrijednosti.
	 * @param capacity veličina na koju će se tablica inicijalizirati
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if(capacity < 1) {
			throw new IllegalArgumentException("Kapacitet tablice mora biti barem 1!");
		}
		capacity = validateCapacity(capacity);
		table = (TableEntry<K, V>[]) new TableEntry[capacity];
		size = 0;
		modificationCount = 0;
	}
	
	/**
	 * Predstavlja jedan slot tablice raspršenog adresiranja.
	 * @author dario
	 *
	 * @param <K> tip ključa uređenog para(ključ, vrijednost)
	 * @param <V> tip vrijednosti uređenog para(ključ, vrijednost)
	 */
	public static class TableEntry<K, V> {
		private K key;
		private V value;
		private TableEntry<K, V> next;
		
		public TableEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		public K getKey() {
			return key;
		}
		
		public V getValue() {
			return value;
		}
		
		public void setValue(V value) {
			this.value = value;
		}
	}
	
	
	/**
	 * Getter za člansku varijablu table.
	 * @return table
	 */
	public TableEntry<K, V>[] getTable() {
		return table;
	}
	
	
	/**
	 * Provjerava unesenu vrijednost koja predstavlja internu veličinu
	 * polja table i ako nije potencija broja 2 postavlja se na prvu potenciju broja 2
	 * veću od zadane vrijednosti.
	 * @param capacity vrijednost koja predstavlja internu veličinu polja
	 * @return prvu potenciju broja 2 koja je veća od capacity ako capacity nije potencija
	 * broja 2, inače capacity
	 */
	private int validateCapacity(int capacity) {
		int powerBigger = 1;
		
		while(powerBigger < capacity) {
			powerBigger *= 2;
		}
		
		return powerBigger;
		
	}
	
	
	/**
	 * Nad pozvanim ključem ubacuje novi par(ključ, vrijednost) u tablicu.
	 * Ako za pozvani ključ u tablici već postoji par s tim ključem, onda se postojeći
	 * par ažurira s novom vrijednošću, ne dodaje se novi par(ključ, vrijednost) i vraća se vrijednost
	 * pregaženog para. Ako se novi par dodaje
	 * onda se postavlja na kraj kolekcije, te se vraća null.
	 * @param key ključ para koji se treba ubaciti u kolekciju
	 * @param value vrijednost para koji se treba ubaciti u kolekciju
	 * @return null ako se par doda u kolekciju, inače pregaženu vrijednost para
	 * @throws NullPointerException ako je ključ null
	 */
	public V put(K key, V value) {
		if(key == null) {
			throw new NullPointerException("Ključ ne smije biti null!");
		}
		
		double a = (double) size / table.length;
		
		if(a >= 0.75) {
			changeTableSize();
			modificationCount++;
		}
		
		TableEntry<K, V> element;
		int slot = Math.abs(key.hashCode() % table.length);
		if(table[slot] == null) {
			element = new TableEntry<K, V>(key, value);
			table[slot] = element;
			size++;
			modificationCount++;
			return null;
		}
		for(TableEntry<K, V> t = table[slot]; t != null; t = t.next) {
			if(key.equals(t.key)) {
				V oldValue = t.value;
				t.value = value;
				return oldValue;
			} else if(t.next == null) {
				element = new TableEntry<K, V>(key, value);
				t.next = element;
				size++;
				modificationCount++;
				return null;
			}
		}
		
		return null;
		
	}
	
	
	/**
	 * Metoda get pozvana s ključem vraća odgovarajući par iz kolekcije ako par postoji
	 * u kolekciji, inače vraća null. Ako se kao ključ preda null ne dolazi do greške, već
	 * metoda vraća null jer se takav ključ svakako ne nalazi u kolekciji.
	 * @param key ključ para koji se želi dohvatiti
	 * @return vrijednost para ako takav par postoji u kolekciji, inače null
	 */
	public V get(Object key) {
		if(key == null) {
			return null;
		}
		int slot = Math.abs(key.hashCode() % table.length);

		
		for(TableEntry<K, V> t = table[slot]; t != null; t = t.next) {
			if(key.equals(t.key)) {
				return t.value;
			}
		}
		
		return null;
	}
	
	
	/**
	 * Vraća broj trenutnih elemenata u kolekciji.
	 * @return broj trenutnih elemenata u kolekciji
	 */
	public int size() {
		return size;
	}
	
	
	/**
	 * Za predani ključ provjerava sadrži li kolekcija par koji
	 * ima takav ključ.
	 * @param key ključ za koji se provjerava postoji li par u kolekciji s tim ključem
	 * @return true ako par s predanim ključem postoji, false inače
	 */
	public boolean containsKey(Object key) {
		return get(key) != null ? true : false;
	}
	
	
	/**
	 * Za predanu vrijednost provjerava sadrži li kolekcija par koji
	 * ima takvu vrijednost.
	 * @param value vrijednost za koju se provjerava postoji li par u kolekciji s tom vrijednošću
	 * @return true ako par s predanom vrijednošću postoji, false inače
	 */
	public boolean containsValue(Object value) {
		for(int i = 0; i < table.length; i++) {
			if(table[i] == null) {
				continue;
			}
			
			for(TableEntry<K, V> t = table[i]; t != null; t = t.next) {
				if(t.value.equals(value)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	
	/**
	 * Provjerava jeli kolekcija prazna.
	 * @return true ako je kolekcija prazna, inače false
	 */
	public boolean isEmpty() {
		return size < 1;
	}
	
	
	
	/**
	 * Uklanja iz kolekcije par za predani ključ, ako takav par postoji.
	 * @param key ključ para kojeg želimo ukloniti iz kolekcije. Ako je kljč null
	 * metoda ne radi ništa
	 * @return vrijednost uklonjenog para ako par postoji, null ako par ne postoji u kolekciji ili
	 * je predana vrijednost ključa null
	 */
	public V remove(Object key) {
		if(key == null) {
			return null;
		}
		int slot = Math.abs(key.hashCode() % table.length);
		if(table[slot] == null) {
			return null;
		}
		if(table[slot].key.equals(key)) {
			V oldValue = table[slot].value;
			if(table[slot].next == null) {
				table[slot] = null;
				size--;
				modificationCount++;
				return oldValue;
			}
			table[slot] = table[slot].next;
			size--;
			modificationCount++;
			return oldValue;
		}
		
		for(TableEntry<K, V> t = table[slot]; t != null; t = t.next) {
			if(t.next != null && t.next.key.equals(key)) {
				V oldValue = t.next.value;
				t.next = t.next.next;
				size--;
				modificationCount++;
				return oldValue;
			}
		}
		
		return null;
		
	}
	
	
	@Override
	public String toString() {
		String s = "[";
		int checkedElements = 0;
		for(int i = 0; i < table.length; i++) {
			if(table[i] == null) {
				continue;
			}
			
			for(TableEntry<K, V> t = table[i]; t != null; t = t.next) {
				if(t != null && checkedElements != size-1) {
					s += t.key + "=" + t.value + "," + " ";
					checkedElements++;
				} else {
					s += t.key + "=" + t.value;
				}
				
			}
		}
		s += "]";
		return s;
	}
	
	
	/**
	 * Kolekciju kopira u polje referenci veličine trenutnog
	 * broja elemenata u kolekciji. Elementi kolekcije se u polje pohranjuju redoslijedom
	 * kojim se nalaze u kolekciji.
	 * @return polje referenci na pohranjene parove
	 */
	@SuppressWarnings("unchecked")
	public TableEntry<K, V>[] toArray() {
		TableEntry<K, V>[] newArray = (TableEntry<K, V>[]) new TableEntry[size];
		int currentElement = 0;
		
		for(int i = 0; i < table.length; i++) {
			if(table[i] == null) {
				continue;
			}
			
			for(TableEntry<K, V> t = table[i]; t != null; t = t.next) {
				if(t != null) {
					TableEntry<K, V> tmp = new TableEntry<K, V>(t.key, t.value);
					newArray[currentElement] = tmp;
					currentElement++;
				}
			}
		}
		
		return newArray;
	}
	
	
	
	/**
	 * Metoda koja se poziva kada je napunjenost kolekcije veća ili jednaka 75%.
	 * Poduplava kapacitet kolekcije.
	 */
	@SuppressWarnings("unchecked")
	private void changeTableSize() {
		TableEntry<K, V>[] newTable = (TableEntry<K, V>[]) new TableEntry[table.length*2];
		TableEntry<K, V>[] array = this.toArray();
		int slot;
		
		for(int i = 0; i < array.length; i++) {
			slot = Math.abs(array[i].getKey().hashCode() % newTable.length);
			if(newTable[slot] == null) {
				newTable[slot] = array[i];
				continue;
			}
			for(TableEntry<K, V> t = newTable[slot]; t != null; t = t.next) {
				if(t.next == null) {
					t.next = array[i];
					break;
				}
			}
		}
		table = newTable;
	}
	
	
	
	/**
	 * Uklanja sve elemente kolekcije.
	 */
	public void clear() {
		for(int i = 0; i < table.length; i++) {
			if(table[i] == null) {
				continue;
			}
			table[i] = null;
		}
		modificationCount++;
		size = 0;
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl(this);
	}
	
	/**
	 * Implementacija iteratora nad kolekcijom {@link SimpleHashtable}
	 * @author dario
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		private SimpleHashtable<K, V> hashtable;
		private int currentSlot;
		private TableEntry<K, V> currentElement;
		private int modificationCount;
		private int removeOnSameElementCounter;
		
		
		public IteratorImpl(SimpleHashtable<K, V> hashtable) {
			this.hashtable = hashtable;
			currentSlot = -1;
			currentElement = null;
			modificationCount = hashtable.modificationCount;
			removeOnSameElementCounter = 0;
		}
		
		@Override
		public boolean hasNext() {
			if(modificationCount != hashtable.modificationCount) {
				throw new ConcurrentModificationException("Kolekcija je promjenjena!");
			}
			
			if(currentSlot == hashtable.table.length-1 && currentElement.next == null) {
				return false;
			}
			return true;
		}

		@Override
		public TableEntry<K, V> next() {
			if(modificationCount != hashtable.modificationCount) {
				throw new ConcurrentModificationException("Kolekcija je promjenjena!");
			}
						
			if(currentElement == null) {
				++currentSlot;
				if(currentSlot >= hashtable.table.length) {
					throw new NoSuchElementException("Nema više elemenata!");
				}
				currentElement = hashtable.table[currentSlot];
			} else if(currentElement.next == null) {
				++currentSlot;
				if(currentSlot >= hashtable.table.length) {
					throw new NoSuchElementException("Nema više elemenata!");
				}
				currentElement = hashtable.table[currentSlot];
			} else {
				currentElement = currentElement.next;
			}
			
			removeOnSameElementCounter = 0;
			
			while(hashtable.table[currentSlot] == null) {
				currentSlot++;
				currentElement = hashtable.table[currentSlot];
			}
			
			if(currentElement != null) {
				return currentElement;
			} else {
				throw new NoSuchElementException("Nema više elemenata!");
			}
		}
		
		@Override
		public void remove() {
			if(modificationCount != hashtable.modificationCount) {
				throw new ConcurrentModificationException("Kolekcija je promjenjena!");
			}
			removeOnSameElementCounter++;
			if(removeOnSameElementCounter > 1) {
				throw new IllegalStateException("Metoda remove se ne smije pozivati za isti element više od jedanput!");
			}
			hashtable.remove(currentElement.key);
			modificationCount++;
		}
		
	}
	
	
	
	
	
	
	

}
