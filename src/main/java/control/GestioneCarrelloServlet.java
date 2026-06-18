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

import bean.CarrelloBean;
import bean.ProdottoBean;
import dao.ProdottoDAO;

@WebServlet("/GestioneCarrelloServlet")
public class GestioneCarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ProdottoDAO prodottoDAO;

    public GestioneCarrelloServlet() {
        super();
    }

	@Override
	public void init(ServletConfig config) throws ServletException {
		prodottoDAO = new ProdottoDAO();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		CarrelloBean carrello = (CarrelloBean) session.getAttribute("carrello");
		if (carrello == null) {
            carrello = new CarrelloBean();
            session.setAttribute("carrello", carrello);
        }

		String action = request.getParameter("action");
		if (action == null) {
			response.sendRedirect(request.getContextPath() + "/CarrelloServlet");
			return;
		}

		try {
		    switch (action) {
		    case "aggiungi":
		    	gestisciAggiunta(request, carrello);
		    	break;
		    case "rimuovi":
		    	gestisciRimozione(request, carrello);
		    	break;
		    case "aggiorna":
		    	gestisciAggiornamento(request, carrello);
		    	break;
		    default:
		    	break;
		    }
		} catch (NumberFormatException e) {
			throw new ServletException("Parametri del carrello non validi", e);
		} catch (SQLException e) {
			throw new ServletException("Errore nel recupero del prodotto", e);
		}

		response.sendRedirect(request.getContextPath() + "/CarrelloServlet");
	}

    private void gestisciAggiunta(HttpServletRequest request, CarrelloBean carrello) throws SQLException {
    	int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
    	int quantita = request.getParameter("quantita") != null ? Integer.parseInt(request.getParameter("quantita")) : 1;
    	ProdottoBean prodotto = prodottoDAO.doRetrieveById(idProdotto);
    	if (prodotto != null) {
    		carrello.aggiungiProdotto(prodotto, quantita);
    	}
    }

    private void gestisciRimozione(HttpServletRequest request, CarrelloBean carrello) {
    	int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
    	carrello.eliminaProdotto(idProdotto);
    }

    private void gestisciAggiornamento(HttpServletRequest request, CarrelloBean carrello) {
    	int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
    	int nuovaQuantita = Integer.parseInt(request.getParameter("nuovaQuantita"));
    	if (nuovaQuantita > 0) {
    		carrello.getElementi().stream()
    		.filter(item -> item.getProdotto().getIdProdotto() == idProdotto)
    		.findFirst().ifPresent(item -> item.setQuantita(nuovaQuantita));
    	} else {
    		carrello.eliminaProdotto(idProdotto);
    	}
   }
}
