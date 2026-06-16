package control;
import dao.UtenteDAO;
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
import javax.servlet.http.HttpSession;
import bean.UtenteBean;

/**
 * Servlet implementation class ModificaProfiloServlet
 */
@WebServlet("/ModificaProfiloServlet")
public class ModificaProfiloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UtenteDAO utenteDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificaProfiloServlet() {
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
		//Verifico la sessione per controllare se l'utente è loggato o no
		HttpSession session= request.getSession(false);
		if(session==null || session.getAttribute("utenteLoggato")==null) {
			response.sendRedirect(request.getContextPath()+ "/LoginServlet");
			return;
		}
		
		UtenteBean utente = (UtenteBean) session.getAttribute("utenteLoggato");
		List<String>errori= new ArrayList<>();
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
		else if(!ValidazioneUtente.validateCognome(cognome)) {
			errori.add("Cognome non valido");
		}
		
		String dataNascitaStr = request.getParameter("dataNascita");
		java.util.Date dataNascita = null;
		try {
		    if (dataNascitaStr != null && !dataNascitaStr.trim().isEmpty()) {
		        java.time.LocalDate localDate = java.time.LocalDate.parse(dataNascitaStr);
		        dataNascita = java.sql.Date.valueOf(localDate); 
		    }
		} catch (java.time.format.DateTimeParseException e) {
		    errori.add("Formato data di nascita non valido. Usa il formato AAAA-MM-GG");
		}
		String nazionalita= request.getParameter("nazionalita");
		
		String prefisso= request.getParameter("prefisso");
		if(prefisso==null || prefisso.trim().isEmpty()) {
			errori.add("Il campo prefisso non può essere vuoto");
		}
		else if(!ValidazioneUtente.validatePrefisso(prefisso)) {
			errori.add("Prefisso non valido");
		}
		
		String cellulare= request.getParameter("cellulare");
		if(cellulare==null || cellulare.trim().isEmpty()) {
			errori.add("Il campo cellulare non può essere valido");
		}
		else if(!ValidazioneUtente.validateCellulare(cellulare)) {
			errori.add("Cellulare non valido");
		}
		
		if(!errori.isEmpty()) {
			request.setAttribute("errore", errori);
			request.getRequestDispatcher("/WEB-INF/jsp/profilo.jsp").forward(request, response);
			return;
		}
		try {
			
		    utente.setNome(nome);
			utente.setCognome(cognome);
			utente.setData(dataNascita);
			utente.setNazionalita(nazionalita);
			utente.setPrefisso(prefisso);
		    utente.setCellulare(cellulare);
		    utenteDAO.doUpdate(utente);
		    session.setAttribute("utenteLoggato",utente);
		response.sendRedirect(request.getContextPath() + "/ModificaProfiloServlet?modifica=true");
		}catch(SQLException e){
			e.printStackTrace();
	    	throw new ServletException("Errore nel database durante la modifica del profilo", e);
		}
	}
}
		
	


