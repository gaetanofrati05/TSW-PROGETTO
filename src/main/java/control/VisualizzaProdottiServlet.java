package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ProdottoBean;
import dao.ProdottoAdminDAO;


@WebServlet("/VisualizzaProdottiServlet")
public class VisualizzaProdottiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		ProdottoAdminDAO pad = new ProdottoAdminDAO();

		try {
			//si prende la lista di tutti prodotti
			List<ProdottoBean> listaProdotti = pad.doRetriveAll();
			//si mette la lista nella request per portarla alla grafica.
			request.setAttribute("listaProdotti", listaProdotti);
			//si consegna la request contenente la lista di prodotti alla pagina incaricata di gestirli
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/profilo.jsp");
			//si passa request e response
			dispatcher.forward(request, response);
		}catch(SQLException e){
			// Se il database ha un problema, stampiamo l'errore nella console di Eclipse
			e.printStackTrace();
			// E mostriamo una pagina di errore all'utente
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore di connessione al database");
		}
	}
	
	// Se per sbaglio la chiamata arriva in POST, la deviamo sul GET per farle fare lo stesso percorso
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
}