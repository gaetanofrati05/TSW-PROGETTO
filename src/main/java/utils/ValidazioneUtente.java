package utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ValidazioneUtente {
	public static boolean validateEmail(String email) {
	    String regex = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b"; //prima parte caratteri ammessi prima di @ una o più volte seconda parte caratteri
	    //ammessi dopo @ una o più volte \\. significa il punto ultima parte dominio caratteri ammessi lungo da 2 a indefinito 
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(email);
	    return matcher.matches();
	}
	public static boolean validatePassword(String password) {
	    String regex = "[A-Za-z0-9@#!?^\\s_-èòàù]{8,16}"; //prima parte caratteri ammessi dopo ^ quelli no e lunghezza minima 
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(password);
	    return matcher.matches();
	}
	public static boolean validateCellulare(String cellulare) {
	    String regex = "[0-9]{9,10}";
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(cellulare);
	    return matcher.matches();
	}
	public static boolean validatePrefisso(String prefisso) {
	    String regex = "\\+[0-9]{1,3}";
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(prefisso);
	    return matcher.matches();
	}
     public static boolean validateNome(String nome) {
    	 String regex= "[A-Za-zàèòù\\s'-]+{4,30}";
    	 Pattern pattern= Pattern.compile(regex);
    	 Matcher matcher= pattern.matcher(nome);
    	 return matcher.matches();
     }
     public static boolean validateCognome(String cognome) {
    	 String regex= "[A-Za-zàèòù\\s'-]+{4,30}";
    	 Pattern pattern= Pattern.compile(regex);
    	 Matcher matcher= pattern.matcher(cognome);
    	 return matcher.matches();
     }
}
