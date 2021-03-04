package hr.fer.oprpp1.custom.collections;


/**
 * Razred koji za zadani ključ pamti predanu vrijednost, u Javi poznato pod
 * imenom mapa.
 * @author dario
 *
 * @param <K>
 * @param <V>
 */
public class Dictionary<K, V> {
	
	private ArrayIndexedCollection<DictionaryPair<K, V>> array;
	
	/**
	 * Jedan zapis u razredu {@link Dictionary}.
	 * @author dario
	 *
	 * @param <K> ključ
	 * @param <V> vrijednost
	 */
	private static class DictionaryPair<K, V> {
		private K key;
		private V value;
		
		/**
		 * Konstruktor koji inicijalizira jedan zapis.
		 * @param key ključ
		 * @param value vrijednost
		 * @throws NullPointerException ako je ključ <code>null</code>
		 */
		public DictionaryPair(K key, V value) {
			if(key == null) {
				throw new NullPointerException("Vrijednost ključa ne smije biti null!");
			}
			this.key = key;
			this.value = value;
		}
		
	}
	
	
	
	/**
	 * Konstruktor kojim se inicijalizira jedan {@link Dictionary} veličine size.
	 * @param size veličina Dictionary-a
	 */
	public Dictionary(int size) {
		array = new ArrayIndexedCollection<>(size);
	}
	
	
	/**
	 * Getter za člansku varijablu array.
	 * @return člansku varijablu array
	 */
	public ArrayIndexedCollection<DictionaryPair<K, V>> getArray() {
		return array;
	}
	
	
	/**
	 * Provjera jeli {@link Dictionary} prazan.
	 * @return <code>true</code> ako je prazan, inače <code>false</code>
	 */
	public boolean isEmpty() {
		return array.isEmpty();
	}
	
	
	
	/**
	 * Vraća trenutni broj elemenata u Dictionaryju.
	 * @return trenutni broj elemenata
	 */
	public int size() {
		return array.size();
	}
	
	
	/**
	 * Uklanja sve elemente Dictionaryja.
	 */
	public void clear() {
		array.clear();
	}
	
	
	/**
	 * Ubacuje jedan par(key, value) u Dictionary. Ako u Dictionaryju postoji
	 * element sa vrijednošću key, na to mjesto se upisuje vrijednost varijable value,
	 * a stara("pregažena") vrijednost se vraća, inače se stvara novi par(key, value) te
	 * se ubacuje u {@link Dictionary}.
	 * @param key ključ
	 * @param value vrijednost
	 * @return stara vrijednost ako vrijednost key već postoji u Dictionaryju, inače
	 * <code>null</code>
	 * @throws NullPointerException ako je vrijednost key jednaka <code>null</code>
	 */
	public V put(K key, V value) {
		if(key == null) {
			throw new NullPointerException("Vrijednost ključa ne smije biti null!");
		}
		
		DictionaryPair<K, V> dp;
		IElementsGetter<DictionaryPair<K, V>> getter = array.createElementsGetter();
		
		while(getter.hasNextElement()) {
			dp = getter.getNextElement();
			if(dp.key.equals(key)) {
				V oldValue = dp.value;
				dp.value = value;
				return oldValue;
			}
		}
		
		dp = new DictionaryPair<K, V>(key, value);
		array.add(dp);
		return null;

	}
	
	
	/**
	 * Dohvaća vrijednost para(key, value) čiji je key jednak argumentu metode key.
	 * @param key ključ koji se traži 
	 * @return vrijednost para(key, value) čiji je key jednak argumentu metode key, inače
	 * <code>null</code>
	 */
	public V get(Object key) {	
		DictionaryPair<K, V> dp;
		IElementsGetter<DictionaryPair<K, V>> getter = array.createElementsGetter();
		
		while(getter.hasNextElement()) {
			dp = getter.getNextElement();
			if(dp.key.equals(key)) {
				return dp.value;
			}
		}
		
		return null;
	}
	
	
	
	/**
	 * Uklanja par(key, value) čiji je key jednak argumentu metode key.
	 * @param key ključ koji se traži
	 * @return vrijednost uklonjenog para ako se par nalazio u Dictionaryju, inače <code>null</code>
	 */
	public V remove(K key) {
		DictionaryPair<K, V> dp;
		IElementsGetter<DictionaryPair<K, V>> getter = array.createElementsGetter();
		
		while(getter.hasNextElement()) {
			dp = getter.getNextElement();
			if(dp.key.equals(key)) {
				V oldValue = dp.value;
				array.remove(array.indexOf(dp));
				return oldValue;
			}
		}
		
		return null;
	}
	
	

}
