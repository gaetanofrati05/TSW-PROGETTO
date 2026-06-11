package control;

import bean.ProdottoBean;
import dao.ProdottoDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;
import java.util.*;

@WebServlet("/catalogo")
public class CatalogoServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
    	try {
    	    ProdottoDAO dao = new ProdottoDAO();
    	    List<ProdottoBean> prodotti = dao.doRetrieveAll();
    	    request.setAttribute("prodotti", prodotti);
    	    request.getRequestDispatcher("/WEB-INF/jsp/catalogo.jsp").forward(request, response);
    	} catch (SQLException e) {
    	    e.printStackTrace();
    	    response.sendError(500, "Errore nel caricamento del catalogo");
    	}
    }
}