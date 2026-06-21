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
import bean.RecensioneBean;
import dao.RecensioneDAO;

/**
 * Servlet implementation class EliminaRecensioneServlet
 */
@WebServlet("/EliminaRecensioneServlet")
public class EliminaRecensioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private RecensioneDAO recensioneDAO; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EliminaRecensioneServlet() {
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
		//Recuperiamo la recensione da dover eliminare 
		HttpSession session= request.getSession(false);
		if(session==null || session.getAttribute("utenteLoggato")==null) {
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
			return;
		}
		String idStr= request.getParameter("idRecensione");
		if(idStr==null || idStr.trim().isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/ModificaProfiloServlet");
			return;
		}
		try {
			int idRecensione= Integer.parseInt(idStr);
			RecensioneBean recensione= recensioneDAO.doRetrieveByKey(idRecensione);
			if (recensione != null) {
				request.setAttribute("recensioneEliminare", recensione);
				request.getRequestDispatcher("/WEB-INF/jsp/eliminaRecensione.jsp").forward(request, response);
			} else {
				response.sendRedirect(request.getContextPath() + "/ModificaProfiloServlet?errore=RecensioneNonTrovata");
			}
			
		}catch(SQLException | NumberFormatException e){
			e.printStackTrace();
			throw new ServletException("Errore durante il recupero della recensione", e);
		}
		 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session= request.getSession(false);
		if(session==null || session.getAttribute("utenteLoggato")==null) {
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
			return;
		}
		//Recupero l'id da dover eliminare
		String idStr= request.getParameter("idRecensione");
		try {
			int idRecensione= Integer.parseInt(idStr);
			RecensioneBean recensione= new RecensioneBean();
			recensione.setIdRecensione(idRecensione);
			recensioneDAO.deleteRecensione(recensione);
			response.sendRedirect(request.getContextPath() + "/VisualizzaRecensioniServlet?recensioneEliminata=true");
		}catch(SQLException  | NumberFormatException e) {
			throw new ServletException("Errore nel daabase durante l'eliminazione della recensione");
		}
	}

}
