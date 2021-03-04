package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Modelira naredbu rotiranja kornjače.
 * @author dario
 *
 */
public class RotateCommand implements Command {

	private double angle;
	
	public RotateCommand(double angle) {
		this.angle = angle;
	}

	
	/**
	 * Modificira vektor smjera kornjače.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		Vector2D newDirection = state.getDirection().rotated(angle);
		state.setDirection(newDirection);
	}
	
}
