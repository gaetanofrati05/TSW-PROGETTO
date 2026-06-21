<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>403 - Luxury Non Accessibile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/componenti.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagerror.css">
</head>
<body>

    
    <jsp:include page="/fragments/navbar.jsp" />

   
    <main class="error-wrapper">
        
        <div class="error-content">
        <div class="text-block">
            <span class="error-code">Errore 403</span>
            <h1 class="error-title">Il trono è attualmente occupato</h1>
            <h2 class="error-subtitle">O forse, semplicemente, non possiedi l'invito per sederti.</h2>
            
           
                <p class="error-text">
                    L'eleganza richiede regole ferree e assoluta privacy. Hai appena tentato di varcare la soglia di un'area riservata esclusivamente alla nostra élite. Ti invitiamo a fare un passo indietro con la dovuta compostezza, prima che intervenga il nostro concierge.
                </p>
            </div>
            
            <div class="error-action-box">
            <div class="box-buttons">
                <a href="${pageContext.request.contextPath}" class="button">Acquista la tua tavoletta</a>
                <a href="${pageContext.request.contextPath}/LoginServlet" class="second-button">Unisciti alla nostra famiglia</a>
            </div>
             </div>
             </div>
        <div class="error-media error-media-403">
            <img class="left-image" src="${pageContext.request.contextPath}/img/403.jpeg" alt="Accesso riservato">
        </div>
    </main>

</body>
</html>