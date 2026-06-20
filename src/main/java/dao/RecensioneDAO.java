package dao;
import bean.RecensioneBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
public class RecensioneDAO {

    public  void doSaveRecensione(RecensioneBean recensione) throws SQLException {
    Connection connection = null;
    PreparedStatement psRecensione = null;
    PreparedStatement psValutazione = null;

    String insertRecensione = "INSERT INTO Recensione (data_Recensione, Scoring, descrizione, fk_Prodotto_idProdotto) VALUES (?, ?, ?, ?)";
    String insertValutazione = "INSERT INTO Valutazione (fk_Valutazione_email, fk_Valutazione_idRecensione) VALUES (?, ?)";

    try {
        connection = ConnectionPool.getConnection();
        connection.setAutoCommit(false); // transazione: o tutto o niente

        psRecensione = connection.prepareStatement(insertRecensione, Statement.RETURN_GENERATED_KEYS);

        if (recensione.getDataRecensione() != null) {
            psRecensione.setDate(1, new java.sql.Date(recensione.getDataRecensione().getTime()));
        } else {
            psRecensione.setNull(1, java.sql.Types.DATE);
        }
        psRecensione.setInt(2, recensione.getScoring());
        psRecensione.setString(3, recensione.getDescrizione());
        psRecensione.setInt(4, recensione.getProdotto().getIdProdotto());
        psRecensione.executeUpdate();

        
        int idRecensioneGenerato;
        ResultSet generatedKeys = psRecensione.getGeneratedKeys();
        if (generatedKeys.next()) {
            idRecensioneGenerato = generatedKeys.getInt(1);
        } else {
            throw new SQLException("Creazione recensione fallita, nessun ID generato.");
        }

        
        psValutazione = connection.prepareStatement(insertValutazione);
        psValutazione.setString(1, recensione.getUtente().getEmail());
        psValutazione.setInt(2, idRecensioneGenerato);
        psValutazione.executeUpdate();

        connection.commit(); // tutto ok, salva

    } catch (SQLException e) {
        if (connection != null) connection.rollback(); // errore, annulla tutto
        throw e;
    } finally {
        if (psRecensione != null) psRecensione.close();
        if (psValutazione != null) psValutazione.close();
        if(connection!=null) {
        connection.setAutoCommit(true);
        ConnectionPool.releaseConnection(connection);
        }
     }
   }
   public void doUpdateRecensione(RecensioneBean recensione)throws SQLException {
	   Connection connection = null;
	   PreparedStatement psRecensione = null;
	   String updateRecensione= "UPDATE Recensione SET data_recensione=?, Scoring=?,descrizione=? WHERE idRecensione=?";
	   try {
			connection= ConnectionPool.getConnection();
			psRecensione= connection.prepareStatement(updateRecensione);
			if (recensione.getDataRecensione() != null) {
	            psRecensione.setDate(1, new java.sql.Date(recensione.getDataRecensione().getTime()));
	        } else {
	            psRecensione.setNull(1, java.sql.Types.DATE);
	        }
	        psRecensione.setInt(2, recensione.getScoring());
	        psRecensione.setString(3, recensione.getDescrizione());
	        psRecensione.setInt(4, recensione.getIdRecensione());
	        psRecensione.executeUpdate();
	   }finally {
		   if(psRecensione!=null) {
	            psRecensione.close();
	    	}
	    ConnectionPool.releaseConnection(connection);
	   }
   }
   public void deleteRecensione(RecensioneBean recensione) throws SQLException{
	   Connection connection = null;
	   PreparedStatement psRecensione = null;
	   String deleteRecensione = "DELETE FROM Recensione WHERE idRecensione=?";
	   try {
		   connection=ConnectionPool.getConnection();
		   psRecensione=connection.prepareStatement(deleteRecensione);
		   psRecensione.setInt(1, recensione.getIdRecensione());
		   psRecensione.executeUpdate();
	   }finally {
		   if(psRecensione!=null) {
			   psRecensione.close();
		   }
		   ConnectionPool.releaseConnection(connection);
	   }
   }
   /*query per mostrare le recensioni in base alla data, lo scoring*/
   public List<RecensioneBean> doRetrieveByDate() throws SQLException {
	    Connection connection = null;
	    PreparedStatement ps = null;
	    ResultSet result = null;
	    String queryDate = "SELECT * FROM Recensione ORDER BY data_Recensione DESC";
	    
	    try {
	        connection = ConnectionPool.getConnection();
	        ps = connection.prepareStatement(queryDate);
	        result = ps.executeQuery();
	        
	        List<RecensioneBean> listaRecensioni = new ArrayList<>(); 
	        
	        while (result.next()) { 
	            RecensioneBean recensione = new RecensioneBean();
	            recensione.setIdRecensione(result.getInt("idRecensione"));
	            recensione.setDataRecensione(result.getDate("data_Recensione"));
	            recensione.setScoring(result.getInt("Scoring"));
	            recensione.setDescrizione(result.getString("descrizione"));
	            
	            listaRecensioni.add(recensione); 
	        }
	        
	        return listaRecensioni;
	        
	    } finally {
	        if (result != null) result.close();
	        if (ps != null) ps.close();
	        ConnectionPool.releaseConnection(connection);
	    }
	}
   public List<RecensioneBean> doRetrieveByScoring() throws SQLException{
	   Connection connection = null;
	    PreparedStatement ps = null;
	    ResultSet result = null;
	    String queryDate = "SELECT * FROM Recensione ORDER BY Scoring DESC";
	    
	    try {
	        connection = ConnectionPool.getConnection();
	        ps = connection.prepareStatement(queryDate);
	        result = ps.executeQuery();
	        
	        List<RecensioneBean> listaRecensioni = new ArrayList<>(); 
	        while (result.next()) { 
	            RecensioneBean recensione = new RecensioneBean();
	            recensione.setIdRecensione(result.getInt("idRecensione"));
	            recensione.setDataRecensione(result.getDate("data_Recensione"));
	            recensione.setScoring(result.getInt("Scoring"));
	            recensione.setDescrizione(result.getString("descrizione"));
	            
	            listaRecensioni.add(recensione); 
	        }
	        return listaRecensioni;
	    }finally {
	    	if (result != null) result.close();
	        if (ps != null) ps.close();
	        ConnectionPool.releaseConnection(connection);
	    }
   }
   //da qui possiamo fare altre query ad esempio per data e scoring in un certo intervallo di scoring e tante altre quelle che volete scrivetele
   public RecensioneBean doRetrieveByKey(int idRecensione) throws SQLException{
	   Connection connection = null;
	    PreparedStatement ps = null;
	    ResultSet result = null;
	    String queryId = "SELECT * FROM RECENSIONE WHERE idRecensione=?";
	    
	    try {
	    	connection = ConnectionPool.getConnection();
	        ps = connection.prepareStatement(queryId);
	        ps.setInt(1, idRecensione);
	        result = ps.executeQuery(); 
	        while (result.next()) {
	        	 RecensioneBean recensione = new RecensioneBean();
		            recensione.setIdRecensione(result.getInt("idRecensione"));
		            recensione.setDataRecensione(result.getDate("data_Recensione"));
		            recensione.setScoring(result.getInt("Scoring"));
		            recensione.setDescrizione(result.getString("descrizione"));
		            return recensione;
	        }
	        return null;
	    }finally {
	    	if (result != null) result.close();
	        if (ps != null) ps.close();
	        ConnectionPool.releaseConnection(connection);
	    }
   }
   public List<RecensioneBean> doRetrieveByEmail(String email) throws SQLException{
	   Connection connection = null;
	    PreparedStatement ps = null;
	    ResultSet result = null;
	    String queryId = "SELECT R.* FROM Recensione R " +
                "JOIN Valutazione V ON R.idRecensione = V.fk_Valutazione_idRecensione " +
                "WHERE V.fk_Valutazione_email = ? ORDER BY R.data_Recensione DESC";
	
	    try {
	        
	    	connection = ConnectionPool.getConnection();
	        ps = connection.prepareStatement(queryId);
	        ps.setString(1, email);
	        result = ps.executeQuery();
	        List<RecensioneBean> listaRecensioni = new ArrayList<>();
	        while(result.next()) {
	        	 RecensioneBean recensione = new RecensioneBean();
	        	 recensione.setIdRecensione(result.getInt("idRecensione"));
	             recensione.setDataRecensione(result.getDate("data_Recensione"));
	             recensione.setScoring(result.getInt("Scoring"));
	             recensione.setDescrizione(result.getString("descrizione"));
	             
	             listaRecensioni.add(recensione);
	        }
	        return listaRecensioni;
	        
     }finally {
         if (result != null) result.close();
         if (ps != null) ps.close();
         ConnectionPool.releaseConnection(connection);
     }
   }

   public List<RecensioneBean> doRetrieveByEmailWithOrder(String email, String orderBy) throws SQLException {
	   String orderClause = " ORDER BY R.data_Recensione DESC";
	   if ("date_asc".equals(orderBy)) {
		   orderClause = " ORDER BY R.data_Recensione ASC";
	   } else if ("scoring_desc".equals(orderBy)) {
		   orderClause = " ORDER BY R.Scoring DESC";
	   } else if ("scoring_asc".equals(orderBy)) {
		   orderClause = " ORDER BY R.Scoring ASC";
	   }

	   Connection connection = null;
	   PreparedStatement ps = null;
	   ResultSet result = null;
	   String query = "SELECT R.*, P.nome AS nomeProdotto, U.nome AS nomeUtente, U.cognome AS cognomeUtente "
			   + "FROM Recensione R "
			   + "JOIN Valutazione V ON R.idRecensione = V.fk_Valutazione_idRecensione "
			   + "LEFT JOIN Prodotto P ON R.fk_Prodotto_idProdotto = P.idProdotto "
			   + "LEFT JOIN Utente U ON V.fk_Valutazione_email = U.email "
			   + "WHERE V.fk_Valutazione_email = ?" + orderClause;

	   try {
		   connection = ConnectionPool.getConnection();
		   ps = connection.prepareStatement(query);
		   ps.setString(1, email);
		   result = ps.executeQuery();

		   List<RecensioneBean> listaRecensioni = new ArrayList<>();
		   while (result.next()) {
			   listaRecensioni.add(mapRecensione(result));
		   }
		   return listaRecensioni;
	   } finally {
		   if (result != null) result.close();
		   if (ps != null) ps.close();
		   ConnectionPool.releaseConnection(connection);
	   }
   }

   public List<RecensioneBean> doRetrieveGlobalWithOrder(String orderBy) throws SQLException {
	   String orderClause = " ORDER BY R.data_Recensione DESC";
	   if ("date_asc".equals(orderBy)) {
		   orderClause = " ORDER BY R.data_Recensione ASC";
	   } else if ("scoring_desc".equals(orderBy)) {
		   orderClause = " ORDER BY R.Scoring DESC";
	   } else if ("scoring_asc".equals(orderBy)) {
		   orderClause = " ORDER BY R.Scoring ASC";
	   }

	   Connection connection = null;
	   PreparedStatement ps = null;
	   ResultSet result = null;
	   String query = "SELECT R.*, P.nome AS nomeProdotto, U.nome AS nomeUtente, U.cognome AS cognomeUtente "
			   + "FROM Recensione R "
			   + "LEFT JOIN Prodotto P ON R.fk_Prodotto_idProdotto = P.idProdotto "
			   + "LEFT JOIN Valutazione V ON R.idRecensione = V.fk_Valutazione_idRecensione "
			   + "LEFT JOIN Utente U ON V.fk_Valutazione_email = U.email "
			   + orderClause;

	   try {
		   connection = ConnectionPool.getConnection();
		   ps = connection.prepareStatement(query);
		   result = ps.executeQuery();

		   List<RecensioneBean> listaRecensioni = new ArrayList<>();
		   while (result.next()) {
			   listaRecensioni.add(mapRecensione(result));
		   }
		   return listaRecensioni;
	   } finally {
		   if (result != null) result.close();
		   if (ps != null) ps.close();
		   ConnectionPool.releaseConnection(connection);
	   }
   }

   private RecensioneBean mapRecensione(ResultSet result) throws SQLException {
	   RecensioneBean recensione = new RecensioneBean();
	   recensione.setIdRecensione(result.getInt("idRecensione"));
	   recensione.setDataRecensione(result.getDate("data_Recensione"));
	   recensione.setScoring(result.getInt("Scoring"));
	   recensione.setDescrizione(result.getString("descrizione"));

	   bean.ProdottoBean prodotto = new bean.ProdottoBean();
	   prodotto.setIdProdotto(result.getInt("fk_Prodotto_idProdotto"));
	   prodotto.setNome(result.getString("nomeProdotto"));
	   recensione.setProdotto(prodotto);

	   bean.UtenteBean utente = new bean.UtenteBean();
	   utente.setNome(result.getString("nomeUtente"));
	   utente.setCognome(result.getString("cognomeUtente"));
	   recensione.setUtente(utente);
	   return recensione;
   }
   
}