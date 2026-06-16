let barraRicerca= document.getElementById("ricerca");
let catalogoGrid= document.getElementById("catalogoGrid");
let timer=null;
const contextPath = "${pageContext.request.contextPath}";
barraRicerca.addEventListener("input", function() { /*evento input avviene ad ogni digitazione*/
    let valoreRicerca= new URLSearchParams();
    valoreRicerca.append("nome", barraRicerca.value);
    clearTimeout(timer);
    timer = setTimeout(() => {
        fetch(`${contextPath}/ricerca/prodotti?nome=${valoreRicerca}`)
        .then(risposta => {
            if (!risposta.ok) {
                throw new Error("Errore nella richiesta");
            }
            return risposta.text(); // estrae il corpo della risposta come testo (HTML)
        })
        .then(html => {
            catalogoGrid.innerHTML = html; // inserisce l'HTML ricevuto nel div della grid
        })
        .catch(errore => {
            console.error(errore);
        });
    }, 300);
});

