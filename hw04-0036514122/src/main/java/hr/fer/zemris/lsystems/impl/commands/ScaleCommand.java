package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Modelira duljinu efektivnog pomaka kornjače.
 * @author dario
 *
 */
public class ScaleCommand implements Command {

	private double factor;
	
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

	
	/**
	 * Modificira efektivni pomak kornjače.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		double newStep = state.getStep() * factor;
		state.setStep(newStep);
	}
	
	
	
}
