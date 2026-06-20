package control;

import java.io.IOException;
import java.sql.SQLException;
<<<<<<< HEAD:src/main/java/control/ModificaProdottoServlet.java
import java.util.List;
=======
>>>>>>> origin/massimo:WebProgramming/src/main/java/control/ModificaProdottoServlet.java

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

<<<<<<< HEAD:src/main/java/control/ModificaProdottoServlet.java
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Per ora gestiamo solo un alert/errore, non c'è una JSP dedicata.
		// Reindirizziamo al catalogo con un parametro per dire che la feature non è ancora implementata graficamente.
		response.sendRedirect(request.getContextPath() + "/CatalogoAdminServlet?errore=modifica_non_implementata");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
=======
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

	//GET: carica i dati del prodotto specifico

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// si imposta il tipo di risposta come JSON
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		//si tenta di recuperare l'id del prodotto dalla richiesta
		try{
			int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));

			// si recupera il prodotto specifico dal DAO
			ProdottoAdminDAO prodottoAdminDAO = new ProdottoAdminDAO();
			ProdottoBean prodotto = prodottoAdminDAO.doRetrieveByKey(idProdotto);

			if(prodotto != null){
				// si costruisce la stringa JSON con i dati del prodotto
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

				// si invia la risposta JSON
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

    //POST: salva le modifiche del prodotto
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// si imposta il tipo di risposta come JSON
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		//si recuperano i dati inviati nel body della Fetch POST
		String idStr = request.getParameter("idProdotto");
		String nome = request.getParameter("nome");
		String stile = request.getParameter("stile");
		String colore = request.getParameter("colore");
		String dimensioni = request.getParameter("dimensioni");
		String prezzo = request.getParameter("prezzo");
		String quantita = request.getParameter("quantita");
		String descrizione = request.getParameter("descrizione");
		String immagine = request.getParameter("immagine");

		// VALIDAZIONE LATO SERVER delle stringhe obbligatorie
		if (idStr == null || idStr.trim().isEmpty() || nome == null || nome.trim().isEmpty() || stile == null || stile.trim().isEmpty() || colore == null || colore.trim().isEmpty() || dimensioni == null || dimensioni.trim().isEmpty() || prezzo == null || prezzo.trim().isEmpty() || quantita == null || quantita.trim().isEmpty() || descrizione == null || descrizione.trim().isEmpty() || immagine == null || immagine.trim().isEmpty()) {
			response.getWriter().write("{\"successo\": false, \"messaggio\": \"Tutti i campi sono obbligatori.\"}");
			return;
		}

		try {
			int idProdotto = Integer.parseInt(idStr.trim());
			double prezzoNum = Double.parseDouble(prezzo.trim().replace(",", "."));
			int quantitaNum = Integer.parseInt(quantita.trim());

			ProdottoAdminDAO prodottoAdminDAO = new ProdottoAdminDAO();
			ProdottoBean prodotto = prodottoAdminDAO.doRetrieveByKey(idProdotto);

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
>>>>>>> origin/massimo:WebProgramming/src/main/java/control/ModificaProdottoServlet.java
	}
}