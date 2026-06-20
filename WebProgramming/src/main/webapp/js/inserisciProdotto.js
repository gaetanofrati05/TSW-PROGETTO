function aggiornaNomeFileImmagine(input) {
    const fileNameDisplay = document.getElementById("file-name-display");
    if (!fileNameDisplay) {
        return;
    }

    if (input.files && input.files.length > 0) {
        fileNameDisplay.textContent = input.files[0].name;
        fileNameDisplay.style.color = "var(--nero, #14181B)";
        fileNameDisplay.style.fontStyle = "normal";
    } else {
        fileNameDisplay.textContent = "Nessun file selezionato (JPG, JPEG, PNG)";
        fileNameDisplay.style.color = "var(--pietra, #5A6670)";
        fileNameDisplay.style.fontStyle = "italic";
    }
}

(function () {
    const immagineInput = document.getElementById("immagineProdotto");
    if (immagineInput) {
        immagineInput.addEventListener("change", function () {
            aggiornaNomeFileImmagine(this);
        });
    }

    const formInserisciProdotto = document.getElementById("formInserisciProdotto");
    if (!formInserisciProdotto) {
        return;
    }

    formInserisciProdotto.addEventListener("submit", function (event) {
        const nomeInput = document.getElementById("nomeProdotto").value;
        const stileInput = document.getElementById("stileProdotto").value;
        const coloreInput = document.getElementById("coloreProdotto").value;
        const dimensioniInput = document.getElementById("dimensioniProdotto").value;
        const prezzoInput = document.getElementById("prezzoProdotto").value;
        const quantitaInput = document.getElementById("quantitaProdotto").value;
        const descrizioneInput = document.getElementById("descrizioneProdotto").value;
        const immagineInputSubmit = document.getElementById("immagineProdotto");

        document.querySelectorAll(".js-error").forEach(function (el) {
            el.style.display = "none";
        });

        let isValid = true;

        if (!validateNomeProdotto(nomeInput)) {
            document.getElementById("nomeProdotto-error").style.display = "block";
            isValid = false;
        }

        if (!validateStile(stileInput)) {
            document.getElementById("stileProdotto-error").style.display = "block";
            isValid = false;
        }

        if (!validateColore(coloreInput)) {
            document.getElementById("coloreProdotto-error").style.display = "block";
            isValid = false;
        }

        if (!validateDimensioni(dimensioniInput)) {
            document.getElementById("dimensioniProdotto-error").style.display = "block";
            isValid = false;
        }

        if (!validatePrezzo(prezzoInput)) {
            document.getElementById("prezzoProdotto-error").style.display = "block";
            isValid = false;
        }

        if (!validateQuantita(quantitaInput)) {
            document.getElementById("quantitaProdotto-error").style.display = "block";
            isValid = false;
        }

        if (!validateDescrizione(descrizioneInput)) {
            document.getElementById("descrizioneProdotto-error").style.display = "block";
            isValid = false;
        }

        if (!validateImmagine(immagineInputSubmit)) {
            document.getElementById("immagineProdotto-error").style.display = "block";
            isValid = false;
        }

        if (!isValid) {
            event.preventDefault();
        }
    });
})();
