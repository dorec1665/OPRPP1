package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.util.function.Consumer;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Omotač oko svih gumba koji su dostupni u razredu {@link Calculator},
 * a predstavljaju binarne operacije(+, -, *, /), te operaciju =.
 * @author dario
 *
 */
public class JCalcBinaryOperButtons {

	private CalcModel calcModel;
	
	public final JCalcBinaryOperButton plus;
	public final JCalcBinaryOperButton minus;
	public final JCalcBinaryOperButton puta;
	public final JCalcBinaryOperButton dijeli;
	public final JCalcBinaryOperButton jednako;
	
	/**
	 * Konstruktor koji inicijalizira sve gumbe.
	 * @param calcModel
	 */
	public JCalcBinaryOperButtons(CalcModel calcModel) {
		this.calcModel = calcModel;
		
		plus = new JCalcBinaryOperButton("+", cbob -> {
			if(cbob.hasFrozenValue()) {
				throw new CalculatorInputException();
			}
			cbob.freezeValue(String.valueOf(cbob.getValue()));
			if(cbob.isActiveOperandSet()) {
				cbob.setActiveOperand(cbob.getPendingBinaryOperation().
						applyAsDouble(cbob.getActiveOperand(), cbob.getValue()));
			} else {
				cbob.setActiveOperand(cbob.getValue());
			}
			
			cbob.clear();
			cbob.setPendingBinaryOperation((left, right) -> left + right);
		});
		
		minus = new JCalcBinaryOperButton("-", cbob -> {
			if(cbob.hasFrozenValue()) {
				throw new CalculatorInputException();
			}
			cbob.freezeValue(String.valueOf(cbob.getValue()));
			if(cbob.isActiveOperandSet()) {
				cbob.setActiveOperand(cbob.getPendingBinaryOperation().
						applyAsDouble(cbob.getActiveOperand(), cbob.getValue()));
			} else {
				cbob.setActiveOperand(cbob.getValue());
			}
			
			cbob.clear();
			cbob.setPendingBinaryOperation((left, right) -> left - right);
		});
		puta = new JCalcBinaryOperButton("*", cbob -> {
			if(cbob.hasFrozenValue()) {
				throw new CalculatorInputException();
			}
			cbob.freezeValue(String.valueOf(cbob.getValue()));
			if(cbob.isActiveOperandSet()) {
				cbob.setActiveOperand(cbob.getPendingBinaryOperation().
						applyAsDouble(cbob.getActiveOperand(), cbob.getValue()));
			} else {
				cbob.setActiveOperand(cbob.getValue());
			}
			
			cbob.clear();
			cbob.setPendingBinaryOperation((left, right) -> left * right);
		});
		dijeli = new JCalcBinaryOperButton("/", cbob -> {
			if(cbob.hasFrozenValue()) {
				throw new CalculatorInputException();
			}
			cbob.freezeValue(String.valueOf(cbob.getValue()));
			if(cbob.isActiveOperandSet()) {
				cbob.setActiveOperand(cbob.getPendingBinaryOperation().
						applyAsDouble(cbob.getActiveOperand(), cbob.getValue()));
			} else {
				cbob.setActiveOperand(cbob.getValue());
			}
			
			cbob.clear();
			cbob.setPendingBinaryOperation((left, right) -> left / right);
		});
		
		jednako = new JCalcBinaryOperButton("=", cbob -> {
			if(cbob.isActiveOperandSet()) {
				cbob.setValue(cbob.getPendingBinaryOperation()
						.applyAsDouble(cbob.getActiveOperand(), cbob.getValue()));
				cbob.clearActiveOperand();
				
			}
		});
	}
	
	
	/**
	 * Razred koji modelira jedan pojedinačan gumb razreda {@link JCalcBinaryOperButtons}.
	 * @author dario
	 *
	 */
	private class JCalcBinaryOperButton extends JButton {
		
		private static final long serialVersionUID = 1L;
		
		/**
		 * Konstruktor koji stvara gumb, te mu postavlja tekst i
		 * postavlja promatrača koji izvodi akciju u ovisnosti o kojem
		 * se gumbu radi.
		 * @param text tekst gumba
		 * @param consumer akcija koju gumb izvodi
		 */
		public JCalcBinaryOperButton(String text, Consumer<CalcModel> consumer) {
			this.setText(text);
			this.setOpaque(true);
			//this.setBackground(Color.GRAY);
			this.addActionListener(e -> {
				consumer.accept(calcModel);
			});
		}
	}
}
