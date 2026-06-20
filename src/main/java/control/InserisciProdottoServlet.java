package control;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import bean.ProdottoBean;
import dao.ProdottoAdminDAO;

// serve per validare e salvare i dati del nuovo prodotto nel DB
@WebServlet("/InserisciProdottoServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB - soglia per salvare temporaneamente il file
    maxFileSize      = 1024 * 1024 * 5,   // 5MB - dimensione massima del file
    maxRequestSize   = 1024 * 1024 * 10   // 10MB - dimensione massima della richiesta
)
public class InserisciProdottoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // recupero i dati dal form
        String nome       = request.getParameter("nome");
        String stile      = request.getParameter("stile");
        String colore     = request.getParameter("colore");
        String dimensioni = request.getParameter("dimensioni");
        String prezzo     = request.getParameter("prezzo");
        String quantita   = request.getParameter("quantita");
        String descrizione = request.getParameter("descrizione");
        Part filePart     = request.getPart("immagine"); // file immagine con multipart/form-data

        // validazione: tutti i campi obbligatori
        if (nome == null || nome.trim().isEmpty() ||
            stile == null || stile.trim().isEmpty() ||
            colore == null || colore.trim().isEmpty() ||
            dimensioni == null || dimensioni.trim().isEmpty() ||
            prezzo == null || prezzo.trim().isEmpty() ||
            quantita == null || quantita.trim().isEmpty() ||
            descrizione == null || descrizione.trim().isEmpty() ||
            filePart == null || filePart.getSize() == 0) {

            inoltraConErrore(request, response, "Tutti i campi sono obbligatori");
            return;
        }

        // validazione lunghezze
        if (nome.length() > 50) {
            inoltraConErrore(request, response, "Il nome non può superare i 50 caratteri");
            return;
        }
        if (stile.length() > 10) {
            inoltraConErrore(request, response, "Lo stile non può superare i 10 caratteri");
            return;
        }
        if (colore.length() > 10) {
            inoltraConErrore(request, response, "Il colore non può superare i 10 caratteri");
            return;
        }
        if (dimensioni.length() > 50) {
            inoltraConErrore(request, response, "Le dimensioni non possono superare i 50 caratteri");
            return;
        }
        if (descrizione.length() > 250) {
            inoltraConErrore(request, response, "La descrizione non può superare i 250 caratteri");
            return;
        }

        // validazione e conversione prezzo
        double prezzoNum;
        try {
            prezzoNum = Double.parseDouble(prezzo.trim());
            if (prezzoNum < 0) {
                inoltraConErrore(request, response, "Il prezzo non può essere negativo");
                return;
            }
        } catch (NumberFormatException e) {
            inoltraConErrore(request, response, "Prezzo non valido");
            return;
        }

        // validazione e conversione quantità
        int quantitaNum;
        try {
            quantitaNum = Integer.parseInt(quantita.trim());
            if (quantitaNum < 0) {
                inoltraConErrore(request, response, "La quantità non può essere negativa");
                return;
            }
        } catch (NumberFormatException e) {
            inoltraConErrore(request, response, "Quantità non valida");
            return;
        }

        // validazione immagine
        String fileName    = filePart.getSubmittedFileName();
        String contentType = filePart.getContentType();

        if (fileName == null || fileName.trim().isEmpty()) {
            inoltraConErrore(request, response, "Il nome dell'immagine non può essere vuoto");
            return;
        }
        if (contentType == null ||
            (!contentType.equals("image/jpeg") && !contentType.equals("image/png"))) {
            inoltraConErrore(request, response, "L'immagine deve essere in formato JPG o PNG");
            return;
        }
        String fileNameLower = fileName.toLowerCase();
        if (!fileNameLower.endsWith(".jpg") && !fileNameLower.endsWith(".jpeg") && !fileNameLower.endsWith(".png")) {
            inoltraConErrore(request, response, "Il file deve essere in formato JPG, JPEG o PNG");
            return;
        }

        // salvataggio fisico del file sul server
        String uploadPath = getServletContext().getRealPath("") + "/img/prodotti";
        String pathImgDB  = "img/prodotti/" + fileNameLower;

        if (pathImgDB.length() > 255) {
            inoltraConErrore(request, response, "Il percorso dell'immagine è troppo lungo");
            return;
        }

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            inoltraConErrore(request, response, "Impossibile creare la cartella per il salvataggio dell'immagine");
            return;
        }
        filePart.write(uploadPath + "/" + fileNameLower);

        // impacchetta tutto nel bean
        ProdottoBean nuovoProdotto = new ProdottoBean();
        nuovoProdotto.setNome(nome);
        nuovoProdotto.setStile(stile);
        nuovoProdotto.setColore(colore);
        nuovoProdotto.setDimensioni(dimensioni);
        nuovoProdotto.setPrezzo(prezzoNum);
        nuovoProdotto.setQuantita(quantitaNum);
        nuovoProdotto.setDescrizione(descrizione);
        nuovoProdotto.setImmagine(pathImgDB); // salviamo il percorso relativo nel DB

        // salvataggio nel DB
        ProdottoAdminDAO pad = new ProdottoAdminDAO();
        try {
            pad.doSave(nuovoProdotto);
            // sendRedirect invece di forward per evitare il doppio inserimento se l'utente ricarica
            response.sendRedirect(request.getContextPath() + "/CatalogoAdminServlet");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Impossibile salvare il prodotto nel database.");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // se qualcuno chiama questa servlet in GET, lo rimanda alla pagina di inserimento
        response.sendRedirect(request.getContextPath() + "/InserisciProdottoAdminServlet");
    }

    // metodo di supporto per non ripetere sempre le stesse 3 righe di forward con errore
    private void inoltraConErrore(HttpServletRequest request, HttpServletResponse response, String messaggio)
            throws ServletException, IOException {
        request.setAttribute("errore", messaggio);
        RequestDispatcher rd = request.getRequestDispatcher("/InserisciProdottoAdminServlet");
        rd.forward(request, response);
    }
}