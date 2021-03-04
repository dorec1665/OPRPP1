package hr.fer.oprpp1.hw04.db;

/**
 * Uspoređuje dva znakovna niza. Podržava operacije:
 * < , <=, >, >=, =, != i LIKE.
 * @author dario
 *
 */
public class ComparisonOperators {

	/**
	 * Provjerava je li prvi niz manji od drugog.
	 */
	public static final IComparisonOperator LESS = (str1, str2) -> str1.compareTo(str2) < 0;
	
	/**
	 * Provjerava je li prvi niz manji ili jednak od drugog.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (str1, str2) -> str1.compareTo(str2) <= 0;
	
	/**
	 * Provjerava je li prvi niz veći od drugog.
	 */
	public static final IComparisonOperator GREATER = (str1, str2) -> str1.compareTo(str2) > 0;
	
	/**
	 * Provjerava je li prvi niz veći ili jedna od drugog.
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (str1, str2) -> str1.compareTo(str2) >= 0;
	
	/**
	 * Provjerava je li prvi niz jednak drugom.
	 */
	public static final IComparisonOperator EQUALS = (str1, str2) -> str1.equals(str2);
	
	/**
	 * Provjerava je li prvi niz različit od drugog.
	 */
	public static final IComparisonOperator NOT_EQUALS = (str1, str2) -> !str1.equals(str2);
	
	/**
	 * Kada se poziva ova operacija usporedbe drugi niz može sadržavati znak *.
	 * Znak * predstavlja bilo koji niz koji može doći umjesto njega. Može se nalaziti
	 * na početku, sredini ili na kraju niza. LIKE provjerava jeli prvi niz jednak drugome.
	 * Primjer niz <code>abcdef</code> je jednak nizu <code>abc*</code>, jer se znak * može zamjeniti s nizom
	 * <code>def</code> , ali niz <code>abc</code> nije jednako nizu <code>abcd*</code>.
	 */
	public static final IComparisonOperator LIKE = (str1, str2) -> checkLikeOperator(str1, str2);
	
	

	private static boolean checkLikeOperator(String str1, String str2) {
		if(str1.equals(str2)) {
			return true;
		}
		
		return firstLikeSecond(str1, str2);
	}
	
	
	/**
	 * Provjerava jeli prvi niz LIKE drugi.
	 * @param str1 Prvi niz
	 * @param str2 Drugi niz
	 * @return true ako je prvi niz jednak drugome, false inače
	 */
	private static boolean firstLikeSecond(String str1, String str2) {
		if(str2.length() == 1 && str2.contains("*")) {
			return true;
		}
		
		int numberOfStars = 0;
		char[] characters = str2.toCharArray();
		for(int i = 0; i < characters.length; i++) {
			if(characters[i] == '*') {
				numberOfStars++;
			}
			if(numberOfStars > 1) {
				throw new IllegalArgumentException("U literalu ne smiju biti dva ili više znaka *!");
			}
		}
		
		String[] data = str2.split("\\*");

		if(str2.charAt(str2.length()-1) == '*') {
			String[] tmp = new String[2];
			tmp[0] = data[0];
			tmp[1] = "";
			data = tmp;
			numberOfStars++;
			
		}
		
		if(data[0] == "" && str2.endsWith(str1)) {
			return true;
		}
		
		if(data[1] == "" && str2.startsWith(str1)) {
			return true;
		}
		if((data[0].length() + data[1].length()) <= str1.length()) {
			if(str1.startsWith(data[0]) && str1.endsWith(data[1])) {
				return true;
			}
		}
		
		return false;
	}
	
	
	
	
	
}
