package hr.fer.oprpp1.hw02;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.oprpp1.custom.scripting.lexer.Lexer;
import hr.fer.oprpp1.custom.scripting.lexer.Token;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptTester {
	
	public static void main(String[] args) {
	
		String filepath = args[0];
		String docBody = null;
		try {
			docBody = new String(
					Files.readAllBytes(Paths.get(filepath)),
					StandardCharsets.UTF_8
			);
		} catch(IOException e) {
			
		}
		
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch(Exception e) {
			  System.out.println("If this line ever executes, you have failed this class!");
				System.exit(-1);
		}
		
		DocumentNode document = parser.getDNode();
		String originalDocumentBody = document.toString();
		System.out.println(originalDocumentBody);
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDNode();
		boolean same = document.equals(document2);
		System.out.println(same);
		
		Lexer l = new Lexer("Ovo je  \\{ tekst");
		Token t =  l.nextToken();
		TextNode tn = (TextNode) t.getNode();
		
		System.out.println(tn.getText());
		
		
	}
}