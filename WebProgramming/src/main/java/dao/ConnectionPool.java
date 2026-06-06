package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ConnectionPool {
   private static List<Connection> freeDbConnections;
   private static final String url= "jdbc:mysql://localhost:3306/TheRoyalRest?serverTimezone=Europe/Rome"; 
   private static final String user= "root";
   private static final String password= "root";
   private static int initial_pool_size=20;
   private static int max_pool_size=100;
   private static int current_pool_size=0;
   private static boolean initialized=false;
     public static synchronized void init()throws SQLException{
    	 if(initialized) {
    		 return;
    	 }
    	 freeDbConnections= new LinkedList<Connection>();
    	 try {
    		 Class.forName("com.mysql.cj.jdbc.Driver");

    	 }catch(ClassNotFoundException e) {
    		 throw new SQLException ("Class non trovata nel progetto");
    		 
    	 }
    	 for(int i=0; i<initial_pool_size;i++) {
    		 Connection conn=DriverManager.getConnection(url,user,password);
    		 freeDbConnections.add(conn);
    		 current_pool_size++;
    	 }
    	 initialized=true;
    	 System.out.println("Connessione avvenuta con successo!"); 	 
     }
     public static synchronized Connection getConnection()throws SQLException{
    	 if (!initialized || freeDbConnections == null) {
             init();
         }

         // Se ci sono connessioni disponibili nella lista, prendiamo la prima
         if (!freeDbConnections.isEmpty()) {
             Connection conn = freeDbConnections.remove(0);

             try {
                 if (!conn.isValid(1)) {
                     try {
                         conn.close();
                     } catch (SQLException ignored) {}
                     current_pool_size--;
                     return getConnection(); // Riprova ricorsivamente a prenderne una valida
                 }
             } catch (SQLException e) {
                 current_pool_size--;
                 return getConnection();
             }

             return conn;
         } 
         
         //  Se la lista è vuota creiamo una nuova connessione (se non abbiamo superato il max)
         if (current_pool_size < max_pool_size) {
             Connection conn = DriverManager.getConnection(url, user, password);
             current_pool_size++;
             return conn;
         } else {
             throw new SQLException("Connessioni massime raggiunte!");
         }
     }
   
    	 
     public static synchronized void releaseConnection(Connection conn) {
    	 if (conn != null && initialized) {
             try {
                 if (!conn.isClosed() && conn.isValid(1)) {
                     freeDbConnections.add(conn);
                 } else {
                     conn.close();
                     current_pool_size--;
                 }
             } catch (SQLException ignored) {
             }
        
         } else if (conn != null) {
             try {
                 conn.close();
             } catch (SQLException ignored) {
            }
             current_pool_size--;
         }
     }
}
