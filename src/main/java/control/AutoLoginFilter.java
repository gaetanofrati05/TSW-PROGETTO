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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import bean.UtenteBean;
import dao.UtenteDAO;

/**
 * Servlet Filter implementation class AutoLoginFilter
 */

@WebFilter("/*") 
public class AutoLoginFilter implements Filter {
    
    private UtenteDAO utenteDAO;

    public AutoLoginFilter() {
        super();
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        // Usando Filter standard, non serve super.init() e non crasha più all'avvio
        this.utenteDAO = new UtenteDAO();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httprequest = (HttpServletRequest) request;
        
        // Protezione contro i file statici per evitare rallentamenti
        String path = httprequest.getRequestURI().substring(httprequest.getContextPath().length());
        if (path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/images/") || path.startsWith("/fragments/")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httprequest.getSession(false);
        
        // Se l'utente non è in sessione, controlliamo i cookie
        if (session == null || session.getAttribute("utenteLoggato") == null) {
            Cookie[] cookies = httprequest.getCookies();
            String email = null;
            String passwordCifrata = null;
            
            if (cookies != null) {
                email = java.util.Arrays.stream(cookies)
                        .filter(c -> "ricordaEmail".equals(c.getName()))
                        .map(Cookie::getValue)
                        .findFirst()
                        .orElse(null);
                        
                passwordCifrata = java.util.Arrays.stream(cookies)
                        .filter(c -> "ricordaPassword".equals(c.getName()))
                        .map(Cookie::getValue)
                        .findFirst()
                        .orElse(null);
                
                if (email != null && passwordCifrata != null) {
                    try {
                        UtenteBean utente = utenteDAO.doRetrieveByEmailAndPassword(email, passwordCifrata);
                        if (utente != null) {
                            // Se la sessione non esisteva proprio, la creiamo al volo
                            if (session == null) {
                                session = httprequest.getSession(true);
                            }
                            session.setAttribute("utenteLoggato", utente);
                            System.out.println("Auto-Login riuscito per: " + utente.getEmail());
                        }
                    } catch (SQLException e) {
                        e.printStackTrace(); 
                    }
                }
            }
        }

        
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        
    }
}