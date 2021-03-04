package hr.fer.oprpp1.custom.scripting.elems;


/**
 * Razred koji sprema realni broj.
 * @author dario
 *
 */
public class ElementConstantDouble extends Element {

	private double value;
	
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}
	
	@Override
	public String asText() {
		return Double.toString(value);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ElementConstantDouble)) {
			return false;
		}
		ElementConstantDouble other = (ElementConstantDouble) obj;
		return Math.abs(this.value - other.value) < 1e-5; 
	}
	
}
