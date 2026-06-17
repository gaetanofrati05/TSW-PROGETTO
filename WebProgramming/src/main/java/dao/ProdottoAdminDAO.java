package dao;
import bean.ProdottoBean;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdottoAdminDAO{
	
	//metodo per ottenere la lista di prodotti del catalogo
	public List<ProdottoBean> doRetriveAll() throws SQLException{
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs= null;
		List<ProdottoBean> prodotti = new ArrayList<>();
		String selectQuery="SELECT * FROM Prodotto";
		
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
	
	//Metodo per "eliminare"  un prodotto
	void doDelete(int idProdotto) throws SQLException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		//non si cancellerà propriamente il prodotto ma verrà impostata a 0 la sua quantità
		String deleteQuery = "UPDATE Prodotto SET quantita = 0 WHERE idProdotto = ?";
		
		try {
			
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(deleteQuery);
			
			preparedStatement.setInt(1, idProdotto);
			preparedStatement.executeUpdate();
			
		}finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			ConnectionPool.releaseConnection(connection);
		}
	}
	
}