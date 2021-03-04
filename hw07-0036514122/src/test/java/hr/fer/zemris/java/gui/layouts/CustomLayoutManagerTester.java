package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;


public class CustomLayoutManagerTester {

	
	@Test
	public void testRowAndColumnLimits() {

		JPanel p = new JPanel(new CalcLayout());
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("test"), new RCPosition(0, 2)));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("test"), new RCPosition(6, 2)));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("test"), new RCPosition(2, 0)));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("test"), new RCPosition(2, 8)));
	}
	
	
	@Test
	public void testAddingComponentWhereDisplayIs() {
		JPanel p = new JPanel(new CalcLayout());
		
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("test"), new RCPosition(1, 2)));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("test"), new RCPosition(1, 3)));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("test"), new RCPosition(1, 4)));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("test"), new RCPosition(1, 5)));

	}
	
	
	@Test
	public void testSamePositionDifferentComponent() {
		JPanel p = new JPanel(new CalcLayout());
		
		p.add(new JLabel("test1"), new RCPosition(1, 7));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("test2"), new RCPosition(1, 7)));
	}
	
	
	@Test
	public void testPrefferedLayoutSize1() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(10, 30));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(20, 15));
		p.add(l1, new RCPosition(2, 2));
		p.add(l2, new RCPosition(3, 3));
		Dimension d = p.getPreferredSize();
		assertEquals(new Dimension(152, 158), d);
		
	}
	
	@Test
	public void testPrefferedLayoutSize2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(108, 15));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(16, 30));
		p.add(l1, new RCPosition(1, 1));
		p.add(l2, new RCPosition(3, 3));
		Dimension d = p.getPreferredSize();
		assertEquals(new Dimension(152, 158), d);
		
	}
	
	
}
