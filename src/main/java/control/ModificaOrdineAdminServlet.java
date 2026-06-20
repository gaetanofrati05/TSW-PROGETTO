// Servlet per la modifica di un ordine lato admin
package control;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bean.OrdinazioneBean;
import dao.OrdineAdminDAO;

@WebServlet("/ModificaOrdineAdminServlet")
public class ModificaOrdineAdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // metodo per fare l'escape dei caratteri speciali nelle stringhe JSON
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

    // GET: carica i dati dell'ordine specifico e li restituisce come JSON
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            int idOrdinazione = Integer.parseInt(request.getParameter("idOrdinazione"));

            // recupero l'ordine specifico dal DAO
            OrdineAdminDAO ordineAdminDAO = new OrdineAdminDAO();
            OrdinazioneBean ordine = ordineAdminDAO.doRetrieveByKey(idOrdinazione);

            if (ordine != null) {
                String dataPulita = (ordine.getDataOrdinazione() != null)
                        ? ordine.getDataOrdinazione().toString().split(" ")[0]
                        : "";

                // costruiamo la stringa JSON con i dati dell'ordine
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
                    escapeJson((ordine.getUtente() != null && ordine.getUtente().getEmail() != null)
                            ? ordine.getUtente().getEmail() : ""),
                    escapeJson(ordine.getCitta()),
                    escapeJson(ordine.getIndirizzo()),
                    escapeJson(ordine.getCivico()),
                    escapeJson(ordine.getCap()),
                    escapeJson(ordine.getStato()),
                    dataPulita,
                    String.valueOf(ordine.getImporto())
                );

                // si invia la risposta JSON
                response.getWriter().write(jsonResponse);

            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"errore\": \"Ordine non trovato\"}");
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"errore\": \"ID formato non valido\"}");
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"errore\": \"Errore database\"}");
        }
    }

    // POST: riceve le modifiche via AJAX e le salva nel DB
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // recupero i parametri inviati nel body della fetch POST
        String idStr      = request.getParameter("idOrdinazione");
        String citta      = request.getParameter("citta");
        String indirizzo  = request.getParameter("indirizzo");
        String civico     = request.getParameter("civico");
        String cap        = request.getParameter("cap");
        String stato      = request.getParameter("stato");
        String dataStr    = request.getParameter("dataOrdinazione");
        String importoStr = request.getParameter("importo");

        // validazione lato server dei campi obbligatori
        if (idStr == null || idStr.trim().isEmpty() ||
            citta == null || citta.trim().isEmpty() ||
            indirizzo == null || indirizzo.trim().isEmpty() ||
            stato == null || stato.trim().isEmpty() ||
            importoStr == null || importoStr.trim().isEmpty()) {

            response.getWriter().write("{\"successo\": false, \"messaggio\": \"Campi obbligatori mancanti.\"}");
            return;
        }

        try {
            // conversione sicura dei dati numerici
            int idOrdinazione = Integer.parseInt(idStr);
            float importo = Float.parseFloat(importoStr.trim().replace(",", "."));

            OrdineAdminDAO ordineAdminDAO = new OrdineAdminDAO();
            OrdinazioneBean ordineAggiornato = ordineAdminDAO.doRetrieveByKey(idOrdinazione);

            // controllo che l'ordine esista nel DB prima di aggiornarlo
            if (ordineAggiornato != null) {
                ordineAggiornato.setCitta(citta.trim());
                ordineAggiornato.setIndirizzo(indirizzo.trim());
                ordineAggiornato.setCivico(civico != null ? civico.trim() : "");
                ordineAggiornato.setCap(cap != null ? cap.trim() : "");
                ordineAggiornato.setStato(stato.trim());
                ordineAggiornato.setImporto(importo);

                // controllo sulla data prima del parsing
                if (dataStr != null && !dataStr.trim().isEmpty()) {
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
        } catch (IllegalArgumentException e) {
            // lanciata da java.sql.Date.valueOf() se il formato della data non è valido
            response.getWriter().write("{\"successo\": false, \"messaggio\": \"Formato data non valido.\"}");
        }
    }
}