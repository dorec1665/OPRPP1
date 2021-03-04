package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Prestavlja naredbu koja kornjača treba izvest.
 * @author dario
 *
 */
public interface Command {

	/**
	 * Pozivom ove metode kornjača izvršava naredbu.
	 * @param ctx prikaziva izvršenje naredbe
	 * @param painter crta sliku
	 */
	void execute(Context ctx, Painter painter);
}
