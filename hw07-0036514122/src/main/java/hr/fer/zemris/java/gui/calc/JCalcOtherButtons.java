package hr.fer.zemris.java.gui.calc;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.function.Consumer;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Omotač oko svih gumba koji ne spadaju u {@link JCalcBinaryOperButtons},
 * {@link JCalcDigitButtons} ili {@link JCalcUnaryOperButtons}, a to su:
 * +/-(predznak), .(decimalna točka), clr(čisti uneseni broj), reset(resetira stanje kalkulatora
 * na početak), push(gura trenutno unesenu vrijednost na stog), pop(skida vrijednost s vrha stoga
 * s kojom se mogu obavljati operacije). 
 * @author dario
 *
 */
public class JCalcOtherButtons {

	private CalcModel calcModel;
	
	/**
	 * Postavlja predznak broju.
	 */
	public final JCalcOtherButton predznak;
	/**
	 * Stavlja decimalnu točku.
	 */
	public final JCalcOtherButton decTocka;
	/**
	 * Čisti unesen broj.
	 */
	public final JCalcOtherButton clr;
	/**
	 * Resetira stanje kalkulatora na početak.
	 */
	public final JCalcOtherButton reset;
	/**
	 * Gura trenutno unesenu vrijednost na stog.
	 */
	public final JCalcOtherButton push;
	/**
	 * Skida vrijednost s vrha stoga s kojom se mogu obavljati operacije.
	 */
	public final JCalcOtherButton pop;
	
	private Stack<Double> stog;
	
	/**
	 * Konstruktor koji inicijalizira sve gumbe.
	 * @param calcModel
	 */
	public JCalcOtherButtons(CalcModel calcModel) {
		this.calcModel = calcModel;
		stog = new Stack<>();
		
		predznak = new JCalcOtherButton("+/-", cob -> cob.swapSign());
		decTocka = new JCalcOtherButton(".", cob -> cob.insertDecimalPoint());
		clr = new JCalcOtherButton("clr", cob -> cob.clear());
		reset = new JCalcOtherButton("reset", cob -> cob.clearAll());
		push = new JCalcOtherButton("push", cob -> stog.push(cob.getValue()));
		pop = new JCalcOtherButton("pop", cob -> {
			try {
				cob.setValue(stog.pop());
			} catch (EmptyStackException e) {
				System.out.println("Empty stack!");
			}
		});
	}
	
	
	/**
	 * Razred koji modelira jedan pojedincačan gumb razreda {@link JCalcOtherButton}.
	 * @author dario
	 *
	 */
	private class JCalcOtherButton extends JButton {
		
		private static final long serialVersionUID = 1L;

		
		/**
		 * Konstruktor koji stvara gumb, te mu postavlja tekst i
		 * postavlja promatrača koji izvodi akciju u ovisnosti o kojem
		 * se gumbu radi.
		 * @param text tekst gumba
		 * @param consumer akcija koju gumb izvodi
		 */
		public JCalcOtherButton(String text, Consumer<CalcModel> consumer) {
			this.setText(text);
			this.setOpaque(true);
			//this.setBackground(Color.GRAY);
			this.addActionListener(e -> {
				consumer.accept(calcModel);
			});
		}

	}
}
