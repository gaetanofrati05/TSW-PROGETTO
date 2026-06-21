package bean;
import java.sql.Date;
import java.io.Serializable;
public class RecensioneBean implements Serializable {
    private static final long serialVersionUID = 1L;
	private int idRecensione;
	private Date dataRecensione;
	private int scoring;
	private String descrizione;
	private ProdottoBean prodotto;
	private UtenteBean utente;
	public RecensioneBean() {
		
	}
	public int getIdRecensione() {
        return idRecensione;
    }

    public void setIdRecensione(int idRecensione) {
        this.idRecensione = idRecensione;
    }

    public Date getDataRecensione() {
        return dataRecensione;
    }

    public void setDataRecensione(Date dataRecensione) {
        this.dataRecensione = dataRecensione;
    }

    public int getScoring() {
        return scoring;
    }

    public void setScoring(int scoring) {
        if (scoring >= 0 && scoring <= 5) {
            this.scoring = scoring;
        } else {
            this.scoring = 0;
        }
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    
    public ProdottoBean getProdotto() {
        return prodotto;
    }

    public void setProdotto(ProdottoBean prodotto) {
        this.prodotto = prodotto;
    }
    public UtenteBean getUtente() {
    	return utente;
    }
    public void setUtente(UtenteBean utente) {
    	this.utente=utente;
    }
}

