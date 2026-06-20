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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/componenti.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagerror.css">
    </head>
    <body class="admin-page-bg">
        <main class="admin-wrapper">
            <div class="admin-heading-box">
                <h1 class="admin-title-luxury">Gestione Ordini</h1>
                <p class="admin-subtitle-luxury">Visualizza tutti gli ordini attualmente salvati nel database</p>
            </div>

            <div class="admin-card-luxury admin-table-wrapper" style="max-width: 90%;">
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
                    <% if(listaOrdinazioni != null && !listaOrdinazioni.isEmpty()) { %>
                        <% for(OrdinazioneBean ordine : listaOrdinazioni) { %>
                            <tr>
                                <td><%= ordine.getIdOrdinazione() %></td>
                                <td><%= ordine.getUtente() != null ? ordine.getUtente().getEmail() : "N/D" %></td>
                                <td><%= ordine.getIndirizzo() + ", " + ordine.getCivico() + ", " + ordine.getCap() + ", " + ordine.getCitta()%></td>
                                <td><%= ordine.getDataOrdinazione() != null ? ordine.getDataOrdinazione().toString().split(" ")[0] : "N/D" %></td>
                                <td><%= ordine.getImporto() %></td>
                                <td><%= ordine.getStato() %></td>
                                <td>
                                    <a href="#" class="btn-action btn-edit js-modifica-ordine" data-id-ordinazione="<%= ordine.getIdOrdinazione() %>">Modifica</a>
                                    <a href="${pageContext.request.contextPath}/EliminaOrdineAdminServlet?idOrdinazione=<%= ordine.getIdOrdinazione() %>" class="btn-action btn-delete" onclick="return confirm('Sei sicuro di voler eliminare questo ordine?');">Elimina</a>
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

            <div id="form-modifica-ordine-admin" class="form-modifica-ordine-admin" style="display: none; position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%); z-index: 1000;">
                <h3 class="admin-section-title">Modifica Ordine</h3>
                <form id="modificaOrdineForm" action="${pageContext.request.contextPath}/ModificaOrdineAdminServlet" method="post">
                    <input type="hidden" name="idOrdinazione" id="idOrdinazione">
                    
                    <div class="admin-form-group">
                        <label class="admin-label">Email Utente</label>
                        <input type="text" class="admin-input" name="email" id="email" readonly>
                    </div>
                    
                    <div class="admin-form-group">
                        <label class="admin-label">Citt�</label>
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
                    <button type="button" class="btn-admin-submit" style="background: transparent; border: 1px solid var(--nero); color: var(--nero);" onclick="chiudiModificaOrdine()">Annulla</button>
                </form>
            </div>
            
            <div class="admin-redirect-box">
                <a href="${pageContext.request.contextPath}/DashboardAdminServlet" class="admin-link">&larr; Torna al pannello admin</a>
            </div>
            
        </main>
        <script>
            const contextPath = "${pageContext.request.contextPath}";
        </script>
        <script src="${pageContext.request.contextPath}/js/modificaOrdine.js"></script>
    </body>
</html>