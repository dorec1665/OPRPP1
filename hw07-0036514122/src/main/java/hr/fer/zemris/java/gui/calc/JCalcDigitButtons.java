package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.util.function.Consumer;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Omotač oko svih gumba koji predstavljaju znamenku(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).
 * @author dario
 *
 */
public class JCalcDigitButtons {
	
	private CalcModel calcModel;
	
	public final JCalcDigitButton zero;
	public final JCalcDigitButton one;
	public final JCalcDigitButton two;
	public final JCalcDigitButton three;
	public final JCalcDigitButton four;
	public final JCalcDigitButton five;
	public final JCalcDigitButton six;
	public final JCalcDigitButton seven;
	public final JCalcDigitButton eight;
	public final JCalcDigitButton nine;
	
	/**
	 * Konstruktor koji inicijalizira sve gumbe.
	 * @param calcModel
	 */
	public JCalcDigitButtons(CalcModel calcModel) {
		this.calcModel = calcModel;
		
		zero = new JCalcDigitButton("0", cdb -> cdb.insertDigit(0));
		one = new JCalcDigitButton("1", cdb -> cdb.insertDigit(1));
		two = new JCalcDigitButton("2", cdb -> cdb.insertDigit(2));
		three = new JCalcDigitButton("3", cdb -> cdb.insertDigit(3));
		four = new JCalcDigitButton("4", cdb -> cdb.insertDigit(4));
		five = new JCalcDigitButton("5", cdb -> cdb.insertDigit(5));
		six = new JCalcDigitButton("6", cdb -> cdb.insertDigit(6));
		seven = new JCalcDigitButton("7", cdb -> cdb.insertDigit(7));
		eight = new JCalcDigitButton("8", cdb -> cdb.insertDigit(8));
		nine = new JCalcDigitButton("9", cdb -> cdb.insertDigit(9));
		
		
	}


	/**
	 * Razred koji modelira jedan pojedincačan gumb razreda {@link JCalcDigitButton}.
	 * @author dario
	 *
	 */
	private class JCalcDigitButton extends JButton {
		
		private static final long serialVersionUID = 1L;
		
		
		/**
		 * Konstruktor koji stvara gumb, te mu postavlja tekst i
		 * postavlja promatrača koji izvodi akciju u ovisnosti o kojem
		 * se gumbu radi.
		 * @param text tekst gumba
		 * @param consumer akcija koju gumb izvodi
		 */
		public JCalcDigitButton(String text, Consumer<CalcModel> consumer) {
			this.setText(text);
			this.setFont(this.getFont().deriveFont(30f));
			this.setOpaque(true);
			//this.setBackground(Color.CYAN);
			this.addActionListener(e -> {
				consumer.accept(calcModel);
			});
		}
	}
}
