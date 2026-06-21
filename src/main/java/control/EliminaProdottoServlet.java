package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProdottoAdminDAO;

@WebServlet("/EliminaProdottoServlet")
public class EliminaProdottoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String idProdottoStr = request.getParameter("idProdotto");

        if (idProdottoStr != null && !idProdottoStr.trim().isEmpty()) {
            try {
                int idProdotto = Integer.parseInt(idProdottoStr.trim());
                ProdottoAdminDAO prodottoAdminDAO = new ProdottoAdminDAO();
                prodottoAdminDAO.doDelete(idProdotto);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect(request.getContextPath() + "/CatalogoAdminServlet");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
