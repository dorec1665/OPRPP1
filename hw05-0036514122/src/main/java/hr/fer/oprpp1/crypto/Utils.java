package hr.fer.oprpp1.crypto;


/**
 * Pomoćna klasa za pretvaranja heksadecimalnog zapisa u polje byteova i obratno.
 * @author dario
 *
 */
public class Utils {


	/**
	 * Pretvara heksadecimalni zapis u polje byteova.
	 * @param keyText Heksadecimalni zapis
	 * @return Polje bajtova
	 */
	public static byte[] hextobyte(String keyText) {
		if(keyText.length() % 2 == 1) {
			throw new IllegalArgumentException("Unos mora sadržavati paran broj znakova!");
		}
		byte[] rezArray = new byte[keyText.length()/2];
		char[] arr = keyText.toCharArray();
		int current = 0;
		for(int i = 0; i < arr.length; i++) {
			int decimal1 = hexToDecimal(arr[i]);
			int decimal2 = hexToDecimal(arr[++i]);
			rezArray[current++] = (byte) (decimal1 * 16 + decimal2);
		}
		
		return rezArray;
		
	}
	
	
	
	/**
	 * Pretvara polje byteova u heksadecimalni zapis.
	 * @param bytearray Polje byteova
	 * @return Heksadecimalni zapis
	 */
	public static String bytoToHex(byte[] bytearray) {
		String rez = "";
		for(byte b : bytearray) {
			int decimal = (b & 0xFF);
			rez += decimalToHex(decimal);
		}
		
		return rez;
	}

	
	
	/**
	 * Pretvara heksadecimalni broj u dekadski.
	 * @param hex Heksadecimalni broj
	 * @return Dekadksi broj
	 */
	private static int hexToDecimal(char hex) {
		if(Character.isDigit(hex)) {
			return hex - '0';
		}
		if(Character.isAlphabetic(hex)) {
			hex = Character.toLowerCase(hex);
			if(hex >= 'a' && hex <= 'f') {
				return hex - 'a' + 10;
			}
		}
		
		throw new IllegalArgumentException("Nedozvoljeni znak!");
		
	}
	
	
	
	/**
	 * Pretvara dekadski broj u heksadecimalni.
	 * @param decimal Dekadski broj
	 * @return HeksaDecimalni broj
	 */
	private static String decimalToHex(int decimal) {
		StringBuilder rez = new StringBuilder();
		while(true) {
			if(decimal % 16 >= 10) {
				rez.append((char) ((decimal % 16) - 10 + 'a'));
			} else if (decimal % 16 < 10) {
				rez.append((char) ((decimal % 16) + '0'));
			}
			decimal = decimal / 16;
			if(decimal == 0) {
				break;
			}
		}
		if(rez.length() == 1) {
			rez.append("0");
		}
		return rez.reverse().toString();
	}
	
	
	
	
	
}
