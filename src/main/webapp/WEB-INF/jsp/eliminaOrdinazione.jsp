<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bean.OrdinazioneBean" %>
<%@ page import="bean.UtenteBean" %>
<%
    UtenteBean utente = (UtenteBean) session.getAttribute("utenteLoggato");
    if (utente == null) {
        response.sendRedirect(request.getContextPath() + "/LoginServlet");
        return;
    }

    OrdinazioneBean ordinazione = (OrdinazioneBean) request.getAttribute("ordinazioneEliminare");
    if (ordinazione == null) {
        response.sendRedirect(request.getContextPath() + "/VisualizzaOrdiniServlet");
        return;
    }
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Elimina Ordine #<%= ordinazione.getIdOrdinazione() %> - Conferma</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/componenti.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/avvisi.css">
</head>
<body class="login-page-bg">
    <header class="login-header">
        <a href="${pageContext.request.contextPath}/VisualizzaOrdiniServlet">
            <img src="${pageContext.request.contextPath}/img/logo.png" alt="logo" class="logo-impact">
        </a>
    </header>
    <main class="login-wrapper">
        <div class="welcome-heading-box">
            <h1 class="login-titolo-fine">Elimina Ordine</h1>
            <p class="sottotitolo-fine">Conferma la tua scelta</p>
        </div>
        <div class="login-card-luxury">
            <p class="elimina-avviso">
                Sei sicuro di voler eliminare l'ordine <strong>#<%= ordinazione.getIdOrdinazione() %></strong>
                del <%= ordinazione.getDataOrdinazione() %>?
                Questa operazione è <strong>irreversibile</strong>.
            </p>

            <form action="${pageContext.request.contextPath}/EliminaOrdinazioneServlet" method="POST">
                <input type="hidden" name="idOrdinazione" value="<%= ordinazione.getIdOrdinazione() %>" />
                <button class="bottone-login-luxury" type="submit">Elimina definitivamente</button>
                <div class="login-redirect">
                    <a href="${pageContext.request.contextPath}/VisualizzaOrdiniServlet" class="redirect-link">Annulla e torna agli ordini</a>
                </div>
            </form>
        </div>
    </main>
    <jsp:include page="/fragments/footer.jsp" />
</body>
</html>
