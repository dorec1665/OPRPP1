package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Modelira naredbu crtanja po prozoru.
 * @author dario
 *
 */
public class DrawCommand implements Command {

	private double step;
	
	public DrawCommand(double step) {
		this.step = step;
	}
	
	/**
	 * Računa u koju točku se kornjača mora pomjeriti, pomjera se i
	 * povlači liniju.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		Vector2D position = state.getCurrentPosition();
		Vector2D direction = state.getDirection();
		Color color = state.getColor();
		Vector2D newPosition = direction.scaled(step*state.getStep());
		newPosition = position.added(newPosition);
		
		painter.drawLine(position.getX(), position.getY(), newPosition.getX(), newPosition.getY(), color, 1f);
		
		state.setCurrentPosition(newPosition);
	}

}
