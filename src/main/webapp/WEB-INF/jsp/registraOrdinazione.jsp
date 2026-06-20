<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bean.UtenteBean" %>
<%@ page import="bean.CarrelloBean" %>
<%
    UtenteBean utente = (UtenteBean) session.getAttribute("utenteLoggato");
    if (utente == null) {
        response.sendRedirect(request.getContextPath() + "/LoginServlet");
        return;
    }
    CarrelloBean carrello = (CarrelloBean) session.getAttribute("carrello");
    if (carrello == null || carrello.getElementi().isEmpty()) {
        response.sendRedirect(request.getContextPath() + "/CarrelloServlet");
        return;
    }
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Completa acquisto — The Royal Rest</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/componenti.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body class="login-page-bg">

    <header class="login-header">
        <a href="${pageContext.request.contextPath}/CarrelloServlet">
            <img src="${pageContext.request.contextPath}/img/logo.png" class="logo-impact" alt="The Royal Rest">
        </a>
    </header>

    <main class="login-wrapper">
        <div class="welcome-heading-box">
            <h1 class="login-titolo-fine">Completa l'acquisto</h1>
            <p class="sottotitolo-fine">Totale: €<%= String.format("%.2f", carrello.getTotale()) %></p>
        </div>

        <div class="login-card-luxury">

            <% if (request.getAttribute("errore") != null) { %>
                <div class="error-message"><%= request.getAttribute("errore") %></div>
            <% } %>

            <form id="formOrdine" action="${pageContext.request.contextPath}/RegistraOrdinazioneServlet" method="POST">

                <div class="form-group">
                    <input type="text" id="citta" name="citta" placeholder="Città" required class="custom-input">
                    <div id="citta-error" class="js-error">Città non valida.</div>
                </div>

                <div class="form-group">
                    <input type="text" id="indirizzo" name="indirizzo" placeholder="Indirizzo" required class="custom-input">
                    <div id="indirizzo-error" class="js-error">Indirizzo non valido.</div>
                </div>

                <div class="form-group">
                    <input type="text" id="civico" name="civico" placeholder="Civico" required class="custom-input">
                    <div id="civico-error" class="js-error">Civico non valido.</div>
                </div>

                <div class="form-group">
                    <input type="text" id="cap" name="cap" placeholder="CAP" required class="custom-input">
                    <div id="cap-error" class="js-error">CAP non valido.</div>
                </div>

                <button class="bottone-login-luxury" type="submit">Conferma acquisto</button>

                <div class="login-redirect">
                    <a href="${pageContext.request.contextPath}/CarrelloServlet" class="redirect-link">Torna al carrello</a>
                </div>

            </form>
        </div>
    </main>

    <jsp:include page="/fragments/footer.jsp" />
    <script src="${pageContext.request.contextPath}/js/validation.js" defer></script>
    <script src="${pageContext.request.contextPath}/js/validazioneOrdine.js" defer></script>
</body>
</html>