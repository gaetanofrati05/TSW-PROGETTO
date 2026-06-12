package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ProdottoBean;
import dao.ProdottoAdminDAO;


//serve per salvare i dati del nuovo prodotto nel DB
@WebServlet("/InserisciProdottoServlet")
public class InserisciProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//si recuperano i  dati inseriti dal form HTML
		String nome= request.getParameter("nome");
		String stile= request.getParameter("stile");
		String colore= request.getParameter("colore");
		String dimensioni= request.getParameter("dimensioni");
		float prezzo= Float.parseFloat(request.getParameter("prezzo"));
		int quantita= Integer.parseInt(request.getParameter("quantita"));
		String descrizione= request.getParameter("descrizione");
		String immagine= request.getParameter("immagine");
		//si impacchetta tutto nel ProdottoBean
		ProdottoBean nuovoProdotto = new ProdottoBean();
		nuovoProdotto.setNome(nome);
		nuovoProdotto.setStile(stile);
		nuovoProdotto.setColore(colore);
		nuovoProdotto.setDimensioni(dimensioni);
		nuovoProdotto.setPrezzo(prezzo);
		nuovoProdotto.setQuantita(quantita);
		nuovoProdotto.setDescrizione(descrizione);
		nuovoProdotto.setImmagine(immagine);
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
