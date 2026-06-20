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
import java.sql.Date;

public class OrdineAdminDAO{

    //Metodo per ottenere la lista di tutte le ordinazioni
    public List<OrdinazioneBean> doRetrieveAll() throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<OrdinazioneBean> ordinazioni = new ArrayList<>();
        String selectQuery = "SELECT * FROM Ordinazione JOIN Utente ON Ordinazione.fk_utente_email = Utente.email";
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectQuery);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                OrdinazioneBean ordinazione = new OrdinazioneBean();
                ordinazione.setIdOrdinazione(resultSet.getInt("idOrdinazione"));
                ordinazione.setCitta(resultSet.getString("citta"));
                ordinazione.setDataOrdinazione(resultSet.getDate("dataOrdinazione"));
                ordinazione.setImporto(resultSet.getFloat("importo"));
                ordinazione.setIndirizzo(resultSet.getString("indirizzo"));
                ordinazione.setCivico(resultSet.getString("civico"));
                ordinazione.setCap(resultSet.getString("cap"));
                ordinazione.setStato(resultSet.getString("statoOrdinazione"));
                ordinazione.setUtente(new UtenteBean());
                ordinazione.getUtente().setEmail(resultSet.getString("email"));
                ordinazione.getUtente().setNome(resultSet.getString("nome"));
                ordinazione.getUtente().setCognome(resultSet.getString("cognome"));
                ordinazione.getUtente().setData(resultSet.getDate("dataNascita"));
                ordinazione.getUtente().setNazionalita(resultSet.getString("nazionalita"));
                ordinazione.getUtente().setPrefisso(resultSet.getString("prefisso"));
                ordinazione.getUtente().setCellulare(resultSet.getString("cellulare"));
                ordinazione.getUtente().setNumeroOrdinazioni(resultSet.getInt("num_ordinazioni"));
                ordinazioni.add(ordinazione);
            }
            return ordinazioni;
        }
        finally {
            if(resultSet != null) {
                resultSet.close();
            }
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            ConnectionPool.releaseConnection(connection);
        }
    }
    //Metodo per ottenere un per idOrdinazione
    public OrdinazioneBean doRetrieveByKey(int idOrdinazione) throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        OrdinazioneBean ordinazione = null;
        String selectQuery = "SELECT * FROM Ordinazione JOIN Utente ON Ordinazione.fk_utente_email = Utente.email WHERE idOrdinazione = ?";
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, idOrdinazione);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                ordinazione = new OrdinazioneBean();
                ordinazione.setIdOrdinazione(resultSet.getInt("idOrdinazione"));
                ordinazione.setCitta(resultSet.getString("citta"));
                ordinazione.setDataOrdinazione(resultSet.getDate("dataOrdinazione"));
                ordinazione.setImporto(resultSet.getFloat("importo"));
                ordinazione.setIndirizzo(resultSet.getString("indirizzo"));
                ordinazione.setCivico(resultSet.getString("civico"));
                ordinazione.setCap(resultSet.getString("cap"));
                ordinazione.setStato(resultSet.getString("statoOrdinazione"));
                ordinazione.setUtente(new UtenteBean());
                ordinazione.getUtente().setEmail(resultSet.getString("email"));
                ordinazione.getUtente().setNome(resultSet.getString("nome"));
                ordinazione.getUtente().setCognome(resultSet.getString("cognome"));
                ordinazione.getUtente().setData(resultSet.getDate("dataNascita"));
                ordinazione.getUtente().setNazionalita(resultSet.getString("nazionalita"));
                ordinazione.getUtente().setPrefisso(resultSet.getString("prefisso"));
                ordinazione.getUtente().setCellulare(resultSet.getString("cellulare"));
                ordinazione.getUtente().setNumeroOrdinazioni(resultSet.getInt("num_ordinazioni"));
                return ordinazione;
            }
            return null;
        }
        finally {
            if(resultSet != null) {
                resultSet.close();
            }
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            ConnectionPool.releaseConnection(connection);
        }
    }

    //Metodo per ottenere un per email dell'utente
    public List<OrdinazioneBean> doRetrieveByUtente(String email) throws SQLException{
            Connection connection = null;      
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            List<OrdinazioneBean> ordinazioni = new ArrayList<>();

            String selectQuery = "SELECT * FROM Ordinazione JOIN Utente ON Ordinazione.fk_utente_email = Utente.email WHERE email = ?";
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                OrdinazioneBean ordinazione = new OrdinazioneBean();
                ordinazione.setIdOrdinazione(resultSet.getInt("idOrdinazione"));
                ordinazione.setCitta(resultSet.getString("citta"));
                ordinazione.setDataOrdinazione(resultSet.getDate("dataOrdinazione"));
                ordinazione.setImporto(resultSet.getFloat("importo"));
                ordinazione.setIndirizzo(resultSet.getString("indirizzo"));
                ordinazione.setCivico(resultSet.getString("civico"));
                ordinazione.setCap(resultSet.getString("cap"));
                ordinazione.setStato(resultSet.getString("statoOrdinazione"));
                ordinazione.setUtente(new UtenteBean());
                ordinazione.getUtente().setEmail(resultSet.getString("email"));
                ordinazione.getUtente().setNome(resultSet.getString("nome"));
                ordinazione.getUtente().setCognome(resultSet.getString("cognome"));
                ordinazione.getUtente().setData(resultSet.getDate("dataNascita"));
                ordinazione.getUtente().setNazionalita(resultSet.getString("nazionalita"));
                ordinazione.getUtente().setPrefisso(resultSet.getString("prefisso"));
                ordinazione.getUtente().setCellulare(resultSet.getString("cellulare"));
                ordinazione.getUtente().setNumeroOrdinazioni(resultSet.getInt("num_ordinazioni"));
                ordinazioni.add(ordinazione);
            }
            return ordinazioni;
        }
        finally {
            if(resultSet != null) {
                resultSet.close();
            }
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            ConnectionPool.releaseConnection(connection);
        }
    }

    //Metodo per ottenere un per date di inizio e fine
    public List<OrdinazioneBean> doRetriveByDates(Date startDate, Date endDate) throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<OrdinazioneBean> ordinazioni = new ArrayList<>();
        String selectQuery = "SELECT * FROM Ordinazione JOIN Utente ON Ordinazione.fk_utente_email = Utente.email WHERE dataOrdinazione BETWEEN ? AND ?";
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setDate(1, startDate);
            preparedStatement.setDate(2, endDate);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                OrdinazioneBean ordinazione = new OrdinazioneBean();
                ordinazione.setIdOrdinazione(resultSet.getInt("idOrdinazione"));
                ordinazione.setCitta(resultSet.getString("citta"));
                ordinazione.setDataOrdinazione(resultSet.getDate("dataOrdinazione"));
                ordinazione.setImporto(resultSet.getFloat("importo"));
                ordinazione.setIndirizzo(resultSet.getString("indirizzo"));
                ordinazione.setCivico(resultSet.getString("civico"));
                ordinazione.setCap(resultSet.getString("cap"));
                ordinazione.setStato(resultSet.getString("statoOrdinazione"));
                ordinazione.setUtente(new UtenteBean());
                ordinazione.getUtente().setEmail(resultSet.getString("email"));
                ordinazione.getUtente().setNome(resultSet.getString("nome"));
                ordinazione.getUtente().setCognome(resultSet.getString("cognome"));
                ordinazione.getUtente().setData(resultSet.getDate("dataNascita"));
                ordinazione.getUtente().setNazionalita(resultSet.getString("nazionalita"));
                ordinazione.getUtente().setPrefisso(resultSet.getString("prefisso"));
                ordinazione.getUtente().setCellulare(resultSet.getString("cellulare"));
                ordinazione.getUtente().setNumeroOrdinazioni(resultSet.getInt("num_ordinazioni"));
                ordinazioni.add(ordinazione);
            }
            return ordinazioni;
        }
        finally {
            if(resultSet != null) {
                resultSet.close();
            }
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            ConnectionPool.releaseConnection(connection);
        }
    }

    //Metodo per ottenere un per status dell'ordine
    public List<OrdinazioneBean> doRetriveByStatus(String status) throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<OrdinazioneBean> ordinazioni = new ArrayList<>();
        String selectQuery = "SELECT * FROM Ordinazione JOIN Utente ON Ordinazione.fk_utente_email = Utente.email WHERE statoOrdinazione = ?";
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, status);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                OrdinazioneBean ordinazione = new OrdinazioneBean();
                ordinazione.setIdOrdinazione(resultSet.getInt("idOrdinazione"));
                ordinazione.setCitta(resultSet.getString("citta"));
                ordinazione.setDataOrdinazione(resultSet.getDate("dataOrdinazione"));
                ordinazione.setImporto(resultSet.getFloat("importo"));
                ordinazione.setIndirizzo(resultSet.getString("indirizzo"));
                ordinazione.setCivico(resultSet.getString("civico"));
                ordinazione.setCap(resultSet.getString("cap"));
                ordinazione.setStato(resultSet.getString("statoOrdinazione"));
                ordinazione.setUtente(new UtenteBean());
                ordinazione.getUtente().setEmail(resultSet.getString("email"));
                ordinazione.getUtente().setNome(resultSet.getString("nome"));
                ordinazione.getUtente().setCognome(resultSet.getString("cognome"));
                ordinazione.getUtente().setData(resultSet.getDate("dataNascita"));
                ordinazione.getUtente().setNazionalita(resultSet.getString("nazionalita"));
                ordinazione.getUtente().setPrefisso(resultSet.getString("prefisso"));
                ordinazione.getUtente().setCellulare(resultSet.getString("cellulare"));
                ordinazione.getUtente().setNumeroOrdinazioni(resultSet.getInt("num_ordinazioni"));
                ordinazioni.add(ordinazione);
            }
            return ordinazioni;
        }
        finally {
            if(resultSet != null) {
                resultSet.close();
            }
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            ConnectionPool.releaseConnection(connection);
        }
    }

    //Metodo per aggiornare un ordine
    public OrdinazioneBean doUpdateOrdinazione(OrdinazioneBean ordinazione) throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String updateQuery = "UPDATE Ordinazione SET citta = ?, dataOrdinazione = ?, importo = ?, indirizzo = ?, civico = ?, cap = ?, statoOrdinazione = ?, fk_utente_email = ? WHERE idOrdinazione = ?";
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, ordinazione.getCitta());
            if(ordinazione.getDataOrdinazione() != null) {
                preparedStatement.setDate(2, new java.sql.Date(ordinazione.getDataOrdinazione().getTime()));
            } else {
                preparedStatement.setNull(2, java.sql.Types.DATE);
            }
            preparedStatement.setFloat(3, ordinazione.getImporto());
            preparedStatement.setString(4, ordinazione.getIndirizzo());
            preparedStatement.setString(5, ordinazione.getCivico());
            preparedStatement.setString(6, ordinazione.getCap());
            preparedStatement.setString(7, ordinazione.getStato());
            preparedStatement.setString(8, ordinazione.getUtente().getEmail());
            preparedStatement.setInt(9, ordinazione.getIdOrdinazione());
            preparedStatement.executeUpdate();
            return ordinazione;
        }
        finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            ConnectionPool.releaseConnection(connection);
        }
    }

    //Metodo per eliminare un ordine
    public void doDeleteOrdinazione(int idOrdinazione) throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String deleteQuery = "DELETE FROM Ordinazione WHERE idOrdinazione = ?";
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, idOrdinazione);
            preparedStatement.executeUpdate();
        }
    finally {
        if(preparedStatement != null) {
            preparedStatement.close();
        }
        ConnectionPool.releaseConnection(connection);
    }
    }
<<<<<<< HEAD:src/main/java/dao/OrdineAdminDAO.java
}
=======
}
>>>>>>> origin/massimo:WebProgramming/src/main/java/dao/OrdineAdminDAO.java
