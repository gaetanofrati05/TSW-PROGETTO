package control;

import dao.UtenteDAO;
import utils.PasswordEncryption;
import bean.UtenteBean;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UtenteDAO utenteDAO;

    @Override
    public void init() throws ServletException {
        utenteDAO = new UtenteDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<String> errori = new ArrayList<>();
        String email    = request.getParameter("email");
        String password = request.getParameter("password");

        // validazione campi vuoti
        if (email == null || email.trim().isEmpty()) {
            errori.add("Il campo email non può essere vuoto");
        }
        if (password == null || password.trim().isEmpty()) {
            errori.add("Il campo password non può essere vuoto");
        }
        if (!errori.isEmpty()) {
            request.setAttribute("errore", errori);
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            return;
        }

        email    = email.trim();
        password = password.trim();

        try {
            String passwordCifrata = PasswordEncryption.encrypt(password);
            UtenteBean utente = utenteDAO.doRetrieveByEmailAndPassword(email, passwordCifrata);

            if (utente != null) {
                // login riuscito: salva l'utente in sessione
                HttpSession session = request.getSession(true);
                session.setAttribute("utenteLoggato", utente);

                // redirect diverso in base al ruolo
                if (utente.getAdmin()) {
                    response.sendRedirect(request.getContextPath() + "/DashboardAdminServlet");
                } else {
                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                }
            } else {
                // credenziali errate
                request.setAttribute("erroredilogin", "Email o password errate");
                request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Errore nel database durante il login", e);
        }
    }
}