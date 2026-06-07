<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="bean.UtenteBean" %>
    <%
    UtenteBean utente= (UtenteBean) session.getAttribute("utenteLoggato");
    if(utente==null){
    	response.sendRedirect(request.getContextPath()+ "/jsp/login.jsp");
    	return;
    }
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Elimina-Profilo</title>
</head>
<body>
<div class="pagina-eliminaProfilo">
<form class="form-Profilo" action="${pageContext.request.contextPath}/EliminazioneUtente" method=POST>
<p>Sei sicuro di voler eliminare il tuo account?</p>
<button class="btn-eliminaUtente" type="submit">Elimina l'account</button>
  </form>
</div>
</body>
</html>