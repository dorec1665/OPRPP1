package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.lexer.Lexer;
import hr.fer.oprpp1.custom.scripting.lexer.NodeType;
import hr.fer.oprpp1.custom.scripting.lexer.Token;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.Node;

/**
 * Razred {@link SmartScriptParser} dobija tokene od razreda {@link Lexer}
 * te na temelju toga stavara sintaksno stablo tokena, čiji korijen je {@link DocumentNode} u kojem
 * se nalazi cijeli tekst kojeg je Lexer obradio.
 * @author dario
 *
 */
public class SmartScriptParser {

	private ObjectStack stack;
	private DocumentNode dnode;
	private Lexer l;
	private int numberOfNodes = 0;
	
	public SmartScriptParser(String text) {
		stack = new ObjectStack();
		dnode = new DocumentNode();
		l = new Lexer(text);
		createSyntaxTree();
	}
	
	/**
	 * Metoda koja stvara sintaksno stablo.
	 * @throws SmartScriptParserException ako stablo nije moguće generirat
	 */
	private void createSyntaxTree() {
		stack.push(dnode);
		numberOfNodes++;
		Token t = l.nextToken();
		while(t.getType() != NodeType.EOF) {
			Node n = (Node) stack.peek();
			if(t.getType() == NodeType.TEXT || t.getType() == NodeType.ECHO) {
				n.addChildNode(t.getNode());
				numberOfNodes++;
			} else if(t.getType() == NodeType.FOR) {
				n.addChildNode(t.getNode());
				numberOfNodes++;
				stack.push(t.getNode());
			} else if(t.getType() == NodeType.END) {
				stack.pop();
			}
			t = l.nextToken();
		}
		if(stack.isEmpty()) {
			throw new SmartScriptParserException();
		}
	}
	
	public int getNumberOfNodes() {
		return numberOfNodes;
	}
	
	public DocumentNode getDNode() {
		return dnode;
	}
}
