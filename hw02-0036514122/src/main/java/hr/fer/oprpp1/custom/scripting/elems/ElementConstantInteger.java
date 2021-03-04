package hr.fer.oprpp1.custom.scripting.elems;



/**
 * Razred koji sprema tip cijeli broj.
 * @author dario
 *
 */
public class ElementConstantInteger extends Element {
	
	private int value;
	
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	@Override
	public String asText() {
		return Integer.toString(value);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ElementConstantInteger)) {
			return false;
		}
		ElementConstantInteger other = (ElementConstantInteger) obj;
		return this.value == other.value;
	}

}
