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

import bean.CarrelloBean;
import bean.ProdottoBean;
import dao.ProdottoDAO;

/**
 * Servlet implementation class GestioneCarrelloServlet
 */
@WebServlet("/GestioneCarrelloServlet")
public class GestioneCarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ProdottoDAO prodottoDAO;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneCarrelloServlet() {
        super();
        
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		prodottoDAO= new ProdottoDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/carrello.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session= request.getSession(true);
		CarrelloBean carrello= (CarrelloBean) session.getAttribute("carrello");
		if (carrello == null) {
            carrello = new CarrelloBean();
            session.setAttribute("carrello", carrello);
        }
		String action= request.getParameter("action"); //recupero l'azione che vuole compiere l'utente nell'url
		try {
		    switch(action) {
		    case "aggiungi":
		    	gestisciAggiunta(request, carrello);
		    	break;
		    case "rimuovi":
		    	gestisciRimozione(request, carrello);
		    	break;
		    case "aggiorna":
		    	gestisciAggiornamento(request, carrello);
		    	break;
		    }
			
		}catch(ServletException |NumberFormatException e) {
			e.printStackTrace();
			throw new ServletException ("Errore nella richiesta per il carrello");
		}
		response.sendRedirect(request.getContextPath()+ "/CarrelloServlet");
	}
    private void gestisciAggiunta(HttpServletRequest request, CarrelloBean carrello) {
    	int idProdotto= Integer.parseInt(request.getParameter("idProdotto"));
    	int quantita= request.getParameter("quantita")!=null ? Integer.parseInt(request.getParameter("quantita")):1;
    	ProdottoBean prodotto= prodottoDAO.doRetriveByKey(idProdotto); //Metodo che ha creato massimo per recuperare il prodotto dalla chiave
    	if(prodotto!=null) {
    		carrello.aggiungiProdotto(prodotto, quantita);
    	}
    }
    private void gestisciRimozione(HttpServletRequest request, CarrelloBean carrello) {
    	int idProdotto= Integer.parseInt(request.getParameter("idProdotto"));
    	carrello.eliminaProdotto(idProdotto);
    }
    private void gestisciAggiornamento(HttpServletRequest request, CarrelloBean carrello) {
    	int idProdotto= Integer.parseInt(request.getParameter("idProdotto"));
    	int nuovaQuantita= Integer.parseInt(request.getParameter("nuovaQuantita"));
    	if(nuovaQuantita>0) {
    		carrello.getElementi().stream().
    		filter(item->item.getProdotto().getIdProdotto()==idProdotto).
    		findFirst().ifPresent(item->item.setQuantita(nuovaQuantita));
    	}else {
    		carrello.eliminaProdotto(idProdotto);
    	}
   }
}
