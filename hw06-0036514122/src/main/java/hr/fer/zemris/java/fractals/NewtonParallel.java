package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;


/**
 * Program koji računa i crta fraktale izvedene iz Newton-Raphson iteracije.
 * Program je višedretven.
 * @author dario
 *
 */
public class NewtonParallel {

	/**
	 * Broj dretvi
	 */
	private static int N;
	/**
	 * Broj poslova
	 */
	private static int K;
	
	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done");
		
		Scanner sc = new Scanner(System.in);
		Complex[] roots = getRoots(sc);
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(Complex.ONE, roots);
		sc.close();
		
		System.out.println("Image of fractal will appear shortly. Thank you");
		
		FractalViewer.show(new MojProducer(crp, args));
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
			Complex c;
			try {
				c = Complex.parse(input);
			} catch (Exception e) {
				System.out.println("Invalid argument! Try again");
				continue;
			}
			roots.add(c);
			i++;
		}
		
		return roots.toArray(new Complex[roots.size()]);
	}
	
	
	
	/**
	 * Razred koji služi za paraleliziranje izračuna fraktala.
	 * @author dario
	 *
	 */
	public static class PosaoIzracuna implements Runnable {
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		double maxIter;
		double convergenceTreshold;
		double rootTreshold;
		ComplexRootedPolynomial crp;
		ComplexPolynomial cp;
		ComplexPolynomial derivative;
		short[] data;
		AtomicBoolean cancel;
		public static PosaoIzracuna NO_JOB = new PosaoIzracuna();
		
		private PosaoIzracuna() {
		}

		
		/**
		 * Konstruktor za stvaranje izračuna.
		 * @param reMin minimalna vrijednost realne osi
		 * @param reMax maksimalna vrijednost realne osi
		 * @param imMin minimalna vrijednsot imaginarne osi
		 * @param imMax maksimalna vrijednost imaginarne osi
		 * @param width širina prozora
		 * @param height visina prozora
		 * @param yMin minimalna vrijednost na y-osi
		 * @param yMax maksimalna vrijednost na y-osi
		 * @param maxIter maksimalan broj iteracija za testiranje konvergencije
		 * @param convergenceTreshold prag za konvergenciju
		 * @param rootTreshold prag za pronalazak minimalne udaljenosti korijena
		 * @param crp polinom korijenskog oblika
		 * @param cp polinom stupanjskog oblika
		 * @param derivative derivacija stupanjskog polinoma
		 * @param data podatci pomoću kojeg se crta fraktal
		 * @param cancel zastavica za zaustavljanje izračuna
		 */
		public PosaoIzracuna(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
				int yMax, double maxIter, double convergenceTreshold, double rootTreshold, ComplexRootedPolynomial crp,
				ComplexPolynomial cp, ComplexPolynomial derivative, short[] data, AtomicBoolean cancel) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.maxIter = maxIter;
			this.convergenceTreshold = convergenceTreshold;
			this.rootTreshold = rootTreshold;
			this.crp = crp;
			this.cp = cp;
			this.derivative = derivative;
			this.data = data;
			this.cancel = cancel;
		}



		@Override
		public void run() {
			int offset = yMin * width;
			for(int y = yMin; y <= yMax; y++) {
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
		}
		
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
		private String[] args;
		
		
		/**
		 * Stvara i inicijalizira producera za crtanje fraktala.
		 * @param crp polinom koji služi za izračun fraktala
		 * @param args argumenti za izračun broja dretvi i poslova
		 */
		public MojProducer(ComplexRootedPolynomial crp, String[] args) {
			this.args = args;
			this.crp = crp;
			cp = crp.toComplexPolynom();
			derivative = cp.derive();
		}

		
		/**
		 * Računa broj dretvi i poslova za izračun i crtanje fraktala.
		 * @param args argumenti za izračun broja dretvi i poslova
		 * @param height maksimalan broj poslova
		 */
		private void getArguments(String[] args, int height) {
			if(args.length == 0) {
				N = Runtime.getRuntime().availableProcessors();
				K = Runtime.getRuntime().availableProcessors() * 4;
			} else if(args.length == 1) {
				if(args[0].contains("--workers=")) {
					String numberOfWorkers = "";
					if(args[0].length() == 11) {
						numberOfWorkers = args[0].substring(args[0].length()-1, args[0].length());
					} else if(args[0].length() == 12) {
						numberOfWorkers = args[0].substring(args[0].length()-2, args[0].length());
					}
					if(Integer.parseInt(numberOfWorkers) < 1) {
						System.out.println("Ne mogu stvoriti " + numberOfWorkers + " dretvi. Program je stvorio "
								+ Runtime.getRuntime().availableProcessors() + " dretvi kao zamjenu.");
						N = Runtime.getRuntime().availableProcessors();
					} else {
						N = Integer.parseInt(numberOfWorkers);
					}
					K = Runtime.getRuntime().availableProcessors() * 4;
				} else if(args[0].contains("--tracks=")) {
					String numberOfTracks = args[0].substring(args[0].length()-1, args[0].length());
					int num = Integer.parseInt(numberOfTracks);
					if(num < 1) {
						K = 1;
					} else {
						K = num > height ? height : num;
					}
					N = Runtime.getRuntime().availableProcessors();
				} else {
					throw new IllegalArgumentException("Invalid arguments!");
				}
			} else if(args.length == 2) {
				if(args[0].contains("--workers=") && args[1].contains("--tracks=")) {
					String numberOfWorkers = "";
					if(args[0].length() == 11) {
						numberOfWorkers = args[0].substring(args[0].length()-1, args[0].length());
					} else if(args[0].length() == 12) {
						numberOfWorkers = args[0].substring(args[0].length()-2, args[0].length());
					}
					if(Integer.parseInt(numberOfWorkers) < 1) {
						System.out.println("Ne mogu stvoriti " + numberOfWorkers + " dretvi. Program je stvorio "
								+ Runtime.getRuntime().availableProcessors() + " dretvi kao zamjenu.");
						N = Runtime.getRuntime().availableProcessors();
					} else {
						N = Integer.parseInt(numberOfWorkers);
					}
					String numberOfTracks = args[1].substring(args[1].length()-1, args[1].length());
					int num = Integer.parseInt(numberOfTracks);
					if(num < 1) {
						K = 1;
					} else {
						K = num > height ? height : num;
					}
				} else if(args[0].equals("-w")) {
					if(Integer.parseInt(args[1]) < 1) {
						System.out.println("Ne mogu stvoriti  " + args[1] + " dretvi. Program je stvorio "
								+ Runtime.getRuntime().availableProcessors() + " kao zamjenu.");
						N = Runtime.getRuntime().availableProcessors();
					} else {
						N = Integer.parseInt(args[1]);
					}
					K = Runtime.getRuntime().availableProcessors() * 4;
				} else if(args[0].equals("-t")) {
					int num = Integer.parseInt(args[1]);
					if(num < 1) {
						K = 1;
					} else {
						K = num > height ? height : num;
					}
					N = Runtime.getRuntime().availableProcessors();
				} else {
					throw new IllegalArgumentException("Invalid arguments!");
				}
			} else if(args.length == 4) {
				if(Integer.parseInt(args[1]) < 1) {
					System.out.println("Ne mogu stvoriti" + args[1] + " dretvi. Program je stvorio "
							+ Runtime.getRuntime().availableProcessors() + " dretvi kao zamjenu.");
					N = Runtime.getRuntime().availableProcessors();
				} else {
					N = Integer.parseInt(args[1]);
				}				int num = Integer.parseInt(args[3]);
				if(num < 1) {
					K = 1;
				} else {
					K = num > height ? height : num;
				}
			} else {
				throw new IllegalArgumentException("Invalid arguments!");
			}
			
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
				IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun....");
			final double convergenceTreshold = 1E-3;
			final double rootTreshold = 2E-3;
			final int maxIter = 16*16*16;
			short[] data = new short[width * height];
			getArguments(this.args, height);
			final int brojTraka = K;
			int brojYPoTraci = height / brojTraka;
			
			final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();
			
			
			Thread[] radnici = new Thread[N];
			System.out.println("Stvoreno je " + radnici.length + " dretvi i stvorena su " + K + " posla.");
			for(int i = 0; i < radnici.length; i++) {
				radnici[i] = new Thread(new Runnable() {
					
					@Override
					public void run() {
						while(true) {
							PosaoIzracuna p = null;
							try {
								p = queue.take();
								if(p==PosaoIzracuna.NO_JOB) break;
							} catch(InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			
			for(int i = 0; i < radnici.length; i++) {
				radnici[i].start();
			}
			
			for(int i = 0; i < brojTraka; i++) {
				int yMin = i*brojYPoTraci;
				int yMax = (i+1)*brojYPoTraci-1;
				if(i == brojTraka-1) {
					yMax = height-1;
				}
				PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height,
						yMin, yMax, maxIter, convergenceTreshold, rootTreshold,
						crp, cp, derivative, data, cancel);
				while(true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException e) {
					}
				}
				
			}
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						queue.put(PosaoIzracuna.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						radnici[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			System.out.println("Racunanje gotovo.");
			observer.acceptResult(data, (short) (cp.order()+1), requestNo);
		}
		
	}
	
	
	
	
}
