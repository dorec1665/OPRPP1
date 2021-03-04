package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Razred zadužen za pozicioniranje i prikaz stupičastog dijagrama.
 * @author dario
 *
 */
public class BarChartDemo extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private BarChart chart;
	
	/**
	 * Konstruktor.
	 * @param chart stupičasti dijagrama kojeg želimo prikazati
	 * @param path putanja to datoteke koja sadrži informacije potrebne
	 * za crtanje i prikaz stupičastog dijagrama
	 */
	public BarChartDemo(BarChart chart, String path) {
		super();
		this.chart = chart;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(750,500);
		initGUI(path);
	}

	/**
	 * Pomoćna metoda za prikaz komponenti prozora.
	 * @param path putanja
	 */
	private void initGUI(String path) {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		JLabel title = new JLabel(path);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setOpaque(true);
		title.setBackground(Color.WHITE);
		
		BarChartComponent bcc = new BarChartComponent(chart);
		
		cp.add(title, BorderLayout.PAGE_START);
		cp.add(bcc, BorderLayout.CENTER);
		
	}
	
	public static void main(String[] args) {
		if(args.length != 1) {
			throw new IllegalArgumentException();
		}
		Path filePath = Paths.get(args[0]);
		List<String> input = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(filePath)));
			String line;
			int numberOfLinesRead = 0;
			while((line = br.readLine()) != null && numberOfLinesRead < 6) {
				input.add(line);
				numberOfLinesRead++;
			}
			if(input.size() != 6) {
				throw new IllegalArgumentException("Not enough data!");
			}
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		BarChart model = createModel(input);
//		BarChart model = new BarChart(
//				Arrays.asList(
//					new XYValue(1, 8), new XYValue(2, 20), new XYValue(3, 22),
//					new XYValue(4, 10), new XYValue(5, 4), new XYValue(6, 13)),
//				"Number of people in the car",
//				"Frequency",
//				0,
//				26,
//				2
//				);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				BarChartDemo prozor = new BarChartDemo(model, args[0]);
				prozor.setVisible(true);
			}
		});
	}

	
	/**
	 * Pomoćna metoda za stvaranje {@link BarChart} razreda iz datoteke
	 * koja dsdadrži potrebne informacije za stvaranje stupičastog dijagrama.
	 * @param input sadržaj datoteke(ne cijele, nego samo ono što je potrebno za
	 * prikaz dijagrama).
	 * @return {@link BarChart} spreman za crtanje.
	 */
	private static BarChart createModel(List<String> input) {
		int line = 0;
		String xAxisText = "";
		String yAxisText = "";
		int yMin = 0, yMax = 0, space = 0;
		List<XYValue> values = new ArrayList<>();
		try {
			for(String s : input) {
				switch(line) {
					case 0:
						xAxisText = s;
						line++;
						break;
					case 1:
						yAxisText = s;
						line++;
						break;
					case 2:
						values = createValues(s);
						line++;
						break;
					case 3:
						yMin = Integer.parseInt(s);
						line++;
						break;
					case 4:
						yMax = Integer.parseInt(s);
						line++;
						break;
					case 5:
						space = Integer.parseInt(s);
						line++;
						break;
				}
			}
		} catch(Exception e) {
			System.out.println("Could not parse document correctly!");
			System.exit(1);
		}
		
		return new BarChart(values, xAxisText, yAxisText, yMin, yMax, space);
	}

	
	/**
	 * Pomoćna metoda koja iz niza znakova stvara
	 * podatke oblika {@link XYValue} potrebne za iscrtavanje
	 * {@link BarChart}-a.
	 * @param s niz znakova koji sadrži informacije potrebne za stvaranje
	 * podataka oblika {@link XYValue}
	 * @return lista svih podataka oblika {@link XYValue}
	 */
	private static List<XYValue> createValues(String s) {
		List<XYValue> values = new ArrayList<>();
		String[] xyvalues = s.split(" ");
		for(String xyvalue : xyvalues) {
			String[] tmp = xyvalue.split(",");
			if(tmp.length != 2) {
				throw new IllegalArgumentException("Invalid data!");
			}
			XYValue v = new XYValue(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]));
			values.add(v);
		}
		
		return values;
	}

}

