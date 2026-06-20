<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bean.UtenteBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    UtenteBean utente = (UtenteBean) session.getAttribute("utenteLoggato");
    if (utente == null) {
        response.sendRedirect(request.getContextPath() + "/LoginServlet");
        return;
    }

    String dataNascitaValue = "";
    if (utente.getData() != null) {
        dataNascitaValue = new SimpleDateFormat("yyyy-MM-dd").format(utente.getData());
    }

    List<String> errori = (List<String>) request.getAttribute("errore");
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/componenti.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <title>Modifica Profilo - <%= utente.getNome() %></title>
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
        .success-message {
            color: var(--ottone-antico, #8A754B);
            font-size: 0.85rem;
            text-align: center;
            margin-bottom: 1.5rem;
            letter-spacing: 0.05rem;
        }
        .error-message {
            color: #a94442;
            font-size: 0.85rem;
            text-align: center;
            margin-bottom: 1rem;
        }
        .field-readonly {
            opacity: 0.65;
            cursor: not-allowed;
        }
    </style>
</head>
<body class="login-page-bg">

    <header class="login-header">
        <a href="${pageContext.request.contextPath}/VisualizzaProfiloUtente">
            <img src="${pageContext.request.contextPath}/img/logo.png" class="logo-impact" alt="The Royal Rest">
        </a>
    </header>

    <main class="login-wrapper">

        <div class="welcome-heading-box">
            <h1 class="login-titolo-fine">Modifica il tuo profilo</h1>
            <p class="sottotitolo-fine">Aggiorna le tue informazioni personali.</p>
        </div>

        <div class="login-card-luxury">

            <% if ("true".equals(request.getParameter("modifica"))) { %>
                <div class="success-message">Profilo aggiornato con successo.</div>
            <% } %>

            <% if (errori != null && !errori.isEmpty()) {
                   for (String msg : errori) { %>
                <div class="error-message"><%= msg %></div>
            <%   }
               } %>

            <form id="form-modifica" action="${pageContext.request.contextPath}/ModificaProfiloServlet" method="POST">

                <div class="form-group">
                    <label class="form-label" for="email">Email</label>
                    <input type="email" id="email" name="email" value="<%= utente.getEmail() %>" readonly class="custom-input field-readonly">
                </div>

                <div class="form-group">
                    <label class="form-label required-label" for="nome">Nome</label>
                    <input type="text" id="nome" name="nome" value="<%= utente.getNome() != null ? utente.getNome() : "" %>" placeholder="Inserisci il tuo nome" required class="custom-input">
                    <span id="nome-error" class="js-error">Nome non valido</span>
                </div>

                <div class="form-group">
                    <label class="form-label required-label" for="cognome">Cognome</label>
                    <input type="text" id="cognome" name="cognome" value="<%= utente.getCognome() != null ? utente.getCognome() : "" %>" placeholder="Inserisci il tuo cognome" required class="custom-input">
                    <span id="cognome-error" class="js-error">Cognome non valido</span>
                </div>

                <div class="form-group">
                    <label class="form-label required-label" for="dataNascita">Data di nascita</label>
                    <input type="date" id="dataNascita" name="dataNascita" value="<%= dataNascitaValue %>" required class="custom-input">
                </div>

                <div class="form-group">
                    <label class="form-label required-label" for="nazionalita">Nazionalità</label>
                    <input type="text" id="nazionalita" name="nazionalita" value="<%= utente.getNazionalita() != null ? utente.getNazionalita() : "" %>" placeholder=" Es. Italiana" required class="custom-input">
                </div>

                <div class="form-group">
                    <label class="form-label required-label" for="prefisso">Prefisso</label>
                    <input type="text" id="prefisso" name="prefisso" value="<%= utente.getPrefisso() != null ? utente.getPrefisso() : "" %>" placeholder=" Es. +39" required class="custom-input">
                    <span id="prefisso-error" class="js-error">Prefisso non valido</span>
                </div>

                <div class="form-group">
                    <label class="form-label required-label" for="cellulare">Cellulare</label>
                    <input type="tel" id="cellulare" name="cellulare" value="<%= utente.getCellulare() != null ? utente.getCellulare() : "" %>" placeholder="Inserisci il tuo numero" required class="custom-input">
                    <span id="cellulare-error" class="js-error">Cellulare non valido</span>
                </div>

                <button class="bottone-login-luxury" type="submit">Salva tutte le modifiche</button>

                <div class="login-redirect">
                    <a href="${pageContext.request.contextPath}/ModificaPasswordServlet" class="redirect-link">Cambia password</a>
                    <span style="margin: 0 0.5rem;">·</span>
                    <a href="${pageContext.request.contextPath}/VisualizzaProfiloUtente" class="redirect-link">Torna al profilo</a>
                </div>
            </form>
        </div>
    </main>

   <jsp:include page="/fragments/footer.jsp" />

    <script src="${pageContext.request.contextPath}/js/validation.js" defer></script>
    <script src="${pageContext.request.contextPath}/js/modificaProfilo.js" defer></script>
</body>
</html>
