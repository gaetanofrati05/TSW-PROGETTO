<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bean.UtenteBean" %>
<%
    UtenteBean utente = (UtenteBean) session.getAttribute("utenteLoggato");
    if (utente == null) {
        response.sendRedirect(request.getContextPath() + "/VisualizzaProfiloUtente");
        return;
    }
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modifica Password - <%= utente.getNome() %></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/componenti.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@latest/dist/tabler-icons.min.css">
    <style>
        .js-error {
            color: var(--oro, #b89a5a);
            font-size: 0.8rem;
            display: none;
            margin-top: 8px;
            text-align: left;
            text-transform: uppercase;
            letter-spacing: 0.08rem;
        }
    </style>
</head>
<body class="login-page-bg">
    <header class="login-header">
        <a href="${pageContext.request.contextPath}/VisualizzaProfiloUtente">
            <img src="${pageContext.request.contextPath}/img/logo.png" alt="logo" class="logo-impact">
        </a>
    </header>
    <main class="login-wrapper">
        <div class="welcome-heading-box">
            <h1 class="login-titolo-fine">Modifica Password</h1>
            <p class="sottotitolo-fine">Sicurezza e raffinatezza</p>
        </div>
        <div class="login-card-luxury">
            <form id="formRegistrazione" action="${pageContext.request.contextPath}/ModificaPasswordServlet" method="POST">
                <div class="form-group">
                    <label class="form-label required-label" for="nuovaPassword">Nuova password</label>
                    <div class="input-password-wrapper">
                        <input type="password" id="nuovaPassword" name="nuovaPassword" required class="custom-input" placeholder="Crea una password diversa...">
                        <button type="button" class="toggle-pwd-btn" aria-label="Mostra password"> <!-- con aria label il letto di schermo legge quello che
                        c'è dentro e lo mostra -->
                            <i class="ti ti-eye toggle-pwd-icon" aria-hidden="true"></i>
                        </button>
                    </div>
                    <span id="password-error-1" class="js-error">Password non valida</span>
                </div>
                <div class="form-group">
                    <label class="form-label required-label" for="confermaPassword">Conferma password</label>
                    <div class="input-password-wrapper">
                        <input type="password" id="confermaPassword" name="confermaPassword" required class="custom-input" placeholder="Digita correttamente la password">
                        <button type="button" class="toggle-pwd-btn" aria-label="Mostra password">
                            <i class="ti ti-eye toggle-pwd-icon" aria-hidden="true"></i>
                        </button>
                    </div>
                    <span id="password-error-2" class="js-error">Le password non coincidono</span>
                </div>
                <button class="bottone-login-luxury" type="submit">Salva tutte le modifiche</button>
                <div class="login-redirect">
                    <a href="${pageContext.request.contextPath}/VisualizzaProfiloUtente" class="redirect-link">Torna al profilo</a>
                </div>
            </form>
        </div>
    </main>
    <jsp:include page="/fragments/footer.jsp" />
 
    <script src="${pageContext.request.contextPath}/js/validation.js" defer></script>
    <script src="${pageContext.request.contextPath}/js/modificaPassword.js" defer></script>
    <script src="${pageContext.request.contextPath}/js/mostraNascondiPwd.js" defer></script>
</body>
</html>
