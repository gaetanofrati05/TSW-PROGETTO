package bean;
import java.io.Serializable;
import java.sql.Date;
public class OrdinazioneBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private int idOrdinazione;
    private String citta;
    private Date dataOrdinazione; 
    private float importo;
    private String indirizzo;
    private int civico;
    private String cap;
    private UtenteBean utente;

    
    public OrdinazioneBean() {
    }

    public int getIdOrdinazione() {
        return idOrdinazione;
    }

    public void setIdOrdinazione(int idOrdinazione) {
        this.idOrdinazione = idOrdinazione;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public Date getDataOrdinazione() {
        return dataOrdinazione;
    }

    public void setDataOrdinazione(Date dataOrdinazione) {
        this.dataOrdinazione = dataOrdinazione;
    }

    public float getImporto() {
        return importo;
    }

    public void setImporto(float importo) {
        this.importo = importo;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public int getCivico() {
        return civico;
    }

    public void setCivico(int civico) {
        this.civico = civico;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public UtenteBean getUtente() {
        return utente;
    }

    public void setUtente(UtenteBean utente) {
        this.utente = utente;
    }
}