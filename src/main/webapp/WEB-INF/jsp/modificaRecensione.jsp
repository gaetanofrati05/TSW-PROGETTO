<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bean.RecensioneBean" %>
<%@ page import="bean.UtenteBean" %>
<%
    // Controllo di sicurezza: se non c'è una recensione da modificare, torna al profilo
    RecensioneBean recensione = (RecensioneBean) request.getAttribute("recensioneDaModificare");
    if(recensione == null) {
        response.sendRedirect(request.getContextPath() + "/VisualizzaProfiloUtente");
        return;
    }
%>

<%
UtenteBean utente= (UtenteBean) session.getAttribute("utenteLoggato");
if(utente==null){
	response.sendRedirect(request.getContextPath()+ "/LoginServlet");
	return;
}


%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modifica Recensione</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/componenti.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profilo.css">
    
  
</head>
<body class="profilo-page-bg">

    <jsp:include page="/fragments/navbar.jsp" />

    <main class="modifica-wrapper">
        <p class="section-label">Gestione Contenuti</p>
        <h2>Modifica la tua recensione</h2>
        <p style="color: #666; font-size: 13px; margin-bottom: 20px;">
            Stai modificando la recensione per il prodotto: 
            <strong><%= (recensione.getProdotto() != null) ? recensione.getProdotto().getNome() : "Selezionato" %></strong>
        </p>
        <hr style="border: 0; border-top: 1px solid #eee; margin-bottom: 20px;">

        <form action="${pageContext.request.contextPath}/ModificaRecensioneServlet" method="POST">
            
            <input type="hidden" name="idRecensione" value="<%= recensione.getIdRecensione() %>" />
            
            <div class="form-modifica">
                
                <div class="form-control-group">
                    <label for="scoring">Dai un voto al prodotto (da 1 a 5)</label>
                    <input type="number" id="scoring" name="scoring" min="1" max="5" value="<%= recensione.getScoring() %>" required />
                </div>
                
                <div class="form-control-group">
                    <label for="descrizione">Descrizione della tua esperienza</label> 
                    <textarea id="descrizione" name="descrizione" required placeholder="Scrivi qui cosa ne pensi..."><%= recensione.getDescrizione() %></textarea>
                </div>
                
            </div>
            
            <div class="azioni-form">
                <a href="${pageContext.request.contextPath}/VisualizzaRecensioniServlet" class="btn-recensione btn-annulla">Annulla</a>
                <button class="btn-recensione btn-salva" type="submit">Salva le modifiche</button>
            </div>
            
        </form>
    </main>

    <footer></footer>

</body>
</html>