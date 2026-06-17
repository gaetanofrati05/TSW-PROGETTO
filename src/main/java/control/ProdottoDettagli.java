package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.ProdottoDAO;
import bean.ProdottoBean;

@WebServlet("/prodotto")
public class ProdottoDettagli extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String parametro= request.getParameter("id");
		int id= Integer.parseInt(parametro);
		ProdottoDAO dao= new ProdottoDAO();
		ProdottoBean prod=null;
		try {
			prod=dao.doRetrieveById(id);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		if(prod!=null)
		{
			request.setAttribute("prodotto", prod);
            request.getRequestDispatcher("/WEB-INF/jsp/prodotto.jsp").forward(request, response);
		}
		else
		{
			response.sendRedirect(request.getContextPath() + "/errors/404.jsp");
		}
	}
}