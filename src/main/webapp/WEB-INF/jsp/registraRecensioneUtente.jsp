<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bean.ProdottoBean" %>
<%@ page import="bean.OrdinazioneBean" %>
<%@ page import="bean.UtenteBean" %>
<%@ page import="java.util.List" %>
<%
    UtenteBean utente = (UtenteBean) session.getAttribute("utenteLoggato");
    if (utente == null) {
        response.sendRedirect(request.getContextPath() + "/LoginServlet");
        return;
    }

    ProdottoBean prodotto = (ProdottoBean) request.getAttribute("prodotto");
    OrdinazioneBean ordinazione = (OrdinazioneBean) request.getAttribute("ordinazione");
    Integer quantitaOrdinata = (Integer) request.getAttribute("quantitaOrdinata");
    List<String> errori = (List<String>) request.getAttribute("erroriRecensione");
    String scoringInserito = (String) request.getAttribute("scoringInserito");
    String descrizioneInserita = (String) request.getAttribute("descrizioneInserita");

    if (prodotto == null) {
        response.sendRedirect(request.getContextPath() + "/VisualizzaOrdiniServlet");
        return;
    }
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Scrivi una recensione — <%= prodotto.getNome() %></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/componenti.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/recensione.css">
</head>
<body class="recensione-page">

    <jsp:include page="/fragments/navbar.jsp" />

    <header class="recensione-intestazione">
        <span class="recensione-eyebrow">La tua opinione conta</span>
        <h1 class="recensione-titolo">Racconta la tua esperienza</h1>
        <p class="recensione-sottotitolo">
            Ogni oggetto racconta una storia. Condividi la tua, con la stessa cura con cui l'hai scelto.
        </p>
        <% if (ordinazione != null) { %>
            <p class="recensione-ordine-ref">Ordine #<%= ordinazione.getIdOrdinazione() %></p>
        <% } %>
    </header>

    <% if (errori != null && !errori.isEmpty()) { %>
        <ul class="recensione-errori">
            <% for (String errore : errori) { %>
                <li><%= errore %></li>
            <% } %>
        </ul>
    <% } %>

    <section class="recensione-dettaglio">
        <div class="recensione-image">
            <img src="<%= prodotto.getImmagine() != null ? prodotto.getImmagine() : "" %>"
                 alt="<%= prodotto.getNome() %>">
        </div>

        <div class="recensione-content">
            <div class="recensione-stile"><%= prodotto.getStile() != null ? prodotto.getStile() : "" %></div>
            <h2 class="recensione-nome"><%= prodotto.getNome() %></h2>
            <p class="recensione-descrizione"><%= prodotto.getDescrizione() != null ? prodotto.getDescrizione() : "" %></p>

            <div class="recensione-specifiche">
                <div class="recensione-spec-item">
                    <span class="recensione-spec-label">Colore</span>
                    <span class="recensione-spec-value"><%= prodotto.getColore() != null ? prodotto.getColore() : "—" %></span>
                </div>
                <div class="recensione-spec-item">
                    <span class="recensione-spec-label">Dimensioni</span>
                    <span class="recensione-spec-value"><%= prodotto.getDimensioni() != null ? prodotto.getDimensioni() : "—" %></span>
                </div>
                <% if (quantitaOrdinata != null) { %>
                    <div class="recensione-spec-item">
                        <span class="recensione-spec-label">Quantità ordinata</span>
                        <span class="recensione-spec-value"><%= quantitaOrdinata %></span>
                    </div>
                <% } %>
            </div>

            <p class="recensione-prezzo">€<%= prodotto.getPrezzo() %></p>

            <section class="recensione-form-section">
                <span class="recensione-form-label">La tua recensione</span>

                <form action="${pageContext.request.contextPath}/RegistraRecensioneServlet" method="POST">
                    <input type="hidden" name="idProdotto" value="<%= prodotto.getIdProdotto() %>">
                    <% if (ordinazione != null) { %>
                        <input type="hidden" name="idOrdinazione" value="<%= ordinazione.getIdOrdinazione() %>">
                    <% } %>

                    <div class="recensione-form-modifica">
                        <div class="recensione-form-group">
                            <label for="scoring">Voto (da 1 a 5)</label>
                            <input type="number" id="scoring" name="scoring" min="1" max="5"
                                   value="<%= scoringInserito != null ? scoringInserito : "" %>" required>
                        </div>

                        <div class="recensione-form-group">
                            <label for="descrizione">Descrizione della tua esperienza</label>
                            <textarea id="descrizione" name="descrizione" required  
                                      placeholder="Scrivi qui cosa ne pensi..."><%= descrizioneInserita != null ? descrizioneInserita : "" %></textarea>
                        </div> <!-- Creato una text area in cui inserire la descrizione -->
                    </div>

                    <div class="recensione-azioni-form">
                        <button class="recensione-btn-salva" type="submit">Pubblica recensione</button>
                        <a href="${pageContext.request.contextPath}/VisualizzaOrdiniServlet" class="recensione-btn-annulla">Annulla</a>
                    </div>
                </form>
            </section>
        </div>
    </section>

    <jsp:include page="/fragments/footer.jsp" />

</body>
</html>