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
<body>
<div>

  <form id="form-modifica"action="${pageContext.request.contextPath}/ModificaProfiloServlet" method=POST>
  <div class="form-group">
   <label>Modifica il nome</label>
   <input type="text" name="nome" value=<%= utente.getNome() %>>
   <label>Modifica il cognome</label>
   <input type="text" name="cognome" value=<%=utente.getCognome() %>>
   <label>Modifica la data di nascita</label>
   <input type="date" name="dataNascita" value=<%=utente.getData() %>>
   <label>Modifica il cellulare </label>
   <input type="text" name="cellulare" value=<%=utente.getCellulare() %>>
   <label>Modifica il prefisso</label>
   <input type="text" name="prefisso" value=<%=utente.getPrefisso() %>>
   <button class="btn-modificaUtente" type="submit">Salva tutte le modifiche</button>
<a href="${pageContext.request.contextPath}/jsp/modificaPasswordUtente.jsp" class="link-password" style="margin-left: 15px;">Cambia Password</a>
    </div>
 </form>
    </div>
    <script src="${pageContext.request.contextPath}/js/validazioni.js"></script>
    <script src="${pageContext.request.contextPath}/js/modificaProfilo.js"></script>
</body>
</html>