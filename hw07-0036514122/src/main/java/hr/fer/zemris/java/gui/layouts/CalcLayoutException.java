package hr.fer.zemris.java.gui.layouts;


/**
 * Iznimka koja nastaje pri razmještanju elementa kod
 * {@link CalcLayout} upravljača razmještaja.
 * @author dario
 *
 */
public class CalcLayoutException extends RuntimeException {

	public CalcLayoutException(String message) {
		super(message);
	}
}
