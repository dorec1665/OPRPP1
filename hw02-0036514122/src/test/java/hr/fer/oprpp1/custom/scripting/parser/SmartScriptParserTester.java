package hr.fer.oprpp1.custom.scripting.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;

public class SmartScriptParserTester {

	@Test
	public void testDocumentBodyEquals() {
		String s = "OVO je parser, ${$= i i i \"2\n\"  $} 321123";
		
		
		SmartScriptParser parser = new SmartScriptParser(s);
		DocumentNode document = parser.getDNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDNode(); // now document and document2 should be structurally identical trees
		boolean same = document.equals(document2);  // ==> "same" must be true
		assertTrue(same);
	}
	
	
	@Test
	public void numberOfNodes() {
		String s = "Example \\{$=1$}. Now actually write one {$=1$}";
		SmartScriptParser parser = new SmartScriptParser(s);
		assertEquals(3, parser.getNumberOfNodes());
		
	}
}
