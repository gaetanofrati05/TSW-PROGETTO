package control;

import java.io.IOException; // Assicurati che IOException sia importata
import java.sql.SQLException;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import bean.ProdottoBean;
import dao.ProdottoDAO;

@WebServlet("/CatalogoServlet")
public class CatalogoServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
        try {
            ProdottoDAO dao = new ProdottoDAO();
            List<ProdottoBean> prodotti = dao.doRetrieveAll();
            request.setAttribute("prodotti", prodotti);
            request.getRequestDispatcher("/catalogo.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace(); // MOSTRA L’ERRORE VERO IN CONSOLE
            throw new ServletException(e); // NON nascondere l’errore
        }

    }
}