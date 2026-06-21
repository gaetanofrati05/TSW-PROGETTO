document.addEventListener("DOMContentLoaded", function() {
    const formOrdine = document.getElementById("formOrdine");
    if (!formOrdine) {
        return;
    }

    formOrdine.addEventListener("submit", function(event) {
        const citta = document.getElementById("citta").value;
        const indirizzo = document.getElementById("indirizzo").value;
        const civico = document.getElementById("civico").value;
        const cap = document.getElementById("cap").value;

        document.querySelectorAll(".js-error").forEach(function(el) {
            el.style.display = "none";
        });

        let isValid = true;

        if (!validateNazione(citta)) {
            document.getElementById("citta-error").style.display = "block";
            isValid = false;
        }
        if (!validateNome(indirizzo)) {
            document.getElementById("indirizzo-error").style.display = "block";
            isValid = false;
        }
        if (!validateCivico(civico)) {
            document.getElementById("civico-error").style.display = "block";
            isValid = false;
        }
        if (!validateCap(cap)) {
            document.getElementById("cap-error").style.display = "block";
            isValid = false;
        }

        if (!isValid) {
            event.preventDefault();
        }
    });
});
