<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bean.OrdinazioneBean" %>
<%@ page import="java.util.List" %>
<%
    List<OrdinazioneBean> listaOrdinazioni = (List<OrdinazioneBean>) request.getAttribute("listaOrdinazioni");
%>
<% if (listaOrdinazioni != null && !listaOrdinazioni.isEmpty()) { 
    boolean ciSonoConsegnati = false; // Flag per verificare se esiste almeno un ordine consegnato
    
    for (OrdinazioneBean ordine : listaOrdinazioni) { 
        // Filtro: Considera solo gli ordini con stato "consegnato"
        if ("consegnato".equalsIgnoreCase(ordine.getStato())) { 
            ciSonoConsegnati = true;
%>
        <tr class="ordini-row">
            <td><%= ordine.getIdOrdinazione() %></td>
            <td><%= ordine.getCitta() != null ? ordine.getCitta() : "—" %></td>
            <td><%= ordine.getDataOrdinazione() %></td>
            <td>€ <%= ordine.getImporto() %></td>
            <td><span class="badge-stato"><%= ordine.getStato() %></span></td>
            <td>
                <%-- Link per inserire la recensione passando l'ID dell'ordine --%>
                <a class="scrivi-recensione" href="${pageContext.request.contextPath}/InserisciRecensioneServlet?idOrdinazione=<%= ordine.getIdOrdinazione() %>">
                    Dai una tua opinione
                </a>
            </td>
        </tr>
<% 
        } 
    } 
    
    // Se c'erano ordini nella lista ma nessuno era "consegnato"
    if (!ciSonoConsegnati) { 
%>
        <tr>
            <td colspan="6"><p class="empty-msg">Nessun ordine nello storico (consegnato) al momento.</p></td>
        </tr>
<% 
    }
} else { 
%>
    <tr>
        <%-- Cambiato colspan a 6 per coprire anche la nuova colonna delle recensioni --%>
        <td colspan="6"><p class="empty-msg">Nessun ordine effettuato</p></td>
    </tr>
<% } %>