<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="bean.ProdottoBean" %>
<%@ page import="java.util.*" %>
<% 
    List<ProdottoBean> prodotti = (List<ProdottoBean>) request.getAttribute("prodotti");
    if (prodotti != null && !prodotti.isEmpty()) {
        for(ProdottoBean p : prodotti) { 
%>
    <div class="card">
        <div class="card-img-wrap">
            <img src="<%= p.getImmagine() %>" alt="<%= p.getNome() %>">
        </div>
        <div class="card-body">
            <div class="card-location"><%= p.getStile() %></div>
            <h3><%= p.getNome() %></h3>
            <p><%= p.getDescrizione() %></p>
            <div class="card-footer">
                <span class="card-price">€<%= p.getPrezzo() %></span>
                <a href="${pageContext.request.contextPath}/prodotto?id=<%= p.getIdProdotto() %>" class="card-link">Scopri →</a>
            </div>
        </div>
    </div>
<%      } 
    } else { 
%>
    <div class="catalogo-vuoto">
        <p>Al momento non ci sono prodotti disponibili nel catalogo.</p>
    </div>
<%  } %>