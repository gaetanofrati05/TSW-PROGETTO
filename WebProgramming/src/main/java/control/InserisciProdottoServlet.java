package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.MultipartConfig;
import bean.ProdottoBean;
import dao.ProdottoAdminDAO;


//serve per salvare i dati del nuovo prodotto nel DB
@WebServlet("/InserisciProdottoServlet")
@MultipartConfig( //configurazione per il multipart/form-data
fileSizeThreshold = 1024 * 1024 * 2, // 2MB - soglia per salvare temporaneamente il file
maxFileSize = 1024 * 1024 * 5, // 5MB - dimensione massima del file
maxRequestSize = 1024 * 1024 * 10) // 10MB - dimensione massima della richiesta	
public class InserisciProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		//si recuperano i  dati inseriti dal form HTML
		String nome= request.getParameter("nome");
		String stile= request.getParameter("stile");
		String colore= request.getParameter("colore");
		String dimensioni= request.getParameter("dimensioni");
		double prezzo= Double.parseDouble(request.getParameter("prezzo"));
		int quantita= Integer.parseInt(request.getParameter("quantita"));
		String descrizione= request.getParameter("descrizione");
		Part filePart = request.getPart("immagine"); //recupera il file immagine con 
		String fileName = filePart.getSubmittedFileName(); //recupera il nome del file
		String uploadPath = getServletContext().getRealPath("")+"/img/prodotti"; //percorso di salvataggio del file
		String pathImgDB = "img/prodotti/"+fileName; //percorso di salvataggio del file nel DB
		String pathImgFile = uploadPath+"/"+fileName; //percorso del file nel file system
		filePart.write(pathImgFile); //salva il file nel file system
		
		//Validazione dei dati inseriti
		if(nome==null || nome.trim().isEmpty() || stile==null || stile.trim().isEmpty() || colore==null || colore.trim().isEmpty() || dimensioni==null || dimensioni.trim().isEmpty() || prezzo==null || quantita.trim().isEmpty() || descrizione==null || descrizione.trim().isEmpty()) {
			request.setAttribute("errore", "Tutti i campi sono obbligatori");
			RequestDispatcher rd = request.getRequestDispatcher("/dashboardAdminServlet");
			rd.forward(request, response);
			return;
		}

		if(prezzo<0 || quantita<0) {
			request.setAttribute("errore", "non può essere negativo");
			RequestDispatcher rd = request.getRequestDispatcher("/dashboardAdminServlet");
			rd.forward(request, response);
			return;
		}
		//si impacchetta tutto nel ProdottoBean
		ProdottoBean nuovoProdotto = new ProdottoBean();
		nuovoProdotto.setNome(nome);
		nuovoProdotto.setStile(stile);
		nuovoProdotto.setColore(colore);
		nuovoProdotto.setDimensioni(dimensioni);
		nuovoProdotto.setPrezzo(prezzo);
		nuovoProdotto.setQuantita(quantita);
		nuovoProdotto.setDescrizione(descrizione);
		nuovoProdotto.setImmagine(pathImgDB); //percorso del file nel DB
		ProdottoAdminDAO pad = new ProdottoAdminDAO();
		
		try {
			// si utilizza il dao per salvare il prodotto nel DB
			pad.doSave(nuovoProdotto);
			//si usa sendRedirect perchè forward effettua un doppo inserimento di prodotto
			response.sendRedirect(request.getContextPath()+"/VisualizzaProdottiServlet");
		}catch(SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Impossibile salvare il prodotto nel database.");
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Se qualcuno prova a chiamare la servlet in GET, lo mandiamo alla servlet che si  occuperà di prendere la jsp in modo sicuro
		response.sendRedirect(request.getContextPath()+"/DashboardAdminServlet");
	}

}
