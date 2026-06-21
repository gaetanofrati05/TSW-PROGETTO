<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="bean.ProdottoBean" %>
<%@ page import="bean.RecensioneBean" %>
<%@ page import="java.util.List" %>
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
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profilo.css">
</head>
<body>
  <jsp:include page="/fragments/navbar.jsp" />

  <%
      ProdottoBean prod = (ProdottoBean) request.getAttribute("prodotto");
      List<RecensioneBean> listaRecensioni = (List<RecensioneBean>) request.getAttribute("listaRecensioni");
  %>

  <section class="dettaglio">
    <div class="dettaglio-image">
      <img src="${pageContext.request.contextPath}/<%= prod.getImmagine() %>" alt="<%= prod.getNome() %>">
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
        <button id="aggAlCarrello" class="dettaglio-btn-carrello">Aggiungi al Carrello</button>
      </div>

      <section class="dettaglio-recensioni">
        <p class="section-label">Recensioni dei clienti</p>

        <% if ("true".equals(request.getParameter("recensioneSalvata"))) { %>
            <p class="recensione-avviso-successo">Recensione pubblicata con successo.</p>
        <% } %>

        <div class="recensioni-track dettaglio-recensioni-track">
          <% if (listaRecensioni != null && !listaRecensioni.isEmpty()) { %>
              <% for (RecensioneBean recensione : listaRecensioni) { %>
                  <div class="recensione-card">
                      <% if (recensione.getUtente() != null) { %>
                          <p class="recensione-autore">
                              <%= recensione.getUtente().getNome() %> <%= recensione.getUtente().getCognome() %>
                          </p>
                      <% } %>
                      <p class="recensione-valutazione">Valutazione: <strong><%= recensione.getScoring() %>/5</strong></p>
                      <p class="recensione-descrizione">"<%= recensione.getDescrizione() %>"</p>
                      <p class="recensione-data">Pubblicata il: <%= recensione.getDataRecensione() %></p>
                  </div>
              <% } %>
          <% } else { %>
              <p class="empty-msg">Nessuna recensione per questo prodotto.</p>
          <% } %>
        </div>
      </section>

    </div>
  </section>

  <jsp:include page="/fragments/footer.jsp" />
  <script>
    const contextPath = "${pageContext.request.contextPath}";
    const idprod = "<%= prod.getIdProdotto() %>";

    document.getElementById("aggAlCarrello").addEventListener("click", aggiunta);

    function aggiunta() {
        fetch(contextPath + "/GestioneCarrelloServlet", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: "action=aggiungi&idProdotto=" + idprod + "&quantita=1"
        })
        .then(risposta => {
            if (risposta.ok) {
                mostraConferma();
            } else {
                console.error("Errore durante l'aggiunta al carrello");
            }
        })
        .catch(errore => console.error(errore));
    }

    function mostraConferma() {
        const btn = document.getElementById("aggAlCarrello");
        const testoOriginale = btn.textContent;
        btn.textContent = "Aggiunto ✓";
        setTimeout(() => {
            btn.textContent = testoOriginale;
        }, 2000);
    }
  </script>
</body>
</html>
