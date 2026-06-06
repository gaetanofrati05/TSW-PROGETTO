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

import bean.ProdottoBean;
import bean.RecensioneBean;
import bean.UtenteBean;
import dao.RecensioneDAO;
import dao.UtenteDAO;

/**
 * Servlet implementation class RegistraRecensioneServlet
 */
@WebServlet("/RegistraRecensioneServlet")
public class RegistraRecensioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private RecensioneDAO recensioneDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistraRecensioneServlet() {
        super();
        
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
	recensioneDAO= new RecensioneDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null || session.getAttribute("utenteLoggato") == null) {
			response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
			return;
		}
		
		// 1. Recupero i dati inviati dal form HTML
		UtenteBean utente = (UtenteBean) session.getAttribute("utenteLoggato");
		String scoringStr = request.getParameter("scoring");
		String descrizione = request.getParameter("descrizione");
		String idStrProdotto = request.getParameter("idProdotto"); // Campo hidden nel form del prodotto
		
		List<String> errori = new ArrayList<>();

		// 2. Validazione dei dati inseriti
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
			request.setAttribute("errore", errori);
			request.getRequestDispatcher("/jsp/dettaglioProdotto.jsp?id=" + idStrProdotto).forward(request, response);
			return;
		}
		
		try {
			int idProdotto = Integer.parseInt(idStrProdotto);
			
			//  Costruiamo il Bean della recensione
			RecensioneBean recensione = new RecensioneBean();
			recensione.setUtente(utente);
			recensione.setScoring(scoring);
			recensione.setDescrizione(descrizione.trim());
			recensione.setDataRecensione(new java.util.Date()); // Data corrente
			ProdottoBean prodotto = new ProdottoBean();
			prodotto.setIdProdotto(idProdotto);
			recensione.setProdotto(prodotto);
			
			// 4. Salvataggio nel Database
			recensioneDAO.doSaveRecensione(recensione);
			
			//  Redirect di successo alla pagina prodotto
			response.sendRedirect(request.getContextPath() + "/jsp/dettaglioProdotto.jsp?recensioneSalvata=true");
			
		} catch(SQLException | NumberFormatException e) {
			e.printStackTrace();
			throw new ServletException("Errore nel database durante il salvataggio della recensione", e);
		}
	}

}
