package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTester {

	
	private ArrayIndexedCollection arrayCollection;
	
	@Test
	public void testFirstConstructorSizeVariable() {
		arrayCollection = new ArrayIndexedCollection();
		assertEquals(0, arrayCollection.size());
	}
	
	@Test
	public void testFirstConstructorElementsCapacity() {
		arrayCollection = new ArrayIndexedCollection();
		assertEquals(16, arrayCollection.elementsCapacity());
	}
	
	@Test 
	public void testSecondConstructorSizeVariable() {
		arrayCollection = new ArrayIndexedCollection(7);
		assertEquals(0, arrayCollection.size());
	}
	
	@Test 
	public void testSecondConstructorElementsCapacity() {
		arrayCollection = new ArrayIndexedCollection(1);
		assertEquals(1, arrayCollection.elementsCapacity());
	}
	
	@Test
	public void testSecondConstructorException() {
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(0));
	}
	
	@Test
	public void testThirdConstructorNullPointerException() {
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
	}
	
	@Test
	public void testThirdConstructorForEachAddAll() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(3);
		collection.add(12);
		collection.add(12.345);
		collection.add("Jastuk");
		
		arrayCollection = new ArrayIndexedCollection(collection);
		
		assertArrayEquals(collection.getElements(), arrayCollection.getElements());
	}
	
	@Test 
	public void testFourthConstructorIllegalArgumentException() {
		arrayCollection = new ArrayIndexedCollection(3);
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(arrayCollection, -50));
	}
	
	@Test
	public void testFourthConstructorNullPointerException() {
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null, 5));
	}
	
	@Test
	public void testFourthConstructorForEachAddAll() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(4);
		collection.add(12);
		collection.add(12.345);
		collection.add("Jastuk");
		
		arrayCollection = new ArrayIndexedCollection(collection, 4);
		
		assertArrayEquals(collection.getElements(), arrayCollection.getElements());
	}
	
	@Test
	public void testFourthConstructorSizeChange() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(7);
		collection.add(12);
		collection.add(12.345);
		collection.add("Jastuk");
		collection.add(12.345);
		collection.add("Jastuk");
		
		arrayCollection = new ArrayIndexedCollection(collection, 4);
		
		assertEquals(5, arrayCollection.elementsCapacity());
	}
	
	@Test
	public void testGetMethodShouldThrowIndexOutOfBoundException() {
		arrayCollection = new ArrayIndexedCollection(4);
		arrayCollection.add(12);

		
		assertThrows(IndexOutOfBoundsException.class, () -> arrayCollection.get(42));
		assertThrows(IndexOutOfBoundsException.class, () -> arrayCollection.get(-100));
	}
	
	
	@Test
	public void testAddMethodShouldThrowNullPointerException() {
		arrayCollection = new ArrayIndexedCollection();
		assertThrows(NullPointerException.class, () -> arrayCollection.add(null));
	}
	
	@Test
	public void testAddMethodAddingFirstElement() {
		arrayCollection = new ArrayIndexedCollection(2);
		arrayCollection.add(12);
		
		assertEquals(12, arrayCollection.get(0));
	}
	
	@Test
	public void testAddGetMethodsFunctionalityWithoutExpandingArray() {
		arrayCollection = new ArrayIndexedCollection(4);
		arrayCollection.add(12);
		arrayCollection.add(12.345);
		arrayCollection.add("Jastuk");
		
		assertEquals(12, arrayCollection.get(0));
		assertEquals(12.345, arrayCollection.get(1));
		assertEquals("Jastuk", arrayCollection.get(2));
	}
	
	@Test
	public void testDoubleTheArraySizeMethodCapacity() {
		arrayCollection = new ArrayIndexedCollection(2);
		arrayCollection.add(12);
		arrayCollection.add(12.345);
		arrayCollection.add("Jastuk");
		arrayCollection.add("Pero");
		arrayCollection.add(true);
		
		assertEquals(8, arrayCollection.elementsCapacity());
	}
	
	@Test
	public void testDoubleTheArraySizeMethodElementCopy() {
		arrayCollection = new ArrayIndexedCollection(2);
		arrayCollection.add(12);
		arrayCollection.add(12.345);
		arrayCollection.add("Jastuk");
		arrayCollection.add("Pero");
		arrayCollection.add(true);
		
		
		assertEquals(12, arrayCollection.get(0));
		assertEquals(12.345, arrayCollection.get(1));
		assertEquals("Jastuk", arrayCollection.get(2));
		assertEquals("Pero", arrayCollection.get(3));
		assertEquals(true, arrayCollection.get(4));
	}
	
	@Test
	public void testContainsMethodFunctionality() {
		arrayCollection = new ArrayIndexedCollection(2);
		arrayCollection.add(12);
		arrayCollection.add(12.345);
		arrayCollection.add("Jastuk");
		arrayCollection.add("Pero");
		arrayCollection.add(true);
		
		assertTrue(arrayCollection.contains("Pero"));
		assertFalse(arrayCollection.contains(null));
	}
	
	@Test
	public void testToArrayFunctionality() {
		arrayCollection = new ArrayIndexedCollection(5);
		arrayCollection.add(12);
		arrayCollection.add(12.345);
		arrayCollection.add("Jastuk");
		arrayCollection.add("Pero");
		arrayCollection.add(true);
		
		Object[] testingArray = arrayCollection.toArray();
		assertArrayEquals(testingArray, arrayCollection.getElements());
	}
	
	@Test
	public void testInsertMethodFuncionality() {
		arrayCollection = new ArrayIndexedCollection(3);
		arrayCollection.add(12);
		arrayCollection.add(12.345);
		arrayCollection.add("Jastuk");
		arrayCollection.insert(false, 1);
		
		assertEquals(12, arrayCollection.get(0));
		assertEquals(false, arrayCollection.get(1));
		assertEquals(12.345, arrayCollection.get(2));
		assertEquals("Jastuk", arrayCollection.get(3));
		
	}
	
	@Test
	public void testInsertMethodWithoutAddMethod() {
		arrayCollection = new ArrayIndexedCollection(1);
		arrayCollection.insert(3, 0);
		arrayCollection.insert(6, 0);
		arrayCollection.insert("abcde", 0);
		arrayCollection.insert(false, 0);
		
		assertEquals(false, arrayCollection.get(0));
		assertEquals("abcde", arrayCollection.get(1));
		assertEquals(6, arrayCollection.get(2));
		assertEquals(3, arrayCollection.get(3));
		
	}
	
	@Test
	public void testInsertMethodShouldThrowNullPointerException() {
		arrayCollection = new ArrayIndexedCollection();
		assertThrows(NullPointerException.class, () -> arrayCollection.insert(null, 2));
	}
	
	@Test
	public void testInsertMethodShouldThrowIndexOutOfBoundException() {
		arrayCollection = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> arrayCollection.insert(10, -1));
		assertThrows(IndexOutOfBoundsException.class, () -> arrayCollection.insert(10, 2));
	}
	
	
	@Test
	public void testRemoveMethodShouldThrowIndexOutOfBoundException() {
		arrayCollection = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> arrayCollection.remove(0));
		assertThrows(IndexOutOfBoundsException.class, () -> arrayCollection.remove(2));
	}
	
	@Test
	public void testRemoveMethodFunctionality() {
		arrayCollection = new ArrayIndexedCollection(1);
		arrayCollection.insert(3, 0);
		arrayCollection.insert(6, 1);
		arrayCollection.insert("abcde", 1);
		arrayCollection.insert(false, 3);
		arrayCollection.remove(2);
		arrayCollection.insert(6, 1);
		arrayCollection.remove(1);
		arrayCollection.remove(0);
		
		assertEquals("abcde", arrayCollection.get(0));
		assertEquals(false, arrayCollection.get(1));
		assertEquals(2, arrayCollection.size());	
	}
	
	@Test
	public void testClearMethodFunctionality() {
		arrayCollection = new ArrayIndexedCollection(1);
		arrayCollection.insert(3, 0);
		arrayCollection.insert(6, 1);
		arrayCollection.insert("abcde", 1);
		arrayCollection.insert(false, 3);
		arrayCollection.remove(2);
		arrayCollection.insert(6, 1);
		arrayCollection.remove(1);
		arrayCollection.remove(0);
		
		arrayCollection.clear();
		
		for(int i = 0, n = arrayCollection.size(); i < n; i++) {
			assertNull(arrayCollection.get(i));
		}
	}
	
	@Test
	public void testIndexOfMethodWhenNullIsArgument() {
		arrayCollection = new ArrayIndexedCollection(3);
		assertEquals(-1, arrayCollection.indexOf(null));
	}
	
	@Test
	public void testIndexOfMethodFuncionality() {
		arrayCollection = new ArrayIndexedCollection(3);
		arrayCollection.add(12);
		arrayCollection.add(12.345);
		arrayCollection.add("Jastuk");
		arrayCollection.insert(false, 1);
		
		assertEquals(0, arrayCollection.indexOf(12));
		assertEquals(1, arrayCollection.indexOf(false));
		assertEquals(2, arrayCollection.indexOf(12.345));
		assertEquals(3, arrayCollection.indexOf("Jastuk"));
		assertEquals(-1, arrayCollection.indexOf(256));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
