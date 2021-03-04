package hr.fer.oprpp1.custom.scripting.elems;


/**
 * Razred koji sprema ime varijable kao String.
 * @author dario
 *
 */
public class ElementVariable extends Element {

	private String name;
	
	public ElementVariable(String name) {
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
		if(!(obj instanceof ElementVariable)) {
			return false;
		}
		ElementVariable other = (ElementVariable) obj;
		return this.name.equals(other.name);
	}
}
