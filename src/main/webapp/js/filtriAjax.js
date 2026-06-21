// Funzione per filtrare gli ordini via AJAX
function filtraOrdini(valoreFiltro) {
    // Usiamo una variabile globale 'contextPath' che definiremo nella JSP
    fetch(contextPath + '/GetOrderAjaxServlet?sortOrdinazioni=' + encodeURIComponent(valoreFiltro))
        .then(response => response.text())
        .then(htmlRicevuto => {
            document.getElementById("corpo-tabella-ordini").innerHTML = htmlRicevuto;
        })
        .catch(error => console.error('Errore AJAX Ordini:', error));
}

// Funzione per filtrare le recensioni via AJAX
function filtraRecensioni(valoreFiltro) {
    fetch(contextPath + '/GetOrderAjaxServlet?sortRecensioni=' + encodeURIComponent(valoreFiltro))
        .then(response => response.text())
        .then(htmlRicevuto => {
            document.getElementById("corpo-recensioni").innerHTML = htmlRicevuto;
        })
        .catch(error => console.error('Errore AJAX Recensioni:', error));
}