package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.nodes.Node;

/**
 * Razred kojeg Lexer vraća ako je tokeniziranje uspješno odrađeno.
 * @author dario
 *
 */
public class Token {

	private Node node;
	private NodeType type;
	
	public Token(Node node, NodeType type) {
		this.node = node;
		this.type = type;
	}
	
	public Node getNode() {
		return node;
	}
	
	public NodeType getType() {
		return type;
	}
}
