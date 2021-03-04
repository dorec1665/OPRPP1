package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class DictionaryTester {

	
	@Test
	public void testDictionaryPairNullKey() {
		Dictionary<String, Integer> dictionary = new Dictionary<>(3);
		
		assertThrows(NullPointerException.class, () -> dictionary.put(null, 7));
	}
	
	@Test
	public void testSizeMethod() {
		Dictionary<String, Integer> dictionary = new Dictionary<>(3);
		
		dictionary.put("Pero", 2);
		dictionary.put("Ivo", 3);
		
		assertEquals(2, dictionary.size());
	}
	
	@Test
	public void testIsEmptyMethod() {
		Dictionary<String, Integer> dictionary = new Dictionary<>(3);

		assertTrue(dictionary.isEmpty());
	}
	
	@Test
	public void testClearMethod() {
		Dictionary<String, Integer> dictionary = new Dictionary<>(3);
		
		dictionary.put("Pero", 2);
		dictionary.put("Ivo", 3);
		dictionary.clear();
		
		assertTrue(dictionary.isEmpty());
	}
	
	@Test
	public void testPutAndGetMethod() {
		Dictionary<String, Integer> dictionary = new Dictionary<>(3);

		dictionary.put("Pero", 2);
		dictionary.put("Ivo", 3);
		dictionary.put("Ivo", 10);
		
		assertEquals(2, dictionary.get("Pero"));
		assertEquals(10, dictionary.get("Ivo"));
		assertEquals(2, dictionary.size());
	}
	
	
	@Test
	public void testPutMethodReturn() {
		Dictionary<String, Integer> dictionary = new Dictionary<>(3);

		Integer returns1 = dictionary.put("Pero", 2);
		Integer returns2 = dictionary.put("Ivo", 3);
		Integer returns3 = dictionary.put("Ivo", 10);
		
		assertNull(returns1);
		assertNull(returns2);
		assertEquals(3, returns3);
	}
	
	@Test
	public void testRemoveMethod() {
		Dictionary<String, Integer> dictionary = new Dictionary<>(3);

		dictionary.put("Pero", 2);
		dictionary.put("Ivo", 3);
		dictionary.put("Ivo", 10);
		
		assertEquals(2, dictionary.remove("Pero"));
		assertEquals(10, dictionary.remove("Ivo"));
		assertNull(dictionary.remove("Ivo"));
		assertEquals(0, dictionary.size());
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
