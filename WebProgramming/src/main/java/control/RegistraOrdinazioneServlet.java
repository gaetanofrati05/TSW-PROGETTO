package control;
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
import bean.OrdinazioneBean;
import bean.UtenteBean;
import dao.OrdinazioneDAO;

/**
 * Servlet implementation class RegistraOrdinazioneServlet
 */
@WebServlet("/RegistraOrdinazioneServlet")
public class RegistraOrdinazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private OrdinazioneDAO ordinazioneDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistraOrdinazioneServlet() {
        super();
        
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		ordinazioneDAO= new OrdinazioneDAO();
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
		//controlliamo sempre se siamo in una sessione valida per l'utente
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("utenteLoggato") == null) {
			response.sendRedirect(request.getContextPath()+ "/LoginServlet");
			return;
		}
		UtenteBean utente= (UtenteBean) session.getAttribute("utenteLoggato");
		String citta= request.getParameter("citta");
		String importoStr= request.getParameter("importo");
		String indirizzo= request.getParameter("indirizzo");
		String civico= request.getParameter("civico");
		String cap= request.getParameter("cap");
		String statoOrdinazione= request.getParameter("statoOrdinazione");
		String idOrdinazioneStr= request.getParameter("idOrdinazione");
		String emailUtente= request.getParameter("emailUtente");
		List<String> errori = new ArrayList<>();
		//Validiamo sempre i campi inseriti
		
		if(citta==null || citta.trim().isEmpty()) {
			errori.add("Il campo città non può essere vuoto");
		}
		if(indirizzo==null || indirizzo.trim().isEmpty()) {
			errori.add("Il campo indirizzo non può essere vuoto");
		}
		if(civico==null || civico.trim().isEmpty()) {
			errori.add("Il campo civico non può essere vuoto");
		}
		if(cap==null || cap.trim().isEmpty()) {
			errori.add("Il campo cap non può essere vuoto");
		}
		if(statoOrdinazione==null || statoOrdinazione.trim().isEmpty()) {
			errori.add("Il campo stato non può essere vuoto");
		}
		double importo=0;
		try {
			importo= Double.parseDouble(importoStr);
			if(importo<0) {
				errori.add("L'importo non può essere negativo");
			}
		}catch(NumberFormatException e) {
				errori.add("Importo non valido");
		}
		if(!errori.isEmpty()) {
			request.setAttribute("errore", errori);
			request.getRequestDispatcher(request.getContextPath()+ "/RegistraOrdinazioneServlet").forward(request,response);
			return;
		}
		
		try {
			int idOrdinazione= Integer.parseInt(idOrdinazioneStr);
			//Costruiamo il bean di ordinazione
			OrdinazioneBean ordinazione= new OrdinazioneBean();
			ordinazione.setUtente(utente);
			utente.setEmail(emailUtente);
			ordinazione.setCitta(citta);
			ordinazione.setCap(cap);
			ordinazione.setIdOrdinazione(idOrdinazione);
			ordinazione.setImporto(importo);
			ordinazione.setIndirizzo(indirizzo);
			ordinazione.setStato(statoOrdinazione);
			ordinazione.setCivico(civico);
			ordinazione.setDataOrdinazione(new java.sql.Date(System.currentTimeMillis()));
			ordinazioneDAO.doCreateOrdinazione(ordinazione);
			response.sendRedirect(request.getContextPath() + "/WEB-INF/jsp/dettaglioOrdinazione.jsp?ordinazioneSalvata=true");
			
		}catch(SQLException | NumberFormatException e) {
			e.printStackTrace();
			throw new ServletException("Errore nel database durante il salvataggio della ordinazione", e);
		}
		
	}

}
