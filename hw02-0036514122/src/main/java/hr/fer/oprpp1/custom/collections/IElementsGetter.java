package hr.fer.oprpp1.custom.collections;

public interface IElementsGetter {
	
	boolean hasNextElement();
	
	Object getNextElement();
	
	void processRemaining(Processor p);

}
