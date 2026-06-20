package control;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.OrdinazioneBean;
import dao.OrdineAdminDAO;

/**
 * Servlet che funge da controller della gestione degli ordini lato admin.
 * Serve esclusivamente a mostrare all'amministratore la lista di tutte le ordinazioni attualmente salvate nel database.
 */

@WebServlet("/OrdiniAdminServlet")
public class OrdiniAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrdineAdminDAO ordineAdminDAO = new OrdineAdminDAO();
		try {
			List<OrdinazioneBean> listaOrdinazioni = ordineAdminDAO.doRetrieveAll();
			request.setAttribute("listaOrdinazioni", listaOrdinazioni);
			request.getRequestDispatcher("/WEB-INF/jsp/ordiniAdmin.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
            request.setAttribute("errore", "Errore durante il recupero delle ordinazioni dal database");
			request.getRequestDispatcher("/WEB-INF/jsp/ordiniAdmin.jsp").forward(request, response);
		}
	}   

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}