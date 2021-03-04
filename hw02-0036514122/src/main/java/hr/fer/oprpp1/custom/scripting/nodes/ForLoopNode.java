package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;


/**
 * Razred koji predstavlja sadržaj for taga. Može imati djecu.
 * @author dario
 *
 */
public class ForLoopNode extends Node {

	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;
	
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	public ElementVariable getVariable() {
		return variable;
	}
	public Element getStartExpression() {
		return startExpression;
	}
	public Element getEndExpression() {
		return endExpression;
	}
	public Element getStepExpression() {
		return stepExpression;
	}
	
	@Override
	public String toString() {
		if(endExpression == null) {
			return variable.asText() + " " + startExpression.asText()+ " " + stepExpression.asText();
		}
		return variable.asText() + " " + startExpression.asText()+ " " + stepExpression.asText() + " " + endExpression.asText();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ForLoopNode)) {
			return false;
		}
		ForLoopNode other = (ForLoopNode) obj;
		if(this.numberOfChildren() != other.numberOfChildren()) {
			return false;
		}
		if(endExpression == null) {
			return variable.equals(other.variable) && startExpression.equals(other.startExpression) &&
					stepExpression.equals(other.stepExpression) && other.endExpression == null;
		}
		return variable.equals(other.variable) && startExpression.equals(other.startExpression) &&
				stepExpression.equals(other.stepExpression) && endExpression.equals(other.endExpression);
	}
	
	
}
