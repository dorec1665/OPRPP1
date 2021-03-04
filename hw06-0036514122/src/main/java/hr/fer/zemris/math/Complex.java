package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred koji predstavlja kompleksni broj oblika a+bi.
 *
 */
public class Complex {
	
	private double re;
	private double im;

	/**
	 * Konstanta 0 + 0i.
	 */
	public static final Complex ZERO = new Complex(0,0);
	
	/**
	 * Konstanta 1 + 0i.
	 */
	
	/**
	 * Konstanta -1 + 0i.
	 */
	public static final Complex ONE = new Complex(1,0);
	
	/**
	 * Konstanta -1 + 0i.
	 */
	public static final Complex ONE_NEG = new Complex(-1,0);
	
	/**
	 * Konstanta 0 + i.
	 */
	public static final Complex IM = new Complex(0,1);
	
	/**
	 * Konstanta 0 - i.
	 */
	public static final Complex IM_NEG = new Complex(0,-1);
	
	/**
	 * Defaultno konstruktor.
	 */
	public Complex() {
		re = 0;
		im = 0;
	}
	
	
	/**
	 * Konstruktor za stvaranje jednog kompleksnog broja s proizvoljnim
	 * realnim i imaginarnim dijelom.
	 * @param re realni dio
	 * @param im kompleksni dio
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	
	
	/**
	 * Računa magnitudu kompleksnog broja.
	 * @return
	 */
	public double module() {
		return Math.sqrt(re*re + im*im);
	}
	
	
	
	/**
	 * Računa kut kompleksnog broja.
	 * @return
	 */
	public double angle() {
		double angle = Math.atan2(im, re);
		angle = angle > 0 ? angle : angle+2*Math.PI;
		
		return angle;
	}
	
	
	
	/**
	 * Množi dva kompleksna broja.
	 * @param c 
	 * @return rezultat umnoška
	 */
	public Complex multiply(Complex c) {
		if(c == null) {
			throw new NullPointerException("c must not be null!");
		}
		
		double real = re * c.re + im*c.im*(-1);
		double imaginary = re * c.im + im*c.re;
		
		return new Complex(real, imaginary);
	}
	
	
	
	
	/**
	 * Dijeli dva kompleksna broja.
	 * @param c
	 * @return rezultat dijeljenja
	 */
	public Complex divide(Complex c) {
		if(c == null) {
			throw new NullPointerException("c must not be null!");
		}
		
		Complex newComp = new Complex(c.re, c.im*(-1));
		newComp = this.multiply(newComp);
		double nazivnik = c.module()*c.module();
		return new Complex(newComp.getRe()/nazivnik, newComp.getIm()/nazivnik);
	}
	
	
	
	
	/**
	 * Zbraja dva kompleksna broja.
	 * @param c
	 * @return rezultat zbrajanja
	 */
	public Complex add(Complex c) {
		if(c == null) {
			throw new NullPointerException("c must not be null!");
		}
		

		return new Complex(this.re + c.re, this.im + c.im);
	}
	
	
	
	/**
	 * Oduzima dva kompleksna broja.
	 * @param c
	 * @return rezultat oduzimanja
	 */
	public Complex sub(Complex c) {
		if(c == null) {
			throw new NullPointerException("c must not be null!");
		}
		
		return new Complex(this.re - c.re, this.im - c.im);
	}
	
	
	
	/**
	 * Negira kompleksni broj.
	 * @return reulatat negiranja
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}
	
	
	
	/**
	 * Računa n-tu potenciju kompleksnog broja.
	 * @param n potencija
	 * @return rezultat potenciranja
	 */
	public Complex power(int n) {
		if(n < 0) {
			throw new IllegalArgumentException("n must be non-negative integer.");
		}
		
		double module = this.module();
		module = Math.pow(module, n);
		double angle = this.angle();
		double real = module * Math.cos(n*angle);
		double imaginary = module * Math.sin(n*angle);
		
		return new Complex(real, imaginary);
		
	}
	
	
	
	/**
	 * Računa n-ti korijen kompleksnog broja.
	 * @param n n-ti korijen
	 * @return Lista svih izračunatih korijena.
	 */
	public List<Complex> root(int n) {
		if(n < 0) {
			throw new IllegalArgumentException("n must be positive integer.");
		}
		
		List<Complex> roots = new ArrayList<>(n);
		
		double module = this.module();
		module = Math.pow(module, 1/n);
		double angle = this.angle();
		double real, imaginary, rootAngle;
		for(int i = 0; i < n; i++) {
			rootAngle = (2*Math.PI*i + angle) / n;
			real = Math.cos(rootAngle) * module;
			imaginary = Math.sin(rootAngle) * module;
			roots.add(new Complex(real, imaginary));
		}
		
		return roots;
	}
	
	
	/**
	 * Dohvaća realni dio kompleksnog broja.
	 * @return
	 */
	public double getRe() {
		return re;
	}

	
	/**
	 * Dohvaća imaginarni dio kompleksnog broja.
	 * @return
	 */
	public double getIm() {
		return im;
	}


	/**
	 * Pretvara kompleksni broj u odgovarajući zapis oblika a+bi.
	 */
	@Override
	public String toString() {
		String str;
		if(im < 0) {
			str = "(" +  re + "" + im + "i)";
		} else {
			str = "(" + re + "+"  + im + "i)";
		}
		return str;
	}
	
	
	
	/**
	 * Pretvara String kompleksnog broja("a+bi", "+a+bi", "-a+bi", "-a-bi")
	 * u kompleksni broj.
	 * @param s komplesni broj zapisan kao String
	 * @return novi kompleksni broj sa realnim i/ili imaginarnim dijelom
	 */
	public static Complex parse(String s) {
		s.replaceAll(" ", "");
		double real = 0;
		double imaginary = 0;
		String[] str;
		
		if(s.contains("i")) {
			str = s.split("i");
			if(str.length == 0) {
				return new Complex(0, 1);
			} else if(str[0].equals("-")) {
				return new Complex(0, -1);
			}
		} else {
			real = Double.parseDouble(s);
			return new Complex(real, 0);
		}
		
		if(!s.contains("+") && !s.contains("-")) {
			str = s.split("i");
			imaginary = Double.parseDouble(str[0]);
			return new Complex(real, imaginary);
		}
		
		if(!s.contains("+")) {
			str = s.split("-");
			if(str.length == 3 && str[2].equals("i")) {
				real = -Double.parseDouble(str[1]);
				return new Complex(real, -1);
			} else if(str.length == 3 && !str[2].equals("i")) {
				real = -Double.parseDouble(str[1]);
				str = str[2].split("i");
				imaginary = -Double.parseDouble(str[0]);
				return new Complex(real, imaginary);
			} else if(str[0].equals("")) {
				str = str[1].split("i");
				imaginary = -Double.parseDouble(str[0]);
				return new Complex(real, imaginary);
			} else if(str.length == 2 && str[1].equals("i")) {
				real = Double.parseDouble(str[0]);
				return new Complex(real, -1);
			} else {
				real = Double.parseDouble(str[0]);
				str = str[1].split("i");
				if(str[0].equals("")) {
					imaginary = -1;
				} else {
					imaginary = -Double.parseDouble(str[0]);
				}
				return new Complex(real, imaginary);
			}
		}
		
		if(!s.contains("-")) {
			str = s.split("\\+");
			if(str.length == 3 && str[2].equals("i")) {
				real = Double.parseDouble(str[1]);
				return new Complex(real, 1);
			} else if(str.length == 3 && !str[2].equals("i")) {
				real = Double.parseDouble(str[1]);
				str = str[2].split("i");
				imaginary = Double.parseDouble(str[0]);
				return new Complex(real, imaginary);
			} else if(str[0].equals(" ")) {
				str = str[1].split("i");
				imaginary = Double.parseDouble(str[0]);
				return new Complex(real, imaginary);
			} else if(str.length == 2 && str[1].equals("i")) {
				real = Double.parseDouble(str[0]);
				return new Complex(real, 1);
			} else {
				real = Double.parseDouble(str[0]);
				str = str[1].split("i");
				if(str[0].equals(" ")) {
					imaginary = 1;
				} else {
					imaginary = Double.parseDouble(str[0]);
				}
				return new Complex(real, imaginary);
			}
		}
		
		if(s.contains("+") && s.contains("-")) {
			str = s.split("-");
			if(str[1].equals("i")) {
				real = Double.parseDouble(str[0]);
				return new Complex(real, -1);
			} else if(str.length == 2 && !str[0].equals("")) {
				real = Double.parseDouble(str[0]);
				str = str[1].split("i");
				imaginary = -Double.parseDouble(str[0]);
				return new Complex(real, imaginary);
			} else {
				str = str[1].split("\\+");
				if(str[1].equals("i")) {
					real = -Double.parseDouble(str[0]);
					return new Complex(real, 1);
				} else {
					real = -Double.parseDouble(str[0]);
					str = str[1].split("i");
					imaginary = Double.parseDouble(str[0]);
					return new Complex(real, imaginary);
				}
			}
		}
		
		return new Complex(0, 0);
		
	}
	
	
	
	
	
	
}
