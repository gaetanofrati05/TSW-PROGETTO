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
    <title>The Royal Rest Lounge -Registrazione</title>
</head>
<body class="login-page-bg"> <header class="login-header">
        <a href="${pageContext.request.contextPath}/">
            <img src="${pageContext.request.contextPath}/img/logo.png" class="logo-impact" alt="The Royal Rest">
        </a>
    </header>

    <main class="login-wrapper">
        
        <div class="welcome-heading-box">
            <h1 class="login-titolo-fine">Unisciti alla nostra famiglia</h1>
            <p class="sottotitolo-fine">Registra le tue credenziali per accedere al portale.</p>
        </div>

        <div class="login-card-luxury">
            
            <% if (request.getAttribute("errore") != null) { %>
                <div class="error-message">
                    <%= request.getAttribute("errore") %>
                </div>
            <% } %>
            
            <form  id="formRegistrazione"action="${pageContext.request.contextPath}/RegistrazioneServlet" method="POST">
                
                <div class="form-group">
                    <label class="form-label" for="nome">Nome</label>
                    <input type="text" id="nome" name="nome" placeholder="Inserisci il tuo nome" required class="custom-input">
                    <span id="nome-error" class="js-error" style="color: var(--oro); display: none;">Nome non valido</span>
                </div>
              
                <div class="form-group">
                    <label class="form-label" for="cognome">Cognome</label>
                    <input type="text" id="cognome" name="cognome" placeholder="Inserisci il tuo cognome" required class="custom-input">
                     <span id="cognome-error" class="js-error" style="color: var(--oro); display: none;">Cognome non valido</span>
                </div>
              
                <div class="form-group">
                    <label class="form-label" for="email">Email</label>
                    <input type="email" id="email" name="email" placeholder="esempio@domain.com" required class="custom-input">
                    <span id="email-error" class="js-error" style="color: var(--oro); display: none;">Email non valida</span>
                </div>
              
                <div class="form-group">
                    <label class="form-label" for="dataNascita">Data di nascita</label>
                    <input type="date" id="dataNascita" name="dataNascita" required class="custom-input">
                </div>
              
                <div class="form-group">
                    <label class="form-label" for="nazionalita">Nazionalità</label>
                    <input type="text" id="nazionalita" name="nazionalita" placeholder="Es. Italiana" required class="custom-input">
                </div>
              
                <div class="form-group">
                    <label class="form-label" for="prefisso">Prefisso</label>
                    <input type="text" id="prefisso" name="prefisso" placeholder="Es. +39" required class="custom-input">
                    <span id="prefisso-error" class="js-error" style="color: var(--oro); display: none;">Prefisso non valido</span>
                </div>
              
                <div class="form-group">
                    <label class="form-label" for="cellulare">Cellulare</label>
                    <input type="tel" id="cellulare" name="cellulare" placeholder="Inserisci il tuo numero" required class="custom-input">
                    <span id="cellulare-error" class="js-error" style="color: var(--oro); display: none;">Cellulare non valido</span>
                </div>
              
                <div class="form-group">
                    <label class="form-label" for="password">Password</label>
                    <input type="password" id="password" name="password" placeholder="Crea una password sicura" required class="custom-input">
                    <span id="password-error" class="js-error" style="color: var(--oro); display: none;">Password non valida</span>
                </div>
              
                <button class="bottone-login-luxury" type="submit">Invia richiesta</button>
                
                <div class="login-redirect">
                    <span>Hai già un account?</span>
                    <a href="${pageContext.request.contextPath}/LoginServlet" class="redirect-link">Accedi</a>
                </div>
                
            </form>
        </div>
        
    </main>

    <footer class="login-footer">
        <p>&copy; 2026 The Royal Rest. L'arte del benessere silenzioso.</p>
       
    </footer>
           <script src="${pageContext.request.contextPath}/js/validation.js" defer></script>
           <script src="${pageContext.request.contextPath}/js/validazioneRegistrazione.js" defer></script>
           
</body>
</html>