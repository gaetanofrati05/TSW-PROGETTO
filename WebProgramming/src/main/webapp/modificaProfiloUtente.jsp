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
<title>Modifica-Profilo</title>
</head>
<body>
<div class="form-Profilo">
<form action="${pageContext.request.contextPath}/ModificaProfiloServlet" method=POST>
<label>Modifica il nome</label>
<input type="text" name="nome" value=<%= utente.getNome() %>>
<label>Modifica il cognome</label>
<input type="text" name="cognome" value=<%=utente.getCognome() %>>
<label>Modifica la data di nascita</label>
<input type="text" name="dataNascita" value=<%=utente.getData() %>>
<label>Modifica il cellulare </label>
<input type="text" name="cellulare" value=<%=utente.getCellulare() %>>
<label>Modifica il prefisso</label>
<input type="text" name="prefisso" value=<%=utente.getPrefisso() %>>
<button class="btn-modificaUtente" type="submit">Salva tutte le modifiche</button>
<a href="${pageContext.request.contextPath}/jsp/modificaPasswordUtente.jsp" class="link-password" style="margin-left: 15px;">Cambia Password</a>
 </form>
 </div>
</body>
</html>