<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bean.OrdinazioneBean" %>
<%@ page import="bean.ProdottoCarrello" %>
<%@ page import="bean.UtenteBean" %>
<%@ page import="java.util.List" %>
<%
    OrdinazioneBean ordinazione = (OrdinazioneBean) request.getAttribute("ordinazione");
    if (ordinazione == null) {
        response.sendRedirect(request.getContextPath() + "/VisualizzaOrdiniServlet");
        return;
    }
    UtenteBean utente = ordinazione.getUtente();
    List<ProdottoCarrello> items = (List<ProdottoCarrello>) request.getAttribute("items");
    float totaleNetto  = (Float) request.getAttribute("totaleNetto");
    float iva          = (Float) request.getAttribute("iva");
    float totaleLordo  = (Float) request.getAttribute("totaleLordo");
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Fattura #<%= ordinazione.getIdOrdinazione() %> — The Royal Rest</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/fattura.css">
</head>
<body>

    <div class="fattura-wrapper">

        <div class="fattura-intestazione no-print">
            <h1>Ordine confermato!</h1>
            <p>Grazie per il tuo acquisto. Di seguito trovi il riepilogo della fattura.</p>
        </div>

        <div class="fattura-documento">

            <div class="fattura-header">
                <div>
                    <h2 class="fattura-brand">The Royal Rest</h2>
                    <p>P.IVA: 12345678901</p>
                </div>
                <div class="fattura-ref">
                    <p><strong>Fattura n°</strong> <%= ordinazione.getIdOrdinazione() %></p>
                    <p><strong>Data:</strong> <%= ordinazione.getDataOrdinazione() %></p>
                </div>
            </div>

            <hr class="fattura-divider">

            <div class="fattura-cliente">
                <p class="fattura-sezione-titolo">Destinatario</p>
                <p><strong><%= utente.getNome() %> <%= utente.getCognome() %></strong></p>
                <p><%= utente.getEmail() %></p>
                <p>
                    <%= ordinazione.getIndirizzo() %>, <%= ordinazione.getCivico() %> —
                    <%= ordinazione.getCap() %> <%= ordinazione.getCitta() %>
                </p>
            </div>

            <hr class="fattura-divider">

            <table class="fattura-tabella">
                <thead>
                    <tr>
                        <th>Prodotto</th>
                        <th>Qtà</th>
                        <th>Prezzo unitario</th>
                        <th>Subtotale</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (ProdottoCarrello item : items) { %>
                        <tr>
                            <td><%= item.getProdotto().getNome() %></td>
                            <td><%= item.getQuantita() %></td>
                            <td>€<%= String.format("%.2f", item.getProdotto().getPrezzo()) %></td>
                            <td>€<%= String.format("%.2f", item.getProdotto().getPrezzo() * item.getQuantita()) %></td>
                        </tr>
                    <% } %>
                </tbody>
            </table>

            <hr class="fattura-divider">

            <div class="fattura-totali">
                <div class="fattura-riga-totale">
                    <span>Imponibile</span>
                    <span>€<%= String.format("%.2f", totaleNetto) %></span>
                </div>
                <div class="fattura-riga-totale">
                    <span>IVA (22%)</span>
                    <span>€<%= String.format("%.2f", iva) %></span>
                </div>
                <div class="fattura-riga-totale fattura-totale-finale">
                    <span>Totale</span>
                    <span>€<%= String.format("%.2f", totaleLordo) %></span>
                </div>
            </div>

        </div>

        <div class="fattura-azioni no-print">
            <button onclick="window.print()" class="fattura-btn-stampa">Stampa / Salva PDF</button>
            <a href="${pageContext.request.contextPath}/VisualizzaOrdiniServlet" class="fattura-link-ordini">Vai ai miei ordini</a>
        </div>

    </div>

</body>
</html>
