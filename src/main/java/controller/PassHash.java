package controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException; 
public class PassHash {
	// Convert byte array to hex string
	public static String bytesToHex(byte[] bytes2convert) {
		StringBuilder hexString = new StringBuilder(2 * bytes2convert.length);
		for (byte b : bytes2convert) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1) hexString.append('0'); // Append leading zero if needed
			hexString.append(hex);
		}
		return hexString.toString();
		//acrescentar .toUpperCase para HEX em maiusculas
	}
	public static String generateHash(String pass) throws
	NoSuchAlgorithmException {
		MessageDigest obj = java.security.MessageDigest.getInstance("SHA-256"); //MD5 SHA-1 SHA-256
		obj.update(pass.getBytes()); 
		//use the digest() method for computing the message digest 
		byte[] byteArray = obj.digest(); 
		//convert the byte array in to Hex String format and return result 
		return bytesToHex(byteArray);
	}
}
