package dao;

import bean.ProdottoBean;
import java.sql.*;
import java.util.*;

public class ProdottoDAO {

    public List<ProdottoBean> doRetrieveAll() throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<ProdottoBean> prodotti = new ArrayList<>();
        String query = "SELECT * FROM Prodotto";

        try {
            connection = ConnectionPool.getConnection();
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                ProdottoBean p = new ProdottoBean();
                p.setIdProdotto(rs.getInt("idProdotto"));
                p.setNome(rs.getString("nome"));
                p.setStile(rs.getString("stile"));
                p.setColore(rs.getString("colore"));
                p.setDimensioni(rs.getString("dimensioni"));
                p.setPrezzo(rs.getFloat("prezzo"));
                p.setQuantita(rs.getInt("quantita"));
                p.setDescrizione(rs.getString("descrizione"));
                p.setImmagine(rs.getString("immagine"));
                prodotti.add(p);
            }

        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConnectionPool.releaseConnection(connection);
        }

        return prodotti;
    }

    public List<ProdottoBean> doRetrieveByName(String nome) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<ProdottoBean> prodotti = new ArrayList<>();
        String query = "SELECT * FROM Prodotto WHERE LOWER(nome) LIKE LOWER(?)";

        try {
            connection = ConnectionPool.getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, "%" + nome + "%");/* 1 indica la posizione del placeholder i % indicano che il nome deve essere
            compreso nel nome del risultato*/
            rs = ps.executeQuery();

            while (rs.next()) {
                ProdottoBean p = new ProdottoBean();
                p.setIdProdotto(rs.getInt("idProdotto"));
                p.setNome(rs.getString("nome"));
                p.setStile(rs.getString("stile"));
                p.setColore(rs.getString("colore"));
                p.setDimensioni(rs.getString("dimensioni"));
                p.setPrezzo(rs.getFloat("prezzo"));
                p.setQuantita(rs.getInt("quantita"));
                p.setDescrizione(rs.getString("descrizione"));
                p.setImmagine(rs.getString("immagine"));
                prodotti.add(p);
            }

        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConnectionPool.releaseConnection(connection);
        }

        return prodotti;
    }
    
    public ProdottoBean doRetrieveById(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Prodotto WHERE idProdotto = ?";
        ProdottoBean prod= null;

        try {
            connection = ConnectionPool.getConnection();
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                prod= new ProdottoBean();
            	prod.setIdProdotto(rs.getInt("idProdotto"));
            	prod.setNome(rs.getString("nome"));
            	prod.setStile(rs.getString("stile"));
            	prod.setColore(rs.getString("colore"));
            	prod.setDimensioni(rs.getString("dimensioni"));
            	prod.setPrezzo(rs.getFloat("prezzo"));
            	prod.setQuantita(rs.getInt("quantita"));
            	prod.setDescrizione(rs.getString("descrizione"));
            	prod.setImmagine(rs.getString("immagine"));
            }

        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConnectionPool.releaseConnection(connection);
        }

        return prod;
    }

    public List<ProdottoBean> doStampaListaProdotti(String emailUtente) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<ProdottoBean> prodotti = new ArrayList<>();
        String query = "SELECT DISTINCT P.* FROM Prodotto P "
                + "INNER JOIN Recensione R ON P.idProdotto = R.fk_Prodotto_idProdotto "
                + "WHERE R.fk_utente_email = ?";

        try {
            connection = ConnectionPool.getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, emailUtente);
            rs = ps.executeQuery();

            while (rs.next()) {
                ProdottoBean p = new ProdottoBean();
                p.setIdProdotto(rs.getInt("idProdotto"));
                p.setNome(rs.getString("nome"));
                p.setStile(rs.getString("stile"));
                p.setColore(rs.getString("colore"));
                p.setDimensioni(rs.getString("dimensioni"));
                p.setPrezzo(rs.getFloat("prezzo"));
                p.setQuantita(rs.getInt("quantita"));
                p.setDescrizione(rs.getString("descrizione"));
                p.setImmagine(rs.getString("immagine"));
                prodotti.add(p);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConnectionPool.releaseConnection(connection);
        }

        return prodotti;
    }

    public boolean doDecrementQuantita(int idProdotto, int quantita, Connection connection) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(
                "UPDATE Prodotto SET quantita = quantita - ? WHERE idProdotto = ? AND quantita >= ?");
            ps.setInt(1, quantita);
            ps.setInt(2, idProdotto);
            ps.setInt(3, quantita);
            return ps.executeUpdate() > 0;
        } finally {
            if (ps != null) ps.close();
        }
    }
}
