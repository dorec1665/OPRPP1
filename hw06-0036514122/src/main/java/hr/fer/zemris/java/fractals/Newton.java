package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;


/**
 * Program koji računa i crta fraktale izvedene iz Newton-Raphson iteracije.
 * Program, za razliku od {@link NewtonParallel}, nije višedretven.
 * @author dario
 *
 */
public class Newton {

	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done");
		
		Scanner sc = new Scanner(System.in);
		Complex[] roots = getRoots(sc);
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(Complex.ONE, roots);
		sc.close();
		
		System.out.println("Image of fractal will appear shortly. Thank you");
		
		FractalViewer.show(new MojProducer(crp));
	}

	/**
	 * Pretvara korijene koje korisnik unose u podatke s kojima
	 * se može raditi izračun.
	 * @param sc
	 * @return korijene
	 */
	private static Complex[] getRoots(Scanner sc) {
		List<Complex> roots = new ArrayList<>();
		
		int i = 1;
		while(true) {
			System.out.printf("Root %d>", i);
			String input = sc.nextLine();
			if(input.equals("done")) {
				if(roots.size() < 2) {
					System.out.println("Please provide at least two roots");
					continue;
				}
				break;
			}
			Complex c = Complex.parse(input);
			roots.add(c);
			i++;
		}
		
		return roots.toArray(new Complex[roots.size()]);
	}
	
	
	/**
	 * Producer koji se koristi za crtanje fraktala.
	 * @author dario
	 *
	 */
	public static class MojProducer implements IFractalProducer {
		
		private ComplexRootedPolynomial crp;
		private ComplexPolynomial cp;
		private ComplexPolynomial derivative;
		
		
		/**
		 * Stvara i inicijalizira producera za crtanje fraktala.
		 * @param crp polinom koji služi za izračun fraktala
		 */
		public MojProducer(ComplexRootedPolynomial crp) {
			this.crp = crp;
			cp = crp.toComplexPolynom();
			derivative = cp.derive();
		}
		
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
				IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinje izracun....");
			int offset = 0;
			final double convergenceTreshold = 1E-3;
			final double rootTreshold = 2E-3;
			final int maxIter = 1024;
			short data[] = new short[width * height];
			for(int y = 0; y < height; y++) {
				if(cancel.get()) break;
				for(int x = 0; x < width; x++) {
					double cre = x / (width-1.0) * (reMax - reMin) + reMin;
					double cim = (height-1.0-y) / (height-1.0) * (imMax - imMin) + imMin;
					Complex c = new Complex(cre, cim);
					Complex zn = c;
					int iter = 0;
					double module;
					do {
						Complex numerator = cp.apply(zn);
						Complex denominator = derivative.apply(zn);
						Complex znold = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						iter++;
					} while(module>convergenceTreshold && iter<maxIter);
					int index = crp.indexOfClosestRootFor(zn, rootTreshold);
					data[offset++] = (short)(index+1);
				}
			}
			
			System.out.println("Racunanje gotovo.");
			observer.acceptResult(data, (short)(cp.order()+1), requestNo);
			
		}
		
	}
}
