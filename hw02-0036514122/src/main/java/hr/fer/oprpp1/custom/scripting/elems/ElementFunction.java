package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Razred koji sprema ime fukcije kao String.
 * @author dario
 *
 */
public class ElementFunction extends Element {

	private String name;
	
	public ElementFunction(String name) {
		this.name = name;
	}
	
	@Override
	public String asText() {
		return name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ElementFunction)) {
			return false;
		}
		ElementFunction other = (ElementFunction) obj;
		return this.name.equals(other.name);
	}

}
