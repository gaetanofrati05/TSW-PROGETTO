<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bean.UtenteBean" %>
<%@ page import="java.util.List" %>
<%@ page import="bean.ProdottoBean" %>
<%
    UtenteBean utente = (UtenteBean) session.getAttribute("utenteLoggato");
    if(utente == null){
        response.sendRedirect(request.getContextPath() + "/LoginServlet");
        return;
    }
    List<ProdottoBean> listaProdotti = (List<ProdottoBean>) request.getAttribute("listaProdotti");
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
     <title>Profilo - Utente <%=utente.getNome() %></title>
</head>
<body class="profilo-page-bg">
  
    <jsp:include page="/fragments/navbar.jsp" />
    
    <header class="profilo">
       <p class="utente-titolo">Lounge Personale</p>
       <h1 class="utente-intestazione">
          <%= utente.getNome() %> <%= utente.getCognome() %>
       </h1>
       <p class="utente-informazioni">
          <%= utente.getNazionalita() %> • <%= utente.getNumeroOrdinazioni() %> ordini effettuati
       </p>
    </header>
    
    <main class="profilo-wrapper">
       
       <aside class="lista-prodotti">
          <div class="prodotti-track">
              <% if(listaProdotti != null && !listaProdotti.isEmpty()) { %>
                  <% for(ProdottoBean prodotto : listaProdotti) { %>
                      <div class="prodotto-card">
                          <p class="prodotto-name"><%= prodotto.getNome() %></p>
                          <p class="prodotto-stile"><%= prodotto.getStile() %></p>
                          <p class="prodotto-prezzo">€ <%= prodotto.getPrezzo() %></p>
                          <p class="prodotto-quantita">Q.tà: <%= prodotto.getQuantita() %></p>
                          <p class="prodotto-immagine"><%=prodotto.getImmagine() %></p>
                      </div>
                  <% } %>
              <% } else { %>
                  <p class="empty-msg">Nessun prodotto disponibile</p>
              <% } %>
          </div>
       </aside>

       <section class="storico-ordini">
          <div class="section-header">
              <p class="section-label">Storico ordini</p>
              <div class="filtri-ordini">
                  <label for="orderBydropdown">Ordina per:</label>
                  <select id="orderBydropdown" onchange="filtraOrdini(this.value)">
                      <option value="">Più recenti</option>
                      <option value="date_asc">Più vecchi</option>
                      <option value="importo_desc">Importo decrescente</option>
                      <option value="importo_asc">Importo crescente</option>
                  </select>
              </div>
          </div>

          <table class="table-ordini">
              <thead>
                 <tr>
                    <th>N° ordine</th>
                    <th>Città</th>
                    <th>Data</th>
                    <th>Totale</th>
                    <th>Stato</th>
                 </tr>
              </thead>
              <tbody id="corpo-tabella-ordini">
                 <jsp:include page="comp_ordini.jsp" />
              </tbody>
          </table>
       </section>

       <section class="storico-recensioni">
          <div class="section-header">
              <p class="section-label">La nostra qualità</p>
              <div class="filtri-recensioni">
                  <label for="recensioniBydropdown">Ordina per:</label>
                  <select id="recensioniBydropdown" onchange="filtraRecensioni(this.value)">
                      <option value="">Più recenti</option>
                      <option value="date_asc">Più vecchie</option>
                      <option value="scoring_desc">Valutazione più alta</option>
                      <option value="scoring_asc">Valutazione più bassa</option>
                  </select>
              </div>
          </div>

          <div class="recensioni-track" id="corpo-recensioni">
              <jsp:include page="comp_recensioni.jsp" />
          </div>
       </section>

       <div class="tendina-wrapper">
          <ul>
            <li><a href="${pageContext.request.contextPath}/index.jsp">Torna alla luxury principale</a></li>
            <li><a href="${pageContext.request.contextPath}/VisualizzaOrdiniServlet">Le mie tavolette</a></li>
            <li><a href="${pageContext.request.contextPath}/RegistraRecensioniServlet">Visualizza recensioni</a></li>
            <li><a href="${pageContext.request.contextPath}/ModificaProfiloServlet">Modifica il tuo status</a></li>
            <li><a href="${pageContext.request.contextPath}/EliminazioneUtenteServlet">Distruggi il tuo trono</a></li>
          </ul>
       </div>

    </main>
       
    <footer></footer>

    <script>
        const contextPath = "${pageContext.request.contextPath}";
    </script>
    <script src="${pageContext.request.contextPath}/js/filtriAjax.js"></script>
     
</body>
</html>
