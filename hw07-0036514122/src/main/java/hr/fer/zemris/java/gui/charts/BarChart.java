package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Razred koji modelira jedan stupičasti dijagram, koji je
 * prikaza na pozitivnim osima koordinatnog sustava.
 * @author dario
 *
 */
public class BarChart {

	private List<XYValue> values;
	private String xAxisText;
	private String yAxisText;
	private int yMin;
	private int yMax;
	private int space;
	
	/**
	 * Konstruktor.
	 * @param values vrijednosti na temelju kojih se generiraju stupci dijagrama
	 * @param xAxisText opis varijable na x-osi
	 * @param yAxisText opis varijable na y-osi
	 * @param yMin minimalni y koji se prikaziva na y-osi
	 * @param yMax maksimalni y koji se prikaziva na y-osi
	 * @param space razmak između dvije susjedne y vrijednosti na y-osi
	 */
	public BarChart(List<XYValue> values, String xAxisText, String yAxisText, 
			int yMin, int yMax, int space) {
		super();
		if(yMin < 0 || yMax <= yMin) {
			throw new IllegalArgumentException();
		}
		if(values == null) {
			throw new NullPointerException();
		}
		checkForMinimumValues(values, yMin);
		this.values = values;
		this.xAxisText = xAxisText;
		this.yAxisText = yAxisText;
		this.yMin = yMin;
		this.yMax = yMax;
		while(!((yMax-yMin) % space == 0)) {
			space++;
		}
		this.space = space;
	}

	
	/**
	 * Provjerava  da su sve y vrijednosti vece ode minimalne.
	 * @param values vrijednost koja sadrzi,uz druge, i y vrijednost
	 * @param yMin minimalni y koji se prikaziva na y-osi
	 */
	private void checkForMinimumValues(List<XYValue> values, int yMin) {
		for(XYValue value : values) {
			if(value.getY() < yMin) {
				throw new IllegalArgumentException();
			}
		}
		
	}

	public List<XYValue> getValues() {
		return values;
	}

	public String getxAxisText() {
		return xAxisText;
	}

	public String getyAxisText() {
		return yAxisText;
	}

	public int getyMin() {
		return yMin;
	}

	public int getyMax() {
		return yMax;
	}

	public int getSpace() {
		return space;
	}
	
	
	
	
}
