//Servlet per la modifica di un ordine lato admin
package control;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.OrdinazioneBean;
<<<<<<< HEAD:src/main/java/control/ModificaOrdineAdminServlet.java
import dao.OrdineAdminDAO; // <-- IMPORTANTE: Aggiunto l'import del DAO
=======
import dao.OrdineAdminDAO;
>>>>>>> origin/massimo:WebProgramming/src/main/java/control/ModificaOrdineAdminServlet.java

@WebServlet("/ModificaOrdineAdminServlet")
public class ModificaOrdineAdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Helper method to escape JSON strings
    private String escapeJson(String data) {
        if (data == null) {
            return "";
        }
<<<<<<< HEAD:src/main/java/control/ModificaOrdineAdminServlet.java
        return data.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\b", "\\b")
                   .replace("\f", "\\f")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }
=======
        return data.replace("\\", "\\\\") //backslash per escape del backslash
                   .replace("\"", "\\\"") //backslash per escape del backslash
                   .replace("\b", "\\b")
                   .replace("\f", "\\f") //backslash per escape del backslash
                   .replace("\n", "\\n")
                   .replace("\r", "\\r") //backslash per escape del backslash
                   .replace("\t", "\\t"); //backslash per escape del backslash
    } //metodo per escpae le stringhe json
>>>>>>> origin/massimo:WebProgramming/src/main/java/control/ModificaOrdineAdminServlet.java

    // GET: carica i dati dell'ordine specifico
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // impostiamo il tipo di risposta come JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            int idOrdinazione = Integer.parseInt(request.getParameter("idOrdinazione"));
            
<<<<<<< HEAD:src/main/java/control/ModificaOrdineAdminServlet.java
            // CORREZIONE: Inizializzazione corretta del DAO
=======
            // recupero l'ordine specifico dal DAO
>>>>>>> origin/massimo:WebProgramming/src/main/java/control/ModificaOrdineAdminServlet.java
            OrdineAdminDAO ordineAdminDAO = new OrdineAdminDAO(); 
            OrdinazioneBean ordine = ordineAdminDAO.doRetrieveByKey(idOrdinazione);
            
            if (ordine != null) {
                String dataPulita = (ordine.getDataOrdinazione() != null) ? ordine.getDataOrdinazione().toString().split(" ")[0] : "";
                
<<<<<<< HEAD:src/main/java/control/ModificaOrdineAdminServlet.java
                // CORREZIONE: Stringa JSON ricostruita correttamente
=======
                // costruiamo la stringa JSON con i dati dell'ordine   
>>>>>>> origin/massimo:WebProgramming/src/main/java/control/ModificaOrdineAdminServlet.java
                String jsonResponse = String.format(
                    "{" +
                    "\"idOrdinazione\": %d," +
                    "\"email\": \"%s\"," +
                    "\"citta\": \"%s\"," +
                    "\"indirizzo\": \"%s\"," +
                    "\"civico\": \"%s\"," +
                    "\"cap\": \"%s\"," +
                    "\"stato\": \"%s\"," +
                    "\"dataOrdinazione\": \"%s\"," +
                    "\"importo\": %s" +
                    "}",
                    ordine.getIdOrdinazione(),
                    escapeJson((ordine.getUtente() != null && ordine.getUtente().getEmail() != null) ? ordine.getUtente().getEmail() : ""),
                    escapeJson(ordine.getCitta()),
                    escapeJson(ordine.getIndirizzo()),
                    escapeJson(ordine.getCivico()),
                    escapeJson(ordine.getCap()),
                    escapeJson(ordine.getStato()),
                    dataPulita,
                    String.valueOf(ordine.getImporto())
                );
<<<<<<< HEAD:src/main/java/control/ModificaOrdineAdminServlet.java

=======
                // si invia la risposta JSON
>>>>>>> origin/massimo:WebProgramming/src/main/java/control/ModificaOrdineAdminServlet.java
                response.getWriter().write(jsonResponse);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"errore\": \"Ordine non trovato\"}");
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"errore\": \"ID Formato non valido\"}");
<<<<<<< HEAD:src/main/java/control/ModificaOrdineAdminServlet.java
        } catch (SQLException e) { // CORREZIONE: Decommentato il blocco SQL obbligatorio
=======
        } catch (SQLException e) {
>>>>>>> origin/massimo:WebProgramming/src/main/java/control/ModificaOrdineAdminServlet.java
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"errore\": \"Errore database\"}");
        } 
    }

    // 2. FASE DI SALVATAGGIO MODIFICHE (AJAX POST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        //recuperiamo i parametri inviati nel body della Fetch POST
        String idStr = request.getParameter("idOrdinazione");
        String email = request.getParameter("email");
        String citta = request.getParameter("citta");
        String indirizzo = request.getParameter("indirizzo");
        String civico = request.getParameter("civico");
        String cap = request.getParameter("cap");
        String stato = request.getParameter("stato");
        String dataStr = request.getParameter("dataOrdinazione");
        String importoStr = request.getParameter("importo");

        // VALIDAZIONE LATO SERVER delle stringhe obbligatorie
        if (idStr == null || idStr.trim().isEmpty() || 
            citta == null || citta.trim().isEmpty() || 
            indirizzo == null || indirizzo.trim().isEmpty() || 
            stato == null || stato.trim().isEmpty() || 
            importoStr == null || importoStr.trim().isEmpty()) {
            
            response.getWriter().write("{\"successo\": false, \"messaggio\": \"Campi obbligatori mancanti.\"}");
            return;
        }

<<<<<<< HEAD:src/main/java/control/ModificaOrdineAdminServlet.java
        try { //ciao
=======
        try {
>>>>>>> origin/massimo:WebProgramming/src/main/java/control/ModificaOrdineAdminServlet.java
            // conversione sicura dei dati numerici
            int idOrdinazione = Integer.parseInt(idStr);
            float importo = Float.parseFloat(importoStr.trim().replace(",", "."));

            OrdineAdminDAO ordineAdminDAO = new OrdineAdminDAO();
            OrdinazioneBean ordineAggiornato = ordineAdminDAO.doRetrieveByKey(idOrdinazione);
            
<<<<<<< HEAD:src/main/java/control/ModificaOrdineAdminServlet.java
            // CORREZIONE: Controllo che l'ordine esista davvero nel DB prima di aggiornarlo
=======
            // controllo che l'ordine esista davvero nel DB prima di aggiornarlo
>>>>>>> origin/massimo:WebProgramming/src/main/java/control/ModificaOrdineAdminServlet.java
            if(ordineAggiornato != null) {
                ordineAggiornato.setCitta(citta.trim());
                ordineAggiornato.setIndirizzo(indirizzo.trim());
                ordineAggiornato.setCivico(civico != null ? civico.trim() : "");
                ordineAggiornato.setCap(cap != null ? cap.trim() : "");
                ordineAggiornato.setStato(stato.trim());
                ordineAggiornato.setImporto(importo);
                
<<<<<<< HEAD:src/main/java/control/ModificaOrdineAdminServlet.java
                // CORREZIONE: Controllo sulla data prima del parsing
=======
                // controllo sulla data prima del parsing
>>>>>>> origin/massimo:WebProgramming/src/main/java/control/ModificaOrdineAdminServlet.java
                if(dataStr != null && !dataStr.trim().isEmpty()) {
                    ordineAggiornato.setDataOrdinazione(java.sql.Date.valueOf(dataStr.trim()));
                }
                
                ordineAdminDAO.doUpdateOrdinazione(ordineAggiornato);
                response.getWriter().write("{\"successo\": true}");
            } else {
                response.getWriter().write("{\"successo\": false, \"messaggio\": \"Ordine non trovato nel database.\"}");
            }

        } catch (NumberFormatException e) {
            response.getWriter().write("{\"successo\": false, \"messaggio\": \"Formato del prezzo o dell'ID non valido.\"}");
        } catch (SQLException e) {
            response.getWriter().write("{\"successo\": false, \"messaggio\": \"Errore durante l'aggiornamento nel DB.\"}");
<<<<<<< HEAD:src/main/java/control/ModificaOrdineAdminServlet.java
        } catch (IllegalArgumentException e) { // Aggiunto in caso di formato data non valido
=======
        } catch (IllegalArgumentException e) {
>>>>>>> origin/massimo:WebProgramming/src/main/java/control/ModificaOrdineAdminServlet.java
            response.getWriter().write("{\"successo\": false, \"messaggio\": \"Formato data non valido.\"}");
        }
    }
}