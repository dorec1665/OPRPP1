package hr.fer.zemris.math;

/**
 * Razred koji predstavlja vektor u 2D prostoru koji sadrži
 * realne komponente x i y.
 * @author dario
 *
 */
public class Vector2D {

	private double x;
	private double y;
	
	
	/**
	 * Konstruktor koji stvara vektor (x, y).
	 * @param x x-komponenta vektora
	 * @param y y-komponenta vektora
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * 
	 * @return x-komponentu vektora
	 */
	public double getX() {
		return x;
	}

	/**
	 * 
	 * @return y-komponentu vektora
	 */
	public double getY() {
		return y;
	}
	
	
	/**
	 * Nadodaje drugi vektor prvome.
	 * @param offset drugi vektor
	 * @throws NullPointerException ako je offset <code>null</code>
	 */
	public void add(Vector2D offset) {
		if(offset == null) {
			throw new NullPointerException("Argument funkcije add ne smije bit null!");
		}
		this.x += offset.x;
		this.y += offset.y;
	}
	
	
	/**
	 * Kopira prvi vektor u drugi, bez da mijenja prvi vektor.
	 * @return novokopirani vektor
	 */
	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}
	
		
	/**
	 * Zbraja dva vektora, i zbroj sprema u novi vektor.
	 * @param offset drugi pribrojnik zbroja
	 * @return zbroj dvaju vektora
	 */
	public Vector2D added(Vector2D offset) {
		if(offset == null) {
			throw new NullPointerException("Argument funkcije added ne smije bit null!");
		}
		Vector2D newVector = this.copy();
		
		newVector.add(offset);
		
		return newVector;
		
	}
	
	
	/**
	 * Rotira vektor za zadani kut
	 * @param angle kut rotacije
	 */
	public void rotate(double angle) {
		double x = this.x;
		double y = this.y;
		
		this.x = x*Math.cos(angle) - y*Math.sin(angle);
		this.y = x*Math.sin(angle) + y*Math.cos(angle);
	}
	
	
	/**
	 * Nad vektorom vrši rotaciju za zadani kut i riješenje sprema
	 * u novi vektor, pri tome ne mijenjajući vektor nad kojim se vršila rotacija.
	 * @param angle kut rotacije
	 * @return novi vektor rotiran za kut rotacije
	 */
	public Vector2D rotated(double angle) {
		Vector2D newVector = this.copy();
		
		newVector.rotate(angle);
		
		return newVector;
		
	}
	
	
	/**
	 * Množi vektor skalarom.
	 * @param scaler skalar
	 */
	public void scale(double scaler) {
		this.x *= scaler;
		this.y *= scaler;
	}
	
	
	/**
	 * Nad vektorom vrši množenje skalarom i riješenje sprema u novi
	 * vektor, pri tome ne mijenjajući vektor nad kojim se vršilo množenje.
	 * @param scaler skalar
	 * @return novi vektor pomnožen skalarom
	 */
	public Vector2D scaled(double scaler) {
		Vector2D newVector = this.copy();
		
		newVector.scale(scaler);
		
		return newVector;
	}
	
	
	
	
	
	
	
	
	
}
