<%-- Direttiva JSP: Configura il linguaggio della pagina e la codifica dei caratteri per evitare problemi con gli accenti --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    
    <div class="admin-card-luxury">
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
                <c:forEach var="prodotto" items="${listaProdotti}">
                    <tr>
                        <td>${prodotto.idProdotto}</td>
                        <td>${prodotto.nome}</td>
                        <td>${prodotto.stile}</td>
                        <td>${prodotto.colore}</td>
                        <td>${prodotto.dimensioni}</td>
                        <td>${prodotto.prezzo}</td>
                        <td>${prodotto.quantita}</td>
                        <td>${prodotto.descrizione}</td>
                        <td>${prodotto.immagine}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/ModificaProdottoServlet?id=${prodotto.idProdotto}" class="admin-btn-edit">Modifica</a>
                            <a href="${pageContext.request.contextPath}/EliminaProdottoServlet?id=${prodotto.idProdotto}" class="admin-btn-delete">Elimina</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    </main>
    <script src="${pageContext.request.contextPath}/js/hidden-btn.js"></script>
</body>
</html>