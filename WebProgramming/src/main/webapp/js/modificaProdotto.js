//file js usato per la modifica del prodotto

// file js per la modifica dell'ordine
// FUNZIONE PER CARICARE I DATI NEL MINI-FORM
function caricaModificaProdotto(link) {
    const row = link.closest('tr');
    const celle = row ? row.querySelectorAll('td') : [];

    if (!row || celle.length < 9) {
        alert('Impossibile caricare i dettagli del prodotto.');
        return;
    }

    document.getElementById('idProdotto').value = link.getAttribute('data-id-prodotto') || "";
    document.getElementById('nome').value = celle[1].textContent.trim();
    document.getElementById('stile').value = celle[2].textContent.trim();
    document.getElementById('colore').value = celle[3].textContent.trim();
    document.getElementById('dimensioni').value = celle[4].textContent.trim();
    document.getElementById('prezzo').value = celle[5].textContent.trim();
    document.getElementById('quantita').value = celle[6].textContent.trim();
    document.getElementById('descrizione').value = celle[7].textContent.trim();
    document.getElementById('immagine').value = celle[8].textContent.trim();

    document.getElementById('form-modifica-prodotto-admin').style.display = 'block';
}

// 2. FUNZIONE PER SALVARE LE MODIFICHE (NOTARE IL POST)
function salvaModificaProdotto(event) {
    event.preventDefault(); // Evita il submit classico HTML
    
    // Recuperiamo i valori aggiornati dai campi
    const idProdotto = document.getElementById('idProdotto').value;
    const nome = document.getElementById('nome').value;
    const stile = document.getElementById('stile').value;
    const colore = document.getElementById('colore').value;
    const dimensioni = document.getElementById('dimensioni').value;
    const prezzo = document.getElementById('prezzo').value;
    const quantita = document.getElementById('quantita').value;
    const descrizione = document.getElementById('descrizione').value;
    const immagine = document.getElementById('immagine').value;

    // Prepariamo i parametri codificati come un form classico per la Servlet
    const params = new URLSearchParams();
    params.append('idProdotto', idProdotto);
    params.append('nome', nome);
    params.append('stile', stile);
    params.append('colore', colore);
    params.append('dimensioni', dimensioni);
    params.append('prezzo', prezzo);
    params.append('quantita', quantita);
    params.append('descrizione', descrizione);
    params.append('immagine', immagine);

    // Eseguiamo la fetch in POST
    fetch(`${contextPath}/ModificaProdottoServlet`, {
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
            alert('Prodotto modificato con successo!');
            document.getElementById('form-modifica-prodotto-admin').style.display = 'none';
            // Ricarichiamo la pagina per aggiornare la lista degli ordini
            window.location.reload(); 
        } else {
            alert('Errore durante il salvataggio: ' + risultato.messaggio);
        }
    })
    .catch(error => {
        console.error('Errore nella modifica del prodotto:', error);
        alert('Errore di rete durante il salvataggio.');
    });
}

function resetFiltriProdotti() {
    window.location.href = contextPath + "/CatalogoAdminServlet";
}

// 3. FUNZIONE PER CHIUDERE IL MINI-FORM
function chiudiModificaProdotto() {
    document.getElementById('form-modifica-prodotto-admin').style.display = 'none';
}

document.addEventListener('DOMContentLoaded', function () {
    // Aggiungi event listener ai pulsanti di modifica
    document.querySelectorAll('.js-modifica-prodotto').forEach(function (link) {
        link.addEventListener('click', function (event) {
            event.preventDefault();
            caricaModificaProdotto(link);
        });
    });

    // Aggiungi event listener al form di submit
    const form = document.getElementById('modificaProdottoForm');
    if (form) {
        form.addEventListener('submit', salvaModificaProdotto);
    }
});