package hr.fer.zemris.java.gui.calc;

import java.util.function.Consumer;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Omotač oko svih gumba koji su dostupni u razredu {@link Calculator},
 * a predstavljaju unarne operacije i njihove inverze.
 * @author dario
 *
 */
public class JCalcUnaryOperButtons {

	private CalcModel calcModel;
	
	public final JCalcUnaryOperButton jedanKrozX;
	public final JCalcUnaryOperButton log;
	public final JCalcUnaryOperButton ln;
	public final JCalcUnaryOperButton eksp;
	public final JCalcUnaryOperButton sin;
	public final JCalcUnaryOperButton cos;
	public final JCalcUnaryOperButton tan;
	public final JCalcUnaryOperButton ctg;
	
	private boolean checkbox = false;
	
	/**
	 * Konstruktor koji inicijalizira sve gumbe.
	 * @param calcModel
	 */
	public JCalcUnaryOperButtons(CalcModel calcModel) {
		this.calcModel = calcModel;
		
		jedanKrozX = new JCalcUnaryOperButton(coup -> coup.setValue(1/coup.getValue()), "1/x");
		log = new JCalcUnaryOperButton(coup -> coup.setValue(Math.log10(coup.getValue())), "log");
		ln = new JCalcUnaryOperButton(coup -> coup.setValue(Math.log(coup.getValue())), "ln");
		eksp = new JCalcUnaryOperButton(coup -> {
			if(coup.hasFrozenValue()) {
				throw new CalculatorInputException();
			}
			coup.freezeValue(String.valueOf(coup.getValue()));
			if(coup.isActiveOperandSet()) {
				coup.setValue(coup.getPendingBinaryOperation().applyAsDouble(coup.getActiveOperand(), coup.getValue()));
			}
			
			coup.setPendingBinaryOperation((a, b) -> Math.pow(a, b));
			coup.setActiveOperand(coup.getValue());
			coup.clear();
		}, "x^n");
		sin = new JCalcUnaryOperButton(coup -> coup.setValue(Math.sin(coup.getValue())), "sin");
		cos = new JCalcUnaryOperButton(coup -> coup.setValue(Math.cos(coup.getValue())), "cos");
		tan = new JCalcUnaryOperButton(coup -> coup.setValue(Math.tan(coup.getValue())), "tan");
		ctg = new JCalcUnaryOperButton(coup -> coup.setValue(1/Math.tan(coup.getValue())), "ctg");
		
	}
	
	
	/**
	 * Mjenja stanje svih gumba, tj. ako su gumbi izvodili
	 * inverzne operacije pozivom ove funkciju pritisak na gumb obavlja
	 * neinverznu operaciju i obratno.
	 */
	public void invert() {
		checkbox = !checkbox;
		if(checkbox) {
			inverted();
		} else {
			notInverted();
		}
	}
	
	
	/**
	 * Postavlja gumb da obavlja unarnu operaciju, a ne njen inverz.
	 */
	private void notInverted() {
		ctg.setConsumer(coup -> coup.setValue(1/Math.tan(coup.getValue())));
		tan.setConsumer(coup -> coup.setValue(Math.tan(coup.getValue())));
		cos.setConsumer(coup -> coup.setValue(Math.cos(coup.getValue())));
		sin.setConsumer(coup -> coup.setValue(Math.sin(coup.getValue())));
		eksp.setConsumer( coup -> {
			if(coup.hasFrozenValue()) {
				throw new CalculatorInputException();
			}
			coup.freezeValue(String.valueOf(coup.getValue()));
			if(coup.isActiveOperandSet()) {
				coup.setValue(coup.getPendingBinaryOperation().applyAsDouble(coup.getActiveOperand(), coup.getValue()));
			}
			
			coup.setPendingBinaryOperation((a, b) -> Math.pow(a, b));
			coup.setActiveOperand(coup.getValue());
			coup.clear();
		});
		ln.setConsumer(coup -> coup.setValue(Math.log(coup.getValue())));
		log.setConsumer(coup -> coup.setValue(Math.log10(coup.getValue())));
		
		jedanKrozX.setText("1/x");
		log.setText("log");
		ln.setText("ln");
		eksp.setText("x^n");
		sin.setText("sin");
		cos.setText("cos");
		tan.setText("tan");
		ctg.setText("ctg");
	}
	
	
	/**
	 * Postavlja gumb da obavlja inverz operacije.
	 */
	private void inverted() {
		ctg.setConsumer(coup -> coup.setValue(Math.PI / 2 - Math.atan(coup.getValue())));
		tan.setConsumer(coup -> coup.setValue(Math.atan(coup.getValue())));
		cos.setConsumer(coup -> coup.setValue(Math.acos(coup.getValue())));
		sin.setConsumer(coup -> coup.setValue(Math.asin(coup.getValue())));
		eksp.setConsumer(coup -> {
			if(coup.hasFrozenValue()) {
				throw new CalculatorInputException();
			}
			coup.freezeValue(String.valueOf(coup.getValue()));
			if(coup.isActiveOperandSet()) {
				coup.setValue(coup.getPendingBinaryOperation().applyAsDouble(coup.getActiveOperand(), coup.getValue()));
			}
			
			coup.setPendingBinaryOperation((a, b) -> Math.pow(a, 1/b));
			coup.setActiveOperand(coup.getValue());
			coup.clear();
		});
		ln.setConsumer(coup -> coup.setValue(Math.pow(Math.E, coup.getValue())));
		log.setConsumer(coup -> coup.setValue(Math.pow(10, coup.getValue())));
		
		
		log.setText("10^x");
		ln.setText("e^x");
		eksp.setText("x^(1/n)");
		sin.setText("arcsin");
		cos.setText("arccos");
		tan.setText("arctan");
		ctg.setText("arcctg");
	}
	
	
	/**
	 * Razred koji modelira jedan pojedincačan gumb razreda {@link JCalcUnaryOperButtons}.
	 * @author dario
	 *
	 */
	private class JCalcUnaryOperButton extends JButton {
		
		private static final long serialVersionUID = 1L;
		
		Consumer<CalcModel> consumer;
		
		/**
		 * Konstruktor koji stvara gumb, te mu postavlja tekst i
		 * postavlja promatrača koji izvodi akciju u ovisnosti o kojem
		 * se gumbu radi.
		 * @param text tekst gumba
		 * @param consumer akcija koju gumb izvodi
		 */
		public JCalcUnaryOperButton(Consumer<CalcModel> consumer, String text) {
			this.setOpaque(true);
			this.setText(text);
			this.consumer = consumer;
			//this.setBackground(Color.GRAY);
			this.addActionListener(e -> {
				this.consumer.accept(calcModel);
			});
		}
		
		/**
		 * Mjenja akciju koju gumb treba obavljat.
		 * @param consumer akcija
		 */
		public void setConsumer(Consumer<CalcModel> consumer) {
			this.consumer = consumer;
		}

	}
}
