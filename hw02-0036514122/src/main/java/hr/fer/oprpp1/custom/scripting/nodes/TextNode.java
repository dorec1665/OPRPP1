package hr.fer.oprpp1.custom.scripting.nodes;



/**
 * Razred koji predstavlja tekst. Ne može imati djecu.
 * @author dario
 *
 */
public class TextNode extends Node {

	private String text;
	
	public TextNode(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof TextNode)) {
			return false;
		}
		TextNode other = (TextNode) obj;
		if(this.numberOfChildren() != other.numberOfChildren()) {
			return false;
		}
		return text.equals(other.text);
	}
}
