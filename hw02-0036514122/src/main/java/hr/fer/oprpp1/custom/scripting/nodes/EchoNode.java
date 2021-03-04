package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

/**
 * Razred koji predstavlja sadržaj echo taga. Ne može imati djecu.
 * @author dario
 *
 */
public class EchoNode extends Node {

	private Element[] elements;
	
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}
	
	public Element[] getElements() {
		return elements;
	}
	
	@Override
	public String toString() {
		String s = "";
		for(int i = 0; i < size(elements); i++) {
			s += elements[i].asText() + " ";
		}
		return s;
	}
	
	/**
	 * Metoda koja vraća trenutni broj elemenata polja.
	 * @param e polje u kojem želimo znati trenutni broj elemenata
	 * @return
	 */
	private int size(Element[] e) {
		int brojacElemenata = 0;
		for(int i = 0; i < e.length; i++) {
			if(e[i] == null) {
				break;
			} else {
				brojacElemenata++;
			}
		}
		return brojacElemenata;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof EchoNode)) {
			return false;
		}
		EchoNode other = (EchoNode) obj;
		if(this.numberOfChildren() != other.numberOfChildren()) {
			return false;
		}
		if(other.elements.length != elements.length) {
			return false;
		}
		for(int i = 0; i < size(elements); ++i) {
			if(!elements[i].equals(other.elements[i])) {
				return false;
			}
		}
		return true;
	}
}
