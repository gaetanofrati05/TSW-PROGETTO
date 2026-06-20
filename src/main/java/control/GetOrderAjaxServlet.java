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
import bean.OrdinazioneBean;
import bean.RecensioneBean;
import bean.UtenteBean;
import dao.OrdinazioneDAO;
import dao.RecensioneDAO;

/**
 * Servlet implementation class GetOrderAjaxServlet
 */
@WebServlet("/GetOrderAjaxServlet")
public class GetOrderAjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private OrdinazioneDAO ordinazioneDAO;
    private RecensioneDAO recensioneDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetOrderAjaxServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		ordinazioneDAO= new OrdinazioneDAO();
		recensioneDAO= new RecensioneDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
	    UtenteBean utente = (UtenteBean) session.getAttribute("utenteLoggato");
	    
	    if (utente == null) {
	        response.sendRedirect(request.getContextPath()+ "/LoginServlet");
	        return;
	    }
	    //Leggiamo entrambi i filtri per capire come ordinare 
	    String filtroRecensioni= request.getParameter("sortRecensioni");
	    String filtroOrdini= request.getParameter("sortOrdinazioni");
	    
	    
	    try {
	    	//caso in cui filtriamo solo gli ordini 
	        if(filtroOrdini!=null) {
	    	List<OrdinazioneBean> listaOrdinazioni= ordinazioneDAO.doRetrieveByEmailWithOrder(utente.getEmail(), filtroOrdini);
	    	request.setAttribute("listaOrdinazioni", listaOrdinazioni);
	    	request.getRequestDispatcher("/WEB-INF/jsp/comp_ordini.jsp").forward(request, response);
	    	return;
	        }
	        //caso in cui filtriamo solo le recensioni
	        if(filtroRecensioni!=null) {
	    	List<RecensioneBean> listaRecensioni= recensioneDAO.doRetrieveByEmailWithOrder(utente.getEmail(), filtroRecensioni);
	    	request.setAttribute("listaRecensioni", listaRecensioni);
	    	request.getRequestDispatcher("/WEB-INF/jsp/comp_recensioni.jsp").forward(request, response);
	    	return;
	        }
	    	//se nessun evento AJAX semplicemente limitiamoci a stampare gli ordinamenti di base 
	        List<OrdinazioneBean> ordiniDefault = ordinazioneDAO.doRetrieveByEmailWithOrder(utente.getEmail(), null);
	        List<RecensioneBean> recensioniDefault = recensioneDAO.doRetrieveByEmailWithOrder(utente.getEmail(), null);
	        
	        request.setAttribute("listaOrdinazioni", ordiniDefault);
	        request.setAttribute("listaRecensioni", recensioniDefault);
	        request.getRequestDispatcher(request.getContextPath()+"/LoginServlet").forward(request, response);
	    }catch(SQLException e) {
	    	e.printStackTrace();
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
