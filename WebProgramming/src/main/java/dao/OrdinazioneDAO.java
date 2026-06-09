package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import bean.OrdinazioneBean;
import bean.UtenteBean;
public class OrdinazioneDAO {
	
	public void doCreateOrdinazione(OrdinazioneBean ordinazione) {
		
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

