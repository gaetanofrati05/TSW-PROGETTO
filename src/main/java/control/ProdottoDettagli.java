package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ProdottoBean;
import bean.RecensioneBean;
import dao.ProdottoDAO;
import dao.RecensioneDAO;

@WebServlet("/prodotto")
public class ProdottoDettagli extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String parametro = request.getParameter("id");
		if (parametro == null || parametro.trim().isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/CatalogoServlet");
			return;
		}

		int id;
		try {
			id = Integer.parseInt(parametro);
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/CatalogoServlet");
			return;
		}

		ProdottoDAO dao = new ProdottoDAO();
		RecensioneDAO recensioneDAO = new RecensioneDAO();
		try {
			ProdottoBean prod = dao.doRetrieveById(id);
			if (prod == null) {
				response.sendRedirect(request.getContextPath() + "/errors/404.jsp");
				return;
			}

			List<RecensioneBean> listaRecensioni = recensioneDAO.doRetrieveByProductId(id);
			request.setAttribute("prodotto", prod);
			request.setAttribute("listaRecensioni", listaRecensioni);
			request.getRequestDispatcher("/WEB-INF/jsp/prodotto.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServletException("Errore durante il caricamento del prodotto", e);
		}
	}
}
