package dao;
import bean.ProdottoBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdottoDAO {

    // Tutti i prodotti (per il catalogo)
    public synchronized List<ProdottoBean> doRetrieveAll() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        List<ProdottoBean> prodotti = new ArrayList<>();
        String query = "SELECT * FROM PRODOTTO";
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(query);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                ProdottoBean p = new ProdottoBean();
                p.setIdProdotto(result.getInt("idProdotto"));
                p.setNome(result.getString("nome"));
                p.setStile(result.getString("stile"));
                p.setColore(result.getString("colore"));
                p.setDimensioni(result.getString("dimensioni"));
                p.setPrezzo(result.getFloat("prezzo"));
                p.setQuantita(result.getInt("quantita"));
                p.setDescrizione(result.getString("descrizione"));
            }
        } finally {
            if (result != null) result.close();
            if (preparedStatement != null) preparedStatement.close();
            ConnectionPool.releaseConnection(connection);
        }
        return prodotti;
    }

    // Singolo prodotto (per la pagina dettaglio)
    public synchronized ProdottoBean doRetrieveById(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        ProdottoBean p = null;
        String query = "SELECT * FROM PRODOTTO WHERE idProdotto = ?";
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            result = preparedStatement.executeQuery();
            if (result.next()) {
                p = new ProdottoBean();
                p.setIdProdotto(result.getInt("idProdotto"));
                p.setNome(result.getString("nome"));
                p.setStile(result.getString("stile"));
                p.setColore(result.getString("colore"));
                p.setDimensioni(result.getString("dimensioni"));
                p.setPrezzo(result.getFloat("prezzo"));
                p.setQuantita(result.getInt("quantita"));
                p.setDescrizione(result.getString("descrizione"));
            }
        } finally {
            if (result != null) result.close();
            if (preparedStatement != null) preparedStatement.close();
            ConnectionPool.releaseConnection(connection);
        }
        return p;
    }

    // Ricerca per nome (per la barra di ricerca AJAX)
    public synchronized List<ProdottoBean> doRetrieveByNome(String nome) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        List<ProdottoBean> prodotti = new ArrayList<>();
        String query = "SELECT * FROM PRODOTTO WHERE nome LIKE ?";
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + nome + "%");
            result = preparedStatement.executeQuery();
            while (result.next()) {
                ProdottoBean p = new ProdottoBean();
                p.setIdProdotto(result.getInt("idProdotto"));
                p.setNome(result.getString("nome"));
                p.setStile(result.getString("stile"));
                p.setColore(result.getString("colore"));
                p.setDimensioni(result.getString("dimensioni"));
                p.setPrezzo(result.getFloat("prezzo"));
                p.setQuantita(result.getInt("quantita"));
                p.setDescrizione(result.getString("descrizione"));
            }
        } finally {
            if (result != null) result.close();
            if (preparedStatement != null) preparedStatement.close();
            ConnectionPool.releaseConnection(connection);
        }
        return prodotti;
    }
}