package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import bean.OrdinazioneBean;
import bean.UtenteBean;
public class OrdinazioneDAO {
	
	public void doCreateOrdinazione(OrdinazioneBean ordinazione)throws SQLException {
		Connection connection=null;
		PreparedStatement psOrdinazione=null;
		PreparedStatement psComposizione=null;
		String insertOrdinazione= "INSERT INTO Ordinazione (citta,dataOrdinazione, importo,indirizzo, cap, statoOrdinazione, fk_utente_email )+"
				+ "VALUES(?,?,?,?,?,?,?)";
		String insertComposizione= "INSERT INTO Composizione (fk_Composizione_idOrdinazione, fk_Composizione_email) VALUES(?,?)";
		
		try {
			connection= ConnectionPool.getConnection();
			connection.setAutoCommit(false); //transazione o tutto o niente
			psOrdinazione= 	connection.prepareStatement(insertOrdinazione, Statement.RETURN_GENERATED_KEYS);
			
			psOrdinazione.setString(1, ordinazione.getCitta());
			if(ordinazione.getDataOrdinazione()!=null) {
				psOrdinazione.setDate(2, new java.sql.Date(ordinazione.getDataOrdinazione().getTime()));
			}else {
				psOrdinazione.setNull(2, java.sql.Types.DATE);
			}
			psOrdinazione.setFloat(3, ordinazione.getImporto());
			psOrdinazione.setString(4, ordinazione.getIndirizzo());
			psOrdinazione.setString(5, ordinazione.getCap());
			psOrdinazione.setString(6,  ordinazione.getStato());
			psOrdinazione.setString(7, ordinazione.getUtente().getEmail());
			psOrdinazione.executeUpdate();
			
			int idGeneratoOrdinazione;
			ResultSet result=psOrdinazione.getGeneratedKeys();
			if(result.next()) {
				idGeneratoOrdinazione= result.getInt(1);
			}else {
				throw new SQLException ("Ordinazione non generata correttamente");
				
			}
			psComposizione= connection.prepareStatement(insertComposizione);
			psComposizione.setInt(1, idGeneratoOrdinazione);
			psComposizione.setString(2, ordinazione.getUtente().getEmail());
			connection.commit(); //tutto ok salva l'ordinazione
			
			
		}catch(SQLException e) {
			if (connection != null) connection.rollback(); // errore, annulla tutto
	        throw e;
		}finally {
			if (psOrdinazione != null) psOrdinazione.close();
	        if (psComposizione != null) psComposizione.close();
	        if(connection!=null) {
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
	    String queryStampaOrdinazioni= "SELECT * FROM Ordinazione WHERE email=?";
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
	         
	            utente.setEmail(result.getString("email")); 
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
	
	}

