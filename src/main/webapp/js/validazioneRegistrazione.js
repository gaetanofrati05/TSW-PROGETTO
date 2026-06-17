document.addEventListener("DOMContentLoaded", function() {
    const formRegistrazione = document.getElementById("formRegistrazione");
    let emailGiaInUso = false;

    if (formRegistrazione) {
        const emailField = document.getElementById("email");

        emailField.addEventListener("blur", function() {
            const emailValue = emailField.value.trim();
            const err = document.getElementById("email-error");

            if (emailValue === "") {
                err.style.display = "none";
                emailGiaInUso = false;
                return;
            }

            if (!validateEmail(emailValue)) {
                err.textContent = "Email non valida";
                err.style.display = "block";
                emailGiaInUso = false;
                return;
            }

            fetch(contextPath + "/controllo/email?email=" + encodeURIComponent(emailValue))
                .then(function(risposta) { return risposta.text(); })
                .then(function(messaggio) {
                    if (messaggio.trim() !== "") {
                        err.textContent = messaggio;
                        err.style.display = "block";
                        emailGiaInUso = true;
                    } else {
                        err.style.display = "none";
                        emailGiaInUso = false;
                    }
                })
                .catch(function(errore) { console.error(errore); });
        });

        formRegistrazione.addEventListener("submit", function(event) {
            const nomeInput = document.getElementById("nome").value;
            const cognomeInput = document.getElementById("cognome").value;
            const cellulareInput = document.getElementById("cellulare").value;
            const prefissoInput = document.getElementById("prefisso").value;
            const passwordInput = document.getElementById("password").value;
            const emailInput = document.getElementById("email").value;

            document.querySelectorAll('.js-error').forEach(function(el) { el.style.display = 'none'; });

            let isValid = true;

            if (!validateNome(nomeInput)) {
                document.getElementById("nome-error").style.display = "block";
                isValid = false;
            }

            if (!validateCognome(cognomeInput)) {
                document.getElementById("cognome-error").style.display = "block";
                isValid = false;
            }

            if (!validatePrefisso(prefissoInput)) {
                document.getElementById("prefisso-error").style.display = "block";
                isValid = false;
            }

            if (!validateCellulare(cellulareInput)) {
                document.getElementById("cellulare-error").style.display = "block";
                isValid = false;
            }

            if (!validatePassword(passwordInput)) {
                document.getElementById("password-error").style.display = "block";
                isValid = false;
            }

            if (!validateEmail(emailInput)) {
                document.getElementById("email-error").textContent = "Email non valida";
                document.getElementById("email-error").style.display = "block";
                isValid = false;
            }

            if (emailGiaInUso) {
                document.getElementById("email-error").textContent = "Email già in uso";
                document.getElementById("email-error").style.display = "block";
                isValid = false;
            }

            if (!isValid) {
                event.preventDefault();
            }
        });
    }
});
