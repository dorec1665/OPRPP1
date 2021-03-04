package hr.fer.zemris.math;


/**
 * Razred koji modelira polinom nad kompleksnim brojevima. Radi se o polinomu
 * oblka f(z) = zn*z^n+...+z2*z^2+z1*z^1+z0, gdje su z0 do zn koeficijenti koji se
 * načaze uz odgovarajuće potencije od z.
 * @author dario
 *
 */
public class ComplexPolynomial {

	//koeficijenti z0 do zn
	private Complex[] factors;
	
	
	/**
	 * Konstruktor u kojem se zadaju koeficijenti polinoma.
	 * @param factors koeficijenti
	 */
	public ComplexPolynomial(Complex ... factors) {
		if(factors == null) {
			throw new NullPointerException();
		}
		
		this.factors = factors;
	}
	
	
	
	/**
	 * Računa stupanj polinoma.
	 * @return stupanj polinoma
	 */
	public short order() {
		return (short) (factors.length-1);
	}
	
	
	
	/**
	 * Dohvaća koefficijente.
	 * @return
	 */
	public Complex[] getFactors() {
		return factors;
	}
	
	
	
	
	/**
	 * Množi dva kompleksna polinoma oblika {@link ComplexPolynomial}.
	 * @param p
	 * @return rezultat množenja
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		if(p == null) {
			throw new NullPointerException();
		}
		
		Complex[] rez = new Complex[p.order()+this.order()+1];
		for(int i = 0; i < rez.length; i++) {
			rez[i] = Complex.ZERO;
		}
		
		for(int i = 0; i < p.getFactors().length; i++) {
			for(int j = 0; j < this.getFactors().length; j++) {
				rez[i+j] = rez[i+j].add(p.getFactors()[i].multiply(this.getFactors()[j]));
			}
		}
		
		return new ComplexPolynomial(rez);
	}
	
	
	
	/**
	 * Računa derivaciju polinoma.
	 * @return  derivaciju polinoma
	 */
	public ComplexPolynomial derive() {
		Complex[] rez = new Complex[this.order()];
		
		for(int i = 1; i < this.getFactors().length; i++) {
			double real = this.getFactors()[i].getRe() * i;
			double imaginary = this.getFactors()[i].getIm() * i;
			rez[i-1] = new Complex(real, imaginary);
		}
		
		return new ComplexPolynomial(rez);
	}
	
	
	
	/**
	 * Računa vrijednost polinoma f(z) za neki konkretan z.
	 * @param z Parametar za koji se računa vrijednost polinoma
	 * @return rezultat izračuna
	 */
	public Complex apply(Complex z) {
		if(z == null) {
			throw new NullPointerException();
		}
		
		Complex rez = Complex.ZERO;
		
		int n = 0;
		for(Complex factor : factors) {
			rez = rez.add(z.power(n).multiply(factor));
			n++;
		}
		
		return rez;
		
	}
	
	
	
	
	@Override
	public String toString() {
		String rez = "";
		short n = order();
		for(int i = factors.length-1; i >= 0; i--) {
			if(i == 0) {
				rez += factors[i].toString();
				continue;
			}
			rez += factors[i].toString() + "*z^" + n + "+";
			n--;
		}
		
		return rez;
	}
	
	
	
	
	
	
}
