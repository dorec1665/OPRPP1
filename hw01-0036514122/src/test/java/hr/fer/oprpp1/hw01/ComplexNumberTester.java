package hr.fer.oprpp1.hw01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ComplexNumberTester {

	private ComplexNumber c;
	
	@Test
	public void testConstructorGetRealGetImaginary() {
		c = new ComplexNumber(2, 3);
		assertEquals(2, c.getReal());
		assertEquals(3, c.getImaginary());
	}
	
	@Test
	public void testGetMagnitudeAndParse() {
		c = ComplexNumber.parse("+3-4i");
		assertEquals(3, c.getReal());
		assertEquals(-4, c.getImaginary());
		assertEquals(5, c.getMagnitude());
	}
	
	@Test
	public void testGetAngle() {
		c = new ComplexNumber(1, 1);
		double epsilon = 1e-5;
		assertTrue((Math.PI/4 - c.getAngle()) < epsilon);
	}
	
	@Test
	public void testFromReal() {
		c = ComplexNumber.fromReal(2);
		assertEquals(2, c.getReal());
	}
	
	@Test
	public void testFromImaginary() {
		c = ComplexNumber.fromImaginary(2.544);
		assertEquals(2.544, c.getImaginary());
	}
	
	@Test
	public void testFromMagnitudeAndAngle() {
		c = ComplexNumber.fromMagnitudeAndAngle(2, Math.PI/4);
		double epsilon = 1e-5;
		assertTrue((Math.sqrt(2) - c.getReal()) < epsilon);
		assertTrue((Math.sqrt(2) - c.getImaginary()) < epsilon);
		
	}
	
	@Test
	public void testAddSubMulDiv() {
		c = new ComplexNumber(2, -3);
		ComplexNumber c1 = new ComplexNumber(2.2, -3.2);
		ComplexNumber c2 = new ComplexNumber(1, -1);
		ComplexNumber c3 = c.add(c1).sub(c2).mul(c1).div(c);
		double epsilon = 1e-5;
		assertTrue((3.5261538 - c3.getReal()) < epsilon);
		assertTrue((-5.55076923 - c3.getImaginary()) < epsilon);
	}
	
	@Test
	public void testPower() {
		c = new ComplexNumber(2.25, -0.25);
		ComplexNumber c1 = c.power(5);
		double epsilon = 1e-5;
		assertTrue((50.58984375 - c1.getReal()) < epsilon);
		assertTrue((-31.24609375 - c1.getImaginary()) < epsilon);
	}
	
	@Test
	public void testRoot() {
		c = new ComplexNumber(1, 0);
		ComplexNumber[] c1 = c.root(6);
		double epsilon = 1e-5;
		assertTrue((1 - c1[0].getReal()) < epsilon);
		assertTrue((0 - c1[0].getImaginary()) < epsilon);
		assertTrue((0.5 - c1[1].getReal()) < epsilon);
		assertTrue((0.8660254038 - c1[1].getImaginary()) < epsilon);
		assertTrue((-0.5 - c1[4].getReal()) < epsilon);
		assertTrue((-0.8660254038 - c1[4].getImaginary()) < epsilon);
	}
	
	@Test
	public void testToString() {
		c = new ComplexNumber(2.2, -1.2);
		assertEquals("2.2-1.2i", c.toString());
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
