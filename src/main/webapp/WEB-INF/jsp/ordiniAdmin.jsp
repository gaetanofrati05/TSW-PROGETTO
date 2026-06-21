<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="bean.OrdinazioneBean" %>
<%
    List<OrdinazioneBean> listaOrdinazioni = (List<OrdinazioneBean>) request.getAttribute("listaOrdinazioni");
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestione Ordini - The Royal Rest</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body class="admin-page-bg">
    <main class="admin-wrapper">

        <div class="admin-heading-box">
            <h1 class="admin-title-luxury">Gestione Ordini</h1>
            <p class="admin-subtitle-luxury">Gestisci e modifica tutti gli ordini nel database</p>
        </div>

        <div class="admin-card-luxury admin-table-wrapper">

            <%-- Sezione filtri --%>
            <p class="admin-subtitle-luxury">Filtra ordini</p>
            <br>
            <form id="formFiltriOrdini" class="admin-form-group-ordini"
                  action="${pageContext.request.contextPath}/GestioneOrdiniAdminServlet" method="get">
                <div>
                    <label class="admin-label-ordini" for="filtro-idOrdinazione">ID Ordine</label>
                    <input class="admin-input-ordini" type="text" name="idOrdinazione" id="filtro-idOrdinazione" placeholder="ID ordine">
                </div>
                <div>
                    <label class="admin-label-ordini" for="filtro-email">Email</label>
                    <input class="admin-input-ordini" type="email" name="email" id="filtro-email" placeholder="Email utente">
                </div>
                <div>
                    <label class="admin-label-ordini" for="filtro-dataInizio">Data inizio</label>
                    <input class="admin-input-ordini" type="date" name="dataInizio" id="filtro-dataInizio">
                </div>
                <div>
                    <label class="admin-label-ordini" for="filtro-dataFine">Data fine</label>
                    <input class="admin-input-ordini" type="date" name="dataFine" id="filtro-dataFine">
                </div>
                <div>
                    <label class="admin-label-ordini" for="filtro-stato">Stato</label>
                    <select class="admin-input-ordini" name="status" id="filtro-stato">
                        <option value="">Tutti gli stati</option>
                        <option value="Consegnato">Consegnato</option>
                        <option value="In transito">In transito</option>
                        <option value="In elaborazione">In elaborazione</option>
                    </select>
                </div>
                <button type="submit" class="btn-action btn-filtra-ordini">Filtra</button>
                <button type="button" class="btn-action btn-filtra-ordini" onclick="resetFiltriOrdini()">Reset</button>
            </form>
            <br>

            <%-- Tabella ordini --%>
            <p class="admin-subtitle-luxury">Ordini</p>
            <br>
            <table class="admin-table">
                <thead>
                    <tr>
                        <th>ID Ordine</th>
                        <th>Email Utente</th>
                        <th>Indirizzo</th>
                        <th>Data Ordine</th>
                        <th>Importo</th>
                        <th>Stato Ordine</th>
                        <th>Azioni</th>
                    </tr>
                </thead>
                <tbody>
                    <% if (listaOrdinazioni != null && !listaOrdinazioni.isEmpty()) { %>
                        <% for (OrdinazioneBean ordine : listaOrdinazioni) { %>
                            <tr>
                                <td><%= ordine.getIdOrdinazione() %></td>
                                <td><%= ordine.getUtente() != null ? ordine.getUtente().getEmail() : "N/D" %></td>
                                <td><%= ordine.getIndirizzo() + ", " + ordine.getCivico() + ", " + ordine.getCap() + ", " + ordine.getCitta() %></td>
                                <td><%= ordine.getDataOrdinazione() != null ? ordine.getDataOrdinazione().toString().split(" ")[0] : "N/D" %></td>
                                <td><%= ordine.getImporto() %></td>
                                <td><%= ordine.getStato() %></td>
                                <td>
                                    <a href="#"
                                       class="btn-action btn-edit js-modifica-ordine"
                                       data-id-ordinazione="<%= ordine.getIdOrdinazione() %>">Modifica</a>
                                    <a href="${pageContext.request.contextPath}/EliminaOrdineAdminServlet?idOrdinazione=<%= ordine.getIdOrdinazione() %>"
                                       class="btn-action btn-delete"
                                       onclick="return confirm('Sei sicuro di voler eliminare questo ordine?');">Elimina</a>
                                </td>
                            </tr>
                        <% } %>
                    <% } else { %>
                        <tr>
                            <td colspan="7">Nessun ordine trovato.</td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        </div>

        <%-- Form popup di modifica ordine (nascosto, aperto via JS) --%>
        <div id="form-modifica-ordine-admin" class="form-modifica-ordine-admin" style="display: none;">
            <h3 class="admin-section-title">Modifica Ordine</h3>
            <form id="modificaOrdineForm" action="${pageContext.request.contextPath}/ModificaOrdineAdminServlet" method="post">
                <input type="hidden" name="idOrdinazione" id="idOrdinazione">
                <div class="admin-form-group">
                    <label class="admin-label">Email Utente</label>
                    <input type="text" class="admin-input" name="email" id="email" readonly>
                </div>
                <div class="admin-form-group">
                    <label class="admin-label">Città</label>
                    <input type="text" class="admin-input" name="citta" id="citta">
                </div>
                <div class="admin-form-group">
                    <label class="admin-label">Indirizzo</label>
                    <input type="text" class="admin-input" name="indirizzo" id="indirizzo">
                </div>
                <div class="admin-form-group">
                    <label class="admin-label">Civico</label>
                    <input type="text" class="admin-input" name="civico" id="civico">
                </div>
                <div class="admin-form-group">
                    <label class="admin-label">CAP</label>
                    <input type="text" class="admin-input" name="cap" id="cap">
                </div>
                <div class="admin-form-group">
                    <label class="admin-label">Stato Ordine</label>
                    <input type="text" class="admin-input" name="stato" id="stato">
                </div>
                <div class="admin-form-group">
                    <label class="admin-label">Data Ordinazione</label>
                    <input type="date" class="admin-input" name="dataOrdinazione" id="dataOrdinazione">
                </div>
                <div class="admin-form-group">
                    <label class="admin-label">Importo</label>
                    <input type="number" step="0.01" class="admin-input" name="importo" id="importo">
                </div>
                <button type="submit" class="btn-admin-submit">Salva Modifiche</button>
                <button type="button" class="btn-admin-submit"
                        style="background: transparent; border: 1px solid var(--nero); color: var(--nero);"
                        onclick="chiudiModificaOrdine()">Annulla</button>
            </form>
        </div>

        <div class="admin-redirect-box">
            <a href="${pageContext.request.contextPath}/index.jsp" class="admin-link">&larr; Torna al pannello admin</a>
        </div>

    </main>
    <script>
        const contextPath = "${pageContext.request.contextPath}";
    </script>
    <script src="${pageContext.request.contextPath}/js/modificaOrdine.js"></script>
    <script src="${pageContext.request.contextPath}/js/validazioneFiltriOrdini.js"></script>
</body>
</html>
