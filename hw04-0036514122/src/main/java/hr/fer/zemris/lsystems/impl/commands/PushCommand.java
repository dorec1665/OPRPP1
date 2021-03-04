package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Modelira dodavanje novog stanja.
 * @author dario
 *
 */
public class PushCommand implements Command {

	/**
	 * Kopira posljednje dodano stanje, te kopiju dodaje.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState copiedState = ctx.getCurrentState().copy();
		ctx.pushState(copiedState);
	}

}
