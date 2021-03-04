package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

/**
 * Razred koji modelira trenutno stanje kornjače.
 * @author dario
 *
 */
public class TurtleState {
	
	/**
	 * Predstavlja trenutno stanje kornjače.
	 */
	private Vector2D currentPosition;
	/**
	 * Smjer u kojem kornjača trenutno gleda.
	 */
	private Vector2D direction;
	/**
	 * Boja crtanja.
	 */
	private Color color;
	/**
	 * Efektivni pomak kornjače.
	 */
	private double step;
	
	public TurtleState(Vector2D currentPosition, Vector2D direction, Color color, double step) {
		this.currentPosition = currentPosition;
		this.direction = direction;
		this.color = color;
		this.step = step;
	}
	
	/**
	 * Kopira trenutno stanje kornjače.
	 * @return Kopirano stanje kornjače
	 */
	public TurtleState copy() {
		Vector2D copiedPosition = currentPosition.copy();
		Vector2D copiedDirection = direction.copy();
		return new TurtleState(copiedPosition, copiedDirection, color, step);
	}
	
	
	


	public void setCurrentPosition(Vector2D currentPosition) {
		this.currentPosition = currentPosition;
	}


	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}


	public void setColor(Color color) {
		this.color = color;
	}


	public void setStep(double step) {
		this.step = step;
	}


	public Vector2D getCurrentPosition() {
		return currentPosition;
	}


	public Vector2D getDirection() {
		return direction;
	}


	public Color getColor() {
		return color;
	}


	public double getStep() {
		return step;
	}
	
	

}
