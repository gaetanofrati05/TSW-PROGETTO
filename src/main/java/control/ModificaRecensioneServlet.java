package control;

import bean.ProdottoBean;
import bean.RecensioneBean;
import bean.UtenteBean;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.ProdottoDAO;
import dao.RecensioneDAO;

@WebServlet("/ModificaRecensioneServlet")
public class ModificaRecensioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private RecensioneDAO recensioneDAO;
    private ProdottoDAO prodottoDAO;

    public ModificaRecensioneServlet() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
		recensioneDAO = new RecensioneDAO();
		prodottoDAO = new ProdottoDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UtenteBean utenteLoggato = getUtenteLoggato(request, response);
		if (utenteLoggato == null) {
			return;
		}

		String idStr = request.getParameter("id");
		if (idStr == null || idStr.trim().isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/VisualizzaRecensioniServlet");
			return;
		}

		try {
			int idRecensione = Integer.parseInt(idStr.trim());
			RecensioneBean recensione = recensioneDAO.doRetrieveByKey(idRecensione, utenteLoggato.getEmail());

			if (recensione != null) {
				forwardModifica(request, response, recensione);
			} else {
				response.sendRedirect(request.getContextPath() + "/VisualizzaRecensioniServlet?errore=RecensioneNonTrovata");
			}
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/VisualizzaRecensioniServlet");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServletException("Errore durante il recupero della recensione", e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UtenteBean utenteLoggato = getUtenteLoggato(request, response);
		if (utenteLoggato == null) {
			return;
		}

		String idStr = request.getParameter("idRecensione");
		String scoringStr = request.getParameter("scoring");
		String descrizione = request.getParameter("descrizione");

		List<String> errori = new ArrayList<>();

		int idRecensione = 0;
		try {
			if (idStr == null || idStr.trim().isEmpty()) {
				throw new NumberFormatException();
			}
			idRecensione = Integer.parseInt(idStr.trim());
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/VisualizzaRecensioniServlet");
			return;
		}

		int scoring = 0;
		try {
			scoring = Integer.parseInt(scoringStr);
			if (scoring < 1 || scoring > 5) {
				errori.add("Il punteggio deve essere compreso tra 1 e 5");
			}
		} catch (NumberFormatException e) {
			errori.add("Punteggio non valido");
		}

		if (descrizione == null || descrizione.trim().isEmpty()) {
			errori.add("La descrizione della recensione non può essere vuota");
		}

		try {
			RecensioneBean recensioneEsistente = recensioneDAO.doRetrieveByKey(idRecensione, utenteLoggato.getEmail());

			if (recensioneEsistente == null) {
				response.sendRedirect(request.getContextPath() + "/VisualizzaRecensioniServlet?errore=NonAutorizzato");
				return;
			}

			if (!errori.isEmpty()) {
				recensioneEsistente.setScoring(scoring);
				recensioneEsistente.setDescrizione(descrizione != null ? descrizione : "");
				request.setAttribute("erroriRecensione", errori);
				forwardModifica(request, response, recensioneEsistente);
				return;
			}

			RecensioneBean recensioneDaAggiornare = new RecensioneBean();
			recensioneDaAggiornare.setIdRecensione(idRecensione);
			recensioneDaAggiornare.setScoring(scoring);
			recensioneDaAggiornare.setDescrizione(descrizione.trim());
			recensioneDaAggiornare.setDataRecensione(new java.sql.Date(System.currentTimeMillis()));

			recensioneDAO.doUpdateRecensione(recensioneDaAggiornare);
			response.sendRedirect(request.getContextPath() + "/VisualizzaRecensioniServlet?recensioneModificata=true");

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServletException("Errore durante l'aggiornamento della recensione nel DB", e);
		}
	}

	private void forwardModifica(HttpServletRequest request, HttpServletResponse response, RecensioneBean recensione)
			throws ServletException, IOException, SQLException {
		if (recensione.getProdotto() == null) {
			response.sendRedirect(request.getContextPath() + "/VisualizzaRecensioniServlet?errore=ProdottoNonTrovato");
			return;
		}

		ProdottoBean prodotto = prodottoDAO.doRetrieveById(recensione.getProdotto().getIdProdotto());
		if (prodotto == null) {
			response.sendRedirect(request.getContextPath() + "/VisualizzaRecensioniServlet?errore=ProdottoNonTrovato");
			return;
		}

		request.setAttribute("recensioneDaModificare", recensione);
		request.setAttribute("prodotto", prodotto);
		request.getRequestDispatcher("/WEB-INF/jsp/modificaRecensione.jsp").forward(request, response);
	}

	private UtenteBean getUtenteLoggato(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("utenteLoggato") == null) {
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
			return null;
		}
		return (UtenteBean) session.getAttribute("utenteLoggato");
	}
}