<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bean.UtenteBean" %>
<%
    UtenteBean utente = (UtenteBean) session.getAttribute("utenteLoggato");
    if (utente == null) {
        response.sendRedirect(request.getContextPath() + "/LoginServlet");
        return;
    }
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Elimina Profilo <%= utente.getNome() %> - Conferma</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/componenti.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/css/avvisi.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@latest/dist/tabler-icons.min.css">
    </head>
<body class="login-page-bg">
    <header class="login-header">
        <a href="${pageContext.request.contextPath}/VisualizzaProfiloUtente">
            <img src="${pageContext.request.contextPath}/img/logo.png" alt="logo" class="logo-impact">
        </a>
    </header>
    <main class="login-wrapper">
        <div class="welcome-heading-box">
            <h1 class="login-titolo-fine">Elimina Account</h1>
            <p class="sottotitolo-fine">Conferma la tua scelta</p>
        </div>
        <div class="login-card-luxury">
            <p class="elimina-avviso">
                Ciao <strong><%= utente.getNome() %></strong>, ci dispiace che abbandoni la nostra famiglia.
                Questa operazione è <strong>irreversibile</strong>: tutti i tuoi dati verranno cancellati definitivamente.
            </p>

            <% String errore = (String) request.getAttribute("erroreEliminazione");
               if (errore != null) { %>
                <div class="error-message"><%= errore %></div>
            <% } %>

            <form id="formElimina" action="${pageContext.request.contextPath}/EliminazioneUtenteServlet" method="POST">
                <div class="form-group">
                    <label class="form-label required-label" for="passwordConferma">Password di conferma</label>
                    <div class="input-password-wrapper">
                        <input type="password" id="passwordConferma" name="passwordConferma" required
                               class="custom-input" placeholder="La tua password attuale">
                        <button type="button" class="toggle-pwd-btn" aria-label="Mostra password">
                            <i class="ti ti-eye toggle-pwd-icon" aria-hidden="true"></i>
                        </button>
                    </div>
                    <span id="password-error" class="js-error">Password non valida</span>
                </div>

                <button class="bottone-login-luxury" type="submit">Elimina definitivamente</button>
                <div class="login-redirect">
                    <a href="${pageContext.request.contextPath}/VisualizzaProfiloUtente" class="redirect-link">Annulla e torna al profilo</a>
                </div>
            </form>
        </div>
    </main>
    <jsp:include page="/fragments/footer.jsp" />

    <script src="${pageContext.request.contextPath}/js/validation.js" defer></script>
    <script src="${pageContext.request.contextPath}/js/validazioneRegistrazione.js" defer></script>
    <script src="${pageContext.request.contextPath}/js/mostraNascondiPwd.js" defer></script>
</body>
</html>
