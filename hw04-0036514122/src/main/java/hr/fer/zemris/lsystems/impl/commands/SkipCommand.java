package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Modelira pomjeranje kornjače.
 * @author dario
 *
 */
public class SkipCommand implements Command {

	private double step;
	
	public SkipCommand(double step) {
		this.step = step;
	}
	
	/**
	 * Računa u koju točku se kornjača mora pomjeriti, pomjera se,
	 * ali ne povlači liniju.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		Vector2D position = state.getCurrentPosition();
		Vector2D direction = state.getDirection();
		Vector2D newPosition = direction.scaled(step);
		newPosition = position.added(direction);
				
		state.setCurrentPosition(newPosition);
	}

}
