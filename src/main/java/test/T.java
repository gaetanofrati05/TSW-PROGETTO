import java.sql.*;
import dao.ConnectionPool;
public class T {
  public static void main(String[] a) throws Exception {
    try (Connection c = ConnectionPool.getConnection();
         ResultSet rs = c.createStatement().executeQuery("SHOW COLUMNS FROM Ordinazione")) {
      while (rs.next()) System.out.println(rs.getString(1));
    }
    ConnectionPool.releaseConnection(c);
  }
}
