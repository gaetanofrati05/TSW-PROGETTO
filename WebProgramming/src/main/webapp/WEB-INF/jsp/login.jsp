<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/componenti.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <title>The Royal Rest Lounge - Login</title>
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
        <a href="${pageContext.request.contextPath}/">
            <img src="${pageContext.request.contextPath}/img/logo.png" class="logo-impact" alt="The Royal Rest">
        </a>
    </header>

    <main class="login-wrapper">
        
        <div class="welcome-heading-box">
            <h1 class="login-titolo-fine">Benvenuto nella The Royal Lounge</h1>
            <p class="sottotitolo-fine">Accedi al tuo portale quiet luxury.</p>
        </div>

        <div class="login-card-luxury">
            
            <%-- Errore lato Server (Java) --%>
            <% if (request.getAttribute("errorMessage") != null) { %>
                <div class="error-message">
                    <%= request.getAttribute("errorMessage") %>
                </div>
            <% } %>
            
            <%-- Errore lato Client (JavaScript) --%>
            <div id="js-global-error" class="js-error"></div>
          
            <form id="loginForm" action="${pageContext.request.contextPath}/LoginServlet" method="POST">
                
                <div class="form-group">
                    <label class="form-label" for="email">Email</label>
                    <input type="email" id="email" name="email" placeholder="Inserisci il tuo indirizzo email" required class="custom-input">
                    <div id="email-error" class="js-error">Email non valida.</div>
                </div>
                 
                <div class="form-group">
                    <label class="form-label" for="password">Password</label>
                    <div class="input-password-wrapper">
                        <input type="password" id="password" name="password" placeholder="Inserisci la tua password segreta" required class="custom-input">
                    </div>
                    <div id="password-error" class="js-error">La password non rispetta i requisiti.</div>
                </div>
                 
                <button class="bottone-login-luxury" type="submit">Entra nella lounge</button>
                
       <div class="login-redirect">
        <span>Non sei ancora registrato?</span>
         <a href="${pageContext.request.contextPath}/RegistrazioneServlet" class="redirect-link">Iscriviti</a>
         </div>
            </form>
        </div>
        
    </main>

    <footer class="login-footer">
        <p>&copy; 2026 The Royal Rest. L'arte del benessere silenzioso.</p>
    </footer>
    
    <script src="${pageContext.request.contextPath}/js/validazioni.js" defer></script>
    <script src="${pageContext.request.contextPath}/js/login.js" defer></script>

</body>
</html>