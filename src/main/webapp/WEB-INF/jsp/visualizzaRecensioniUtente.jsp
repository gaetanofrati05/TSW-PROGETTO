<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bean.UtenteBean" %>
<%@ page import="bean.RecensioneBean" %>
<%@ page import="java.util.List" %>
<%
    // Controllo Sessione Utente
    UtenteBean utente = (UtenteBean) session.getAttribute("utenteLoggato");
    if(utente == null){
        response.sendRedirect(request.getContextPath() + "/LoginServlet");
        return;
    }

    // Recupero della lista delle recensioni passata dalla Servlet
    List<RecensioneBean> listaRecensioni = (List<RecensioneBean>) request.getAttribute("listaRecensioni");
%>
    
<!DOCTYPE html>
<html>
<head>
     <meta charset="UTF-8">
     <meta name="viewport" content="width=device-width, initial-scale=1.0">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/css/componenti.css">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profilo.css">
     <title>Le mie Recensioni - <%= utente.getNome() %></title>
     
     <style>
         .recensione-card {
             background: white;
             padding: 15px;
             border-radius: 8px;
             margin-bottom: 15px;
             box-shadow: 0 2px 4px rgba(0,0,0,0.05);
         }
         .recensione-info {
             font-size: 13px;
             color: #777;
             margin-top: 5px;
         }
         .recensione-azioni {
             display: flex;
             gap: 10px;
             margin-top: 10px;
             justify-content: flex-end;
         }
         .btn-azione {
             padding: 6px 12px;
             border: none;
             border-radius: 4px;
             font-weight: bold;
             cursor: pointer;
             text-decoration: none;
             font-size: 13px;
         }
         .btn-modifica { background-color: #ffc107; color: #212529; }
         .btn-elimina { background-color: #dc3545; color: white; }
     </style>
</head>
<body class="profilo-page-bg">
  
    <jsp:include page="/fragments/navbar.jsp" />
    
    <header class="profilo">
       <p class="utente-titolo">Lounge Personale • Recensioni</p>
       <h1 class="utente-intestazione">
          <%= utente.getNome() %> <%= utente.getCognome() %>
       </h1>
       <p class="utente-informazioni">
          <%= utente.getNazionalita() != null ? utente.getNazionalita() : "" %> • <%= utente.getNumeroOrdinazioni() %> ordini effettuati
       </p>
    </header>
    
    <main class="profilo-wrapper">
       
       <aside class="lista-recensioni">
          <p class="section-label">Le tue recensioni pubblicate</p>
          <div class="recensioni-track">
              <% if(listaRecensioni != null && !listaRecensioni.isEmpty()) { %>
                  <% for(RecensioneBean recensione : listaRecensioni) { %>
                      <div class="recensione-card">
                          <p class="prodotto-name">
                             <%= (recensione.getProdotto() != null) ? recensione.getProdotto().getNome() : "Prodotto non specificato" %>
                          </p>
                          <p class="recensione-valutazione">Valutazione: <strong><%= recensione.getScoring() %>/5</strong></p>
                          <p class="recensione-descrizione">"<%= recensione.getDescrizione() %>"</p>
                          <p class="recensione-data">Pubblicata il: <%= recensione.getDataRecensione() %></p>
                          
                          <div class="recensione-azioni">
                              <a href="${pageContext.request.contextPath}/ModificaRecensioneServlet?id=<%= recensione.getIdRecensione() %>" class="btn-azione btn-modifica">Modifica</a>
                              <button type="button" class="btn-azione btn-elimina" onclick="confermaEliminazione('<%= recensione.getIdRecensione() %>')">Elimina</button>
                          </div>
                      </div>
                  <% } %>
              <% } else { %>
                  <p class="empty-msg">Non hai ancora scritto nessuna recensione.</p>
              <% } %>
          </div>
       </aside>

       <div class="tendina-wrapper">
          <ul>
            <li><a href="${pageContext.request.contextPath}/ModificaProfiloServlet">Modifica il tuo status</a></li>
            <li><a href="${pageContext.request.contextPath}/VisualizzaProfiloUtente">Torna al profilo principale</a></li>
            <li><a href="${pageContext.request.contextPath}/index.jsp">Torna alla luxury principale</a></li>
            <li><a href="${pageContext.request.contextPath}/VisualizzaOrdiniServlet">Le mie tavolette</a></li>
          </ul>
       </div>

    </main>
       
    <form id="formEliminaRecensione" action="${pageContext.request.contextPath}/EliminaRecensioneServlet" method="POST" style="display:none;">
        <input type="hidden" id="idRecensioneInput" name="idRecensione">
    </form>

    

   
     
</body>
</html>