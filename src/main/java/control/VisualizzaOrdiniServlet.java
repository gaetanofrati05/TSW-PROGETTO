package control;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
 * Servlet implementation class VisualizzaOrdiniServlet
 */
@WebServlet("/VisualizzaOrdiniServlet")
public class VisualizzaOrdiniServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private OrdinazioneDAO ordinazioneDAO;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VisualizzaOrdiniServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		ordinazioneDAO= new OrdinazioneDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("utenteLoggato") == null) {
			response.sendRedirect(request.getContextPath()+ "/LoginServlet");
			return;
		}
		UtenteBean utente = (UtenteBean) session.getAttribute("utenteLoggato");
		String emailUtente= utente.getEmail();
		try {
            // 3. Chiamiamo il metodo del DAO passando l'email per avere SOLO i suoi ordini
            ArrayList<OrdinazioneBean> listaOrdini = (ArrayList<OrdinazioneBean>) ordinazioneDAO.doStampaListaOrdinazione(emailUtente);

            request.setAttribute("ordiniUtente", listaOrdini);

           
            request.getRequestDispatcher("/WEB-INF/jsp/visualizzaOrdiniUtente.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Errore durante il recupero delle ordinazioni dal database", e);
        }
    }
		

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
