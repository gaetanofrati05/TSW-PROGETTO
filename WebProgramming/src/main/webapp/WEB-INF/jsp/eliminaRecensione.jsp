<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="bean.RecensioneBean" %>
    <%
    RecensioneBean recensione= (RecensioneBean) session.getAttribute("recensioneDaEliminare");
    if(recensione==null) {
    	response.sendRedirect(request.getContextPath()+ "/jsp/profilo.jsp");
    	return;
    }
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Elimina-Recensione</title>
</head>
<body>
  <div class="form-recensione">
  <form action="${request.pageContextPath}/ EliminaRecensioneServlet " method=POST>
  <input type="hidden" name="idRecensione" value=<%= recensione.getIdRecensione() %>>
  <p>Sei sicuro di voler eliminare la recensione?</p>
  <button class="btnElimina-recensione" type="submit">Elimina recensione</button>
  </form>
   </div>
</body>
</html>