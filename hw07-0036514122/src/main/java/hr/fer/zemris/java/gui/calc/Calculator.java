package hr.fer.zemris.java.gui.calc;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;


/**
 * Predstavlja prozor koji prikaziva i iscrtava kalkulator.
 * @author dario
 *
 */
public class Calculator extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private JLabel display;
	private JCheckBox invert;
	private CalcModel calcModel;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator(new CalcModelImpl()).setVisible(true);
		});
	}
	
	/**
	 * Konstruktor.
	 * @param calcModel implementracija modela kalkulatora
	 */
	public Calculator(CalcModel calcModel) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(750, 400);
		setTitle("Java Calculator v1.0");
		this.calcModel = calcModel;
		initGUI();
	}

	/**
	 * Stvara i pozicionira komponente.
	 */
	private void initGUI() {
		this.setLayout(new CalcLayout(5));
		JCalcDigitButtons digits = new JCalcDigitButtons(calcModel);
		JCalcBinaryOperButtons binaryOperators = new JCalcBinaryOperButtons(calcModel);
		JCalcUnaryOperButtons unaryOperators = new JCalcUnaryOperButtons(calcModel);
		JCalcOtherButtons other = new JCalcOtherButtons(calcModel);
		
		
		display = new JLabel("0", SwingConstants.RIGHT);
		display.setOpaque(true);
		display.setBackground(Color.YELLOW);
		display.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		display.setFont(display.getFont().deriveFont(30f));
		calcModel.addCalcValueListener(new CalcValueListener() {
			
			@Override
			public void valueChanged(CalcModel model) {
				display.setText(model.toString());
				
			}
		});
		
		invert = new JCheckBox("Inv");
		invert.addActionListener(e -> {
			unaryOperators.invert();
		});
		
		this.add(display, new RCPosition(1, 1));
		
		this.add(binaryOperators.jednako, new RCPosition(1, 6));
		this.add(binaryOperators.dijeli, new RCPosition(2, 6));
		this.add(binaryOperators.puta, new RCPosition(3, 6));
		this.add(binaryOperators.minus, new RCPosition(4, 6));
		this.add(binaryOperators.plus, new RCPosition(5, 6));

		this.add(other.clr, new RCPosition(1, 7));
		this.add(other.reset, new RCPosition(2, 7));
		this.add(other.predznak, new RCPosition(5, 4));
		this.add(other.decTocka, new RCPosition(5, 5));
		this.add(other.push, new RCPosition(3, 7));
		this.add(other.pop, new RCPosition(4, 7));
		
		this.add(unaryOperators.jedanKrozX, new RCPosition(2, 1));
		this.add(unaryOperators.sin, new RCPosition(2, 2));
		this.add(unaryOperators.log, new RCPosition(3, 1));
		this.add(unaryOperators.cos, new RCPosition(3, 2));
		this.add(unaryOperators.ln, new RCPosition(4, 1));
		this.add(unaryOperators.tan, new RCPosition(4, 2));
		this.add(unaryOperators.eksp, new RCPosition(5, 1));
		this.add(unaryOperators.ctg, new RCPosition(5, 2));

		this.add(digits.seven, new RCPosition(2, 3));
		this.add(digits.eight, new RCPosition(2, 4));
		this.add(digits.nine, new RCPosition(2, 5));
		this.add(digits.four, new RCPosition(3, 3));
		this.add(digits.five, new RCPosition(3, 4));
		this.add(digits.six, new RCPosition(3, 5));
		this.add(digits.one, new RCPosition(4, 3));
		this.add(digits.two, new RCPosition(4, 4));
		this.add(digits.three, new RCPosition(4, 5));
		this.add(digits.zero, new RCPosition(5, 3));


		this.add(invert, new RCPosition(5, 7));
	}
}
