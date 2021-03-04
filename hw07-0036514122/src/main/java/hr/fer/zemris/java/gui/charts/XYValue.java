package hr.fer.zemris.java.gui.charts;

/**
 * Razred koji modelira odnos neki dviju varijabli.
 * @author dario
 *
 */
public class XYValue {

	private int x;
	private int y;
	
	/**
	 * Konstruktor.
	 * @param x prva varijabla
	 * @param y druga varijabla
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter za prvu varijablu.
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 * Getter za drugu varijablu.
	 * @return
	 */
	public int getY() {
		return y;
	}
	
	
}
