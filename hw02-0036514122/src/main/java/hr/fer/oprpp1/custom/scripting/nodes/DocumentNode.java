package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;

/**
 * Razred koji se nalazi na početku sintaksnog stabla kojeg
 * {@link SmartScriptParser} izgenerira. Može imati djecu.
 * @author dario
 *
 */
public class DocumentNode extends Node {

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		visit(this, s);
		return s.toString();
	}
	
	/**
	 * Metoda koja služi prolasku kroz cijelo sintaksno stablo,
	 * te čita što se nalazi u pojedinom čvoru.
	 * @param node čvor od kojeg kreće pretraga
	 * @param s u koji se spremaju pročitani podatci svakog čvora
	 */
	private void visit(Node node, StringBuilder s) {
		for(int i = 0, n = node.numberOfChildren(); i < n; ++i) {
			Node child = node.getChild(i);
			
			if(child instanceof ForLoopNode) {
				ForLoopNode forNode = (ForLoopNode) child;
				s.append("{$ FOR " + forNode.toString() + " $}");
				if(forNode.numberOfChildren() > 0) {
					visit(forNode, s);
				}
			} else if(child instanceof EchoNode) {
				EchoNode echoNode = (EchoNode) child;
				s.append("{$= " + echoNode.toString() + " $}");
			} else if(child instanceof TextNode) {
				TextNode textNode = (TextNode) child;
				s.append(textNode.getText());
			}
		}
		if(node instanceof ForLoopNode) {
			s.append( "{$END$}");
		}
	}
	
	/**
	 * Metoda služi za usporedbu dva Objekta {@link Node}.
	 * @param node prvi čvor koji se uspoređuje
	 * @param other drugi čvor koji se uspoređuje
	 * @return true ako su čvorovi jednaki, inače false
	 */
	private boolean visitEquals(Node node, Node other) {
		for(int i = 0, n = node.numberOfChildren(); i < n; ++i) {
			Node child = node.getChild(i);
			Node otherChild = other.getChild(i);
			if(child.numberOfChildren() != otherChild.numberOfChildren()) {
				return false;
			}
			if(!child.equals(otherChild)) {
				return false;
			}
			if(child instanceof ForLoopNode) {
				ForLoopNode forNode = (ForLoopNode) child;
				ForLoopNode otherForNode = (ForLoopNode) otherChild;
				if(forNode.numberOfChildren() > 0) {
					return visitEquals(forNode, otherForNode);
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof DocumentNode)) {
			return false;
		}
		DocumentNode other = (DocumentNode) obj;
		if(this.numberOfChildren() != other.numberOfChildren()) {
			return false;
		}
		return visitEquals(this, other);
	}
}
