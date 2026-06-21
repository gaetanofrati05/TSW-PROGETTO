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
import dao.ProdottoDAO;

@WebServlet("/ricerca/prodotti")
public class RicercaCatalogoServlet extends HttpServlet {

    private static final int MAX_SUGGERIMENTI = 5;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        ProdottoDAO dao = new ProdottoDAO();

        try {
            List<ProdottoBean> prodotti;

            if (nome == null || nome.trim().isEmpty()) {
                prodotti = dao.doRetrieveAll();
            } else {
                prodotti = dao.doRetrieveByName(nome.trim());
            }

            // Se soloNomi=true, rispondiamo con JSON (usato dai suggerimenti)
            if ("true".equals(request.getParameter("soloNomi"))) {
                scriviSuggerimentiJson(response, prodotti);
                return;
            }

            // Altrimenti rispondiamo con HTML (griglia prodotti)
            request.setAttribute("prodotti", prodotti);
            request.getRequestDispatcher("/fragments/catalogo-grid.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void scriviSuggerimentiJson(HttpServletResponse response, List<ProdottoBean> prodotti) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        StringBuilder json = new StringBuilder("[");
        int count = 0;

        for (ProdottoBean prodotto : prodotti) {
            if (count >= MAX_SUGGERIMENTI) {
                break;
            }
            if (count > 0) {
                json.append(",");
            }
            json.append("{\"id\":")
                .append(prodotto.getIdProdotto())
                .append(",\"nome\":\"")
                .append(escapeJson(prodotto.getNome()))
                .append("\"}");
            count++;
        }

        json.append("]");
        response.getWriter().write(json.toString());
    }

    private String escapeJson(String testo) {
        if (testo == null) {
            return "";
        }
        return testo.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
