package hr.fer.oprpp1.crypto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UtilsTester {

	
	@Test
	public void testHexToByteMethod() {
		byte[] b = Utils.hextobyte("01ae22");
		assertEquals(1, b[0]);
		assertEquals(-82, b[1]);
		assertEquals(34, b[2]);
		
	}
	
	@Test
	public void testHexToByteCase() {
		byte[] b = Utils.hextobyte("5E5eDDddDd");
		assertEquals(94, b[0]);
		assertEquals(94, b[1]);
		assertEquals(-35, b[2]);
		assertEquals(-35, b[3]);
		assertEquals(-35, b[4]);
	}
	
	@Test
	public void testHexToByteEmptyString() {
		byte[] b = Utils.hextobyte("");
		assertEquals(0, b.length);
	}
	
	
	@Test
	public void testByteToHex() {
		String s = Utils.bytoToHex(new byte[] {94, 94, -35, -35, -35});
		assertEquals("5e5edddddd", s);
	}
	
	
	@Test
	public void testByteToHexEmptyArray() {
		String s = Utils.bytoToHex(new byte[] {});
		assertEquals("", s);
	}
}
