package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet che funge da controller della dashboard amministrativa.
 * Gestisce l'accesso sicuro alla pagina JSP in WEB-INF e prepara eventuali dati
 * da mostrare all'amministratore prima del forward.
 */

@WebServlet("/DashboardAdminServlet")
public class DashboardAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//si prende la richiesta e si va add aprire la WEB-INF per mostrare all'amministratore la pagina del pannello di controllo
		//si richiede un dispatcher per raggiungere la jsp
		//con forward si inoltre la richiesta alla JSP con la stessa request e scrive l'HTML nell response
		request.getRequestDispatcher("/WEB-INF/jsp/dashboardAdmin.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//se si verifica un post si mostra lo stesso la dashboard
		doGet(request, response);
	}

}
