package hr.fer.zemris.java.hw05.shell;

/**
 * Iznimka koja se može dogoditi pri radu MyShell programa.
 * @author dario
 *
 */
public class ShellIOException extends RuntimeException{

	public ShellIOException(String message) {
		super(message);
	}
}
