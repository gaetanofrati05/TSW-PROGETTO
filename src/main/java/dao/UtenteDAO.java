package dao;
import bean.UtenteBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class UtenteDAO {

	//Metodo per inserire un nuovo utente nel database
	public void doSave(UtenteBean utente)throws SQLException {
		Connection connection=null;
	    PreparedStatement preparedStatement=null;
	    String insertQuery= "INSERT INTO Utente (email, password_hash, nome,cognome, dataNascita, nazionalita,"
	    		+ "prefisso,cellulare, num_ordinazioni, isAdmin) VALUES(?,?,?,?,?,?,?,?,?,?)";
	    try {
	    	connection= ConnectionPool.getConnection(); //prendo la connessione dal pool
	    	preparedStatement= connection.prepareStatement(insertQuery);
	    	preparedStatement.setString(1, utente.getEmail());
	    	preparedStatement.setString(2, utente.getHashPassword());
	    	preparedStatement.setString(3, utente.getNome());
	    	preparedStatement.setString(4, utente.getCognome());
	    	if (utente.getData() != null) {
	            preparedStatement.setDate(5, new java.sql.Date(utente.getData().getTime()));
	        } else {
	            preparedStatement.setNull(5, java.sql.Types.DATE); // Gestisce il caso in cui la data sia vuota
	        }
	    	preparedStatement.setString(6, utente.getNazionalita());
	    	preparedStatement.setString(7, utente.getPrefisso());
	    	preparedStatement.setString(8, utente.getCellulare());
	    	preparedStatement.setInt(9, utente.getNumeroOrdinazioni());
	    	preparedStatement.setBoolean(10,utente.getAdmin());
	    	preparedStatement.executeUpdate();
	    	
	    }finally {
	    	if(preparedStatement!=null) {
	    		preparedStatement.close();
	    	}
	    	ConnectionPool.releaseConnection(connection);
	    }
	}
	//Metodo per il login per cercare un utente se già è registrato o meno nel database
	public  UtenteBean doRetrieveByEmailAndPassword(String email, String password)throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet result=null;
		UtenteBean utente=null;
		String trovaUtenteSQL= "SELECT * FROM Utente WHERE email= ? AND password_hash= ?";
		try {
			connection=ConnectionPool.getConnection();
			preparedStatement= connection.prepareStatement(trovaUtenteSQL);
			preparedStatement.setString(1,email);
			preparedStatement.setString(2, password);
			result= preparedStatement.executeQuery();
			if(result.next()) {
				utente= new UtenteBean();
				utente.setEmail(result.getString("email"));
				utente.setHashPassword(result.getString("password_hash"));
				utente.setNome(result.getString("nome"));
				utente.setCognome(result.getString("cognome"));
				utente.setData(result.getDate("dataNascita"));
				utente.setNazionalita(result.getString("nazionalita"));
				utente.setPrefisso(result.getString("prefisso"));
				utente.setCellulare(result.getString("cellulare"));
				utente.setNumeroOrdinazioni(result.getInt("num_ordinazioni"));
				utente.setAdmin(result.getBoolean("isAdmin"));
			}
		}finally {
			if(result!=null) {
				result.close();
			}
			if(preparedStatement!=null) {
				preparedStatement.close();
			}
			ConnectionPool.releaseConnection(connection);
		}
		return utente;
	}
	//Metodo che permette all'utente di modificare le proprie informazioni
	public void doUpdate(UtenteBean utente)throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		String updateQuery= "UPDATE Utente SET  nome=?, cognome=?, dataNascita=?, nazionalita=?, prefisso=?, cellulare=? WHERE email=?";
		try {
			connection= ConnectionPool.getConnection();
			preparedStatement= connection.prepareStatement(updateQuery);
	    	preparedStatement.setString(1, utente.getNome());
	    	preparedStatement.setString(2, utente.getCognome());
	    	if (utente.getData() != null) {
	            preparedStatement.setDate(3, new java.sql.Date(utente.getData().getTime()));
	        } else {
	            preparedStatement.setNull(3, java.sql.Types.DATE); // Gestisce il caso in cui la data sia vuota
	        }
	    	preparedStatement.setString(4, utente.getNazionalita());
	    	preparedStatement.setString(5, utente.getPrefisso());
	    	preparedStatement.setString(6, utente.getCellulare());
	    	preparedStatement.setString(7, utente.getEmail());
	    	preparedStatement.executeUpdate();
	        }
		finally 
		{
		if(preparedStatement!=null) {
	    		preparedStatement.close();
	    	}
	    ConnectionPool.releaseConnection(connection);
		}
	}
	//Metodo specifico per il cambio della password
	public void doUpdatePassword(String email, String nuovaPassword)throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		String updatePassword= "UPDATE Utente SET password_hash=? WHERE email=?";
		try {
		connection= ConnectionPool.getConnection();
		preparedStatement= connection.prepareStatement(updatePassword);
		preparedStatement.setString(1, nuovaPassword);
		preparedStatement.setString(2,email);
		preparedStatement.executeUpdate();
		}finally{
			if(preparedStatement!=null) {
	    		preparedStatement.close();
	    	}
	    ConnectionPool.releaseConnection(connection);
		}
	}
	public void doDeleteUtente(UtenteBean utente)throws SQLException {
		Connection connection=null;
		PreparedStatement  psUtente=null;
		String deleUtente= "DELETE FROM Utente WHERE email=?";
		try {
			connection= ConnectionPool.getConnection();
			psUtente= connection.prepareStatement(deleUtente);
			psUtente.setString(1, utente.getEmail());
			psUtente.executeUpdate();
		}finally {
			if(psUtente!=null) {
				psUtente.close();
			}
			ConnectionPool.releaseConnection(connection);
		}
	}
	public UtenteBean doRetrieveByEmail(String email)throws SQLException {
		Connection connection=null;
		PreparedStatement psUtente=null;
		ResultSet result=null;
		String searchUtente= "SELECT * FROM Utente WHERE email=?";
		try {
			connection= ConnectionPool.getConnection();
			psUtente= connection.prepareStatement(searchUtente);
			psUtente.setString(1,email);
			result= psUtente.executeQuery();
			while(result.next()) {
				UtenteBean utente= new UtenteBean();
				utente.setEmail(result.getString("email"));
				utente.setHashPassword(result.getString("password_hash"));
				utente.setNome(result.getString("nome"));
				utente.setCognome(result.getString("cognome"));
				utente.setData(result.getDate("dataNascita"));
				utente.setNazionalita(result.getString("nazionalita"));
				utente.setPrefisso(result.getString("prefisso"));
				utente.setCellulare(result.getString("cellulare"));
				return utente;
			}
			return null;
		}finally {
			if (result != null) result.close();
	        if (psUtente != null) psUtente.close();
	        ConnectionPool.releaseConnection(connection);
		}
	}
}
