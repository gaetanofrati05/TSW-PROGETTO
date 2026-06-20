<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bean.OrdinazioneBean" %>
<%@ page import="bean.ProdottoBean" %>
<%@ page import="bean.UtenteBean" %>
<%@ page import="java.util.List" %>
<%
    UtenteBean utente = (UtenteBean) session.getAttribute("utenteLoggato");
    if (utente == null) {
        response.sendRedirect(request.getContextPath() + "/LoginServlet");
        return;
    }

    OrdinazioneBean ordinazione = (OrdinazioneBean) request.getAttribute("ordinazione");
    List<ProdottoBean> listaProdotti = (List<ProdottoBean>) request.getAttribute("listaProdotti");
    if (ordinazione == null) {
        response.sendRedirect(request.getContextPath() + "/VisualizzaProfiloUtente");
        return;
    }

    List<String> errori = (List<String>) request.getAttribute("errore");
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lascia una recensione</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/componenti.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profilo.css">
</head>
<body class="profilo-page-bg">

    <jsp:include page="/fragments/navbar.jsp" />

    <main class="modifica-wrapper">
        <p class="section-label">Gestione Contenuti</p>
        <h2>Lascia la tua recensione</h2>
        <p style="color: #666; font-size: 13px; margin-bottom: 20px;">
            Stai recensendo un prodotto dell'ordine <strong>#<%= ordinazione.getIdOrdinazione() %></strong>
            consegnato il <%= ordinazione.getDataOrdinazione() %>.
        </p>
        <hr style="border: 0; border-top: 1px solid #eee; margin-bottom: 20px;">

        <% if (errori != null && !errori.isEmpty()) { %>
            <div class="error-message">
                <% for (String errore : errori) { %>
                    <p><%= errore %></p>
                <% } %>
            </div>
        <% } %>

        <form action="${pageContext.request.contextPath}/InserisciRecensioneServlet" method="POST">
            <input type="hidden" name="idOrdinazione" value="<%= ordinazione.getIdOrdinazione() %>" />

            <div class="form-modifica">
                <div class="form-control-group">
                    <label for="idProdotto">Prodotto da recensire</label>
                    <select id="idProdotto" name="idProdotto" required class="custom-input">
                        <option value="">Seleziona un prodotto</option>
                        <% if (listaProdotti != null) {
                            for (ProdottoBean prodotto : listaProdotti) { %>
                                <option value="<%= prodotto.getIdProdotto() %>"><%= prodotto.getNome() %></option>
                        <%  }
                           } %>
                    </select>
                </div>

                <div class="form-control-group">
                    <label for="scoring">Dai un voto al prodotto (da 1 a 5)</label>
                    <input type="number" id="scoring" name="scoring" min="1" max="5" required />
                </div>

                <div class="form-control-group">
                    <label for="descrizione">Descrizione della tua esperienza</label>
                    <textarea id="descrizione" name="descrizione" required placeholder="Scrivi qui cosa ne pensi..."></textarea>
                </div>
            </div>

            <div class="azioni-form">
                <a href="${pageContext.request.contextPath}/VisualizzaProfiloUtente" class="btn-recensione btn-annulla">Annulla</a>
                <button class="btn-recensione btn-salva" type="submit">Pubblica recensione</button>
            </div>
        </form>
    </main>

    <footer></footer>
</body>
</html>
