<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import= bean.UtenteBean %>
    <%
    UtenteBean utente= (UtenteBean) session.getAttribute("utenteLoggato");
    if(utente==null){
    	response.sendRedirect(request.getContextPath()+ "VisualizzaProfiloUtente");
    	return;
    }
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

    <title>Modifica-Password</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/componenti.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    
</head>
<body class="login-page-bg">
    <header class="login-header">
        <img src="${pageContext.request.contextPath}/img/logo.png" alt="logo" class="logo-impact">
    </header>
    <div class="login-wrapper">
        <div class="welcome-heading-box">
            <h1 class="login-titolo-fine">Modifica Password</h1>
            <p class="sottotitolo-fine">Sicurezza e raffinatezza</p>
        </div>
        <div class="login-card-luxury">
            <form id="formRegistrazione" action="${pageContext.request.contextPath}/ModificaPasswordServlet" method="POST">
                <div class="form-group">
                    <label class="form-label">Nuova password</label>
                    <input type="password" name="nuova-password" required class="custom-input">
                    <span id="password-error-1" class="js-error" style="display:none;">Password non valida</span>
                </div>
                <div class="form-group">
                    <label class="form-label">Conferma password</label>
                    <input type="password" name="conferma-password" required class="custom-input">
                    <span id="password-error-2" class="js-error" style="display:none;">Le password non coincidono</span>
                </div>
                <button class="bottone-login-luxury" type="submit">Salva tutte le modifiche</button>
            </form>
        </div>
    </div>
    <footer class="login-footer">© 2026 The Royal Rest</footer>

    <script src="${pageContext.request.contextPath}/js/validazioni.js" defer></script>
    <script src="${pageContext.request.contextPath}/js/modificaPassword.js" defer></script>
</body>
</html>