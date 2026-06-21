<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="bean.ProdottoBean" %>
<%
    List<ProdottoBean> listaProdotti = (List<ProdottoBean>) request.getAttribute("listaProdotti");
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Catalogo Prodotti - The Royal Rest</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagerror.css">
</head>
<body class="admin-page-bg">
    <main class="admin-wrapper">

        <div class="admin-heading-box">
            <h1 class="admin-title-luxury">Catalogo Prodotti</h1>
            <p class="admin-subtitle-luxury">Gestisci tutti i prodotti nel database</p>
        </div>

        <div class="admin-card-luxury admin-table-wrapper">

            <%-- Sezione filtri --%>
            <p class="admin-subtitle-luxury">Filtra prodotti</p>
            <form id="formFiltraProdotti" class="admin-form-group-prodotti"
                  action="${pageContext.request.contextPath}/CatalogoAdminServlet" method="get">
                <div>
                    <label class="admin-label-prodotti" for="filtro-nome">Nome</label>
                    <input class="admin-input-prodotti" type="text" name="nome" id="filtro-nome" placeholder="Nome prodotto" value="${param.nome != null ? param.nome : ''}">
                </div>
                <div>
                    <label class="admin-label-prodotti" for="filtro-stile">Stile</label>
                    <input class="admin-input-prodotti" type="text" name="stile" id="filtro-stile" placeholder="Stile prodotto" value="${param.stile != null ? param.stile : ''}">
                </div>
                <div>
                    <label class="admin-label-prodotti" for="filtro-colore">Colore</label>
                    <input class="admin-input-prodotti" type="text" name="colore" id="filtro-colore" placeholder="Colore prodotto" value="${param.colore != null ? param.colore : ''}">
                </div>
                <div>
                    <label class="admin-label-prodotti" for="filtro-dimensioni">Dimensioni</label>
                    <input class="admin-input-prodotti" type="text" name="dimensioni" id="filtro-dimensioni" placeholder="Dimensioni prodotto" value="${param.dimensioni != null ? param.dimensioni : ''}">
                </div>
                <div>
                    <label class="admin-label-prodotti" for="filtro-prezzo">Prezzo</label>
                    <input class="admin-input-prodotti" type="number" name="prezzo" id="filtro-prezzo" placeholder="Prezzo minimo" value="${param.prezzo != null ? param.prezzo : ''}">
                </div>
                <button type="submit" class="btn-action btn-edit">Filtra</button>
                <button type="button" class="btn-action btn-edit" onclick="resetFiltriProdotti()">Reset</button>
            </form>

            <%-- Link inserimento nuovo prodotto --%>
            <p class="admin-subtitle-luxury" style="margin-top: 1.5rem;">Inserisci un nuovo prodotto</p>
            <br>
            <a href="${pageContext.request.contextPath}/InserisciProdottoAdminServlet" class="btn-action btn-edit">
                + Inserisci nuovo prodotto
            </a>
            <br><br>

            <%-- Tabella prodotti --%>
            <p class="admin-subtitle-luxury">Catalogo prodotti</p>
            <br>
            <table class="admin-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Stile</th>
                        <th>Colore</th>
                        <th>Dimensioni</th>
                        <th>Prezzo</th>
                        <th>Quantità</th>
                        <th>Descrizione</th>
                        <th>Immagine</th>
                        <th>Azioni</th>
                    </tr>
                </thead>
                <tbody>
                    <% if (listaProdotti != null && !listaProdotti.isEmpty()) { %>
                        <% for (ProdottoBean prodotto : listaProdotti) { %>
                            <tr>
                                <td><%= prodotto.getIdProdotto() %></td>
                                <td><%= prodotto.getNome() %></td>
                                <td><%= prodotto.getStile() %></td>
                                <td><%= prodotto.getColore() %></td>
                                <td><%= prodotto.getDimensioni() %></td>
                                <td><%= prodotto.getPrezzo() %></td>
                                <td><%= prodotto.getQuantita() %></td>
                                <td><%= prodotto.getDescrizione() %></td>
                                <td><%= prodotto.getImmagine() %></td>
                                <td>
                                    <a href="#"
                                       class="btn-action btn-edit js-modifica-prodotto"
                                       data-id-prodotto="<%= prodotto.getIdProdotto() %>">Modifica</a>
                                    <a href="${pageContext.request.contextPath}/EliminaProdottoServlet?idProdotto=<%= prodotto.getIdProdotto() %>"
                                       class="btn-action btn-delete"
                                       onclick="return confirm('Sei sicuro di voler eliminare questo prodotto?');">Elimina</a>
                                </td>
                            </tr>
                        <% } %>
                    <% } else { %>
                        <tr>
                            <td colspan="10">Nessun prodotto presente nel catalogo.</td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        </div>

        <%-- Form popup di modifica prodotto (nascosto, aperto via JS) --%>
        <div id="form-modifica-prodotto-admin" class="form-modifica-ordine-admin" style="display: none;">
            <h3 class="admin-section-title">Modifica Prodotto</h3>
            <form id="modificaProdottoForm" action="${pageContext.request.contextPath}/ModificaProdottoServlet" method="post">
                <input type="hidden" name="idProdotto" id="idProdotto">
                <div class="admin-form-group">
                    <label class="admin-label">Nome</label>
                    <input type="text" class="admin-input" name="nome" id="nome">
                </div>
                <div class="admin-form-group">
                    <label class="admin-label">Stile</label>
                    <input type="text" class="admin-input" name="stile" id="stile">
                </div>
                <div class="admin-form-group">
                    <label class="admin-label">Colore</label>
                    <input type="text" class="admin-input" name="colore" id="colore">
                </div>
                <div class="admin-form-group">
                    <label class="admin-label">Dimensioni</label>
                    <input type="text" class="admin-input" name="dimensioni" id="dimensioni">
                </div>
                <div class="admin-form-group">
                    <label class="admin-label">Prezzo</label>
                    <input type="number" step="0.01" min="0" class="admin-input" name="prezzo" id="prezzo">
                </div>
                <div class="admin-form-group">
                    <label class="admin-label">Quantità</label>
                    <input type="number" min="0" class="admin-input" name="quantita" id="quantita">
                </div>
                <div class="admin-form-group">
                    <label class="admin-label">Descrizione</label>
                    <textarea class="custom-textarea" name="descrizione" id="descrizione" rows="3"></textarea>
                </div>
                <div class="admin-form-group">
                    <label class="admin-label">Percorso immagine</label>
                    <input type="text" class="admin-input" name="immagine" id="immagine" placeholder="Es: img/prodotti/nome.jpg">
                </div>
                <button type="submit" class="btn-admin-submit">Salva Modifiche</button>
                <button type="button" class="btn-admin-submit"
                        style="background: transparent; border: 1px solid var(--nero); color: var(--nero);"
                        onclick="chiudiModificaProdotto()">Annulla</button>
            </form>
        </div>

        <div class="admin-redirect-box">
            <a href="${pageContext.request.contextPath}/index.jsp" class="admin-link">&larr; Torna alla home</a>
        </div>

    </main>
    <script>
        const contextPath = "${pageContext.request.contextPath}";
    </script>
    <script src="${pageContext.request.contextPath}/js/modificaProdotto.js"></script>
</body>
</html>
