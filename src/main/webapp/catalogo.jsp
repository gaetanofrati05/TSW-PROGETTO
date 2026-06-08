<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="bean.ProdottoBean" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="it">
<head>
  <link rel="stylesheet" href="css/base.css">
  <link rel="stylesheet" href="css/layout.css">
  <link rel="stylesheet" href="css/componenti.css">
</head>
<body>
  <jsp:include page="componenti/navbar.jsp" />
  
  <section class="catalogo">
    <!-- barra ricerca -->
    <div class="catalogo-search">
      <input type="text" placeholder="Cerca la tua tavoletta...">
    </div>
    </section>
    
    
    <section class="catalogo" id="catalogo">
    <div class="catalogo-grid">

        <% 
            List<ProdottoBean> prodotti = (List<ProdottoBean>) request.getAttribute("prodotti");
            for(ProdottoBean p : prodotti) { 
        %>
            <div class="card">
           
                <div class="card-body">
                    <div class="card-location"><%= p.getStile() %></div>
                    <h3><%= p.getNome() %></h3>
                    <p><%= p.getDescrizione() %></p>
                    <div class="card-footer">
                        <span class="card-price">€<%= p.getPrezzo() %></span>
                        <a href="prodotto?id=<%= p.getIdProdotto() %>" class="card-link">Scopri →</a>
                    </div>
                </div>
            </div>
        <% } %>

	</div>
	</section>
      
</body>
</html>