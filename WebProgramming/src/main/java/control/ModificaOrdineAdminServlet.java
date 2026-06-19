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
import dao.OrdineAdminDAO; // <-- IMPORTANTE: Aggiunto l'import del DAO

@WebServlet("/ModificaOrdineAdminServlet")
public class ModificaOrdineAdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Helper method to escape JSON strings
    private String escapeJson(String data) {
        if (data == null) {
            return "";
        }
        return data.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\b", "\\b")
                   .replace("\f", "\\f")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }

    // GET: carica i dati dell'ordine specifico
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // impostiamo il tipo di risposta come JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            int idOrdinazione = Integer.parseInt(request.getParameter("idOrdinazione"));
            
            // CORREZIONE: Inizializzazione corretta del DAO
            OrdineAdminDAO ordineAdminDAO = new OrdineAdminDAO(); 
            OrdinazioneBean ordine = ordineAdminDAO.doRetrieveByKey(idOrdinazione);
            
            if (ordine != null) {
                String dataPulita = (ordine.getDataOrdinazione() != null) ? ordine.getDataOrdinazione().toString().split(" ")[0] : "";
                
                // CORREZIONE: Stringa JSON ricostruita correttamente
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

                response.getWriter().write(jsonResponse);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"errore\": \"Ordine non trovato\"}");
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"errore\": \"ID Formato non valido\"}");
        } catch (SQLException e) { // CORREZIONE: Decommentato il blocco SQL obbligatorio
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

        try { //ciao
            // conversione sicura dei dati numerici
            int idOrdinazione = Integer.parseInt(idStr);
            float importo = Float.parseFloat(importoStr.trim().replace(",", "."));

            OrdineAdminDAO ordineAdminDAO = new OrdineAdminDAO();
            OrdinazioneBean ordineAggiornato = ordineAdminDAO.doRetrieveByKey(idOrdinazione);
            
            // CORREZIONE: Controllo che l'ordine esista davvero nel DB prima di aggiornarlo
            if(ordineAggiornato != null) {
                ordineAggiornato.setCitta(citta.trim());
                ordineAggiornato.setIndirizzo(indirizzo.trim());
                ordineAggiornato.setCivico(civico != null ? civico.trim() : "");
                ordineAggiornato.setCap(cap != null ? cap.trim() : "");
                ordineAggiornato.setStato(stato.trim());
                ordineAggiornato.setImporto(importo);
                
                // CORREZIONE: Controllo sulla data prima del parsing
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
        } catch (IllegalArgumentException e) { // Aggiunto in caso di formato data non valido
            response.getWriter().write("{\"successo\": false, \"messaggio\": \"Formato data non valido.\"}");
        }
    }
}