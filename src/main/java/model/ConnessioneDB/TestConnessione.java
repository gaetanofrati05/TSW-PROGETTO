package test;
import java.sql.Connection;
import java.sql.SQLException;
import dao.ConnectionPool;
public class TestConnessione {
	public static void main(String[] args) {
        System.out.println("--- INIZIO TEST CONNECTION POOL ---");
        
        Connection con = null;
        try {
            // Chiediamo una connessione al tuo Pool
            System.out.println("Richiedo una connessione al pool...");
            con = ConnectionPool.getConnection();
            
            if (con != null && !con.isClosed()) {
                System.out.println(" CONNESSO CON SUCCESSO TRAMITE IL POOL!");
                System.out.println("Info connessione: " + con.toString());
            }
            
        } catch (SQLException e) {
            System.out.println(" ERRORE CRITICO DI CONNESSIONE:");
            e.printStackTrace();
        } finally {
            // FONDAMENTALE: rilasciamo la connessione nel pool, non dobbiamo chiuderla fisicamente
            if (con != null) {
                System.out.println("Rilascio la connessione nel pool...");
                ConnectionPool.releaseConnection(con);
            }
        }
        
        System.out.println("--- FINE TEST ---");
    }
}
	
	/*
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/TheRoyalRest?serverTimezone=Europe/Rome"; 
        String user = "root";
        String password = "root"; 

        System.out.println("Tentativo di connessione in corso...");
        
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            if (conn != null) {
                System.out.println(" CONNESSO CON SUCCESSO AL DATABASE!");
            }
        } catch (Exception e) {
            System.out.println(" ERRORE DI CONNESSIONE:");
            e.printStackTrace();
        }
    }
}
*/
	

