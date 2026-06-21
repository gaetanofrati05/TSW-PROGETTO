// Prendiamo gli elementi HTML che ci servono
var barraRicerca = document.getElementById("barraRicerca");
var catalogoGrid = document.getElementById("catalogoGrid");
var listaSuggerimenti = document.getElementById("suggerimenti");

// Un solo timer: aspettiamo 300ms dopo l'ultimo tasto premuto
var timer = null;

barraRicerca.addEventListener("input", function () {

    // Se l'utente digita di nuovo, annulliamo il timer precedente
    clearTimeout(timer);

    timer = setTimeout(function () {

        var testo = barraRicerca.value.trim();

        // ---- PARTE 1: SUGGERIMENTI ----
        // Svuotiamo la lista e la nascondiamo
        listaSuggerimenti.innerHTML = "";
        listaSuggerimenti.style.display = "none";

        if (testo !== "") {
            // Stessa servlet della griglia, ma con soloNomi=true ci manda JSON
            fetch(contextPath + "/ricerca/prodotti?soloNomi=true&nome=" + encodeURIComponent(testo))
                .then(function (risposta) {
                    if (!risposta.ok) {
                        throw new Error("Errore suggerimenti");
                    }
                    return risposta.json();
                })
                .then(function (prodotti) {

                    // Per ogni prodotto trovato, creiamo una riga cliccabile
                    for (var i = 0; i < prodotti.length; i++) {
                        var riga = document.createElement("li");
                        riga.textContent = prodotti[i].nome;

                        // Quando clicchi un suggerimento, copi il nome nella barra
                        riga.onclick = function () {
                            barraRicerca.value = this.textContent;
                            listaSuggerimenti.style.display = "none";
                        };

                        listaSuggerimenti.appendChild(riga);
                    }

                    // Se c'è almeno un risultato, mostriamo la lista
                    if (prodotti.length > 0) {
                        listaSuggerimenti.style.display = "block";
                    }
                })
                .catch(function (errore) {
                    console.error(errore);
                });
        }

        // ---- PARTE 2: GRIGLIA PRODOTTI ----
        var url = contextPath + "/ricerca/prodotti";

        if (testo !== "") {
            url = url + "?nome=" + encodeURIComponent(testo);
        }

        fetch(url)
            .then(function (risposta) {
                // Trasformiamo la risposta in testo HTML
                return risposta.text();
            })
            .then(function (html) {
                // Sostituiamo il contenuto della griglia con le nuove card ricevute
                catalogoGrid.innerHTML = html;
            });

    }, 300);

});
