<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import= bean.UtenteBean %>
    <%
    UtenteBean utente= (UtenteBean) session.getAttribute("utenteLoggato");
    if(utente==null){
    	response.sendRedirect(request.getContextPath()+ "/jsp/profilo.jsp");
    	return;
    }
    
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modifica-Password</title>
</head>
<body>
<div>
<form id="form-modificaPassword" action="${pageContext.request.contextPath}/ModificaPasswordServlet" method=POST>
   <div class="form-group">
   <label>Inserisci la nuova password</label>
   <input type="password" name="nuova-password" required>
      <div id="password-error" class="js-error">La password non rispetta i requisiti.</div>
   <label>Conferma la tua password</label>
   <input type="password" name="conferma-password" required>
    <div id="password-error" class="js-error">La password non rispetta i requisiti.</div>
      </div>
    </form>
<script src="${pageContext.request.contextPath}/js/validazioni.js" defer></script>
<script src="${pageContext.request.contextPath}/js/modificaPassword.js"></script>
  </div>
</body>
</html>