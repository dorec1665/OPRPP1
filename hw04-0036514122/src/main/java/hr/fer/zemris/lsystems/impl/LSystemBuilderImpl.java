package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * Implementacija sučelja LSystemBuilder koje modelira objekte
 * koje je moguće konfigurirati, te pozivom odgovarajućih metoda vratit jedan
 * Lindermayerov sustav prema zadanim konfiguracijama.
 */
public class LSystemBuilderImpl implements LSystemBuilder {

	/**
	 * Pamti registrirane produkcije.
	 */
	private Dictionary<Character, String> productions;
	/**
	 * Pamti registrirane naredbe.
	 */
	private Dictionary<Character, Command> commands;
	/**
	 * Predstavlja duljinu jediničnog pomaka kornjače.
	 */
	private double unitLength;
	/**
	 * Skalira unitLength kako bi se dimenzije fraktala sadržale konstantnima.
	 */
	private double unitLengthDegreeScaler;
	/**
	 * Početna točka iz koje kornjača kreće.
	 */
	private Vector2D origin;
	/**
	 * Kut prema pozitivnoj x-osi u kojem kornjača gleda.
	 */
	private double angle;
	/**
	 * Početni niz iz kojeg kreće generiranje sustava.
	 */
	private String axiom;
	
	/**
	 * Implementacija sučelja LSystem koje modelira jedan Lindermayerov sustav.
	 * @author dario
	 *
	 */
	private class LSystemImpl implements LSystem {

		/**
		 * Pomoću {@link Painter} crta rezultatntni fraktal.
		 */
		@Override
		public void draw(int level, Painter painter) {
			Context ctx = new Context();
			Vector2D direction = new Vector2D(1, 0);
			direction.rotate(angle);
			TurtleState state = new TurtleState(origin, direction, Color.BLACK, unitLength * Math.pow(unitLengthDegreeScaler, level));
			ctx.pushState(state);
			String production = generate(level);
			char[] data = production.toCharArray();
			for(int i = 0; i < production.length(); i++) {
				if(commands.get(data[i]) != null) {
					commands.get(data[i]).execute(ctx, painter);
				}
			}
		}

		/**
		 * Ova metoda prima razinu i vraća String koji odgovara generiranom nizu
		 * nakon zadanog broja razina primjena produkcija. Ako je razina 0, metoda vraća
		 * aksiom, ako je razina 1, metoda vraća primjenu produkcije na aksiom. Općenito ako
		 * je razina n, vraća niz koji je dobiven primjeno produkcija za niz n-1.
		 */
		@Override
		public String generate(int level) {
			String endProduction = axiom;
			if(level == 0) {
				return endProduction;
			}
			
			for(int i = level; i > 0; i--) {
				char[] data = endProduction.toCharArray();
				String newLevelProduction = "";
				for(int current = 0; current < endProduction.length(); current++) {
					if(productions.get(data[current]) != null) {
						newLevelProduction += productions.get(data[current]);
					} else {
						newLevelProduction += data[current];
					}
				}
				endProduction = newLevelProduction;
			}
			
			return endProduction;
			
		}
		
	}
	
	/**
	 * Konstruktor koji postavlja vrijednosti na defaultne vrijednosti.
	 */
	public LSystemBuilderImpl() {
		productions = new Dictionary<>(10);
		commands = new Dictionary<>(10);
		unitLength = 0.1;
		unitLengthDegreeScaler = 1;
		origin = new Vector2D(0, 0);
		angle = 0;
		axiom = "";
	}

	/**
	 * Stvara jedan primjerak implementacije sučelja LSystem.
	 */
	@Override
	public LSystem build() {
		return new LSystemImpl();
	}


	/**
	 * Konfiguracija objekata koje modelira sučelje LSystemBuilede.
	 */
	@Override
	public LSystemBuilder configureFromText(String[] text) {
		for(String str : text) {
			String[] line = str.split("\\s+");
			if(line[0].equals("origin")) {
				this.setOrigin(Double.parseDouble(line[1]), Double.parseDouble(line[2]));
			} else if(line[0].equals("angle")) {
				this.setAngle(Double.parseDouble(line[1]));
			} else if(line[0].equals("unitLength")) {
				this.setUnitLength(Double.parseDouble(line[1]));
			} else if(line[0].equals("unitLengthDegreeScaler")) {
				if(line.length == 2 && !line[1].contains("/")) {
					this.setUnitLengthDegreeScaler(Double.parseDouble(line[1]));
				} else if(line.length == 2 && line[1].contains("/")) {
					line = line[1].split("/");
					this.setUnitLengthDegreeScaler(Double.parseDouble(line[0]) / Double.parseDouble(line[1]));
					
				} else if(line.length == 3 && line[1].contains("/")) {
					String[] tmp = line[1].split("/");
					this.setUnitLengthDegreeScaler(Double.parseDouble(tmp[0]) / Double.parseDouble(line[2]));
				} else if(line.length == 3 && line[2].contains("/")) {
					String[] tmp = line[2].split("/");
					this.setUnitLengthDegreeScaler(Double.parseDouble(line[1]) / Double.parseDouble(tmp[1]));
				} else {
					this.setUnitLengthDegreeScaler(Double.parseDouble(line[1]) / Double.parseDouble(line[3]));
				}
			} else if(line[0].equals("command")) {
				if(line.length == 4) {
					registerCommand(line[1].charAt(0), line[2] + " " + line[3]);
				} else {
					registerCommand(line[1].charAt(0), line[2]);
				}
			} else if(line[0].equals("axiom")) {
				this.setAxiom(line[1]);
			} else if(line[0].equals("production")) {
				registerProduction(line[1].charAt(0), line[2]);
			}
		}
		return this;
	}


	/**
	 * Metoda koja registrira novu naredbu i sprema je.
	 */
	@Override
	public LSystemBuilder registerCommand(char symbol, String command) {
		String[] commandArguments = command.split("\\s+");
		if(commandArguments[0].equals("draw")) {
			DrawCommand dc = new DrawCommand(Double.parseDouble(commandArguments[1]));
			commands.put(symbol, dc);
		} else if(commandArguments[0].equals("skip")) {
			SkipCommand sc = new SkipCommand(Double.parseDouble(commandArguments[1]));
			commands.put(symbol, sc);
		} else if(commandArguments[0].equals("scale")) {
			ScaleCommand scaleCom = new ScaleCommand(Double.parseDouble(commandArguments[1]));
			commands.put(symbol, scaleCom);
		}  else if(commandArguments[0].equals("rotate")) {
			RotateCommand rc = new RotateCommand(Double.parseDouble(commandArguments[1])*Math.PI/180);
			commands.put(symbol, rc);
		} else if(commandArguments[0].equals("push")) {
			PushCommand pushCom = new PushCommand();
			commands.put(symbol, pushCom);
		} else if(commandArguments[0].equals("pop")) {
			PopCommand popCom = new PopCommand();
			commands.put(symbol, popCom);
		} else if(commandArguments[0].equals("color")) {
			Integer red = Integer.parseInt(commandArguments[1].substring(0, 2), 16);
			Integer blue = Integer.parseInt(commandArguments[1].substring(2, 4), 16);
			Integer green = Integer.parseInt(commandArguments[1].substring(4, 6), 16);
			ColorCommand cc = new ColorCommand(new Color(red, blue, green));
			commands.put(symbol, cc);
		} else {
			throw new IllegalArgumentException("Nedozvoljena akcija!");
		}
		
		return this;
	}


	/**
	 * Metoda koja registrira novu produkciju i sprema je.
	 */
	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {
		productions.put(symbol, production);
		return this;
	}


	/**
	 * Postavlja kut na zadanu vrijednost.
	 */
	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle * Math.PI/180;
		return this;
	
	}


	/**
	 * Postavlja aksiom na zadanu vrijednost.
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}


	/**
	 * Postavlja varijablu origin na zadanu vrijednost.
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		origin = new Vector2D(x, y);
		return this;
	}


	/**
	 * Postavlja varijablu unitLength na zadanu vrijednost.
	 */
	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}


	/**
	 * Postavlja varijablu unitLengthDegreeScaler na zadanu vrijednost.
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}
	
	
}
