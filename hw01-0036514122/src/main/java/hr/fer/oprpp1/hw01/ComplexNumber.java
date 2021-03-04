package hr.fer.oprpp1.hw01;

/**
 * Razred koji predstavlja kompleksni broj oblika a+bi.
 * @author Dario
 *
 */
public class ComplexNumber {
	
	private double real;
	private double imaginary;
	
	/**
	 * Konstruktor za stvaranje kompleksnog broja.
	 * @param real realni dio kompleksnog broja
	 * @param imaginary imaginarni dio kompleksnog broja
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * Za dani realni dio kompleksnog broja stvara kompleskni broj
	 * bez imaginarnog dijela.
	 * @param real realni dio kompleksnog broja
	 * @return kompleksni broj bez imaginarne vrijednosti
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * Za dani imaginarni dio komplesknog broja stvara kompleksni
	 * broj bez realnog dijela.
	 * @param imaginary imaginarni dio kompleksnog broja
	 * @return vraća kompleksni broj bez realnog dijela
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	/**
	 * Za danu magnitudu i kut stvara odgovarajući kompleksan broj.
	 * @param magnitude magnituda
	 * @param angle kut kompleksnog broja
	 * @return novi kompleksni broj sa realnim i/ili imaginarnim dijelom
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		double real = magnitude * Math.cos(angle);
		double imaginary = magnitude * Math.sin(angle);
		
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Pretvara String kompleksnog broja("a+bi", "+a+bi", "-a+bi", "-a-bi")
	 * u kompleksni broj.
	 * @param s komplesni broj zapisan kao String
	 * @return novi kompleksni broj sa realnim i/ili imaginarnim dijelom
	 */
	public static ComplexNumber parse(String s) {
		double real = 0;
		double imaginary = 0;
		String[] str;
		
		if(s.contains("i")) {
			str = s.split("i");
			if(str.length == 0) {
				return new ComplexNumber(0, 1);
			} else if(str[0].equals("-")) {
				return new ComplexNumber(0, -1);
			}
		} else {
			real = Double.parseDouble(s);
			return new ComplexNumber(real, 0);
		}
		
		if(!s.contains("+") && !s.contains("-")) {
			str = s.split("i");
			imaginary = Double.parseDouble(str[0]);
			return new ComplexNumber(real, imaginary);
		}
		
		if(!s.contains("+")) {
			str = s.split("-");
			if(str.length == 3 && str[2].equals("i")) {
				real = -Double.parseDouble(str[1]);
				return new ComplexNumber(real, -1);
			} else if(str.length == 3 && !str[2].equals("i")) {
				real = -Double.parseDouble(str[1]);
				str = str[2].split("i");
				imaginary = -Double.parseDouble(str[0]);
				return new ComplexNumber(real, imaginary);
			} else if(str[0].equals("")) {
				str = str[1].split("i");
				imaginary = -Double.parseDouble(str[0]);
				return new ComplexNumber(real, imaginary);
			} else if(str.length == 2 && str[1].equals("i")) {
				real = Double.parseDouble(str[0]);
				return new ComplexNumber(real, -1);
			} else {
				real = Double.parseDouble(str[0]);
				str = str[1].split("i");
				imaginary = -Double.parseDouble(str[0]);
				return new ComplexNumber(real, imaginary);
			}
		}
		
		if(!s.contains("-")) {
			str = s.split("\\+");
			if(str.length == 3 && str[2].equals("i")) {
				real = Double.parseDouble(str[1]);
				return new ComplexNumber(real, 1);
			} else if(str.length == 3 && !str[2].equals("i")) {
				real = Double.parseDouble(str[1]);
				str = str[2].split("i");
				imaginary = Double.parseDouble(str[0]);
				return new ComplexNumber(real, imaginary);
			} else if(str[0].equals("")) {
				str = str[1].split("i");
				imaginary = Double.parseDouble(str[0]);
				return new ComplexNumber(real, imaginary);
			} else if(str.length == 2 && str[1].equals("i")) {
				real = Double.parseDouble(str[0]);
				return new ComplexNumber(real, 1);
			} else {
				real = Double.parseDouble(str[0]);
				str = str[1].split("i");
				imaginary = Double.parseDouble(str[0]);
				return new ComplexNumber(real, imaginary);
			}
		}
		
		if(s.contains("+") && s.contains("-")) {
			str = s.split("-");
			if(str[1].equals("i")) {
				real = Double.parseDouble(str[0]);
				return new ComplexNumber(real, -1);
			} else if(str.length == 2 && !str[0].equals("")) {
				real = Double.parseDouble(str[0]);
				str = str[1].split("i");
				imaginary = -Double.parseDouble(str[0]);
				return new ComplexNumber(real, imaginary);
			} else {
				str = str[1].split("\\+");
				if(str[1].equals("i")) {
					real = -Double.parseDouble(str[0]);
					return new ComplexNumber(real, 1);
				} else {
					real = -Double.parseDouble(str[0]);
					str = str[1].split("i");
					imaginary = Double.parseDouble(str[0]);
					return new ComplexNumber(real, imaginary);
				}
			}
		}
		
		return new ComplexNumber(0, 0);
		
	}
	
	/**
	 * Dohvaća realni dio kompleksnog broja.
	 * @return realni dio kompleksnog broja
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * Dohvaća imaginarni dio kompleksnog broja.
	 * @return imaginarni dio kompleksnog broja
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Dohvaća magnitudu kompleksnog broja.
	 * @return magnitudu kompleksnog broja
	 */
	public double getMagnitude() {
		return Math.sqrt(real*real + imaginary*imaginary);
	}
	
	/**
	 * Dohvaća kut kompleksnog broja.
	 * @return kut kompleksnog broja
	 */
	public double getAngle() {
		return Math.acos(real/getMagnitude());
	}
	
	/**
	 * Zbraja dva kompleksna broja.
	 * @param c drugi operand operacije zbrajanja
	 * @return rezultat zbroja
	 * @throws NullPointerException kada je c <code>null</code>
	 */
	public ComplexNumber add(ComplexNumber c) {
		if(c == null) {
			throw new NullPointerException("Kompleksni broj ne smije biti null da bi se mogao zbrojiti.");
		}
		
		return new ComplexNumber(this.real+c.real, this.imaginary+c.imaginary);
	}
	
	/**
	 * Oduzima dva kompleksna broja.
	 * @param c drugi operand operacije oduzimanja
	 * @return rezultat oduzimanja
	 * @throws NullPointerException kada je c <code>null</code>
	 */
	public ComplexNumber sub(ComplexNumber c) {
		if(c == null) {
			throw new NullPointerException("Kompleksni broj ne smije biti null da bi se mogao oduzeti.");
		}
		
		return new ComplexNumber(this.real-c.real, this.imaginary-c.imaginary);
	}
	
	/**
	 * Množi dva kompleksna broja.
	 * @param c drugi operand operacije množenja
	 * @return rezultat množenja
	 * @throws NullPointerException kada je c <code>null</code>
	 */
	public ComplexNumber mul(ComplexNumber c) {
		if(c == null) {
			throw new NullPointerException("Kompleksni broj ne smije biti null da bi se mogao pomnožiti.");
		}
		
		double real = this.real*c.real + this.imaginary*c.imaginary*(-1);
		double imaginary = this.real*c.imaginary + this.imaginary*c.real;
		
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Dijeli dva kompleksna broja.
	 * @param c drugi operand operacije dijeljenja
	 * @return rezultat dijeljenja
	 * @throws NullPointerException kada je c <code>null</code>
	 */
	public ComplexNumber div(ComplexNumber c) {
		if(c == null) {
			throw new NullPointerException("Kompleksni broj ne smije biti null da bi se mogao podijeliti.");
		}
		if(c.getReal() == 0 && c.getImaginary() == 0) {
			throw new ArithmeticException("Ne može se dijelit s 0!");
		}
		
		ComplexNumber comp = new ComplexNumber(c.real, c.imaginary*(-1));
		comp = this.mul(comp);
		double nazivnik = c.getMagnitude()*c.getMagnitude();
		
		return new ComplexNumber(comp.getReal()/nazivnik, comp.getImaginary()/nazivnik);
	}
	
	/**
	 * Računa n-tu potenciju kompleksnog broja.
	 * @param n n-ta potencija koju želimo izračunati
	 * @return rezultat potenciranja
	 */
	public ComplexNumber power(int n) {
		if(n < 0) {
			throw new IllegalArgumentException("Argument n mora biti veći ili jednak 0.");
		}
		
		double magnitude = this.getMagnitude();
		magnitude = Math.pow(magnitude, n);
		double angle = this.getAngle();
		double real = magnitude * Math.cos(n*angle);
		double imaginary = magnitude * Math.sin(n*angle);
		
		return new ComplexNumber(real, imaginary);	
	}
	
	/**
	 * Računa n-ti korijen kompleksnog broja.
	 * @param n n-ti korijen koji želimo izračunati
	 * @return polje kompleksni brojeva veličine n koji su rezultat
	 * izvršavanja metode root
	 */
	public ComplexNumber[] root(int n) {
		if(n < 1) {
			throw new IllegalArgumentException("Argument n mora biti veći od 0.");
		}
		
		ComplexNumber[] array = new ComplexNumber[n];
		
		double real;
		double imaginary;
		double rootAngle;
		double magnitude = this.getMagnitude();
		double angle = this.getAngle();
		magnitude = Math.pow(magnitude, 1/n);
		
		for(int i = 0; i < n; i++) {
			rootAngle = (2 * Math.PI * i + angle) / n;
			real = Math.cos(rootAngle) * magnitude;
			imaginary = Math.sin(rootAngle) * magnitude;
			array[i] = new ComplexNumber(real, imaginary);
		}
		
		return array;
	}
	
	/**
	 * Pretvara kompleksni broj u odgovarajući zapis oblika a+bi.
	 */
	@Override
	public String toString() {
		String str;
		if(imaginary < 0) {
			str = real + "" + imaginary + "i";
		} else {
			str = real + "+"  + imaginary + "i";
		}
		return str;
	}
	
}
