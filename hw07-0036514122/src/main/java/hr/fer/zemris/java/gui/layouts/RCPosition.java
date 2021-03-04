package hr.fer.zemris.java.gui.layouts;


/**
 * Modelira poziciju komponente u {@link CalcLayout} upravljaču
 * razmještaja.
 * @author dario
 *
 */
public class RCPosition {

	private int row;
	private int column;
	
	public static RCPosition firstPosition = new RCPosition(1, 1);
	
	/**
	 * Konstruktor
	 * @param row broj redka
	 * @param column broj stupca
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	
	
	/**
	 * Parsira niz znakova oblika ("<code>row</code>, <code>column</code>") u odgovarajući
	 * {@link RCPosition}.
	 * @param text niz znakova
	 * @return parsiranu {@link RCPosition}
	 */
	public static RCPosition parse(String text) {
		text = text.replace(" ", "");
		String[] position = text.split(",");
		int row = Integer.parseInt(position[0]);
		int column = Integer.parseInt(position[1]);
		return new RCPosition(row, column);
	}



	/**
	 * Getter članske varijable row.
	 * @return
	 */
	public int getRow() {
		return row;
	}


	/**
	 * Getter članske varijable column.
	 * @return
	 */
	public int getColumn() {
		return column;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}




	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
	
	
	
	
	
	

	
}
