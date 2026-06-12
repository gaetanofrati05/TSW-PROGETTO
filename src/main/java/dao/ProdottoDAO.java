package dao;

import bean.ProdottoBean;
import java.sql.*;
import java.util.*;

public class ProdottoDAO {

    public synchronized List<ProdottoBean> doRetrieveAll() throws SQLException {
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

    public synchronized ProdottoBean doRetrieveById(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ProdottoBean p = null;
        String query = "SELECT * FROM Prodotto WHERE idProdotto = ?";

        try {
            connection = ConnectionPool.getConnection();
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                p = new ProdottoBean();
                p.setIdProdotto(rs.getInt("idProdotto"));
                p.setNome(rs.getString("nome"));
                p.setStile(rs.getString("stile"));
                p.setColore(rs.getString("colore"));
                p.setDimensioni(rs.getString("dimensioni"));
                p.setPrezzo(rs.getFloat("prezzo"));
                p.setQuantita(rs.getInt("quantita"));
                p.setDescrizione(rs.getString("descrizione"));
                p.setImmagine(rs.getString("immagine"));
            }

        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConnectionPool.releaseConnection(connection);
        }

        return p;
    }
}
