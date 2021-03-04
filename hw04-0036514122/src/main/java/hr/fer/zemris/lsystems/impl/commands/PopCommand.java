package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Modelira brisanje jednog stanja kornjače.
 * @author dario
 *
 */
public class PopCommand implements Command {

	/**
	 * Briše jedno stannje.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}

}
