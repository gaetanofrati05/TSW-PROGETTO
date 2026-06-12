package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.UtenteBean;
import dao.UtenteDAO;

/**
 * Servlet Filter implementation class AutoLoginFilter
 */
@WebFilter("/AutoLoginFilter")
public class AutoLoginFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;
       private UtenteDAO utenteDAO;
    /**
     * @see HttpFilter#HttpFilter()
     */
    public AutoLoginFilter() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void init(FilterConfig fConfig) throws ServletException {
		utenteDAO=new UtenteDAO();
	}
	/**
	 * @see Filter#destroy()
	 */
	

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httprequest= (HttpServletRequest) request;
		HttpSession session= httprequest.getSession(true);
		if (session.getAttribute("utenteLoggato") == null) {
            Cookie[] cookies = httprequest.getCookies();
            String email = null;
            String passwordCifrata = null;
            if(cookies!=null) {
            	//cerca il valore della password ed email ed estrai
            	email= java.util.Arrays.stream(cookies)
            			.filter(c->"ricordaEmail".equals(c.getName())).map(Cookie::getValue).findFirst().orElse(null);
            	passwordCifrata= java.util.Arrays.stream(cookies)
            			.filter(c->"ricordaPassword".equals(c.getName())).map(Cookie::getValue).findFirst().orElse(null);
            	// Se abbiamo trovato entrambi i cookie, proviamo il login automatico
                if (email != null && passwordCifrata != null) {
                    try {
                        UtenteBean utente = utenteDAO.doRetrieveByEmailAndPassword(email, passwordCifrata);
                        if (utente != null) {
                            // Ricreiamo la sessione automaticamente
                            session.setAttribute("utenteLoggato", utente);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace(); // Log dell'errore ma non blocchiamo l'applicazione
                    }
                }
            
              }
            }
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}
	
	
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	

}
