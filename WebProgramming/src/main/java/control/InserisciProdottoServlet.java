package control;

import java.io.IOException;
import java.io.File;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.MultipartConfig;
import bean.ProdottoBean;
import javax.servlet.http.Part;
import dao.ProdottoAdminDAO;


//serve per validare esalvare i dati del nuovo prodotto nel DB
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
		String prezzo= request.getParameter("prezzo");
		String quantita= request.getParameter("quantita");
		String descrizione= request.getParameter("descrizione");
		Part filePart = request.getPart("immagine"); //recupera il file immagine con multipart/form-data
		
		//Validazione dei dati inseriti
		if(nome==null || nome.trim().isEmpty() || stile==null || stile.trim().isEmpty() || colore==null || colore.trim().isEmpty() || dimensioni==null || dimensioni.trim().isEmpty() || prezzo==null || prezzo.trim().isEmpty() || quantita==null || quantita.trim().isEmpty() || descrizione==null || descrizione.trim().isEmpty() || filePart==null || filePart.getSize()==0) {
			request.setAttribute("errore", "Tutti i campi sono obbligatori");
			RequestDispatcher rd = request.getRequestDispatcher("/InserisciProdottoAdminServlet");	
			rd.forward(request, response);
			return;
		}

        if(nome.length()>50) {
			request.setAttribute("errore", "Il nome del prodotto non può essere lungo più di 50 caratteri");
			RequestDispatcher rd = request.getRequestDispatcher("/InserisciProdottoAdminServlet");
			rd.forward(request, response);
			return;
		}
		if(stile.length()>10) {
			request.setAttribute("errore", "Il stile del prodotto non può essere lungo più di 10 caratteri");
			RequestDispatcher rd = request.getRequestDispatcher("/InserisciProdottoAdminServlet");
			rd.forward(request, response);
			return;
		}
		if(colore.length()>10) {
			request.setAttribute("errore", "Il colore del prodotto non può essere lungo più di 10 caratteri");
			RequestDispatcher rd = request.getRequestDispatcher("/InserisciProdottoAdminServlet");
			rd.forward(request, response);
			return;
		}
		if(dimensioni.length()>50) {
			request.setAttribute("errore", "Le dimensioni del prodotto non può essere lungo più di 50 caratteri");
			RequestDispatcher rd = request.getRequestDispatcher("/InserisciProdottoAdminServlet");
			rd.forward(request, response);
			return;
		}
		if(descrizione.length()>250) {
			request.setAttribute("errore", "La descrizione del prodotto non può essere lungo più di 250 caratteri");
			RequestDispatcher rd = request.getRequestDispatcher("/InserisciProdottoAdminServlet");
			rd.forward(request, response);
			return;
		}

		double prezzoNum;
		int quantitaNum;

		try {	//validazione del prezzo
    		prezzoNum = Double.parseDouble(prezzo.trim()); //conversione del prezzo in double
    		if (prezzoNum < 0) { //validazione del prezzo
        		request.setAttribute("errore", "Il prezzo non può essere negativo");
        		RequestDispatcher rd = request.getRequestDispatcher("/InserisciProdottoAdminServlet"); //reindirizzamento alla pagina di errore
        		rd.forward(request, response); //forwarding dell'errore
        		return;
    		}
		} catch (NumberFormatException e) { 
    		request.setAttribute("errore", "Prezzo non valido"); //impostazione dell'errore	
    		RequestDispatcher rd = request.getRequestDispatcher("/InserisciProdottoAdminServlet"); //reindirizzamento alla pagina di errore
    		rd.forward(request, response); //forwarding dell'errore
    		return;
		}

		try {	//validazione della quantità
			quantitaNum = Integer.parseInt(quantita.trim()); //conversione della quantità in int
			if (quantitaNum < 0) {
				request.setAttribute("errore", "La quantità non può essere negativa"); //impostazione dell'errore
				RequestDispatcher rd = request.getRequestDispatcher("/InserisciProdottoAdminServlet"); //reindirizzamento alla pagina di errore
				rd.forward(request, response); //forwarding dell'errore
				return;
			}
		} catch (NumberFormatException e) {
			request.setAttribute("errore", "Quantità non valida"); //impostazione dell'errore
			RequestDispatcher rd = request.getRequestDispatcher("/InserisciProdottoAdminServlet"); //reindirizzamento alla pagina di errore
			rd.forward(request, response); //forwarding dell'errore
			return;
		}

		//validazione dell'immagine
		String fileName = filePart.getSubmittedFileName(); //recupera il nome del file
		String contentType = filePart.getContentType(); //recupera il tipo di contenuto del file
		if(fileName==null || fileName.trim().isEmpty()) {
			request.setAttribute("errore", "Il nome dell'immagine non può essere vuoto"); //impostazione dell'errore
			RequestDispatcher rd = request.getRequestDispatcher("/InserisciProdottoAdminServlet"); //reindirizzamento alla pagina di errore
			rd.forward(request, response); //forwarding dell'errore
			return;
		}
		if(contentType==null || contentType.trim().isEmpty()) {  //validazione del tipo di contenuto
			request.setAttribute("errore", "Il tipo di contenuto dell'immagine non può essere vuoto"); //impostazione dell'errore
			RequestDispatcher rd = request.getRequestDispatcher("/InserisciProdottoAdminServlet"); //reindirizzamento alla pagina di errore
			rd.forward(request, response); //forwarding dell'errore
			return;
		}
		if(!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
			request.setAttribute("errore", "Il tipo di contenuto dell'immagine deve essere JPG, JPEG o PNG"); //impostazione dell'errore
			RequestDispatcher rd = request.getRequestDispatcher("/InserisciProdottoAdminServlet"); //reindirizzamento alla pagina di errore
			rd.forward(request, response); //forwarding dell'errore
			return;
		}
		String fileNameLower = fileName.toLowerCase(); //conversione del nome del file in minuscolo
		if(!fileNameLower.endsWith(".jpg") && !fileNameLower.endsWith(".jpeg") && !fileNameLower.endsWith(".png")) {  //validazione del tipo di file
			request.setAttribute("errore", "Il file immagine deve essere in formato JPG, JPEG o PNG"); //impostazione dell'errore
			RequestDispatcher rd = request.getRequestDispatcher("/InserisciProdottoAdminServlet"); //reindirizzamento alla pagina di errore
			rd.forward(request, response); //forwarding dell'errore
			return;
		}
		String uploadPath = getServletContext().getRealPath("") + "/img/prodotti";
		String pathImgDB = "img/prodotti/" + fileNameLower;
		if(pathImgDB.length()>255) {
			request.setAttribute("errore", "Il percorso dell'immagine non può essere lungo più di 255 caratteri");
			RequestDispatcher rd = request.getRequestDispatcher("/InserisciProdottoAdminServlet");
			rd.forward(request, response);
			return;
		}

		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists() && !uploadDir.mkdirs()) {
			request.setAttribute("errore", "Impossibile creare la cartella per il salvataggio dell'immagine.");
			RequestDispatcher rd = request.getRequestDispatcher("/InserisciProdottoAdminServlet");
			rd.forward(request, response);
			return;
		}

		String pathImgFile = uploadPath + "/" + fileNameLower;
		filePart.write(pathImgFile);

		//si impacchetta tutto nel ProdottoBean
		ProdottoBean nuovoProdotto = new ProdottoBean();
		nuovoProdotto.setNome(nome);
		nuovoProdotto.setStile(stile);
		nuovoProdotto.setColore(colore);
		nuovoProdotto.setDimensioni(dimensioni);
		nuovoProdotto.setPrezzo(prezzoNum);
		nuovoProdotto.setQuantita(quantitaNum);
		nuovoProdotto.setDescrizione(descrizione);
		nuovoProdotto.setImmagine(pathImgDB); //percorso del file nel DB
		ProdottoAdminDAO pad = new ProdottoAdminDAO();
		
		try {
			// si utilizza il dao per salvare il prodotto nel DB
			pad.doSave(nuovoProdotto);
			//si usa sendRedirect perchè forward effettua un doppo inserimento di prodotto
			response.sendRedirect(request.getContextPath()+"/CatalogoAdminServlet");
		}catch(SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Impossibile salvare il prodotto nel database.");
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Se qualcuno prova a chiamare la servlet in GET, lo mandiamo alla servlet che si  occuperà di prendere la jsp in modo sicuro
		response.sendRedirect(request.getContextPath()+"/InserisciProdottoAdminServlet");
	}

}