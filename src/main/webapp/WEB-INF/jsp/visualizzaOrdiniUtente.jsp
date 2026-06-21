<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bean.UtenteBean" %>
<%@ page import="bean.OrdinazioneBean" %>
<%@ page import="java.util.List" %>
<%
    UtenteBean utente = (UtenteBean) session.getAttribute("utenteLoggato");
    if (utente == null) {
        response.sendRedirect(request.getContextPath() + "/LoginServlet");
        return;
    }

    List<OrdinazioneBean> ordiniUtente = (List<OrdinazioneBean>) request.getAttribute("ordiniUtente");
%>
    
<!DOCTYPE html>
<html lang="it">
<head>
     <meta charset="UTF-8">
     <meta name="viewport" content="width=device-width, initial-scale=1.0">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/css/componenti.css">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profilo.css">
     <title>I miei Ordini - <%= utente.getNome() %></title>
     
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
         .btn-elimina { background-color: #dc3545; color: white; }
     </style>
</head>
<body class="profilo-page-bg">
  
    <jsp:include page="/fragments/navbar.jsp" />
    
    <header class="profilo">
       <p class="utente-titolo">Lounge Personale • Ordini</p>
       <h1 class="utente-instestazione">
          <%= utente.getNome() %> <%= utente.getCognome() %>
       </h1>
       <p class="utente-informazioni">
          <%= utente.getNazionalita() %> • <%= utente.getNumeroOrdinazioni() %> ordini effettuati
       </p>
    </header>
    
    <main class="profilo-wrapper">

       <% if (request.getParameter("ordinazioneSalvata") != null) { %>
           <p class="success-message" style="text-align:center; margin-bottom:1rem;">
               Ordine #<%= request.getParameter("ordinazioneSalvata") %> registrato con successo.
               Apparirà nello storico del profilo quando sarà consegnato.
           </p>
       <% } %>
       
       <aside class="lista-recensioni">
          <p class="section-label">I tuoi ordini effettuati</p>
          <div class="recensioni-track">
              <% if (ordiniUtente != null && !ordiniUtente.isEmpty()) { %>
                  <% for (OrdinazioneBean ordinazione : ordiniUtente) { %>
                      <div class="recensione-card">
                          <p class="prodotto-name">Ordine #<%= ordinazione.getIdOrdinazione() %></p>
                          <p class="recensione-valutazione">Destinazione: <strong><%= ordinazione.getCitta() != null ? ordinazione.getCitta() : "Non specificata" %></strong></p>
                          <p class="recensione-descrizione">
                              <%= ordinazione.getIndirizzo() != null ? ordinazione.getIndirizzo() : "" %>
                              <%= ordinazione.getCivico() != null ? ordinazione.getCivico() : "" %>
                              <% if (ordinazione.getCap() != null && !ordinazione.getCap().isEmpty()) { %>
                                  — <%= ordinazione.getCap() %>
                              <% } %>
                          </p>
                          <p class="recensione-data">Data ordine: <%= ordinazione.getDataOrdinazione() %></p>
                          <p class="recensione-info">Importo: <strong>€ <%= ordinazione.getImporto() %></strong></p>
                          <p class="recensione-info">
                              Stato: <span class="badge-stato"><%= ordinazione.getStato() != null ? ordinazione.getStato() : "In attesa" %></span>
                          </p>
                          
                          <%-- Blocco condizionale per la recensione inserito --%>
                          <% if ("consegnato".equalsIgnoreCase(ordinazione.getStato())) { %>
                              <a class="scrivi-recensione" href="${pageContext.request.contextPath}/RegistraRecensioneServlet?idOrdinazione=<%= ordinazione.getIdOrdinazione() %>">
                                  Dicci cosa ne pensi
                              </a>
                          <% } %>
                          
                          <div class="recensione-azioni">
                              <a href="${pageContext.request.contextPath}/EliminaOrdinazioneServlet?idOrdinazione=<%= ordinazione.getIdOrdinazione() %>" class="btn-azione btn-elimina">Elimina</a>
                          </div>
                      </div>
                  <% } %>
              <% } else { %>
                  <p class="empty-msg">Non hai ancora effettuato nessun ordine.</p>
              <% } %>
          </div>
       </aside>

       <div class="tendina-wrapper">
          <ul>
            <li><a href="${pageContext.request.contextPath}/ModificaProfiloServlet">Modifica il tuo status</a></li>
            <li><a href="${pageContext.request.contextPath}/VisualizzaProfiloUtente">Torna al profilo principale</a></li>
            <li><a href="${pageContext.request.contextPath}/index.jsp">Torna alla luxury principale</a></li>
            <li><a href="${pageContext.request.contextPath}/VisualizzaRecensioniServlet">Visualizza recensioni</a></li>
          </ul>
       </div>

    </main>
       
    <footer></footer>
     
</body>
</html>