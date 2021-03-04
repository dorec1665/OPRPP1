package hr.fer.oprpp1.custom.scripting.elems;


/**
 * Razred koji sprema podatak kao String.
 * @author dario
 *
 */
public class ElementString extends Element {
	
	private String value;
	
	public ElementString(String value) {
		this.value = value;
	}
	
	@Override
	public String asText() {
		return value;
	}
	
	public String getValue() {
		return value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ElementString)) {
			return false;
		}
		ElementString other = (ElementString) obj;
		return this.value.equals(other.value);
	}

}
