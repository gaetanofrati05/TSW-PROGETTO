<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="bean.RecensioneBean" %>
    <%
    RecensioneBean recensione= (RecensioneBean) session.getAttribute("recensioneDaModificare");
    if(recensione==null) {
    	response.sendRedirect(request.getContextPath()+ "/jsp/profilo.jsp");
    	return;
    }
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modifica-recensione</title>
</head>
<body>
<form action="${pageContext.request.contextPath}ModificaRecensioneServlet" method="POST">
<div class="form-modifica">
<input type="hidden" name="idRecensione" value= <%=recensione.getIdRecensione() %>/>
<label>Dai un voto al prodotto (da 1 a 5)</label>
<input type="number" name="scoring" min="1" max="5" value="<%= recensione.getScoring() %>" />
 <label>Dai una descrizione</label> 
<textarea name="descrizione" required><%= recensione.getDescrizione() %></textarea>
   </div>
   <button class="button-recensione"type="submit">Salva le modifiche</button>
 </form>
</body>
</html>