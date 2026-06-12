<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ page import= "bean.UtenteBean" %>
     <%@ page import="java.util.List" %>
     <%@ page import="bean.ProdottoBean" %>
     <%@ page import="java.util.ArrayList" %>
     <%@ page import="bean.OrdinazioneBean" %>
    <%
    UtenteBean utente= (UtenteBean) session.getAttribute("utenteLoggato");
    if(utente==null){
    	response.sendRedirect(request.getContextPath()+ "/LoginServlet");
    	return;
    }
      %>
    <%
    List<ProdottoBean> listaProdotti = (List<ProdottoBean>) request.getAttribute("listaProdotti");
      %>
    <%
    List<OrdinazioneBean> listaOrdinazioni= (List<OrdinazioneBean>) request.getAttribute("listaOrdinazioni");
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
     <title>Profilo-Utente</title>
</head>
<body class="profilo-page-bg">
  
    <jsp:include page="/fragments/navbar.jsp" />
    
    <header class="profilo">
       <p class="utente-titolo">Lounge Personale</p>
       <h1 class="utente-instestazione">
       
          <%=utente.getNome() %> <%=utente.getCognome() %>
          
       </h1>
       
          <p class="utente-informazioni">
          <%=utente.getNazionalita() %> <%=utente.getNumeroOrdinazioni() %> 
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
                    <p class="prodotto-quantita"><%= prodotto.getQuantita() %></p>
                </div>
            <% } %>
        <% } else { %>
            <p>Nessun prodotto disponibile</p>
        <% } %>
    </div>
      </aside>
           <section class="storico-ordini">
              <p class="section-label">Storico ordini</p>
              <% if(listaOrdinazioni!=null && !listaOrdinazioni.isEmpty()) { %>
                      <table class="table-ordini">
                        <thead>
                           <tr>
                           
                              <th>N° ordine</th>
                              <th>Prodotto</th>
                              <th>Data</th>
                              <th>Totale</th>
                              <th>Stato</th>
                              
                              </tr>
                           </thead>
                             <tbody>
                             <% for(OrdinazioneBean ordini: listaOrdinazioni){ %> 
                             <tr class="ordini-raw">
                                <td><%=ordini.getIdOrdinazione() %></td>
                                <td><%=ordini.getCitta() %></td>
                                <td><%=ordini.getDataOrdinazione() %></td>
                                <td><%=ordini.getImporto() %></td>
                                <td><%=ordini.getStato() %></td>
                             </tr>
                           <%} %>
                             
                             </tbody>
                        </table>
                   <% } else { %>
                  <p class="empty-msg">Nessun ordine effettuato</p>
                   <% } %>

           </section>
                 <div class="tendina-wrapper">
                    <ul>
                       
                      <li><a href="${pageContext.request.contextPath}/ModificaProfiloServlet">Modifica il tuo status</a></li>
                      <li><a href="${pageContext.request.contextPath}/index.jsp">Torna alla luxury principale</a></li>
                      <li><a href="${pageContext.request.contextPath}/VisualizzaOrdiniServlet">Le mie tavolette</a></li>
                      <li><a href="${pageContext.request.contextPath}/VisualizzaRecensioniServlet">Visualizza recensioni</a></li>
                      <li><a href="${pageContext.request.contextPath}/LogoutServlet">Esci dalla tua lounge</a></li>
                    </ul>
                     
                 </div>

       </main>
       
     <footer></footer>
     
</body>
</html>