package control;
import bean.ProdottoBean;
import bean.OrdinazioneBean;
import bean.UtenteBean;
import dao.OrdinazioneDAO;
import dao.ProdottoDAO;
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



/**
 * Servlet implementation class VisualizzaProfiloUtente
 */
@WebServlet("/VisualizzaProfiloUtente")
public class VisualizzaProfiloUtente extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private OrdinazioneDAO ordinazioneDAO;
    private ProdottoDAO prodottoDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VisualizzaProfiloUtente() {
        super();
       
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		ordinazioneDAO= new OrdinazioneDAO();
		//prodottoDAO= new ProdottoDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//recuperiamo la sessione e verifichiamo sempre se l'utente è loggato
		HttpSession session= request.getSession(false);
		if(session==null || session.getAttribute("utenteLoggato")==null){
			response.sendRedirect(request.getContextPath()+ "/LoginServlet");
			return;
		}
		UtenteBean utente= (UtenteBean) session.getAttribute("utenteLoggato");
		String email= utente.getEmail();
		try {
			List<OrdinazioneBean> listaOrdinazioni= ordinazioneDAO.doStampaListaOrdinazione(email);
		    List<ProdottoBean> listaProdotti= prodottoDAO.doStampaListaProdotti(email); //metodo che ha creato massimo
			request.setAttribute("listaOrdinazioni", listaOrdinazioni);
			request.setAttribute("listaProdotti", listaProdotti);
			request.getRequestDispatcher("/WEB-INF/jsp/profilo.jsp").forward(request,response);
		} catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Errore durante il caricamento dell'utente dal database", e);
        }
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
