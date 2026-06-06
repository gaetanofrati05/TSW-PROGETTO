package control;
import bean.RecensioneBean;
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
import dao.RecensioneDAO;

/**
 * Servlet implementation class ModificaRecensioneServlet
 */
@WebServlet("/ModificaRecensioneServlet")
public class ModificaRecensioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private RecensioneDAO recensioneDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificaRecensioneServlet() {
        super();
        
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		recensioneDAO= new RecensioneDAO();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null || session.getAttribute("utenteLoggato") == null) {
			response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
			return;
		}

		String idStr = request.getParameter("id");
		if (idStr == null || idStr.trim().isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/jsp/profilo.jsp");
			return;
		}

		try {
			int idRecensione = Integer.parseInt(idStr);
			RecensioneBean recensione = recensioneDAO.doRetrieveByKey(idRecensione);

			if (recensione != null) {
				request.setAttribute("recensioneDaModificare", recensione);
				request.getRequestDispatcher("/jsp/modificaRecensione.jsp").forward(request, response);
			} else {
				response.sendRedirect(request.getContextPath() + "/jsp/profilo.jsp?errore=RecensioneNonTrovata");
			}
		} catch (SQLException | NumberFormatException e) {
			e.printStackTrace();
			throw new ServletException("Errore durante il recupero della recensione", e);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null || session.getAttribute("utenteLoggato") == null) {
			response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
			return;
		}

		// 1. Recupero i dati inviati dal form HTML
		String idStr = request.getParameter("idRecensione"); // Campo nascosto (hidden) nel form
		String scoringStr = request.getParameter("scoring");
		String descrizione = request.getParameter("descrizione");

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
			doGet(request, response); 
			return;
		}

		try {
			int idRecensione = Integer.parseInt(idStr);
			
		    // Costruiamo il Bean con i NUOVI dati da aggiornare
			RecensioneBean recensione = new RecensioneBean();
			recensione.setIdRecensione(idRecensione);
			recensione.setScoring(scoring);
			recensione.setDescrizione(descrizione.trim());
			// Impostiamo la data corrente come data di modifica
			recensione.setDataRecensione(new java.util.Date()); 

			recensioneDAO.doUpdateRecensione(recensione);
			response.sendRedirect(request.getContextPath() + "/jsp/profilo.jsp?recensioneModificata=true");

		} catch (SQLException | NumberFormatException e) {
			e.printStackTrace();
			throw new ServletException("Errore durante l'aggiornamento della recensione nel DB", e);
		}
	}
	

}
