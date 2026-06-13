package control;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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
 * Servlet implementation class VisualizzaRecensioniServlet
 */
@WebServlet("/VisualizzaRecensioniServlet")
public class VisualizzaRecensioniServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private RecensioneDAO recensioneDAO;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VisualizzaRecensioniServlet() {
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
	//servlet che mostra tutte le recensioni
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("utenteLoggato") == null) {
			response.sendRedirect(request.getContextPath()+ "/LoginServlet");
			return;
		}
		
		try {
			List<RecensioneBean> listaRecensioni= recensioneDAO.doRetrieveByScoring();			
			request.setAttribute("recensioniUtenti", listaRecensioni);
			
			request.getRequestDispatcher("/WEB-INF/jsp/visualizzaRecensioni.jsp").forward(request, response);
			
		}catch(SQLException e) {
			e.printStackTrace();
            throw new ServletException("Errore durante il recupero delle recensioni dal database", e);
        
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
