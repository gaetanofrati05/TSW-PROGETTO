<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>The Royal Rest Lounge</title>
</head>
<body>
<!-- qui inserire la nav bar in base a come l'ha fatta David -->
<div>
<span class="icona">🔒</span>
  <h1 class="login-titolo">Benvenuto nella The Royal Lounge</h1>
    <p class="sottotitolo">Accedi al tuo portale quiet luxury.</p>
    <% if (request.getAttribute("errorMessage") != null) { %>
      <div class="error-message">
        <%= request.getAttribute("errorMessage") %>
      </div>
    <% } %>
    <form action="pageContext.request.contextPath" method="POST">
    <div class="form-group">
      <label class="form-label" for="email">Email</label>
      <div class="form-group">
      <input type="text" id="email" name="email" placeholder="Inserisci la tua email">
       </div>
     </div>
     <div class="form-group">
     <label class="form-label" for="password">Password</label>
     <div class="input-password">
      <input type="text" id="password" name="password" placeholder="Inserisci la tua password">
      </div>
     </div>
     <button class="bottone-login" type="submit">Entra nella lounge </button>
    </form>
  </div>
  <footer>
  
  </footer>
</body>
</html>