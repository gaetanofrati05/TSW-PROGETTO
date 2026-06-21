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
import bean.OrdinazioneBean;
import bean.ProdottoBean;
import bean.ProdottoCarrello;
import bean.RecensioneBean;
import bean.UtenteBean;
import dao.OrdinazioneDAO;
import dao.RecensioneDAO;

@WebServlet("/RegistraRecensioneServlet")
public class RegistraRecensioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private RecensioneDAO recensioneDAO;
    private OrdinazioneDAO ordinazioneDAO;

    public RegistraRecensioneServlet() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
		recensioneDAO = new RecensioneDAO();
		ordinazioneDAO = new OrdinazioneDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UtenteBean utente = getUtenteLoggato(request, response);
		if (utente == null) {
			return;
		}

		String idOrdinazioneStr = request.getParameter("idOrdinazione");
		if (idOrdinazioneStr == null || idOrdinazioneStr.trim().isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/VisualizzaOrdiniServlet");
			return;
		}

		try {
			int idOrdinazione = Integer.parseInt(idOrdinazioneStr.trim());
			String idProdottoStr = request.getParameter("idProdotto");

			OrdinazioneBean ordinazione = ordinazioneDAO.doRetrieveByKey(idOrdinazione, utente.getEmail());
			if (ordinazione == null) {
				response.sendRedirect(request.getContextPath() + "/VisualizzaOrdiniServlet?errore=OrdineNonTrovato");
				return;
			}

			if (!"consegnato".equalsIgnoreCase(ordinazione.getStato())) {
				response.sendRedirect(request.getContextPath() + "/VisualizzaOrdiniServlet?errore=OrdineNonConsegnato");
				return;
			}

			List<ProdottoCarrello> prodottiOrdine = ordinazioneDAO.doStampaProdottiOrdinazione(idOrdinazione, utente.getEmail());
			if (prodottiOrdine.isEmpty()) {
				response.sendRedirect(request.getContextPath() + "/VisualizzaOrdiniServlet?errore=ProdottoNonTrovato");
				return;

			}

			ProdottoCarrello itemSelezionato = null;
			if (idProdottoStr != null && !idProdottoStr.trim().isEmpty()) {
				itemSelezionato = trovaProdotto(prodottiOrdine, idProdottoStr);
				if (itemSelezionato == null) {
					response.sendRedirect(request.getContextPath() + "/VisualizzaOrdiniServlet?errore=ProdottoNonValido");
					return;
				}
			}

			impostaAttributiForm(request, ordinazione, prodottiOrdine, itemSelezionato);
			request.getRequestDispatcher("/WEB-INF/jsp/registraRecensioneUtente.jsp").forward(request, response);

		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/VisualizzaOrdiniServlet");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServletException("Errore durante il recupero dei dati per la recensione", e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UtenteBean utente = getUtenteLoggato(request, response);
		if (utente == null) {
			return;
		}

		String scoringStr = request.getParameter("scoring");
		String descrizione = request.getParameter("descrizione");
		String idStrProdotto = request.getParameter("idProdotto");
		String idOrdinazioneStr = request.getParameter("idOrdinazione");

		List<String> errori = new ArrayList<>();

		int idProdotto = 0;
		try {
			if (idStrProdotto == null || idStrProdotto.trim().isEmpty()) {
				throw new NumberFormatException();
			}
			idProdotto = Integer.parseInt(idStrProdotto);
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/VisualizzaOrdiniServlet");
			return;
		}

		int idOrdinazione = 0;
		OrdinazioneBean ordinazione = null;
		ProdottoCarrello itemSelezionato = null;
		List<ProdottoCarrello> prodottiOrdine = new ArrayList<>();

		try {
			if (idOrdinazioneStr != null && !idOrdinazioneStr.trim().isEmpty()) {
				idOrdinazione = Integer.parseInt(idOrdinazioneStr.trim());
				ordinazione = ordinazioneDAO.doRetrieveByKey(idOrdinazione, utente.getEmail());

				if (ordinazione == null) {
					response.sendRedirect(request.getContextPath() + "/VisualizzaOrdiniServlet?errore=OrdineNonTrovato");
					return;
				}

				if (!"consegnato".equalsIgnoreCase(ordinazione.getStato())) {
					response.sendRedirect(request.getContextPath() + "/VisualizzaOrdiniServlet?errore=OrdineNonConsegnato");
					return;
				}

				prodottiOrdine = ordinazioneDAO.doStampaProdottiOrdinazione(idOrdinazione, utente.getEmail());
				itemSelezionato = trovaProdotto(prodottiOrdine, idStrProdotto);

				if (itemSelezionato == null) {
					response.sendRedirect(request.getContextPath() + "/VisualizzaOrdiniServlet?errore=ProdottoNonValido");
					return;
				}
			}
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/VisualizzaOrdiniServlet");
			return;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServletException("Errore durante la verifica dell'ordinazione", e);
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

		if (!errori.isEmpty()) {
			request.setAttribute("erroriRecensione", errori);
			request.setAttribute("scoringInserito", scoringStr);
			request.setAttribute("descrizioneInserita", descrizione);

			if (itemSelezionato != null && ordinazione != null) {
				impostaAttributiForm(request, ordinazione, prodottiOrdine, itemSelezionato);
			} else {
				ProdottoBean prodotto = new ProdottoBean();
				prodotto.setIdProdotto(idProdotto);
				request.setAttribute("prodotto", prodotto);
			}

			request.getRequestDispatcher("/WEB-INF/jsp/registraRecensioneUtente.jsp").forward(request, response);
			return;
		}

		try {
			RecensioneBean recensione = new RecensioneBean();
			recensione.setUtente(utente);
			recensione.setScoring(scoring);
			recensione.setDescrizione(descrizione.trim());
			recensione.setDataRecensione(new java.sql.Date(System.currentTimeMillis()));

			ProdottoBean prodotto = new ProdottoBean();
			prodotto.setIdProdotto(idProdotto);
			recensione.setProdotto(prodotto);

			recensioneDAO.doSaveRecensione(recensione);
			response.sendRedirect(request.getContextPath() + "/VisualizzaRecensioniServlet?recensioneSalvata=true");

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServletException("Errore nel database durante il salvataggio della recensione", e);
		}
	}

	private UtenteBean getUtenteLoggato(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("utenteLoggato") == null) {
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
			return null;
		}
		return (UtenteBean) session.getAttribute("utenteLoggato");
	}

	private ProdottoCarrello trovaProdotto(List<ProdottoCarrello> prodottiOrdine, String idProdottoStr) {
		if (idProdottoStr == null || idProdottoStr.trim().isEmpty()) {
			return null;
		}
		int idProdotto = Integer.parseInt(idProdottoStr.trim());
		for (ProdottoCarrello item : prodottiOrdine) {
			if (item.getProdotto().getIdProdotto() == idProdotto) {
				return item;
			}
		}
		return null;
	}

	private void impostaAttributiForm(HttpServletRequest request, OrdinazioneBean ordinazione,
			List<ProdottoCarrello> prodottiOrdine, ProdottoCarrello itemSelezionato) {
		List<ProdottoBean> listaProdotti = new ArrayList<>();
		for (ProdottoCarrello item : prodottiOrdine) {
			listaProdotti.add(item.getProdotto());
		}
		request.setAttribute("listaProdotti", listaProdotti);
		request.setAttribute("ordinazione", ordinazione);

		ProdottoCarrello itemDaMostrare = prodottiOrdine.get(0);
		if (itemSelezionato != null) {
			itemDaMostrare = itemSelezionato;
			request.setAttribute("idProdottoSelezionato", itemSelezionato.getProdotto().getIdProdotto());
		}
		request.setAttribute("prodotto", itemDaMostrare.getProdotto());
		request.setAttribute("quantitaOrdinata", itemDaMostrare.getQuantita());
	}
}