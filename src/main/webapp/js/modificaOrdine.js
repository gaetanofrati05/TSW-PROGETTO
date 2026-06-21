// file js per la modifica dell'ordine
// FUNZIONE PER CARICARE I DATI NEL MINI-FORM
function caricaModificaOrdine(idOrdinazione) {
    fetch(`${contextPath}/ModificaOrdineAdminServlet?idOrdinazione=${idOrdinazione}`)
    .then(response => {
        if (!response.ok) {
            throw new Error("Errore network: " + response.status);
        }
        return response.json();
    })
    .then(ordine => {
        // Popoliamo i campi del form con i dati ricevuti dal JSON
        document.getElementById('idOrdinazione').value = ordine.idOrdinazione;
        document.getElementById('email').value = ordine.email || "";
        document.getElementById('citta').value = ordine.citta || "";
        document.getElementById('indirizzo').value = ordine.indirizzo || "";
        document.getElementById('civico').value = ordine.civico || "";
        document.getElementById('cap').value = ordine.cap || "";
        document.getElementById('stato').value = ordine.stato || "";
        document.getElementById('dataOrdinazione').value = ordine.dataOrdinazione || "";
        document.getElementById('importo').value = ordine.importo || "0.0";

        // Mostriamo il form
        document.getElementById('form-modifica-ordine-admin').style.display = 'block';
    })
    .catch(error => {
        console.error('Errore nel caricamento del form di modifica:', error);
        alert('Impossibile caricare i dettagli dell\'ordine.'); 
    });
}

// 2. FUNZIONE PER SALVARE LE MODIFICHE (NOTARE IL POST)
function salvaModificaOrdine(event) {
    event.preventDefault(); // Evita il submit classico HTML
    
    // Recuperiamo i valori aggiornati dai campi
    const idOrdinazione = document.getElementById('idOrdinazione').value;
    const email = document.getElementById('email').value;
    const citta = document.getElementById('citta').value;
    const indirizzo = document.getElementById('indirizzo').value;
    const civico = document.getElementById('civico').value;
    const cap = document.getElementById('cap').value;
    const stato = document.getElementById('stato').value;
    const dataOrdinazione = document.getElementById('dataOrdinazione').value;
    const importo = document.getElementById('importo').value;

    // Prepariamo i parametri codificati come un form classico per la Servlet
    const params = new URLSearchParams();
    params.append('idOrdinazione', idOrdinazione);
    params.append('email', email);
    params.append('citta', citta);
    params.append('indirizzo', indirizzo);
    params.append('civico', civico);
    params.append('cap', cap);
    params.append('stato', stato);
    params.append('dataOrdinazione', dataOrdinazione);
    params.append('importo', importo);

    // Eseguiamo la fetch in POST
    fetch(`${contextPath}/ModificaOrdineAdminServlet`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: params
    })
    .then(response => response.json()) //risposta in JSON
    .then(risultato => { 
        // Se la modifica è andata a buon fine, mostriamo un messaggio di successo e chiudiamo il form
        if (risultato.successo) {
            alert('Ordine modificato con successo!');
            document.getElementById('form-modifica-ordine-admin').style.display = 'none';
            // Ricarichiamo la pagina per aggiornare la lista degli ordini
            window.location.reload(); 
        } else {
            alert('Errore durante il salvataggio: ' + risultato.messaggio);
        }
    })
    .catch(error => {
        console.error('Errore nella modifica dell\'ordine:', error);
        alert('Errore di rete durante il salvataggio.');
    });
}

function resetFiltriOrdini() {
    window.location.href = contextPath + "/GestioneOrdiniAdminServlet";
}

// 3. FUNZIONE PER CHIUDERE IL MINI-FORM
function chiudiModificaOrdine() {
    document.getElementById('form-modifica-ordine-admin').style.display = 'none';
}

document.addEventListener('DOMContentLoaded', function () {
    // Aggiungi event listener ai pulsanti di modifica
    document.querySelectorAll('.js-modifica-ordine').forEach(function (link) {
        link.addEventListener('click', function (event) {
            event.preventDefault();
            caricaModificaOrdine(link.getAttribute('data-id-ordinazione'));
        });
    });

    // Aggiungi event listener al form di submit
    const form = document.getElementById('modificaOrdineForm');
    if (form) {
        form.addEventListener('submit', salvaModificaOrdine);
    }
});
