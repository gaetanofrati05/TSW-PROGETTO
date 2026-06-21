package control;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bean.UtenteBean;
import dao.UtenteDAO;

@WebServlet("/controllo/email")
public class ControlloEmail extends HttpServlet {

    private UtenteDAO utenteDAO;

    public void init() throws ServletException {
        utenteDAO = new UtenteDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain;charset=UTF-8");

        String email = request.getParameter("email");

        try {
            if (email != null && !email.trim().isEmpty()) {
                UtenteBean utente = utenteDAO.doRetrieveByEmail(email.trim());
                if (utente != null) {
                    response.getWriter().write("Email già in uso");
                } else {
                    response.getWriter().write("");
                }
            } else {
                response.getWriter().write("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Errore nel controllo email", e);
        }
    }
}