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
import bean.OrdinazioneBean;
import bean.UtenteBean;
import dao.OrdinazioneDAO;

/**
 * Servlet implementation class EliminaOrdinazioneServlet
 */
@WebServlet("/EliminaOrdinazioneServlet")
public class EliminaOrdinazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private OrdinazioneDAO ordinazioneDAO;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EliminaOrdinazioneServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	public void init(ServletConfig config) throws ServletException {
		ordinazioneDAO= new OrdinazioneDAO();
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("utenteLoggato")==null){
			response.sendRedirect(request.getContextPath()+ "/LoginServlet");
			return;
		}
		String idStr= request.getParameter("idOrdinazione");
		if(idStr==null || idStr.trim().isEmpty()) {
			response.sendRedirect(request.getContextPath()+"/ModificaProfiloServlet");
			return;
		}
		try {
			
			int idOrdinazione= Integer.parseInt(idStr);
			UtenteBean utenteLoggato= (UtenteBean) session.getAttribute("utenteLoggato");
			OrdinazioneBean ordinazione= ordinazioneDAO.doRetrieveByKey(idOrdinazione,utenteLoggato.getEmail());
			if(ordinazione!=null) {
				request.setAttribute("ordinazioneEliminare", ordinazione);
				request.getRequestDispatcher("/WEB-INF/jsp/eliminaOrdinazione.jsp").forward(request, response);
			}else {
				
				response.sendRedirect(request.getContextPath()+ "/VisualizzaOrdiniServlet?errore=OrdinazioneNonTrovata");
			}
			
		}catch(SQLException | NumberFormatException e){
			e.printStackTrace();
			throw new ServletException("Errore durante il recupero della ordinazione", e);
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
		String idStr= request.getParameter("idOrdinazione");
		if(idStr==null || idStr.trim().isEmpty()) {
			response.sendRedirect(request.getContextPath()+"/ModificaProfiloServlet");
			return;
		}
		try {
		int idOrdinazione= Integer.parseInt(idStr);
		UtenteBean utenteLoggato= (UtenteBean) session.getAttribute("utenteLoggato");
		OrdinazioneBean ordinazione= ordinazioneDAO.doRetrieveByKey(idOrdinazione,utenteLoggato.getEmail());
		if(ordinazione!=null) {
		    ordinazioneDAO.doDeleteOrdinazione(ordinazione);
		    response.sendRedirect(request.getContextPath()+ "/VisualizzaOrdiniServlet?ordinazioneEliminata=true");
		
		 }else {
			 response.sendRedirect(request.getContextPath()+ "/VisualizzaOrdiniServlet?errore=NonAutorizzato");
		 }
		}
	catch(SQLException | NumberFormatException e) {
		throw new ServletException("Errore nel database durante l'eliminazione della recensione", e);
	}
	
	}

}
