package control;

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

import bean.CarrelloBean;
import bean.OrdinazioneBean;
import bean.ProdottoCarrello;
import bean.UtenteBean;
import dao.OrdinazioneDAO;

@WebServlet("/RegistraOrdinazioneServlet")
public class RegistraOrdinazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrdinazioneDAO ordinazioneDAO;

	@Override
	public void init(ServletConfig config) throws ServletException {
		ordinazioneDAO = new OrdinazioneDAO();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);//se non esiste già non la ricrea
		if (session == null || session.getAttribute("utenteLoggato") == null) {
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
			return;
		}

		CarrelloBean carrello = (CarrelloBean) session.getAttribute("carrello");
		if (carrello == null || carrello.getElementi().isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/CarrelloServlet");
			return;
		}

		request.getRequestDispatcher("/WEB-INF/jsp/registraOrdinazione.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("utenteLoggato") == null) {
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
			return;
		}

		UtenteBean utente = (UtenteBean) session.getAttribute("utenteLoggato");
		CarrelloBean carrello = (CarrelloBean) session.getAttribute("carrello");
		if (carrello == null || carrello.getElementi().isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/CarrelloServlet");
			return;
		}

		try {
			OrdinazioneBean ordinazione = new OrdinazioneBean();
			ordinazione.setUtente(utente);
			ordinazione.setCitta(request.getParameter("citta").trim());
			ordinazione.setIndirizzo(request.getParameter("indirizzo").trim());
			ordinazione.setCivico(request.getParameter("civico").trim());
			ordinazione.setCap(request.getParameter("cap").trim());
			float totaleNetto = carrello.getTotale();
			float iva = totaleNetto * 0.22f;
			float totaleLordo = totaleNetto + iva;
			ordinazione.setImporto(totaleLordo);
			ordinazione.setStato("in elaborazione");
			ordinazione.setDataOrdinazione(new java.sql.Date(System.currentTimeMillis()));

			List<ProdottoCarrello> items = new ArrayList<>(carrello.getElementi());
			int idOrdinazione = ordinazioneDAO.doCreateOrdinazioneFromCarrello(ordinazione, items);
			ordinazione.setIdOrdinazione(idOrdinazione);

			session.removeAttribute("carrello");

			request.setAttribute("ordinazione", ordinazione);
			request.setAttribute("items", items);
			request.setAttribute("totaleNetto", totaleNetto);
			request.setAttribute("iva", iva);
			request.setAttribute("totaleLordo", totaleLordo);
			request.getRequestDispatcher("/WEB-INF/jsp/fattura.jsp").forward(request, response);
		} catch (SQLException e) {
			request.setAttribute("errore", "Impossibile completare l'ordine. Riprova.");
			request.getRequestDispatcher("/WEB-INF/jsp/registraOrdinazione.jsp").forward(request, response);
		}
	}
}
