<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>The Royal Rest Lounge</title>
<style>
  .js-error { color: red; font-size: 0.9em; display: none; margin-top: 5px; }
</style>
</head>
<body>
<div>
  <span class="icona">🔒</span>
  <h1 class="login-titolo">Benvenuto nella The Royal Lounge</h1>
  <p class="sottotitolo">Accedi al tuo portale quiet luxury.</p>
  
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
      <input type="email" id="email" name="email" placeholder="Inserisci la tua email" required>
      <div id="email-error" class="js-error">Email non valida.</div>
    </div>
     
    <div class="form-group">
      <label class="form-label" for="password">Password</label>
      <div class="input-password">
        <input type="password" id="password" name="password" placeholder="Inserisci la tua password" required>
      </div>
      <div id="password-error" class="js-error">La password non rispetta i requisiti.</div>
    </div>
     
    <button class="bottone-login" type="submit">Entra nella lounge</button>
  </form>
</div>

<footer>
</footer>
<script src="${pageContext.request.contextPath}/js/validazioni.js" defer></script>
<script src="${pageContext.request.contextPath}/js/logib.js" defer></script>
</body>
</html>