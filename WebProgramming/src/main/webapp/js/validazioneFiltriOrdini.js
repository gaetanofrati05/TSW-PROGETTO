//funzione per validare i filtri degli ordini e assicurarsi che i campi

// siano compilati e che le date siano entrambe presenti
function validateFiltriOrdini(){
    const idOrdinazione = document.getElementById("filtro-idOrdinazione").value.trim();
    const email = document.getElementById("filtro-email").value.trim();
    const dataInizio = document.getElementById("filtro-dataInizio").value;
    const dataFine = document.getElementById("filtro-dataFine").value;
    const stato = document.getElementById("filtro-stato").value;

    if(idOrdinazione === "" && email === "" && dataInizio === "" && dataFine === "" && stato === ""){
        alert("Almeno un campo deve essere compilato");
        return false;
    }

    if((dataInizio === "" && dataFine !== "") || (dataInizio !== "" && dataFine === "")){
        alert("Devi inserire entrambe le date per filtrare per data");
        return false;
    }

    if(idOrdinazione !== "" && !validateidOrdinazione(idOrdinazione)){
        alert("L'ID ordine deve essere solo numerico");
        return false;
    }

    if(email !== "" && !validateEmail(email)){
        alert("L'email deve essere valida e contenere @ e . almeno una volta");
        return false;
    }

    if(dataInizio !== "" && !validateDataInizio(dataInizio)){
        alert("La data inizio non è valida");
        return false;
    }

    if(dataFine !== "" && !validateDataFine(dataFine)){
        alert("La data fine non è valida");
        return false;
    }

    if(dataInizio !== "" && dataFine !== "" && dataInizio > dataFine){
        alert("La data inizio deve essere precedente alla data fine");
        return false;
    }
    return true;
}

function validateidOrdinazione(idOrdinazione){
    const regex=/^\d+$/;
    return regex.test(idOrdinazione);
}

function validateEmail(email){
    const regex=/^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
    return regex.test(email);
}

function validateDataInizio(dataInizio){
    const regex=/^\d{4}-\d{2}-\d{2}$/;
    return regex.test(dataInizio);
}

function validateDataFine(dataFine){
    const regex=/^\d{4}-\d{2}-\d{2}$/;
    return regex.test(dataFine);
}

document.addEventListener("DOMContentLoaded", function() {
    const formFiltriOrdini = document.getElementById("formFiltriOrdini");
    if(formFiltriOrdini){
        formFiltriOrdini.addEventListener("submit", function(event) {
            if(!validateFiltriOrdini()){
                event.preventDefault();
            }
        });
    }
});


