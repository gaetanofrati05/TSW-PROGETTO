<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>The Royal Rest Lounge</title>
</head>
<body>
<div class="registrazione-wrap">
<div class="registrazione-animazione">
 <h1 class="titolo-registrazione">Richiedi l'accesso</h1>
 <p class="registrazione-sottotitolo">Registra le tue credenziali per accedere</p>
<% if (request.getAttribute("errorMessage") != null) { %>
      <div class="error-message">
        <%= request.getAttribute("errorMessage") %>
      </div>
    <% } %>
    <form action="${pageContext.request.contextPath}/RegistrazioneServlet" method="POST">
     <div class="form-group">
     <label class="form-label"for="nome">Nome</label>
     <div class="input-wrap">
     <input type="text" id="nome" name="nome">
        </div>
      </div>
      
      <div class="form-group">
     <label class="form-label"for="cognome">Cognome</label>
     <div class="input-wrap">
     <input type="text" id="cognome" name="cognome">
        </div>
      </div>
      
      <div class="form-group">
     <label class="form-label"for="email">Email</label>
     <div class="input-wrap">
     <input type="text" id="email" name="email">
        </div>
      </div>
      
      <div class="form-group">
     <label class="form-label"for="dataNascita">Data di nascita</label>
     <div class="input-wrap">
     <input type="text" id="dataNascita" name="dataNascita">
        </div>
      </div>
      
      <div class="form-group">
     <label class="form-label"for="nazionalita">Nazionalità</label>
     <div class="input-wrap">
     <input type="text" id="nozionalita" name="nome">
        </div>
      </div>
      
      <div class="form-group">
     <label class="form-label"for="prefisso">Prefisso</label>
     <div class="input-wrap">
     <input type="text" id="prefisso" name="prefisso">
        </div>
      </div>
      
      <div class="form-group">
     <label class="form-label"for="cellulare">Cellulare</label>
     <div class="input-wrap">
     <input type="text" id="cellulare" name="cellulare">
        </div>
      </div>
      
      <div class="form-group">
     <label class="form-label"for="password">Password</label>
     <div class="input-wrap">
     <input type="text" id="password" name="password">
        </div>
      </div>
      <button class= "button-submit" type="submit">Invia richiesta</button>
    </form>
    </div>
   </div>
</body>
</html>