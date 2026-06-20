package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import bean.ProdottoBean;

public class ProdottoAdminDAO{
	
	//metodo per ottenere la lista di prodotti del catalogo
	public List<ProdottoBean> doRetriveAll() throws SQLException{
		
		Connection connection = null; //connessione al database
		PreparedStatement preparedStatement = null; //istruzione SQL
		ResultSet rs= null; //risultato della query
		List<ProdottoBean> prodotti = new ArrayList<>(); //lista di prodotti
		String selectQuery="SELECT * FROM Prodotto";
		//query per selezionare tutti i prodotti dal database
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectQuery);
			rs = preparedStatement.executeQuery();
			while(rs.next()) {
				ProdottoBean prodotto = new ProdottoBean();
				prodotto.setIdProdotto(rs.getInt("idProdotto"));
				prodotto.setNome(rs.getString("nome"));
				prodotto.setStile(rs.getString("stile"));
				prodotto.setColore(rs.getString("colore"));
				prodotto.setDimensioni(rs.getString("dimensioni"));
				prodotto.setPrezzo(rs.getDouble("prezzo"));
				prodotto.setQuantita(rs.getInt("quantita"));
				prodotto.setDescrizione(rs.getString("descrizione"));
				prodotto.setImmagine(rs.getString("immagine"));
				prodotti.add(prodotto);
			}
		}finally { //chiude la connessione al database
			if(rs != null) { //chiude il risultato della query
				rs.close();
			}
			if(preparedStatement != null) { //chiude l'istruzione SQL
				preparedStatement.close();
			}
			ConnectionPool.releaseConnection(connection); //chiude la connessione al database
		}
		return prodotti; //restituisce la lista di prodotti
	}

	//Metodo per ottenere un prodotto specifico dal database
	public ProdottoBean doRetrieveByKey(int idProdotto) throws SQLException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ProdottoBean prodotto = null;
		String selectQuery = "SELECT * FROM Prodotto WHERE idProdotto = ?";
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setInt(1, idProdotto);
			rs = preparedStatement.executeQuery();
			if(rs.next()) {
				prodotto = new ProdottoBean();
				prodotto.setIdProdotto(rs.getInt("idProdotto"));
				prodotto.setNome(rs.getString("nome"));
				prodotto.setStile(rs.getString("stile"));
	
				prodotto.setColore(rs.getString("colore"));
				prodotto.setDimensioni(rs.getString("dimensioni"));
				prodotto.setPrezzo(rs.getDouble("prezzo"));
				prodotto.setQuantita(rs.getInt("quantita"));
				prodotto.setDescrizione(rs.getString("descrizione"));
				prodotto.setImmagine(rs.getString("immagine"));
			}
		}finally {
			if(rs != null) {
				rs.close();
			}
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			ConnectionPool.releaseConnection(connection);
		}
		return prodotto;
	}

	//Metodo per otternere un prodotto in base al nome

	public List<ProdottoBean> doRetriveByName(String nome) throws SQLException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<ProdottoBean> prodotti = new ArrayList<>();
		String selectQuery = "SELECT * FROM Prodotto WHERE nome LIKE ?";
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setString(1, "%" + nome + "%");
			rs = preparedStatement.executeQuery();
			while(rs.next()) {
				ProdottoBean prodotto = new ProdottoBean();
				prodotto.setIdProdotto(rs.getInt("idProdotto"));
				prodotto.setNome(rs.getString("nome"));
				prodotto.setStile(rs.getString("stile"));
				prodotto.setColore(rs.getString("colore"));
				prodotto.setDimensioni(rs.getString("dimensioni"));
				prodotto.setPrezzo(rs.getDouble("prezzo"));
				prodotto.setQuantita(rs.getInt("quantita"));
				prodotto.setDescrizione(rs.getString("descrizione"));
				prodotto.setImmagine(rs.getString("immagine"));
				prodotti.add(prodotto);
			}
		}finally {
			if(rs != null) {
				rs.close();
			}
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			ConnectionPool.releaseConnection(connection);
		}
		return prodotti;
	}

		//Metodo per ottenere un prodotto in base allo stile
	public List<ProdottoBean> doRetriveByStyle(String stile) throws SQLException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<ProdottoBean> prodotti = new ArrayList<>();
		String selectQuery = "SELECT * FROM Prodotto WHERE stile LIKE ?";
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setString(1, "%" + stile + "%");
			rs = preparedStatement.executeQuery();
			while(rs.next()) {
				ProdottoBean prodotto = new ProdottoBean();
				prodotto.setIdProdotto(rs.getInt("idProdotto"));
				prodotto.setNome(rs.getString("nome"));
				prodotto.setStile(rs.getString("stile"));
				prodotto.setColore(rs.getString("colore"));
				prodotto.setDimensioni(rs.getString("dimensioni"));
				prodotto.setPrezzo(rs.getDouble("prezzo"));
				prodotto.setQuantita(rs.getInt("quantita"));
				prodotto.setDescrizione(rs.getString("descrizione"));
				prodotto.setImmagine(rs.getString("immagine"));
				prodotti.add(prodotto);
			}
		}finally {
			if(rs != null) {
				rs.close();
			}
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			ConnectionPool.releaseConnection(connection);
		}
		return prodotti;
	}

	//Metodo per ottenere un prodotto in base al colore
	public List<ProdottoBean> doRetriveByColor(String colore) throws SQLException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<ProdottoBean> prodotti = new ArrayList<>();
		String selectQuery = "SELECT * FROM Prodotto WHERE colore LIKE ?";
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setString(1, "%" + colore + "%");
			rs = preparedStatement.executeQuery();
			while(rs.next()) {
				ProdottoBean prodotto = new ProdottoBean();
				prodotto.setIdProdotto(rs.getInt("idProdotto"));
				prodotto.setNome(rs.getString("nome"));
				prodotto.setStile(rs.getString("stile"));
				prodotto.setColore(rs.getString("colore"));
				prodotto.setDimensioni(rs.getString("dimensioni"));
				prodotto.setPrezzo(rs.getDouble("prezzo"));
				prodotto.setQuantita(rs.getInt("quantita"));
				prodotto.setDescrizione(rs.getString("descrizione"));
				prodotto.setImmagine(rs.getString("immagine"));
				prodotti.add(prodotto);
			}
		}finally {
			if(rs != null) {
				rs.close();
			}
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			ConnectionPool.releaseConnection(connection);
		}
		return prodotti;
	}

	public List<ProdottoBean> doRetriveBySize(String dimensioni) throws SQLException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<ProdottoBean> prodotti = new ArrayList<>();
		String selectQuery = "SELECT * FROM Prodotto WHERE dimensioni = ?";
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setString(1, dimensioni);
			rs = preparedStatement.executeQuery();
			while(rs.next()) {
				ProdottoBean prodotto = new ProdottoBean();
				prodotto.setIdProdotto(rs.getInt("idProdotto"));
				prodotto.setNome(rs.getString("nome"));
				prodotto.setStile(rs.getString("stile"));
				prodotto.setColore(rs.getString("colore"));
				prodotto.setDimensioni(rs.getString("dimensioni"));
				prodotto.setPrezzo(rs.getDouble("prezzo"));
				prodotto.setQuantita(rs.getInt("quantita"));
				prodotto.setDescrizione(rs.getString("descrizione"));
				prodotto.setImmagine(rs.getString("immagine"));
				prodotti.add(prodotto);
			}
		}finally {
			if(rs != null) {
				rs.close();
			}
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			ConnectionPool.releaseConnection(connection);
		}
		return prodotti;
	}

	//Metodo per ottenere un prodotto in base al prezzo
	public List<ProdottoBean> doRetriveByPrice(double prezzo) throws SQLException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<ProdottoBean> prodotti = new ArrayList<>();
		String selectQuery = "SELECT * FROM Prodotto WHERE prezzo >= ?";
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setDouble(1, prezzo);
			rs = preparedStatement.executeQuery();
			while(rs.next()) {
				ProdottoBean prodotto = new ProdottoBean();
				prodotto.setIdProdotto(rs.getInt("idProdotto"));
				prodotto.setNome(rs.getString("nome"));
				prodotto.setStile(rs.getString("stile"));
				prodotto.setColore(rs.getString("colore"));
				prodotto.setDimensioni(rs.getString("dimensioni"));
				prodotto.setPrezzo(rs.getDouble("prezzo"));
				prodotto.setQuantita(rs.getInt("quantita"));
				prodotto.setDescrizione(rs.getString("descrizione"));
				prodotto.setImmagine(rs.getString("immagine"));
				prodotti.add(prodotto);
			}
		}finally {
			if(rs != null) {
				rs.close();
			}
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			ConnectionPool.releaseConnection(connection);
		}
		return prodotti;
	}

	//Metodo per salvare un nuovo prodotto inserito dall'Admin
	public void doSave(ProdottoBean prodotto) throws SQLException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String  insertQuery = "INSERT INTO Prodotto (nome, stile, colore, dimensioni, prezzo, quantita, descrizione, immagine)"
								+ "VALUES (?,?,?,?,?,?,?,?)";
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(insertQuery);
			
			preparedStatement.setString(1, prodotto.getNome());
			preparedStatement.setString(2, prodotto.getStile());
			preparedStatement.setString(3, prodotto.getColore());
			preparedStatement.setString(4, prodotto.getDimensioni());
			preparedStatement.setDouble(5, prodotto.getPrezzo());
			preparedStatement.setInt(6, prodotto.getQuantita());
			preparedStatement.setString(7, prodotto.getDescrizione());
			preparedStatement.setString(8, prodotto.getImmagine());
			
			preparedStatement.executeUpdate();
		}finally {
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			ConnectionPool.releaseConnection(connection);
		}
	}
	
	//Metodo per modificare un prodotto già esistente
	public void doUpdate(ProdottoBean prodotto) throws SQLException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateQuery = "UPDATE Prodotto SET nome=?, stile=?, colore=?, dimensioni=?, prezzo=?, quantita=?, descrizione=?, immagine=? WHERE idProdotto=?";

		try{
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(updateQuery);

			preparedStatement.setString(1, prodotto.getNome());
			preparedStatement.setString(2, prodotto.getStile());
			preparedStatement.setString(3, prodotto.getColore());
			preparedStatement.setString(4, prodotto.getDimensioni());
			preparedStatement.setDouble(5, prodotto.getPrezzo());
			preparedStatement.setInt(6, prodotto.getQuantita());
			preparedStatement.setString(7, prodotto.getDescrizione());
			preparedStatement.setString(8, prodotto.getImmagine());
			preparedStatement.setInt(9, prodotto.getIdProdotto());
			preparedStatement.executeUpdate();
		}finally {
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			ConnectionPool.releaseConnection(connection);
		}
	}
	//Metodo per eliminare un prodotto dal database
	public void doDelete(int idProdotto) throws SQLException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String deleteQuery = "DELETE FROM Prodotto WHERE idProdotto = ?";

		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(deleteQuery);
			preparedStatement.setInt(1, idProdotto);
			preparedStatement.executeUpdate();
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			ConnectionPool.releaseConnection(connection);
		}
	}
	
}