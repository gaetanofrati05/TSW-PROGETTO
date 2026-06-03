package control;
import java.io.IOException;
import java.sql.SQLException;
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
	     UtenteDAO utenteDAO= new UtenteDAO();
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
		String email= request.getParameter("email");
		String passwordLeggibile= request.getParameter("password");
		String nome= request.getParameter("nome");
		String cognome= request.getParameter("cognome");
		String dataNascitaStr = request.getParameter("dataNascita");
		java.util.Date dataNascita = null;
		if (dataNascitaStr != null && !dataNascitaStr.isEmpty()) {
		    java.time.LocalDate localDate = java.time.LocalDate.parse(dataNascitaStr);
		    // Converte il LocalDate nel java.util.Date che si aspetta il tuo Bean
		    dataNascita = java.sql.Date.valueOf(localDate); 
		}
		String nazionalita= request.getParameter("nazionalita");
		String prefisso= request.getParameter("prefisso");
		String cellulare= request.getParameter("cellulare");
		//qui vanno i controlli regex sulla registrazione per vedere se è corretta o meno
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
		response.sendRedirect(request.getContextPath()+ "Login?registrato=true");
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    	response.sendError(0); //qui devo creare la pagina di errore con XML
	    }
		
	}

}
