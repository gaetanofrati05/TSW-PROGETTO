package control;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.UtenteBean;
import dao.UtenteDAO;
import utils.ValidazioneUtente;

/**
 * Servlet implementation class ModificaPasswordServlet
 */
@WebServlet("/ModificaPasswordServlet")
public class ModificaPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UtenteDAO utenteDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificaPasswordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		utenteDAO= new UtenteDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/profilo.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session= request.getSession(false);
		if(session==null || session.getAttribute("utenteLoggato")==null) {
			response.sendRedirect(request.getContextPath()+ "/LoginServlet");
			return;
		}
		UtenteBean utente = (UtenteBean) session.getAttribute("utenteLoggato");
		String nuovaPassword= request.getParameter("nuovaPassword");
		String confermaPassword= request.getParameter("confermaPassword");
		java.util.List<String> errori = new java.util.ArrayList<>();
	    
	    // 2. VALIDAZIONE
	    if(nuovaPassword == null || nuovaPassword.trim().isEmpty()) {
	        errori.add("La nuova password non può essere vuota");
	    }else if(!ValidazioneUtente.validatePassword(nuovaPassword)) {
	    	errori.add("La nuova password non rispecchia i requisiti");
	    }
	    if(confermaPassword == null || confermaPassword.trim().isEmpty()) {
	        errori.add("Il campo di conferma password non può essere vuoto");
	    }
	    if(nuovaPassword != null && !nuovaPassword.equals(confermaPassword)) {
	        errori.add("Le due password non coincidono");
	    }
	    if(!errori.isEmpty()) {
	        request.setAttribute("errore", errori);
	        request.getRequestDispatcher("/WEB-INF/jsp/profilo.jsp").forward(request, response);
	        return;
	    }
		try {
			String passwordCifrata= utils.PasswordEncryption.encrypt(nuovaPassword.trim());
			utenteDAO.doUpdatePassword(utente.getEmail(), passwordCifrata);
			utente.setHashPassword(passwordCifrata);
			session.setAttribute("utenteLoggato", utente);
			response.sendRedirect(request.getContextPath() + "/ModificaProfiloServlet?passwordVariata=true");
		}catch(SQLException e) {
			e.printStackTrace();
			throw new ServletException("Errore nel database durante il cambio password", e);
		}
		
	}

}
