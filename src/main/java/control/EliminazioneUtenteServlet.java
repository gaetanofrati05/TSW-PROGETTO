package control;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.UtenteBean;
import dao.UtenteDAO;
/**
 * Servlet implementation class EliminazioneUtenteServlet
 */
@WebServlet("/EliminazioneUtenteServlet")
public class EliminazioneUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UtenteDAO utenteDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EliminazioneUtenteServlet() {
        super();
        
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		utenteDAO=new UtenteDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null || session.getAttribute("utenteLoggato") == null) {
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
			return;
		}
		
		UtenteBean utenteLoggato= (UtenteBean) session.getAttribute("utenteLoggato");
		try {
			UtenteBean utente= utenteDAO.doRetrieveByEmail(utenteLoggato.getEmail());
			if(utente!=null) {
				request.setAttribute("utenteEliminare", utente);
				request.getRequestDispatcher("/WEB-INF/jsp/eliminaAccount.jsp").forward(request, response);
			}else {
				// Se per qualche motivo l'utente in sessione non è nel DB, resettiamo la sessione
				response.sendRedirect(request.getContextPath() + "/LoginServlet");
			}
		}catch(SQLException e) {
			
			e.printStackTrace();
			throw new ServletException("Errore durante il recupero dell'utente", e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null || session.getAttribute("utenteLoggato") == null) {
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
			return;
		}
		
		UtenteBean utenteLoggato = (UtenteBean) session.getAttribute("utenteLoggato");
		try {
			UtenteBean utente= new UtenteBean();
			utente.setEmail(utenteLoggato.getEmail());
			utenteDAO.doDeleteUtente(utente);
			session.invalidate();
			response.sendRedirect(request.getContextPath() + "/LoginServlet?utenteEliminato=true");
			
		}catch(SQLException e) {
			e.printStackTrace();
	    	throw new ServletException("Errore nel database durante l'eliminazionee del profilo", e);
		}
	}

}
