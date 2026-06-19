package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.ProdottoAdminDAO;
import bean.ProdottoBean;
import java.util.List;
import java.sql.SQLException;
import javax.servlet.annotation.WebServlet;

/**
 * Servlet che funge da controller del catalogo dei prodotti lato admin.
 * Serve esclusivamente a mostrare all'amministratore il catalogo completo 
 * di tutti i prodotti attualmente salvati nel database. 
 */

@WebServlet("/CatalogoAdminServlet")
public class CatalogoAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProdottoAdminDAO pad = new ProdottoAdminDAO();
		try {
			List<ProdottoBean> listaProdotti = pad.doRetriveAll();
			request.setAttribute("listaProdotti", listaProdotti); //si mette la lista nella request per portarla alla grafica.
			request.getRequestDispatcher("/WEB-INF/jsp/dashboardAdmin.jsp").forward(request, response); //reindirizzamento alla pagina di dashboard
			return;
		} catch (SQLException e) {
			// Se il database ha un problema, stampiamo l'errore nella console di Eclipse
			e.printStackTrace();
			// E mostriamo una pagina di errore all'utente
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore di connessione al database");
			return;
		}
	}
	
	// Se per sbaglio la chiamata arriva in POST, la deviamo sul GET per farle fare lo stesso percorso
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}