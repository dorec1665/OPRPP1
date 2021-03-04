package hr.fer.oprpp1.hw04.db;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.oprpp1.hw04.formatter.RecordFormatter;
import hr.fer.oprpp1.hw04.parser.ParserException;
import hr.fer.oprpp1.hw04.parser.QueryParser;

public class StudentDB {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		List<String> lines = new ArrayList<>();

		try {
			lines = Files.readAllLines(
					Paths.get("/home/dario/Dario/fer/5.semestar/OPRPP1/eclipse-zadace/database.txt"),
					StandardCharsets.UTF_8
			);
		} catch(Exception e) {
			
		}
		
		StudentDatabase db = new StudentDatabase(lines);
		QueryParser parser;
		List<StudentRecord> records;
		
		System.out.print("> ");
		String line = sc.nextLine();
		

		while(!line.equals("exit")) {
			try {
				if(!line.contains("query")) {
					throw new ParserException("Početak upita mora započet naredbom query!");
				}
				line = line.substring(5, line.length());
				parser = new QueryParser(line);
				if(parser.isDirectQuery()) {
					StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
					records = new ArrayList<StudentRecord>();
					records.add(r);
					List<String> output = RecordFormatter.format(records);
					output.forEach(System.out::println);
					System.out.println("Records selected: " + records.size());
					
				} else {
					records = db.filter(new QueryFilter(parser.getQuery()));
					List<String> output = RecordFormatter.format(records);
					output.forEach(System.out::println);
					System.out.println("Records selected: " + records.size());
				}
				System.out.println();
				System.out.print("> ");
				line = sc.nextLine();
			} catch(Exception e) {
				System.err.println(e.getMessage());
				System.out.print("> ");
				line = sc.nextLine();
			}
	
		}
		sc.close();
		System.out.println("Goodbye!");
	}
}
