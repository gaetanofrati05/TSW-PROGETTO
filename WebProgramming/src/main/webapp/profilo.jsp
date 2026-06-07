<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="bean.UtenteBean"%>
    <%
    Object utenteSessione =session.getAttribute("utenteLoggato");
    String nome="";
    if(utenteSessione!=null){
    	UtenteBean utente= (UtenteBean) utenteSessione;
    	nome=utente.getNome();
    }else{
    	nome="Ospite";
    }
    %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Profilo</title>
<link rel="stylesheet" href="profilo.css">
</head>
<body>
 <div class="pagina-profilo">
   <h1 class="titolo-profilo">Benvenuto nella sua luonge <%= nome %></h1>
  </div>
   <div class="dropdown">
    <button onclick="btnonclick" class="dropbtn">Menù</button>
    <div class="dropdwn-content">
     <a href="">Area Personale</a>
     <a href="">I miei ordini</a>
     <a href="">Impostazioni account</a>
     <a href="LogoutServelt">Esci</a>
     </div>
    </div>
 </body>
</html>