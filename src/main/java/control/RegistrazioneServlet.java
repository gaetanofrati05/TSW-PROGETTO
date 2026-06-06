package control;
import utils.ValidazioneUtente;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.PasswordEncryption;
import bean.UtenteBean;
import dao.UtenteDAO;
/**
 * Servlet implementation class RegistrazioneServlet
 */
@WebServlet("/RegistrazioneServlet")
public class RegistrazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UtenteDAO utenteDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrazioneServlet() {
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
		request.getRequestDispatcher("/jsp/registrazione.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> errori= new ArrayList<>();
		String email= request.getParameter("email");
		
		if(email==null || email.trim().isEmpty()) {
			errori.add("Il campo email non può essere vuoto");
		}
		
		else if(!ValidazioneUtente.validateEmail(email)) {
			errori.add("Email non valida");
		}
		
		String passwordLeggibile= request.getParameter("password");
		if(passwordLeggibile==null || passwordLeggibile.trim().isEmpty()) {
			errori.add("Il campo password non può essere vuoto");
		}
		else if(!ValidazioneUtente.validatePassword(passwordLeggibile)) {
			errori.add("Password non valida");
		}
		
		String nome= request.getParameter("nome");
		if(nome==null || nome.trim().isEmpty()) {
			errori.add("Il campo nome utente non può essere vuoto");
		}
		else if(!ValidazioneUtente.validateNome(nome)) {
			errori.add("Nome non valido");
		}
		
		String cognome= request.getParameter("cognome");
		if(cognome==null || cognome.trim().isEmpty()) {
			errori.add("Il campo cognome non può essere vuoto");
		}
		if(!ValidazioneUtente.validateCognome(cognome)) {
			errori.add("Cognome non valido");
		}
		
		String dataNascitaStr = request.getParameter("dataNascita");
		java.util.Date dataNascita = null;
		if (dataNascitaStr != null && !dataNascitaStr.isEmpty()) {
		    java.time.LocalDate localDate = java.time.LocalDate.parse(dataNascitaStr);
		    // Converte il LocalDate nel java.util.Date che si aspetta il Bean
		    dataNascita = java.sql.Date.valueOf(localDate); 
		}
		String nazionalita= request.getParameter("nazionalita");
		
		String prefisso= request.getParameter("prefisso");
		if(!ValidazioneUtente.validatePrefisso(prefisso)) {
			errori.add("Prefisso non valido");
		}
		
		String cellulare= request.getParameter("cellulare");
		if(!ValidazioneUtente.validateCellulare(cellulare)) {
			errori.add("Cellulare non valido");
		}
		
		if(!errori.isEmpty()) {
			request.setAttribute("errore", errori);
			request.getRequestDispatcher("/jsp/registrazione.jsp").forward(request, response);
			return;
		}
		
		try {
		String passwordCifrata= PasswordEncryption.encrypt(passwordLeggibile);
		UtenteBean nuovoUtente= new UtenteBean();
		nuovoUtente.setEmail(email);
		nuovoUtente.setHashPassword(passwordCifrata);
		nuovoUtente.setNome(nome);
		nuovoUtente.setCognome(cognome);
		nuovoUtente.setData(dataNascita);
		nuovoUtente.setNazionalita(nazionalita);
		nuovoUtente.setPrefisso(prefisso);
		nuovoUtente.setCellulare(cellulare);
		nuovoUtente.setAdmin(false);
		utenteDAO.doSave(nuovoUtente);
		response.sendRedirect(request.getContextPath()+ "/LoginServlet?registrato=true");
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    	response.sendError(0); //qui devo creare la pagina di errore con XML
	    }
		
	}

}
