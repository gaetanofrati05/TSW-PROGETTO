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
            <h1 class="error-title">Accesso riservato.</h1>
            <h2 class="error-subtitle">Fermati, non hai i permessi per accedere a questa lounge!</h2>
            
           
                <p class="error-text">
                    Le nostre lounge sono riservate agli ospiti con tavoletta attiva. 
                    Acquista la tua, torna qui e goditi il relax che meriti.
                </p>
            </div>
            
            <div class="error-action-box">
            <div class="box-buttons">
                <a href="${pageContext.request.contextPath}" class="button">Acquista la tua tavoletta</a>
                <a href="${pageContext.request.contextPath}/LoginServlet" class="second-button">Unisciti alla nostra famiglia</a>
            </div>
             </div>
             </div>
    </main>

</body>
</html>