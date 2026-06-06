<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>403-Accesso Negato</title>
</head>
<body>
<div class="error-container">
   <h1 class="error-403">403-error</h1>
   <h2>Accesso negato</h2>
 <div class="text-error">
 <p class="text">Accesso negato per questo server</p>
 </div>
  <div class="error-action>">
    <a href="${pageContext.request.contextPath }" class="button">Torna alla home</a>
    <a href="${pageContext.request.contextPath }"class="button-second">Torna al login</a>
    </div>
 </div>
</body>
</html>