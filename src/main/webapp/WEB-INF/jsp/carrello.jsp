<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="bean.CarrelloBean" %>
<%@ page import="bean.ProdottoCarrello" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>The Royal Rest — Carrello</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/componenti.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/carrello.css">
</head>
<body>
  <jsp:include page="/fragments/navbar.jsp" />

  <%
      CarrelloBean carrello = (CarrelloBean) session.getAttribute("carrello");
      List<ProdottoCarrello> elementi = (carrello != null) ? carrello.getElementi() : null;
      boolean carrelloVuoto = elementi == null || elementi.isEmpty();
      boolean utenteLoggato = session.getAttribute("utenteLoggato") != null;
      float totaleNetto = (carrello != null) ? carrello.calcolaTotaleNetto() : 0f;
      float iva         = (carrello != null) ? carrello.calcolaIVA() : 0f;
      float totaleLordo = (carrello != null) ? carrello.calcolaTotaleLordo() : 0f;
  %>

  <section class="carrello">
    <div class="carrello-header">
      <p class="carrello-eyebrow">La tua selezione</p>
      <h1 class="carrello-titolo">Carrello</h1>
    </div>

    <% if (carrelloVuoto) { %>
      <div class="carrello-vuoto">
        <p>Il tuo carrello è vuoto.</p>
        <a href="${pageContext.request.contextPath}/CatalogoServlet" class="carrello-link-catalogo">Esplora il catalogo →</a>
      </div>
    <% } else { %>
      <div class="carrello-items">
        <% for (ProdottoCarrello item : elementi) { %>
          <div class="carrello-item">
            <div class="carrello-item-img">
              <img src="<%= item.getProdotto().getImmagine() %>" alt="<%= item.getProdotto().getNome() %>">
            </div>
            <div class="carrello-item-info">
              <span class="carrello-item-stile"><%= item.getProdotto().getStile() %></span>
              <h2><%= item.getProdotto().getNome() %></h2>
              <p class="carrello-item-prezzo">€<%= String.format("%.2f", item.getProdotto().getPrezzo()) %> cad.</p>
            </div>
            <div class="carrello-item-azioni">
              <form action="${pageContext.request.contextPath}/GestioneCarrelloServlet" method="POST" class="carrello-form-quantita">
                <input type="hidden" name="action" value="aggiorna">
                <input type="hidden" name="idProdotto" value="<%= item.getProdotto().getIdProdotto() %>">
                <span class="carrello-qty-label">Qtà</span>
                <input type="number" name="nuovaQuantita"
                       value="<%= item.getQuantita() %>" min="1" max="<%= item.getProdotto().getQuantita() %>" class="carrello-qty-input">
                <button type="submit" class="carrello-btn-aggiorna">Aggiorna</button>
              </form>
              <form action="${pageContext.request.contextPath}/GestioneCarrelloServlet" method="POST">
                <input type="hidden" name="action" value="rimuovi">
                <input type="hidden" name="idProdotto" value="<%= item.getProdotto().getIdProdotto() %>">
                <button type="submit" class="carrello-btn-rimuovi">Rimuovi</button>
              </form>
              <p class="carrello-item-subtotale">€<%= String.format("%.2f", item.getProdotto().getPrezzo() * item.getQuantita()) %></p>
            </div>
          </div>
        <% } %>
      </div>

      <div class="carrello-footer">
        <div class="carrello-totale">
          <span>Totale</span>
          <span class="carrello-totale-importo">€<%= String.format("%.2f", totaleNetto) %></span>
          <span>IVA</span>
          <span class="carrello-totale-importo">€<%= String.format("%.2f", iva) %></span>
          <span>Totale Lordo</span>
          <span class="carrello-totale-importo">€<%= String.format("%.2f", totaleLordo) %></span>
        </div>
        <div class="carrello-azioni-finali">
          <a href="${pageContext.request.contextPath}/CatalogoServlet" class="carrello-link-catalogo">Continua lo shopping</a>
          <% if (utenteLoggato) { %>
            <a href="${pageContext.request.contextPath}/RegistraOrdinazioneServlet" class="carrello-btn-acquisto">Procedi all'acquisto</a>
          <% } else { %>
            <a href="${pageContext.request.contextPath}/LoginServlet" class="carrello-btn-acquisto">Procedi all'acquisto</a>
          <% } %>
        </div>
      </div>
    <% } %>
  </section>

  <jsp:include page="/fragments/footer.jsp" />
</body>
</html>
