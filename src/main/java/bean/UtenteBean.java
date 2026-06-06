package bean;
import java.util.Date;

public class UtenteBean {
	
  private String email;
  private String hash_password;
  private String nome;
  private String cognome;
  private Date dataNascita;
  private String nazionalita;
  private String prefisso;
  private String cellulare;
  private int num_ordinazioni;
  private boolean isAdmin;
     
    public UtenteBean() {
    	
    }
  
    public String getEmail() {
    	return email;
    }
    public void setEmail(String email) {
    	this.email=email;
    }
    public String getHashPassword() {
    	return hash_password;
    }
    public void setHashPassword(String hash_password) {
    	this.hash_password=hash_password;
    }
    public String getNome() {
    	return nome;
    }
    public void setNome(String nome) {
    	this.nome=nome;
    }
    public String getCognome() {
    	return cognome;
    }
    public void setCognome(String cognome) {
    	this.cognome=cognome;
    }
    public Date getData() {
    	return dataNascita;
    }
    public void setData(Date dataNascita) {
    	this.dataNascita=dataNascita;
    }
    public String getNazionalita() {
    	return nazionalita;
    }
    public void setNazionalita(String nazionalita) {
    	this.nazionalita=nazionalita;
    }
    public String getPrefisso() {
    	return prefisso;
    }
    public void setPrefisso(String prefisso) {
    	this.prefisso=prefisso;
    }
    public String getCellulare() {
    	return cellulare;
    }
    public void setCellulare(String cellulare) {
    	this.cellulare=cellulare;
    }
    public int getNumeroOrdinazioni() {
    	return num_ordinazioni;
    }
    public void setNumeroOrdinazioni(int num_ordinazioni) {
    	this.num_ordinazioni=num_ordinazioni;
    }
    public boolean getAdmin() {
    	return isAdmin;
    }
    public void setAdmin(boolean isAdmin) {
    	this.isAdmin=isAdmin;
    }
}
