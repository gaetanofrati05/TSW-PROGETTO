import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bean.ProdottoBean;
import dao.ProdottoDAO;

@WebServlet("/ricerca/prodotti")
public class RicercaCatalogoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ProdottoBean> prodotti=null;
        String nome = request.getParameter("nome");
        ProdottoDAO dao = new ProdottoDAO();
        
        try{
            if(!(nome==null || nome.trim().isEmpty())){
                prodotti = dao.doRetrieveByName(nome.trim());
                 
            }
            else
                prodotti = dao.doRetrieveAll();   

            request.setAttribute("prodotti", prodotti);
                 request.getRequestDispatcher("/fragments/catalogo-grid.jsp").forward(request, response);
    }
    catch(SQLException e){
        e.printStackTrace();
    }
    }
}