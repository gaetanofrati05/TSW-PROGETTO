package control;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import bean.UtenteBean;
import javax.servlet.http.HttpServletResponse;

/* Servlet per verificare che l'utente sia loggato e sia admin*/
<<<<<<< HEAD:src/main/java/control/AdminAuthFilter.java
@WebFilter(urlPatterns = {
	"/CatalogoAdminServlet",
	"/OrdiniAdminServlet",
	"/GestioneOrdiniAdminServlet",
	"/ModificaOrdineAdminServlet",
	"/EliminaOrdineAdminServlet",
	"/InserisciProdottoServlet",
	"/ModificaProdottoServlet",
	"/EliminaProdottoServlet",
	"/VisualizzaProdottiServlet",
	"/GetOrderAjaxServlet",
	"/DashboardAdminServlet"
})
=======
@WebFilter(urlPatterns = {})
>>>>>>> origin/massimo:WebProgramming/src/main/java/control/AdminAuthFilter.java
public class AdminAuthFilter extends HttpFilter implements Filter {
    private static final long serialVersionUID = 1L;

	public void init(FilterConfig config) throws ServletException {
        
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    
    HttpServletRequest req=(HttpServletRequest) request;
    HttpServletResponse res=(HttpServletResponse) response;
    HttpSession session = req.getSession(false);

    if(session ==  null || session.getAttribute("utenteLoggato") == null || !((UtenteBean) session.getAttribute("utenteLoggato")).getAdmin()){
        res.sendRedirect(req.getContextPath()+"/LoginServlet");
        return;
    }

    chain.doFilter(request, response);
    }

    public void destroy(){

    }
}