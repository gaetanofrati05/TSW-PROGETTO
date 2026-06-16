const barraRicerca = document.getElementById("barraRicerca");
const catalogoGrid = document.getElementById("catalogoGrid");
let timer = null;

barraRicerca.addEventListener("input", function () {
    clearTimeout(timer);
    timer = setTimeout(() => {
        const nome = barraRicerca.value.trim();
        const url = nome
            ? `${contextPath}/ricerca/prodotti?nome=${encodeURIComponent(nome)}`
            : `${contextPath}/ricerca/prodotti`;

        fetch(url)
            .then(risposta => {
                if (!risposta.ok) {
                    throw new Error("Errore nella richiesta");
                }
                return risposta.text();
            })
            .then(html => {
                catalogoGrid.innerHTML = html;
            })
            .catch(errore => {
                console.error(errore);
            });
    }, 300);
});
