package hr.fer.zemris.math;

/**
 * Razred koji modelira polinom nad kompleksnim brojevima. Radi se o polinomu
 * f(z) = z0*(z-z1)*(z-z2)*...*(z-zn), gdje su z1 do zn njegove nultočke,
 * a z0 konstanta.
 * @author dario
 *
 */
public class ComplexRootedPolynomial {

	//z0
	private Complex constant;
	//z1 do zn
	private Complex[] roots;
	
	
	/**
	 * Konstrukor s kojim se zadaju nultočke polinoma i konstanta
	 * @param constant z0
	 * @param roots z1 do zn
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		if(constant == null || roots == null) {
			throw new NullPointerException();
		}
		
		this.constant = constant;
		this.roots = roots;
	}
	
	
	
	/**
	 * Računa vrijednost polinoma f(z) za neki konkretan z.
	 * @param z Parametar za koji se računa vrijednost polinoma
	 * @return rezultat izračuna
	 */
	public Complex apply(Complex z) {
		if(z == null) {
			throw new NullPointerException("z must not be null");
		}
		
		Complex rez = constant;
		for(Complex z_n : roots) {
			rez = rez.multiply(z.sub(z_n));
		}
		
		return rez;
	}
	
	
	
	/**
	 * Za zadani z i za zadanu minimalnu udaljenost, metoda računa indeks korijena 
	 * koji je najbliže zadanom kompleksnom broju z. Ako su sve udaljenosti veće od
	 * zadane minimalne udaljenosti metoda vraća -1. 
	 * @param z zadani kompleksni broj
	 * @param treshold minimalna udaljenost
	 * @return -1 ako su sve udaljenosti veće od treshold, inače indeks najbližeg korijena
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		if(z == null) {
			throw new NullPointerException("z must not be null");
		}
		
		double smallest = Double.MAX_VALUE;
		int index = -1;
		for(int i = 0; i < roots.length; i++) {
			double u = roots[i].getRe() - z.getRe();
			double v = roots[i].getIm() - z.getIm();
			double distance = Math.sqrt(u*u + v*v);
			if(smallest > distance) {
				smallest = distance;
				index = i;
			}
		}
		
		if(smallest <= treshold) {
			return index;
		}
		
		return -1;
		
	}
	
	
	
	/**
	 * Pretvara {@link ComplexRootedPolynomial} u {@link ComplexPolynomial}.
	 * @return
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial cp = new ComplexPolynomial(constant);
		for(Complex root : roots) {
			ComplexPolynomial tmp = new ComplexPolynomial(root.negate(), Complex.ONE);
			cp = cp.multiply(tmp);
		}
		
		return cp;
	}
	
	
	@Override
	public String toString() {
		String rez = constant.toString() + "*";
		for(int i = 0; i < roots.length; i++) {
			if(i == roots.length-1) {
				rez += "(z-" + roots[i].toString() + ")";
				continue;
			}
			rez += "(z-" + roots[i].toString() + ")*";
		}
		
		return rez;
	}
	
	
}
