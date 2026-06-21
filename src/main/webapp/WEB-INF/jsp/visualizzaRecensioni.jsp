<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bean.UtenteBean" %>
<%@ page import="bean.RecensioneBean" %>
<%@ page import="java.util.List" %>
<%
    UtenteBean utente = (UtenteBean) session.getAttribute("utenteLoggato");
    if (utente == null) {
        response.sendRedirect(request.getContextPath() + "/LoginServlet");
        return;
    }

    List<RecensioneBean> recensioniUtenti = (List<RecensioneBean>) request.getAttribute("recensioniUtenti");
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
    <title>Recensioni - <%= utente.getNome() %></title>
</head>
<body class="profilo-page-bg">

    <jsp:include page="/fragments/navbar.jsp" />

    <header class="profilo">
        <p class="utente-titolo">Lounge Personale • Recensioni</p>
        <h1 class="utente-instestazione">Recensioni della community</h1>
        <p class="utente-informazioni">Ordinate per valutazione</p>
    </header>

    <main class="profilo-wrapper">

        <aside class="lista-recensioni">
            <p class="section-label">Tutte le recensioni pubblicate da <%=utente.getNome() %></p>
            <div class="recensioni-track">
                <% if (recensioniUtenti != null && !recensioniUtenti.isEmpty()) { %>
                    <% for (RecensioneBean recensione : recensioniUtenti) { %>
                        <div class="recensione-card">
                            <p class="prodotto-name">
                                <% if (recensione.getProdotto() != null && recensione.getProdotto().getNome() != null && !recensione.getProdotto().getNome().isEmpty()) { %>
                                    <%= recensione.getProdotto().getNome() %>
                                <% } else if (recensione.getProdotto() != null) { %>
                                    Prodotto #<%= recensione.getProdotto().getIdProdotto() %>
                                <% } else { %>
                                    Prodotto non specificato
                                <% } %>
                            </p>
                            <% if (recensione.getUtente() != null && recensione.getUtente().getEmail() != null) { %>
                                <p class="recensione-autore">Recensito da: <%= recensione.getUtente().getEmail() %></p>
                            <% } %>
                            <p class="recensione-valutazione">Valutazione: <strong><%= recensione.getScoring() %>/5</strong></p>
                            <p class="recensione-descrizione">"<%= recensione.getDescrizione() %>"</p>
                            <p class="recensione-data">Pubblicata il: <%= recensione.getDataRecensione() %></p>
                        </div>
                    <% } %>
                <% } else { %>
                    <p class="empty-msg">Nessuna recensione disponibile.</p>
                <% } %>
            </div>
        </aside>

        <div class="tendina-wrapper">
            <ul>
                <li><a href="${pageContext.request.contextPath}/VisualizzaProfiloUtente">Torna al profilo principale</a></li>
                <li><a href="${pageContext.request.contextPath}/index.jsp">Torna alla luxury principale</a></li>
                <li><a href="${pageContext.request.contextPath}/VisualizzaOrdiniServlet">Le mie tavolette</a></li>
            </ul>
        </div>

    </main>

    

</body>
</html>
