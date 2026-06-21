package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.ProdottoAdminDAO;
import bean.ProdottoBean;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import javax.servlet.annotation.WebServlet;

/**
 * Servlet che funge da controller del catalogo dei prodotti lato admin.
 * Serve esclusivamente a mostrare all'amministratore il catalogo completo 
 * di tutti i prodotti attualmente salvati nel database. 
 */

@WebServlet("/CatalogoAdminServlet")
public class CatalogoAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nome = request.getParameter("nome");
		String stile = request.getParameter("stile");
		String colore = request.getParameter("colore");
		String dimensioni = request.getParameter("dimensioni");
		String prezzoStr = request.getParameter("prezzo");

		ProdottoAdminDAO pad = new ProdottoAdminDAO();
		try {
			List<ProdottoBean> listaProdotti = filtraProdotti(
					pad.doRetriveAll(), nome, stile, colore, dimensioni, prezzoStr);
			request.setAttribute("listaProdotti", listaProdotti);
			request.getRequestDispatcher("/WEB-INF/jsp/catalogoAdmin.jsp").forward(request, response);
			return;
		} catch (SQLException e) {
			// Se il database ha un problema, stampiamo l'errore nella console di Eclipse
			e.printStackTrace();
			// E mostriamo una pagina di errore all'utente
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore di connessione al database");
			return;
		}
	}
	
	// Se per sbaglio la chiamata arriva in POST, la deviamo sul GET per farle fare lo stesso percorso
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private List<ProdottoBean> filtraProdotti(List<ProdottoBean> prodotti, String nome, String stile,
			String colore, String dimensioni, String prezzoStr) {
		List<ProdottoBean> risultato = new ArrayList<>(prodotti);

		if (nome != null && !nome.trim().isEmpty()) {
			String filtro = nome.trim().toLowerCase();
			risultato.removeIf(p -> p.getNome() == null || !p.getNome().toLowerCase().contains(filtro));
		}
		if (stile != null && !stile.trim().isEmpty()) {
			String filtro = stile.trim().toLowerCase();
			risultato.removeIf(p -> p.getStile() == null || !p.getStile().toLowerCase().contains(filtro));
		}
		if (colore != null && !colore.trim().isEmpty()) {
			String filtro = colore.trim().toLowerCase();
			risultato.removeIf(p -> p.getColore() == null || !p.getColore().toLowerCase().contains(filtro));
		}
		if (dimensioni != null && !dimensioni.trim().isEmpty()) {
			String filtro = dimensioni.trim().toLowerCase();
			risultato.removeIf(p -> p.getDimensioni() == null || !p.getDimensioni().toLowerCase().contains(filtro));
		}
		if (prezzoStr != null && !prezzoStr.trim().isEmpty()) {
			try {
				double prezzoMin = Double.parseDouble(prezzoStr.trim());
				risultato.removeIf(p -> p.getPrezzo() < prezzoMin);
			} catch (NumberFormatException ignored) {
				// prezzo non valido: ignora il filtro
			}
		}

		return risultato;
	}
}