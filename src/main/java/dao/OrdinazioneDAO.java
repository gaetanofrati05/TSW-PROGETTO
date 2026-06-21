package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import bean.OrdinazioneBean;
import bean.ProdottoBean;
import bean.ProdottoCarrello;
import bean.UtenteBean;
public class OrdinazioneDAO {
	
	public int doCreateOrdinazioneFromCarrello(OrdinazioneBean ordinazione, List<ProdottoCarrello> items) throws SQLException {

	    if (items == null || items.isEmpty()) {
	        throw new SQLException("Il carrello è vuoto");
	    }

	    Connection connection = null;
	    PreparedStatement psDecrementa = null;
	    PreparedStatement psOrdinazione = null;
	    PreparedStatement psComposizione = null;
	    PreparedStatement psUtente = null;

	    // Query per decrementare la quantità disponibile del prodotto
	    String decrementaQuantita = 
	        "UPDATE Prodotto SET quantita = quantita - ? WHERE idProdotto = ? AND quantita >= ?";
	    
	    // Query per creare l'ordinazione
	    String insertOrdinazione = 
	        "INSERT INTO Ordinazione (citta, dataOrdinazione, importo, indirizzo, civico, cap, statoOrdinazione, fk_utente_email) " +
	        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	    
	    // Query per registrare ogni prodotto dell'ordine nella tabella di composizione
	    String insertComposizione = 
	        "INSERT INTO Composizione (fk_Composizione_idOrdinazione, fk_Composizione_idProdotto, quantita_ordinata) " +
	        "VALUES (?, ?, ?)";
	    
	    // Query per aggiornare il contatore degli ordini dell'utente
	    String incrementaOrdiniUtente = 
	        "UPDATE Utente SET num_ordinazioni = num_ordinazioni + 1 WHERE email = ?";

	    try {
	        connection = ConnectionPool.getConnection();
	        connection.setAutoCommit(false); // inizia la transazione: o tutto va a buon fine, o si annulla tutto

	        // STEP 1: decrementa la quantità disponibile per ogni prodotto ordinato
	        psDecrementa = connection.prepareStatement(decrementaQuantita);
	        for (ProdottoCarrello item : items) {
	            psDecrementa.setInt(1, item.getQuantita());
	            psDecrementa.setInt(2, item.getProdotto().getIdProdotto());
	            psDecrementa.setInt(3, item.getQuantita()); // controlla che ci sia disponibilità sufficiente
	            if (psDecrementa.executeUpdate() == 0) {
	                // se nessuna riga è stata aggiornata, la quantità non era sufficiente
	                throw new SQLException("Quantità non disponibile per: " + item.getProdotto().getNome());
	            }
	        }

	        // STEP 2: inserisci l'ordinazione nel DB e recupera l'id generato automaticamente
	        psOrdinazione = connection.prepareStatement(insertOrdinazione, Statement.RETURN_GENERATED_KEYS);
	        psOrdinazione.setString(1, ordinazione.getCitta());
	        psOrdinazione.setDate(2, ordinazione.getDataOrdinazione());
	        psOrdinazione.setFloat(3, ordinazione.getImporto());
	        psOrdinazione.setString(4, ordinazione.getIndirizzo());
	        psOrdinazione.setString(5, ordinazione.getCivico());
	        psOrdinazione.setString(6, ordinazione.getCap());
	        psOrdinazione.setString(7, ordinazione.getStato());
	        psOrdinazione.setString(8, ordinazione.getUtente().getEmail());
	        psOrdinazione.executeUpdate();

	        // recupera l'id dell'ordinazione appena inserita
	        int idOrdinazione;
	        ResultSet generatedKeys = psOrdinazione.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            idOrdinazione = generatedKeys.getInt(1);
	        } else {
	            throw new SQLException("Ordinazione non generata correttamente");
	        }

	        // STEP 3: inserisci un record in Composizione per ogni prodotto del carrello
	        psComposizione = connection.prepareStatement(insertComposizione);
	        for (ProdottoCarrello item : items) {
	            psComposizione.setInt(1, idOrdinazione);
	            psComposizione.setInt(2, item.getProdotto().getIdProdotto());
	            psComposizione.setInt(3, item.getQuantita());
	            psComposizione.executeUpdate();
	        }

	        // STEP 4: incrementa il contatore degli ordini dell'utente
	        psUtente = connection.prepareStatement(incrementaOrdiniUtente);
	        psUtente.setString(1, ordinazione.getUtente().getEmail());
	        psUtente.executeUpdate();

	        connection.commit(); // tutto ok: salva tutto nel DB
	        return idOrdinazione;

	    } catch (SQLException e) {
	        if (connection != null) {
	            connection.rollback(); // qualcosa è andato storto: annulla tutto
	        }
	        throw e; // rilancia l'eccezione così la Servlet la intercetta
	    } finally {
	        // chiudi sempre le risorse, anche in caso di errore
	        if (psDecrementa != null) psDecrementa.close();
	        if (psOrdinazione != null) psOrdinazione.close();
	        if (psComposizione != null) psComposizione.close();
	        if (psUtente != null) psUtente.close();
	        if (connection != null) {
	            connection.setAutoCommit(true);
	            ConnectionPool.releaseConnection(connection);
	        }
	    }
	}
	
/*Metodo per vedere il proprio storico ordini dello specifico utente*/
	public List<OrdinazioneBean> doStampaListaOrdinazione(String emailUtente) throws SQLException {
		Connection connection = null;
	    PreparedStatement psOrdinazione = null;
	    ResultSet result= null;
	    String queryStampaOrdinazioni= "SELECT * FROM Ordinazione WHERE fk_utente_email=?";
	    try {
	    	connection=ConnectionPool.getConnection();
	        psOrdinazione= connection.prepareStatement(queryStampaOrdinazioni);
	        psOrdinazione.setString(1, emailUtente);
	        result= psOrdinazione.executeQuery();
	        List<OrdinazioneBean> listaOrdinazioni= new ArrayList<>();
	        while(result.next()) {
	        	OrdinazioneBean ordinazione= new OrdinazioneBean();
	        	ordinazione.setIdOrdinazione(result.getInt("idOrdinazione"));
	            ordinazione.setCitta(result.getString("citta"));
	            ordinazione.setDataOrdinazione(result.getDate("dataOrdinazione"));
	            ordinazione.setImporto(result.getFloat("importo"));
	            ordinazione.setIndirizzo(result.getString("indirizzo"));
	            ordinazione.setCivico(result.getString("civico"));
	            ordinazione.setCap(result.getString("cap"));
	            ordinazione.setStato(result.getString("statoOrdinazione"));	        
	            
	            UtenteBean utente = new UtenteBean();
	         
	            utente.setEmail(result.getString("fk_utente_email")); 
	            ordinazione.setUtente(utente);
	            
	            // Aggiunta dell'ordinazione completata alla lista
	            listaOrdinazioni.add(ordinazione);
	        
	        }
	        return listaOrdinazioni;
	    
	    }finally {
	    	 if (result != null) result.close();
		        if (psOrdinazione != null) psOrdinazione.close();
		        ConnectionPool.releaseConnection(connection);
		    }
	    }

	public List<OrdinazioneBean> doRetrieveByEmailWithOrder(String emailUtente, String orderBy) throws SQLException {
		String orderClause = " ORDER BY dataOrdinazione DESC";
		if ("date_asc".equals(orderBy)) {
			orderClause = " ORDER BY dataOrdinazione ASC";
		} else if ("importo_desc".equals(orderBy)) {
			orderClause = " ORDER BY importo DESC";
		} else if ("importo_asc".equals(orderBy)) {
			orderClause = " ORDER BY importo ASC";
		}

		Connection connection = null;
		PreparedStatement psOrdinazione = null;
		ResultSet result = null;
		String query = "SELECT * FROM Ordinazione WHERE fk_utente_email = ?" + orderClause;

		try {
			connection = ConnectionPool.getConnection();
			psOrdinazione = connection.prepareStatement(query);
			psOrdinazione.setString(1, emailUtente);
			result = psOrdinazione.executeQuery();

			List<OrdinazioneBean> listaOrdinazioni = new ArrayList<>();
			while (result.next()) {
				OrdinazioneBean ordinazione = new OrdinazioneBean();
				ordinazione.setIdOrdinazione(result.getInt("idOrdinazione"));
				ordinazione.setCitta(result.getString("citta"));
				ordinazione.setDataOrdinazione(result.getDate("dataOrdinazione"));
				ordinazione.setImporto(result.getFloat("importo"));
				ordinazione.setIndirizzo(result.getString("indirizzo"));
				ordinazione.setCivico(result.getString("civico"));
				ordinazione.setCap(result.getString("cap"));
				ordinazione.setStato(result.getString("statoOrdinazione"));

				UtenteBean utente = new UtenteBean();
				utente.setEmail(result.getString("fk_utente_email"));
				ordinazione.setUtente(utente);

				listaOrdinazioni.add(ordinazione);
			}
			return listaOrdinazioni;
		} finally {
			if (result != null) result.close();
			if (psOrdinazione != null) psOrdinazione.close();
			ConnectionPool.releaseConnection(connection);
		}
	}

	public OrdinazioneBean doRetrieveByKey(int idOrdinazione, String emailUtente) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		String query = "SELECT * FROM Ordinazione WHERE idOrdinazione = ? AND fk_utente_email = ?";

		try {
			connection = ConnectionPool.getConnection();
			ps = connection.prepareStatement(query);
			ps.setInt(1, idOrdinazione);
			ps.setString(2, emailUtente);
			result = ps.executeQuery();

			if (result.next()) {
				OrdinazioneBean ordinazione = new OrdinazioneBean();
				ordinazione.setIdOrdinazione(result.getInt("idOrdinazione"));
				ordinazione.setCitta(result.getString("citta"));
				ordinazione.setDataOrdinazione(result.getDate("dataOrdinazione"));
				ordinazione.setImporto(result.getFloat("importo"));
				ordinazione.setIndirizzo(result.getString("indirizzo"));
				ordinazione.setCivico(result.getString("civico"));
				ordinazione.setCap(result.getString("cap"));
				ordinazione.setStato(result.getString("statoOrdinazione"));

				UtenteBean utente = new UtenteBean();
				utente.setEmail(result.getString("fk_utente_email"));
				ordinazione.setUtente(utente);
				return ordinazione;
			}
			return null;
		} finally {
			if (result != null) result.close();
			if (ps != null) ps.close();
			ConnectionPool.releaseConnection(connection);
		}
	}

	public List<ProdottoCarrello> doStampaProdottiOrdinazione(int idOrdinazione, String emailUtente) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		String query = "SELECT P.*, C.quantita_ordinata "
				+ "FROM Composizione C "
				+ "INNER JOIN Prodotto P ON C.fk_Composizione_idProdotto = P.idProdotto "
				+ "WHERE C.fk_Composizione_idOrdinazione = ?";

		try {
			connection = ConnectionPool.getConnection();
			ps = connection.prepareStatement(query);
			ps.setInt(1, idOrdinazione);
			result = ps.executeQuery();

			List<ProdottoCarrello> prodottiOrdine = new ArrayList<>();
			while (result.next()) {
				ProdottoBean prodotto = new ProdottoBean();
				prodotto.setIdProdotto(result.getInt("idProdotto"));
				prodotto.setNome(result.getString("nome"));
				prodotto.setStile(result.getString("stile"));
				prodotto.setColore(result.getString("colore"));
				prodotto.setDimensioni(result.getString("dimensioni"));
				prodotto.setPrezzo(result.getFloat("prezzo"));
				prodotto.setQuantita(result.getInt("quantita"));
				prodotto.setDescrizione(result.getString("descrizione"));
				prodotto.setImmagine(result.getString("immagine"));

				int quantitaOrdinata = result.getInt("quantita_ordinata");
				prodottiOrdine.add(new ProdottoCarrello(prodotto, quantitaOrdinata));
			}
			return prodottiOrdine;
		} finally {
			if (result != null) result.close();
			if (ps != null) ps.close();
			ConnectionPool.releaseConnection(connection);
		}
	}

	public void doDeleteOrdinazione(OrdinazioneBean ordinazione) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		String query = "DELETE FROM Ordinazione WHERE idOrdinazione = ? AND fk_utente_email = ?";

		try {
			connection = ConnectionPool.getConnection();
			ps = connection.prepareStatement(query);
			ps.setInt(1, ordinazione.getIdOrdinazione());
			ps.setString(2, ordinazione.getUtente().getEmail());
			ps.executeUpdate();
		} finally {
			if (ps != null) ps.close();
			ConnectionPool.releaseConnection(connection);
		}
	}

}
