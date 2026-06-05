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
   
}