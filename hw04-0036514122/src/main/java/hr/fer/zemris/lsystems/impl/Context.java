package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.custom.collections.ObjectStack;

/**
 * Primjerci ovog razreda omogućavaju izvođenje postupka
 * prikazivanja fraktala.
 * @author dario
 *
 */
public class Context {

	private ObjectStack<TurtleState> turtleStack;
	
	public Context() {
		turtleStack = new ObjectStack<>();
	}
	
	
	/**
	 * Dohvaća trenutno stanje kornjače.
	 * @return Trenutno stanje kornjače
	 */
	public TurtleState getCurrentState() {
		return turtleStack.peek();
	}
	
	
	/**
	 * Dodaje novo stanje kornjače.
	 * @param state Novo stanje kornjače
	 */
	public void pushState(TurtleState state) {
		turtleStack.push(state);
	}
	
	
	/**
	 * Uklanja jedno stanje kornjače.
	 */
	public void popState() {
		turtleStack.pop();
	}
}
