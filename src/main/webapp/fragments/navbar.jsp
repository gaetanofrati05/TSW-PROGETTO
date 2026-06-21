<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header>
  <nav>
    <div class="nav-logo">
      <a href="${pageContext.request.contextPath}/index.jsp">
        <img src="${pageContext.request.contextPath}/img/logo.png" alt="The Royal Rest">
      </a>
    </div>
    <ul class="nav-links">
      <li><a href="${pageContext.request.contextPath}/storia.jsp">Storia e Filosofia</a></li>

      <%
        Object utente = session.getAttribute("utenteLoggato");
        bean.UtenteBean utenteLoggato = (bean.UtenteBean) utente;
        boolean isAdmin = utenteLoggato != null && utenteLoggato.getAdmin();
      %>

      <% if (isAdmin) { %>
        <%-- Menu admin: catalogo admin e ordini admin al posto di quelli normali --%>
        <li><a href="${pageContext.request.contextPath}/CatalogoAdminServlet">Catalogo Admin</a></li>
        <li><a href="${pageContext.request.contextPath}/OrdiniAdminServlet">Ordini</a></li>
        <li><a href="${pageContext.request.contextPath}/VisualizzaProfiloUtente">Mio Profilo</a></li>
        <li><a href="${pageContext.request.contextPath}/LogoutServlet">Esci dalla lounge</a></li>
      <% } else if (utenteLoggato != null) { %>
        <%-- Menu utente normale loggato --%>
        <li><a href="${pageContext.request.contextPath}/CatalogoServlet">Catalogo</a></li>
        <li><a href="${pageContext.request.contextPath}/CarrelloServlet">Carrello</a></li>
        <li><a href="${pageContext.request.contextPath}/VisualizzaProfiloUtente">Mio Profilo</a></li>
        <li><a href="${pageContext.request.contextPath}/LogoutServlet">Esci dalla lounge</a></li>
      <% } else { %>
        <%-- Menu ospite non loggato --%>
        <li><a href="${pageContext.request.contextPath}/CatalogoServlet">Catalogo</a></li>
        <li><a href="${pageContext.request.contextPath}/CarrelloServlet">Carrello</a></li>
        <li><a href="${pageContext.request.contextPath}/LoginServlet">Profilo</a></li>
      <% } %>

    </ul>
  </nav>
</header>