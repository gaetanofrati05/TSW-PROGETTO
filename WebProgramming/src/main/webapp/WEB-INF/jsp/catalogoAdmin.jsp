<%-- Direttiva JSP: Configura il linguaggio della pagina e la codifica dei caratteri per evitare problemi con gli accenti --%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>

<%@ page import="bean.ProdottoBean" %>

<%

    List<ProdottoBean> listaProdotti = (List<ProdottoBean>) request.getAttribute("listaProdotti");

%>

<%-- Pagina JSP utilizzata come catalogo dei prodotti (Admin)--%>

<!DOCTYPE html>



<html lang="it">



<head>

    <meta charset="UTF-8">

    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Catalogo Prodotti - The Royal Rest</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/componenti.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagerror.css">

</head>



<body class="admin-page-bg">

    <main class="admin-wrapper">

        <div class="admin-heading-box">

            <h1 class="admin-title-luxury">Catalogo Prodotti</h1>

            <p class="admin-subtitle-luxury">Visualizza tutti i prodotti attualmente salvati nel database</p>

        </div>



        <div class="admin-card-luxury admin-table-wrapper">

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

                                    <a href="${pageContext.request.contextPath}/ModificaProdottoServlet?id=<%= prodotto.getIdProdotto() %>" class="btn-action btn-edit">Modifica</a>

                                    <a href="${pageContext.request.contextPath}/EliminaProdottoServlet?id=<%= prodotto.getIdProdotto() %>" class="btn-action btn-delete" onclick="return confirm('Sei sicuro di voler eliminare questo prodotto?');">Elimina</a>

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



        <div class="admin-redirect-box">

            <a href="${pageContext.request.contextPath}/DashboardAdminServlet" class="admin-link">&larr; Torna al pannello admin</a>

        </div>

    </main>

</body>

</html>


