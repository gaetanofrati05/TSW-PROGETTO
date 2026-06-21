<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bean.RecensioneBean" %>
<%@ page import="java.util.List" %>
<%
    List<RecensioneBean> listaRecensioni = (List<RecensioneBean>) request.getAttribute("listaRecensioni");
%>
<% if (listaRecensioni != null && !listaRecensioni.isEmpty()) { %>
    <% for (RecensioneBean recensione : listaRecensioni) { %>
        <div class="recensione-card">
            <p class="prodotto-stile">Recensione</p>
            <p class="prodotto-name">
                <%= (recensione.getProdotto() != null && recensione.getProdotto().getNome() != null)
                    ? recensione.getProdotto().getNome() : "Prodotto non specificato" %>
            </p>
            <p class="recensione-autore"><%=recensione.getUtente().getNome() %> <%=recensione.getUtente().getCognome() %></p>
            <p class="recensione-valutazione">Valutazione: <strong><%= recensione.getScoring() %>/5</strong></p>
            <p class="recensione-descrizione">"<%= recensione.getDescrizione() %>"</p>
            <p class="recensione-data">Pubblicata il: <%= recensione.getDataRecensione() %></p>
        </div>
    <% } %>
<% } else { %>
    <p class="empty-msg">Non sono presenti recensioni.</p>
<% } %>
