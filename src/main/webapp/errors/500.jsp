<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
<meta charset="UTF-8">
<title>500-error</title>
 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/componenti.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagerror.css">
  </head>
<body>

<body>
    <jsp:include page="/fragments/navbar.jsp" />
    
    <main class="error-wrapper">
    
       <div class="error-content">
       
       <div class="text-block">
        <span class="error-code">Errore 500</span>
         <h1 class="error-title">Una temporanea deviazione dal perfetto rigore.</h1>
         <h2 class="error-subtitle">Anche l'eccellenza richiede una pausa di riflessione.</h2>
        
        
        <p class="error-text">
            Crediamo nella fluidità delle linee e nell'armonia geometrica, ma il nostro sistema 
            ha appena subito un'imperfezione tecnica. Un evento decisamente poco <em>luxury</em>. 
            I nostri ingegneri stanno già ripristinando la compostezza del backend. Nel frattempo, 
            la invitiamo a ritrovare l'equilibrio.
        </p>
        
        </div>
        <div class="error-action-box">
        <div class="box-buttons">
            <a href="${pageContext.request.contextPath}" class="button">Ritorna alla Home Luxury</a>
            </div>
        </div>
    </div>
      </main>

</body>
</body>
</html>