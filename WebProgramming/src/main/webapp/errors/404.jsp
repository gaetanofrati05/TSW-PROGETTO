<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404 - Luxury Non Trovata</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
    <link rel="stylesheet" href= "${pageContext.request.contextPath}/css/componenti.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagerror.css">
</head>
<body>


    <jsp:include page="/fragments/navbar.jsp" />

    <main class="error-wrapper">
        <div class="error-content">
        <div class="text-block">
            <span class="error-code">Errore 404</span>
            <h1 class="error-title">Fuori bersaglio.</h1>
            <h2 class="error-subtitle">Ricarica il colpo...</h2>
            
            
                <p class="error-text">
                    Le nostre tavolette vantano una precisione millimetrica al millesimo di millimetro. 
                    Tu, invece, hai appena mancato una pagina intera. Ricomponiti, prenditi il tuo tempo 
                    e mira al link corretto.
                </p>
            </div>
            
            <div class="error-action-box">
               <div class="box-buttons">
                <a href="${pageContext.request.contextPath}/index.jsp" class="button">Centra la Home</a>
                </div>
            </div>
        </div>
        
    </main>

</body>
</html>