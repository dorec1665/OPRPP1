package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Modelira naredbu bojanja.
 * @author dario
 *
 */
public class ColorCommand implements Command {

	private Color color;
	
	public ColorCommand(Color color) {
		this.color = color;
	}
	
	/**
	 * U trenutno stanje kornjače zapisuje boju.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setColor(color);
	}
}
