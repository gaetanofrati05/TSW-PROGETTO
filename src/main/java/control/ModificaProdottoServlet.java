package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProdottoAdminDAO;
import bean.ProdottoBean;

@WebServlet("/ModificaProdottoServlet")
public class ModificaProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Per ora gestiamo solo un alert/errore, non c'è una JSP dedicata.
		// Reindirizziamo al catalogo con un parametro per dire che la feature non è ancora implementata graficamente.
		response.sendRedirect(request.getContextPath() + "/CatalogoAdminServlet?errore=modifica_non_implementata");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}