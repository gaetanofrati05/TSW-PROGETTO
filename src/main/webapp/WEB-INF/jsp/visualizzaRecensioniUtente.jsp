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

          <% if ("true".equals(request.getParameter("recensioneModificata"))) { %>
              <p class="recensione-avviso-successo">Recensione aggiornata con successo.</p>
          <% } else if ("true".equals(request.getParameter("recensioneEliminata"))) { %>
              <p class="recensione-avviso-successo">Recensione eliminata con successo.</p>
          <% } else if ("true".equals(request.getParameter("recensioneSalvata"))) { %>
              <p class="recensione-avviso-successo">Recensione pubblicata con successo.</p>
          <% } else if ("NonAutorizzato".equals(request.getParameter("errore"))) { %>
              <p class="recensione-avviso-errore">Non sei autorizzato a modificare questa recensione.</p>
          <% } else if ("RecensioneNonTrovata".equals(request.getParameter("errore"))) { %>
              <p class="recensione-avviso-errore">Recensione non trovata.</p>
          <% } else if ("ProdottoNonTrovato".equals(request.getParameter("errore"))) { %>
              <p class="recensione-avviso-errore">Prodotto associato alla recensione non trovato.</p>
          <% } %>

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

    <script>
        function confermaEliminazione(idRecensione) {
            if (confirm("Sei sicuro di voler eliminare questa recensione?")) {
                document.getElementById("idRecensioneInput").value = idRecensione;
                document.getElementById("formEliminaRecensione").submit();
            }
        }
    </script>

    <jsp:include page="/fragments/footer.jsp" />

</body>
</html>