package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.OrdineAdminDAO;

@WebServlet("/EliminaOrdineAdminServlet")
public class EliminaOrdineAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idOrdinazioneStr = request.getParameter("idOrdinazione");
		
		if(idOrdinazioneStr != null && !idOrdinazioneStr.trim().isEmpty()) {
			try {
				int idOrdinazione = Integer.parseInt(idOrdinazioneStr);
				OrdineAdminDAO ordineAdminDAO = new OrdineAdminDAO();
				ordineAdminDAO.doDeleteOrdinazione(idOrdinazione);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		response.sendRedirect(request.getContextPath() + "/OrdiniAdminServlet");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}