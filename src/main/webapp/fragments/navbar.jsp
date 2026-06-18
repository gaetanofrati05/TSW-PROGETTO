<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header>
  <nav>
    <div class="nav-logo">
     <a href="${pageContext.request.contextPath}/index.jsp">
    	<img src="${pageContext.request.contextPath}/img/logo.png" alt="The Royal Rest">
  	</a>
    </div>
    <ul class="nav-links">
      <li><a href="#">Filosofia</a></li>
      <li><a href="#">Storia</a></li>
      <li><a href="#">Concierge</a></li>
      <li><a href="${pageContext.request.contextPath}/CatalogoServlet">Catalogo</a></li>
      <li><a href="${pageContext.request.contextPath}/CarrelloServlet">Carrello</a></li>
      
     <%--Verifichiamo se l'utente è loggato o meno per reindizzarlo correttamente --%>
      <% 
        if (session.getAttribute("utenteLoggato") != null) { 
      %>
          <li><a href="${pageContext.request.contextPath}/VisualizzaProfiloUtente">Mio Profilo</a></li>
          <li><a href="${pageContext.request.contextPath}/LogoutServlet">Esci dalla lounge</a></li>
      <% 
        } else { 
      %>
          <li><a href="${pageContext.request.contextPath}/LoginServlet">Profilo</a></li>
      <% 
        } 
      %>
     
      
    </ul>
  </nav>
</header>