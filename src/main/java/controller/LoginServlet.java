package control;
import dao.UtenteDAO;
import utils.PasswordEncryption;
import bean.UtenteBean;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UtenteDAO utenteDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email =request.getParameter("email");
		String password=request.getParameter("password");
		String passwordCifrata= PasswordEncryption.encrypt(password);
		try {
			UtenteBean utente= utenteDAO.doRetrieveByEmailAndPassword(email, passwordCifrata);
			if(utente!=null) {
				HttpSession session = request.getSession(true);
				session.setAttribute("utenteLoggato", utente);
				//controllo se l'utente è admin oppure no
				if(utente.getAdmin()) {
					System.out.println("Benvenuto admin" + utente.getNome());
					response.sendRedirect(request.getContextPath() +"/jsp/admin.jsp");				
				}else {
					System.out.println("Benvenuto"+ utente.getNome());
					response.sendRedirect(request.getContextPath()+ "/jsp/index.jsp");
				}
			}else {
				request.setAttribute("erroredilogin", "Email o password errate");
				request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
			}			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
