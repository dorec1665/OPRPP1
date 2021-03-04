package hr.fer.oprpp1.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * Predstavlja program koji služi za kriptiranje i dekriptiranje datoteka pomoću
 * AES crypto algoritma, te za 128 bitno kriptiranje ključa i izračuna i provjera
 * sažetka datoteke pomoću SHA-256.
 * @author dario
 *
 */
public class Crypto {

	private static Scanner sc;
	
	public static void main(String[] args) {
		if(args.length < 2) {
			throw new IllegalArgumentException("Nedovoljan broj argumenata!");
		}
		
		sc = new Scanner(System.in);
		
		if(args[0].equals("checksha") && args.length == 2) {
			digest(args[1]);
		} else if(args[0].equals("encrypt") && args.length == 3) {
			crypt(args[1], args[2], true);
		} else if(args[0].equals("decrypt") && args.length == 3) {
			crypt(args[1], args[2], false);
		} else {
			throw new IllegalArgumentException("pogrešan unos argumenata! Unesite ponovno.");
		}
		
		sc.close();
		
	}

	
	/**
	 * Provodi kriptiranje ili dekriptiranje dane datoteke.
	 * @param readFile Datoteka koja se kriptira/dekriptira
	 * @param writeFile Rezultantna datoteka
	 * @param encrypt Zasatavica za odabir kriptiranja/dekriptiranja
	 */
	private static void crypt(String readFile, String writeFile, boolean encrypt) {
		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		System.out.print("> ");
		String keyText = sc.nextLine();
		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		System.out.print("> ");
		String ivText = sc.nextLine();
		
		Path createdFile;
		try {
			createdFile = Files.createFile(Paths.get(writeFile));
		} catch (IOException e) {
			System.err.println("Došlo je do greške pri stvaranju nove datoteke.");
			return;
		}
		
		SecretKeySpec keySpec = new SecretKeySpec(Utils.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Utils.hextobyte(ivText));
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch(Exception e) {
			System.err.println("Došlo je do greške pri stvaranju Cipher-a.");
			return;
		}
		
		try {
			BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(Paths.get(readFile)));
			BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(createdFile, StandardOpenOption.WRITE));
			byte[] bytearray = new byte[4096];
			while(true) {
				int readReturn = bis.read(bytearray);
				if(readReturn == -1) {
					break;
				}
				bos.write(cipher.update(bytearray, 0, readReturn));
			}
			bos.write(cipher.doFinal());
			bis.close();
			bos.close();
		} catch (Exception e) {
			System.err.println("Došlo je do pogreške.");
			e.printStackTrace();
			return;
		}
		
		if(encrypt) {
			System.out.print("Encryption completed. ");
		} else {
			System.out.print("Decryption completed. ");
		}
		System.out.println("Generated file " + writeFile + " based on file " + readFile + " .");
	}

	
	
	/**
	 * Računa i provjerava sažetak zadane datoteke.
	 * @param fileName Datoteka za koju se računa i provjera sažetak
	 */
	private static void digest(String fileName) {
		System.out.println("Please provide expected sha-256 digest for " + fileName + " :");
		System.out.print("> ");
		String expectedDigest = sc.nextLine();
		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Došlo je do greške kod stvaranja objekta MessageDigest.");
			return;
		}
		
		File f = new File(fileName);
		DataInputStream dis = null;
		
		try {
			dis = new DataInputStream(
									new BufferedInputStream(
										new FileInputStream(f)));
		} catch (FileNotFoundException e) {
			System.err.println("Datoteka ne postoji!");
			return;
		}
		
		byte[] arr = null;
		try {
			arr = dis.readAllBytes();
			dis.close();
		} catch (IOException e) {
			System.err.println("Došlo je do greške pri citanju datoteke.");
			return;
		}
		
		sha.update(arr);
		arr = sha.digest();
		String digestRez = Utils.bytoToHex(arr);
		
		System.out.print("Digest completed. ");
		if(digestRez.equals(expectedDigest)) {
			System.out.println("Digest of " + fileName + " matches expected digest.");
		} else {
			System.out.println("Digest of " + fileName + " does not match the expected digest.");
			System.out.println("Digest was: " + expectedDigest);
		}
	}
}
