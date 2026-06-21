package control;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.ProdottoAdminDAO;
import bean.ProdottoBean;

@WebServlet("/ModificaProdottoServlet")
public class ModificaProdottoServlet extends HttpServlet {
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

    // GET: carica i dati del prodotto specifico e li restituisce come JSON
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));

            ProdottoAdminDAO prodottoAdminDAO = new ProdottoAdminDAO();
            ProdottoBean prodotto = prodottoAdminDAO.doRetrieveByKey(idProdotto);

            if (prodotto != null) {
                // costruiamo la stringa JSON con i dati del prodotto
                String jsonResponse = String.format(
                    "{" +
                    "\"idProdotto\": %d," +
                    "\"nome\": \"%s\"," +
                    "\"stile\": \"%s\"," +
                    "\"colore\": \"%s\"," +
                    "\"dimensioni\": \"%s\"," +
                    "\"prezzo\": %f," +
                    "\"quantita\": %d," +
                    "\"descrizione\": \"%s\"," +
                    "\"immagine\": \"%s\"" +
                    "}",
                    prodotto.getIdProdotto(),
                    escapeJson(prodotto.getNome()),
                    escapeJson(prodotto.getStile()),
                    escapeJson(prodotto.getColore()),
                    escapeJson(prodotto.getDimensioni()),
                    prodotto.getPrezzo(),
                    prodotto.getQuantita(),
                    escapeJson(prodotto.getDescrizione()),
                    escapeJson(prodotto.getImmagine())
                );
                response.getWriter().write(jsonResponse);

            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"errore\": \"Prodotto non trovato\"}");
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"errore\": \"ID prodotto non valido\"}");
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"errore\": \"Errore nel recupero del prodotto\"}");
        }
    }

    // POST: riceve le modifiche via AJAX e le salva nel DB
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // recupero i parametri inviati nel body della fetch POST
        String idStr      = request.getParameter("idProdotto");
        String nome       = request.getParameter("nome");
        String stile      = request.getParameter("stile");
        String colore     = request.getParameter("colore");
        String dimensioni = request.getParameter("dimensioni");
        String prezzo     = request.getParameter("prezzo");
        String quantita   = request.getParameter("quantita");
        String descrizione = request.getParameter("descrizione");
        String immagine   = request.getParameter("immagine");

        // validazione lato server: tutti i campi sono obbligatori
        if (idStr == null || idStr.trim().isEmpty() ||
            nome == null || nome.trim().isEmpty() ||
            stile == null || stile.trim().isEmpty() ||
            colore == null || colore.trim().isEmpty() ||
            dimensioni == null || dimensioni.trim().isEmpty() ||
            prezzo == null || prezzo.trim().isEmpty() ||
            quantita == null || quantita.trim().isEmpty() ||
            descrizione == null || descrizione.trim().isEmpty() ||
            immagine == null || immagine.trim().isEmpty()) {

            response.getWriter().write("{\"successo\": false, \"messaggio\": \"Tutti i campi sono obbligatori.\"}");
            return;
        }

        try {
            // conversione sicura dei tipi numerici
            int idProdotto  = Integer.parseInt(idStr.trim());
            double prezzoNum = Double.parseDouble(prezzo.trim().replace(",", "."));
            int quantitaNum  = Integer.parseInt(quantita.trim());

            ProdottoAdminDAO prodottoAdminDAO = new ProdottoAdminDAO();
            ProdottoBean prodotto = prodottoAdminDAO.doRetrieveByKey(idProdotto);

            // controllo che il prodotto esista nel DB prima di aggiornarlo
            if (prodotto != null) {
                prodotto.setNome(nome.trim());
                prodotto.setStile(stile.trim());
                prodotto.setColore(colore.trim());
                prodotto.setDimensioni(dimensioni.trim());
                prodotto.setPrezzo(prezzoNum);
                prodotto.setQuantita(quantitaNum);
                prodotto.setDescrizione(descrizione.trim());
                prodotto.setImmagine(immagine.trim());

                prodottoAdminDAO.doUpdate(prodotto);
                response.getWriter().write("{\"successo\": true}");

            } else {
                response.getWriter().write("{\"successo\": false, \"messaggio\": \"Prodotto non trovato nel database.\"}");
            }

        } catch (NumberFormatException e) {
            response.getWriter().write("{\"successo\": false, \"messaggio\": \"Formato del prezzo, della quantità o dell'ID non valido.\"}");
        } catch (SQLException e) {
            response.getWriter().write("{\"successo\": false, \"messaggio\": \"Errore durante l'aggiornamento nel DB.\"}");
        }
    }
}