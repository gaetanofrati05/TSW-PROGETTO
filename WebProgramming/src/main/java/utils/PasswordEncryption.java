package utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
public class PasswordEncryption {
    public static String encrypt(String passwordLeggibile) {
    	try {
    	MessageDigest digest= MessageDigest.getInstance("SHA-256");
    	byte [] hash= digest.digest(passwordLeggibile.getBytes(StandardCharsets.UTF_8)); //conversione della stringa in byte
    	StringBuilder hexString= new StringBuilder();
    	for(byte b: hash) {
    		String hex= Integer.toHexString(0xff &b); //conversione della stringa in esadecimale 
    		if(hex.length()==1) { //controllo di aggiunta di padding alla password
    			hexString.append(0);
    		}
			hexString.append(hex);
    	}
    	return hexString.toString();
    }catch(NoSuchAlgorithmException e) {
      throw new RuntimeException ("Algortimo SHA-256 non supportato");
      }
    }
}