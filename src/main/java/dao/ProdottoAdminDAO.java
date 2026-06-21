package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import bean.ProdottoBean;

public class ProdottoAdminDAO {

    // metodo di supporto: popola un ProdottoBean da una riga del ResultSet
    // evita di ripetere le stesse righe in ogni metodo
    private ProdottoBean mappaProdotto(ResultSet rs) throws SQLException {
        ProdottoBean prodotto = new ProdottoBean();
        prodotto.setIdProdotto(rs.getInt("idProdotto"));
        prodotto.setNome(rs.getString("nome"));
        prodotto.setStile(rs.getString("stile"));
        prodotto.setColore(rs.getString("colore"));
        prodotto.setDimensioni(rs.getString("dimensioni"));
        prodotto.setPrezzo(rs.getDouble("prezzo")); // double, più preciso di float per i prezzi
        prodotto.setQuantita(rs.getInt("quantita"));
        prodotto.setDescrizione(rs.getString("descrizione"));
        prodotto.setImmagine(rs.getString("immagine"));
        return prodotto;
    }

    // restituisce tutti i prodotti del catalogo
    public List<ProdottoBean> doRetriveAll() throws SQLException {
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
                prodotti.add(mappaProdotto(rs));
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConnectionPool.releaseConnection(connection);
        }
        return prodotti;
    }

    // restituisce un singolo prodotto dato il suo id
    public ProdottoBean doRetrieveByKey(int idProdotto) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ProdottoBean prodotto = null;
        String query = "SELECT * FROM Prodotto WHERE idProdotto = ?";
        try {
            connection = ConnectionPool.getConnection();
            ps = connection.prepareStatement(query);
            ps.setInt(1, idProdotto);
            rs = ps.executeQuery();
            if (rs.next()) {
                prodotto = mappaProdotto(rs);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConnectionPool.releaseConnection(connection);
        }
        return prodotto;
    }

    // restituisce i prodotti il cui nome contiene la stringa cercata
    public List<ProdottoBean> doRetriveByName(String nome) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ProdottoBean> prodotti = new ArrayList<>();
        String query = "SELECT * FROM Prodotto WHERE nome LIKE ?";
        try {
            connection = ConnectionPool.getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, "%" + nome + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                prodotti.add(mappaProdotto(rs));
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConnectionPool.releaseConnection(connection);
        }
        return prodotti;
    }

    // restituisce i prodotti il cui stile contiene la stringa cercata
    public List<ProdottoBean> doRetriveByStyle(String stile) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ProdottoBean> prodotti = new ArrayList<>();
        String query = "SELECT * FROM Prodotto WHERE stile LIKE ?";
        try {
            connection = ConnectionPool.getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, "%" + stile + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                prodotti.add(mappaProdotto(rs));
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConnectionPool.releaseConnection(connection);
        }
        return prodotti;
    }

    // restituisce i prodotti il cui colore contiene la stringa cercata
    public List<ProdottoBean> doRetriveByColor(String colore) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ProdottoBean> prodotti = new ArrayList<>();
        String query = "SELECT * FROM Prodotto WHERE colore LIKE ?";
        try {
            connection = ConnectionPool.getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, "%" + colore + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                prodotti.add(mappaProdotto(rs));
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConnectionPool.releaseConnection(connection);
        }
        return prodotti;
    }

    // restituisce i prodotti con dimensioni esatte
    public List<ProdottoBean> doRetriveBySize(String dimensioni) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ProdottoBean> prodotti = new ArrayList<>();
        String query = "SELECT * FROM Prodotto WHERE dimensioni = ?";
        try {
            connection = ConnectionPool.getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, dimensioni);
            rs = ps.executeQuery();
            while (rs.next()) {
                prodotti.add(mappaProdotto(rs));
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConnectionPool.releaseConnection(connection);
        }
        return prodotti;
    }

    // restituisce i prodotti con prezzo maggiore o uguale al valore indicato
    public List<ProdottoBean> doRetriveByPrice(double prezzo) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ProdottoBean> prodotti = new ArrayList<>();
        String query = "SELECT * FROM Prodotto WHERE prezzo >= ?";
        try {
            connection = ConnectionPool.getConnection();
            ps = connection.prepareStatement(query);
            ps.setDouble(1, prezzo);
            rs = ps.executeQuery();
            while (rs.next()) {
                prodotti.add(mappaProdotto(rs));
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConnectionPool.releaseConnection(connection);
        }
        return prodotti;
    }

    // salva un nuovo prodotto nel DB
    public void doSave(ProdottoBean prodotto) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        String query = "INSERT INTO Prodotto (nome, stile, colore, dimensioni, prezzo, quantita, descrizione, immagine) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            connection = ConnectionPool.getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, prodotto.getNome());
            ps.setString(2, prodotto.getStile());
            ps.setString(3, prodotto.getColore());
            ps.setString(4, prodotto.getDimensioni());
            ps.setDouble(5, prodotto.getPrezzo());
            ps.setInt(6, prodotto.getQuantita());
            ps.setString(7, prodotto.getDescrizione());
            ps.setString(8, prodotto.getImmagine());
            ps.executeUpdate();
        } finally {
            if (ps != null) ps.close();
            ConnectionPool.releaseConnection(connection);
        }
    }

    // aggiorna un prodotto già esistente
    public void doUpdate(ProdottoBean prodotto) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        String query = "UPDATE Prodotto SET nome=?, stile=?, colore=?, dimensioni=?, prezzo=?, quantita=?, descrizione=?, immagine=? "
                     + "WHERE idProdotto=?";
        try {
            connection = ConnectionPool.getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, prodotto.getNome());
            ps.setString(2, prodotto.getStile());
            ps.setString(3, prodotto.getColore());
            ps.setString(4, prodotto.getDimensioni());
            ps.setDouble(5, prodotto.getPrezzo());
            ps.setInt(6, prodotto.getQuantita());
            ps.setString(7, prodotto.getDescrizione());
            ps.setString(8, prodotto.getImmagine());
            ps.setInt(9, prodotto.getIdProdotto());
            ps.executeUpdate();
        } finally {
            if (ps != null) ps.close();
            ConnectionPool.releaseConnection(connection);
        }
    }

    public void doDelete(int idProdotto) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = ConnectionPool.getConnection();
            try {
                ps = connection.prepareStatement("DELETE FROM Prodotto WHERE idProdotto = ?");
                ps.setInt(1, idProdotto);
                ps.executeUpdate();
            } catch (SQLException e) {
                // il prodotto è collegato a degli ordini: imposta quantita = 0
                if (ps != null) ps.close();
                ps = connection.prepareStatement("UPDATE Prodotto SET quantita = 0 WHERE idProdotto = ?");
                ps.setInt(1, idProdotto);
                ps.executeUpdate();
            }
        } finally {
            if (ps != null) ps.close();
            ConnectionPool.releaseConnection(connection);
        }
    }
}