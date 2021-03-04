package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;


/**
 * Klasa koju nasljeđuju ostali konkretni čvorovi.
 * @author dario
 *
 */
public class Node {

	private ArrayIndexedCollection children;
	private int size = 0;
	
	/**
	 * Dodaje predani čvor kao dijete ovog čvora
	 * @param child čvor dijete
	 */
	public void addChildNode(Node child) {
		if(size == 0) {
			children = new ArrayIndexedCollection();
		}
		children.add(child);
		size++;
	}
	
	/**
	 * Broj djece koju ovak čvor ima.
	 * @return broj djece
	 */
	public int numberOfChildren() {
		return size;
	}
	
	
	/**
	 * Dohvaća čvor dijete na zadanom indeksu.
	 * @param index indeks dijeteta koje želimo
	 * @return čvor dijete na indeksu index
	 */
	public Node getChild(int index) {
		if(index < 0 || index > size-1) {
			throw new IllegalArgumentException("Nedozvoljena pozicija");
		}

		return (Node) children.get(index);
	}
}
