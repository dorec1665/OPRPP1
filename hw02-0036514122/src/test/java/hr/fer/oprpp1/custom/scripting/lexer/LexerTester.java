package hr.fer.oprpp1.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.nodes.TextNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;


public class LexerTester {

	@Test
	public void testNotNull() {
		Lexer lexer = new Lexer("");
		
		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}
	
	@Test
	public void testGetReturnsLastNext() {
		Lexer lexer = new Lexer("Ovo je tekst 321");
		
		Token token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}
	
	
	@Test
	public void testEmpty() {
		Lexer lexer = new Lexer("");
		
		assertEquals(NodeType.EOF, lexer.nextToken().getType());
	}
	
	@Test
	public void testNullInput() {
		assertThrows(NullPointerException.class, () -> new Lexer(null));
	}
	
	
	@Test
	public void testText() {
		Lexer lexer = new Lexer("Ovo je \n te\\{$kst u \r \n 3 reda");
		
		Token token = lexer.nextToken();
		assertEquals(token, lexer.getToken());
		assertEquals(NodeType.TEXT, lexer.getToken().getType());

	}
	
	
	@Test
	public void testFor() {
		Lexer lexer = new Lexer("{$   FOR i 2 \"10\" varijabla$}");
		
		Token token = lexer.nextToken();
		assertEquals(token, lexer.getToken());
		assertEquals(NodeType.FOR, lexer.getToken().getType());
	}
	
	@Test
	public void testEcho() {
		Lexer lexer = new Lexer("{$=  @sin - + i 2 \"1\n0\" varijabla$}");
		
		Token token = lexer.nextToken();
		assertEquals(token, lexer.getToken());
		assertEquals(NodeType.ECHO, lexer.getToken().getType());
	}
	
	@Test
	public void testEnd() {
		Lexer lexer = new Lexer("{$ END  $}");

		Token token = lexer.nextToken();
		assertEquals(token, lexer.getToken());
		assertEquals(NodeType.END, lexer.getToken().getType());
	}
	
	@Test
	public void testExampleOne() {
		String text = readExample(1);
		
		SmartScriptParser parser = new SmartScriptParser(text);
		assertEquals(2, parser.getNumberOfNodes());
		assertEquals(TextNode.class, parser.getDNode().getChild(0).getClass());
	}
	
	@Test
	public void testExampleTwo() {
		String text = readExample(2);
		
		SmartScriptParser parser = new SmartScriptParser(text);
		assertEquals(2, parser.getNumberOfNodes());
		assertEquals(TextNode.class, parser.getDNode().getChild(0).getClass());
	}
	
	@Test
	public void testExampleThree() {
		String text = readExample(3);
		
		SmartScriptParser parser = new SmartScriptParser(text);
		assertEquals(2, parser.getNumberOfNodes());
		assertEquals(TextNode.class, parser.getDNode().getChild(0).getClass());
	}
	
	@Test
	public void testExampleFour() {
		String text = readExample(4);
		
		assertThrows(LexerException.class, () -> new SmartScriptParser(text));
	}
	
	@Test
	public void testExampleFive() {
		String text = readExample(5);
		
		assertThrows(LexerException.class, () -> new SmartScriptParser(text));
	}
	
	@Test
	public void testExampleSix() {
		String text = readExample(6);
		
		SmartScriptParser parser = new SmartScriptParser(text);
		assertEquals(2, parser.getNumberOfNodes());
		assertEquals(TextNode.class, parser.getDNode().getChild(0).getClass());
	}
	
	@Test
	public void testExampleSeven() {
		String text = readExample(7);
		
		assertThrows(LexerException.class, () -> new SmartScriptParser(text));

	}
	
	@Test
	public void testExampleEight() {
		String text = readExample(8);
		
		SmartScriptParser parser = new SmartScriptParser(text);
		assertEquals(2, parser.getNumberOfNodes());
		assertEquals(TextNode.class, parser.getDNode().getChild(0).getClass());
	}
	
	@Test
	public void testExampleNine() {
		String text = readExample(9);
		
		assertThrows(LexerException.class, () -> new SmartScriptParser(text));
	}
	
	
	
	private String readExample(int n) {
		  try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt")) {
		    if(is==null) throw new RuntimeException("Datoteka extra/primjer"+n+".txt je nedostupna.");
		    byte[] data = is.readAllBytes();
		    String text = new String(data, StandardCharsets.UTF_8);
		    return text;
		  } catch(IOException ex) {
		    throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		  }
		}
	
	
	
	
}
