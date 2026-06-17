<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="bean.ProdottoBean" %>
<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>The Royal Rest — Dettaglio Prodotto</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/componenti.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dettaglioProd.css">
</head>
<body>
  <jsp:include page="/fragments/navbar.jsp" />

  <%
      ProdottoBean prod = (ProdottoBean) request.getAttribute("prodotto");
  %>

  <section class="dettaglio">
    <div class="dettaglio-image">
      <img src="<%= prod.getImmagine() %>" alt="<%= prod.getNome() %>">
    </div>
    <div class="dettaglio-content">

      <div class="dettaglio-location"><%= prod.getStile() %></div>
      <h1 class="dettaglio-nome"><%= prod.getNome() %></h1>
      <p class="dettaglio-descrizione"><%= prod.getDescrizione() %></p>

      <div class="dettaglio-specifiche">
        <div class="spec-item">
          <span class="spec-label">Colore</span>
          <span class="spec-value"><%= prod.getColore() %></span>
        </div>
        <div class="spec-item">
          <span class="spec-label">Dimensioni</span>
          <span class="spec-value"><%= prod.getDimensioni() %></span>
        </div>
        <div class="spec-item">
          <span class="spec-label">Disponibilità</span>
          <span class="spec-value"><%= prod.getQuantita() %> pezzi</span>
        </div>
      </div>

      <div class="dettaglio-footer">
        <span class="dettaglio-prezzo">€<%= prod.getPrezzo() %></span>
        <button class="dettaglio-btn-carrello">Aggiungi al Carrello</button>
      </div>

    </div>
  </section>

  <jsp:include page="/fragments/footer.jsp" />

</body>
</html>