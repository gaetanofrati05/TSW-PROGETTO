package control;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.OrdinazioneBean;
import dao.OrdineAdminDAO;

/**
 * Servlet che funge da controller della gestione degli ordini lato admin.
 * Serve esclusivamente a mostrare all'amministratore la lista di tutte le ordinazioni attualmente salvate nel database.
 * Gestisce anche la ricerca degli ordini in base a diversi criteri, come ID ordine, email dell'utente, date di inizio e fine, e status dell'ordine.
 * Gestisce anche l'aggiornamento dello stato di un ordine.
 */
@WebServlet("/GestioneOrdiniAdminServlet")
public class GestioneOrdiniAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String JSP_ORDINI_ADMIN = "/WEB-INF/jsp/ordiniAdmin.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idOrdinazioneStr = request.getParameter("idOrdinazione");
		String email = request.getParameter("email");
		String dataInizioStr = request.getParameter("dataInizio");
		String dataFineStr = request.getParameter("dataFine");
		String status = request.getParameter("status");

		impostaParametriFiltro(request, idOrdinazioneStr, email, dataInizioStr, dataFineStr, status);

		OrdineAdminDAO ordineAdminDAO = new OrdineAdminDAO();
		List<OrdinazioneBean> listaOrdinazioni = new ArrayList<>();

		try {
			if (idOrdinazioneStr != null && !idOrdinazioneStr.trim().isEmpty()) {
				int idOrdinazione = Integer.parseInt(idOrdinazioneStr.trim());
				OrdinazioneBean ordine = ordineAdminDAO.doRetrieveByKey(idOrdinazione);
				if (ordine != null) {
					listaOrdinazioni.add(ordine);
				}
			} else if (email != null && !email.trim().isEmpty()) {
				listaOrdinazioni = ordineAdminDAO.doRetrieveByUtente(email.trim());
			} else if (dataInizioStr != null && !dataInizioStr.trim().isEmpty()
					&& dataFineStr != null && !dataFineStr.trim().isEmpty()) {
				Date dataInizio = Date.valueOf(dataInizioStr.trim());
				Date dataFine = Date.valueOf(dataFineStr.trim());
				listaOrdinazioni = ordineAdminDAO.doRetriveByDates(dataInizio, dataFine);
			} else if (status != null && !status.trim().isEmpty()) {
				listaOrdinazioni = ordineAdminDAO.doRetriveByStatus(status.trim());
			} else {
				listaOrdinazioni = ordineAdminDAO.doRetrieveAll();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errore", "Errore di connessione al database durante la ricerca.");
		} catch (IllegalArgumentException e) {
			request.setAttribute("errore", "Formato dei dati di ricerca non valido.");
			try {
				listaOrdinazioni = ordineAdminDAO.doRetrieveAll();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		request.setAttribute("listaOrdinazioni", listaOrdinazioni);
		request.getRequestDispatcher(JSP_ORDINI_ADMIN).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idOrdinazioneStr = request.getParameter("idOrdinazioneUpdate");
		String nuovoStato = request.getParameter("nuovoStatoUpdate");

		if (idOrdinazioneStr == null || idOrdinazioneStr.trim().isEmpty()
				|| nuovoStato == null || nuovoStato.trim().isEmpty()) {
			doGet(request, response);
			return;
		}

		try {
			int idOrdinazione = Integer.parseInt(idOrdinazioneStr.trim());
			OrdineAdminDAO ordineAdminDAO = new OrdineAdminDAO();
			OrdinazioneBean ordinazione = ordineAdminDAO.doRetrieveByKey(idOrdinazione);

			if (ordinazione == null) {
				request.setAttribute("errore", "Ordine non trovato.");
				impostaListaCompleta(request, ordineAdminDAO);
				request.getRequestDispatcher(JSP_ORDINI_ADMIN).forward(request, response);
				return;
			}

			ordinazione.setStato(nuovoStato.trim());
			ordineAdminDAO.doUpdateOrdinazione(ordinazione);
			response.sendRedirect(request.getContextPath() + "/OrdiniAdminServlet");
		} catch (NumberFormatException e) {
			request.setAttribute("errore", "ID ordine non valido.");
			impostaListaCompleta(request, new OrdineAdminDAO());
			request.getRequestDispatcher(JSP_ORDINI_ADMIN).forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errore", "Errore durante l'aggiornamento dell'ordine.");
			impostaListaCompleta(request, new OrdineAdminDAO());
			request.getRequestDispatcher(JSP_ORDINI_ADMIN).forward(request, response);
		}
	}

	private void impostaParametriFiltro(HttpServletRequest request, String idOrdinazioneStr, String email,
			String dataInizioStr, String dataFineStr, String status) {
		request.setAttribute("idOrdinazione", idOrdinazioneStr);
		request.setAttribute("email", email);
		request.setAttribute("dataInizio", dataInizioStr);
		request.setAttribute("dataFine", dataFineStr);
		request.setAttribute("status", status);
	}

	private void impostaListaCompleta(HttpServletRequest request, OrdineAdminDAO ordineAdminDAO) throws ServletException {
		try {
			request.setAttribute("listaOrdinazioni", ordineAdminDAO.doRetrieveAll());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServletException("Errore durante il caricamento degli ordini.", e);
		}
	}
}
