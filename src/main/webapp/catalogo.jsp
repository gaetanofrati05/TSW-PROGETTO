<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="bean.ProdottoBean" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="it">
<head>
  <link rel="stylesheet" href="css/base.css">
  <link rel="stylesheet" href="css/layout.css">
  <link rel="stylesheet" href="css/componenti.css">
  <link rel="stylesheet" href="css/animazioni.css">
</head>
<body>
  <jsp:include page="/fragments/navbar.jsp" />

    
  <section class="catalogo" id="catalogo">
    <div class="catalogo-search">
      <div class="catalogo-search-wrap">
        <!-- autocomplete="off" evita che il browser mostri i suoi suggerimenti -->
        <input type="text" name="nome" id="barraRicerca" placeholder="Cerca la tua tavoletta..." autocomplete="off">
        <!-- Lista popolata via JavaScript con i suggerimenti JSON -->
        <ul id="suggerimenti" class="suggerimenti-lista"></ul>
      </div>
    </div>
    <div class="catalogo-grid" id="catalogoGrid">

        <jsp:include page="/fragments/catalogo-grid.jsp" />
	</div>
	</section>
	<jsp:include page="/fragments/footer.jsp" />
  <script>
    const contextPath = "${pageContext.request.contextPath}";
  </script>
  <script src="${pageContext.request.contextPath}/js/catalogo.js"></script>
</body>
</html>