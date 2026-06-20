document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".input-password-wrapper").forEach(function (wrapper) {
        const input = wrapper.querySelector("input"); //query selector restituisce una lista di nodi 
        const button = wrapper.querySelector(".toggle-pwd-btn"); 
        const icon = wrapper.querySelector(".toggle-pwd-icon");

        if (!input || !button) {
            return;
        }

        button.addEventListener("click", function () { //mettiamoci in ascolto se l'utente dovesse fare click
            const isHidden = input.type === "password"; //usiamo l'operatore ternario per confrontare tipo e valore
            input.type = isHidden ? "text" : "password";

            if (icon) {
                icon.className = isHidden ? "ti ti-eye-off toggle-pwd-icon" : "ti ti-eye toggle-pwd-icon"; //valori che specificano se l'occhio vede o meno
            }

            button.setAttribute("aria-label", isHidden ? "Nascondi password" : "Mostra password"); //è un if compatto: condizione ? valore_se_vero: valore_se_falso
        });
    });
});
