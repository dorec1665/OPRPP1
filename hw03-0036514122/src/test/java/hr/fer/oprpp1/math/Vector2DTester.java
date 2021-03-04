package hr.fer.oprpp1.math;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class Vector2DTester {

	@Test
	public void testCopyMethod() {
		Vector2D vector1 = new Vector2D(2.2, 3.2);
		Vector2D vector2 = vector1.copy();
		assertEquals(vector1.getX(), vector2.getX());
		assertEquals(vector1.getY(), vector2.getY());
	}
	
	@Test
	public void testAddMethod() {
		Vector2D vector1 = new Vector2D(2.2, 3.2);
		Vector2D vector2 = new Vector2D(3.0, 2.0);
		
		vector1.add(vector2);
		
		assertEquals(5.2, vector1.getX());
		assertEquals(5.2, vector1.getY());
	}
	
	@Test
	public void testAddAndAddedShouldThrowException() {
		Vector2D vector1 = new Vector2D(2.2, 3.2);
		
		assertThrows(NullPointerException.class, () -> vector1.add(null));
		assertThrows(NullPointerException.class, () -> vector1.add(null));

	}
	
	@Test
	public void testAddedMethod() {
		Vector2D vector1 = new Vector2D(2.2, 3.2);
		Vector2D vector2 = vector1.added(vector1);
		
		assertEquals(4.4, vector2.getX());
		assertEquals(6.4, vector2.getY());
	}

	@Test
	public void testRotateMethod() {
		Vector2D vector1 = new Vector2D(3, 2);
		vector1.rotate(Math.PI);
		
		double epsilon = 1e-5;
		
		assertTrue((-3-vector1.getX()) < epsilon);
		assertTrue((-2-vector1.getY()) < epsilon);

	}

	@Test
	public void testRotatedMethod() {
		Vector2D vector1 = new Vector2D(3, 2);
		Vector2D vector2 = vector1.rotated(Math.PI);
		
		double epsilon = 1e-5;
		
		assertTrue((-3-vector2.getX()) < epsilon);
		assertTrue((-2-vector2.getY()) < epsilon);


	}

	@Test
	public void testScaleMethod() {
		Vector2D vector1 = new Vector2D(3, 2);
		vector1.scale(3.0);
		
		assertEquals(9.0, vector1.getX());
		assertEquals(6.0, vector1.getY());
		
	}

	@Test
	public void testScaledMethod() {
		Vector2D vector1 = new Vector2D(3, 2);
		Vector2D vector2 = vector1.scaled(3.0);
		
		assertEquals(9.0, vector2.getX());
		assertEquals(6.0, vector2.getY());
	}
}
