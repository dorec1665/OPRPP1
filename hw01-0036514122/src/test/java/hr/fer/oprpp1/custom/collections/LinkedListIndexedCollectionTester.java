package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


public class LinkedListIndexedCollectionTester {
	
	private LinkedListIndexedCollection linkedListCollection;
	
	@Test
	public void testFirstConstructorSizeVariable() {
		linkedListCollection = new LinkedListIndexedCollection();
		assertEquals(0, linkedListCollection.size());
	}
	
	@Test
	public void testSecondConstructorException() {
		assertThrows(NullPointerException.class, () -> new LinkedListIndexedCollection(null));
	}
	
	@Test
	public void testSecondConstructorForEach() {
		linkedListCollection = new LinkedListIndexedCollection();
		linkedListCollection.add(19);
		linkedListCollection.add("String");
		linkedListCollection.add(22.222);
		
		LinkedListIndexedCollection testingList = new LinkedListIndexedCollection(linkedListCollection);
		
		for(int i = 0; i < linkedListCollection.size(); i++) {
			assertEquals(testingList.get(i), linkedListCollection.get(i));
		}
		assertEquals(testingList.size(), linkedListCollection.size());
	}
	
	@Test
	public void testAddMethodShouldThrowNullPointerException() {
		linkedListCollection = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, () -> linkedListCollection.add(null));
	}
	
	@Test
	public void testAddMethodAddingFirstElement() {
		linkedListCollection = new LinkedListIndexedCollection();
		linkedListCollection.add(12);
		
		assertEquals(12, linkedListCollection.get(0));
	}
	
	@Test
	public void testAddGetMethodsFunctionality() {
		linkedListCollection = new LinkedListIndexedCollection();
		linkedListCollection.add(19);
		linkedListCollection.add("String");
		linkedListCollection.add(22.222);
		
		assertEquals(19, linkedListCollection.get(0));
		assertEquals("String", linkedListCollection.get(1));
		assertEquals(22.222, linkedListCollection.get(2));
	}
	
	@Test
	public void testGetMethodShouldThrowIndexOutOfBoundException() {
		linkedListCollection = new LinkedListIndexedCollection();
		linkedListCollection.add(100);
		
		assertThrows(IndexOutOfBoundsException.class, () -> linkedListCollection.get(3));
		assertThrows(IndexOutOfBoundsException.class, () -> linkedListCollection.get(-2));
	}
	
	@Test
	public void testContainsMethodFunctionality() {
		linkedListCollection = new LinkedListIndexedCollection();
		linkedListCollection.add(100);
		linkedListCollection.add(100);
		linkedListCollection.add(200);
		
		assertTrue(linkedListCollection.contains(100));
		assertTrue(linkedListCollection.contains(200));
		assertFalse(linkedListCollection.contains(300));
		assertFalse(linkedListCollection.contains(null));
	}
	
	@Test
	public void testToArrayMEthodFunctionality() {
		linkedListCollection = new LinkedListIndexedCollection();
		linkedListCollection.add(100);
		linkedListCollection.add(55.55);
		linkedListCollection.add("foo");
		
		Object[] testingArray = linkedListCollection.toArray();
		
		for(int i = 0; i < linkedListCollection.size(); i++) {
			assertEquals(testingArray[i], linkedListCollection.get(i));
		}
		assertEquals(testingArray.length, linkedListCollection.size());	
	}
	
	@Test
	public void testClearMethodFunctionality() {
		linkedListCollection = new LinkedListIndexedCollection();
		linkedListCollection.add(100);
		linkedListCollection.add(55.55);
		linkedListCollection.add("foo");
		
		linkedListCollection.clear();
		
		assertEquals(0, linkedListCollection.size());
	}
	
	@Test
	public void testInsertMethodShouldThrowNullPointerException() {
		linkedListCollection = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, () -> linkedListCollection.insert(null, 2));
	}
	
	@Test
	public void testInsertMethodShouldThrowIndexOutOfBoundException() {
		linkedListCollection = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> linkedListCollection.insert(10, -1));
		assertThrows(IndexOutOfBoundsException.class, () -> linkedListCollection.insert(10, 2));
	}
	
	@Test
	public void testInsertMethodFunctionality() {
		linkedListCollection = new LinkedListIndexedCollection();
		linkedListCollection.insert(100, 0);
		linkedListCollection.add(200);
		linkedListCollection.add(300);
		linkedListCollection.insert("Ivan", 2);
		linkedListCollection.insert(2, 1);
		
		assertEquals(100, linkedListCollection.get(0));
		assertEquals(2, linkedListCollection.get(1));
		assertEquals(200, linkedListCollection.get(2));
		assertEquals("Ivan", linkedListCollection.get(3));
		assertEquals(300, linkedListCollection.get(4));
		
	}
	
	@Test
	public void testInsertWithouAddMethod() {
		linkedListCollection = new LinkedListIndexedCollection();
		linkedListCollection.insert("Ivan", 0);
		linkedListCollection.insert(2, 0);
		linkedListCollection.insert(2, 1);
		linkedListCollection.insert(200, 0);
		linkedListCollection.insert("Filip", 3);
		
		assertEquals(200, linkedListCollection.get(0));
		assertEquals(2, linkedListCollection.get(1));
		assertEquals(2, linkedListCollection.get(2));
		assertEquals("Filip", linkedListCollection.get(3));
		assertEquals("Ivan", linkedListCollection.get(4));
	}
	
	@Test
	public void testRemoveMethodShouldThrowIndexOutOfBoundException() {
		linkedListCollection = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> linkedListCollection.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> linkedListCollection.remove(2));
	}
	
	@Test
	public void testRemoveMethodFunctionality() {
		linkedListCollection = new LinkedListIndexedCollection();
		linkedListCollection.insert(100, 0);
		linkedListCollection.add(200);
		linkedListCollection.add(300);
		linkedListCollection.insert("Ivan", 2);
		linkedListCollection.insert(2, 1);
		linkedListCollection.remove(0);
		linkedListCollection.remove(2);
		linkedListCollection.remove(1);
		
		assertEquals(2, linkedListCollection.get(0));
		assertEquals(300, linkedListCollection.get(1));
		assertEquals(2, linkedListCollection.size());
		
	}

	@Test
	public void testIndexOfMethodFunctionality() {
		linkedListCollection = new LinkedListIndexedCollection();
		linkedListCollection.insert(100, 0);
		linkedListCollection.add(200);
		linkedListCollection.add(300);
		linkedListCollection.insert("Ivan", 2);
		linkedListCollection.insert(2, 1);
		linkedListCollection.remove(0);
		linkedListCollection.remove(2);
		linkedListCollection.remove(1);
		
		assertEquals(-1, linkedListCollection.indexOf(null));
		assertEquals(0, linkedListCollection.indexOf(2));
		assertEquals(1, linkedListCollection.indexOf(300));
		assertEquals(-1, linkedListCollection.indexOf(150));
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
