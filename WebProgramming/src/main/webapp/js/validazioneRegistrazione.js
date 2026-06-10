document.addEventListener("DOMContentLoaded", function() {
    const formRegistrazione = document.getElementById("formRegistrazione");
    
    if (formRegistrazione) {
        // Ascoltiamo l'evento 'submit' (quando l'utente preme il bottone invia)
        formRegistrazione.addEventListener("submit", function(event) {
            
           
            const nomeInput = document.getElementById("nome").value;
            const cognomeInput = document.getElementById("cognome").value;
            const cellulareInput = document.getElementById("cellulare").value;
            const prefissoInput = document.getElementById("prefisso").value;
            const passwordInput = document.getElementById("password").value;
			const emailInput= document.getElementById("email").value;
            

            // 2. Nascondiamo tutti gli errori precedenti
            document.querySelectorAll('.js-error').forEach(el => el.style.display = 'none');
            
            let isValid = true;
            
            // 3. Eseguiamo le validazioni
            // Validazione Nome
            if (!validateNome(nomeInput)) {
                const err = document.getElementById("nome-error");
                if(err) err.style.display = "block";
                isValid = false;
            }
            
            // Validazione Cognome
            if (!validateCognome(cognomeInput)) {
                const err = document.getElementById("cognome-error");
                if(err) err.style.display = "block";
                isValid = false;
            }
            
            // Validazione Prefisso
            if (!validatePrefisso(prefissoInput)) {
                const err = document.getElementById("prefisso-error");
                if(err) err.style.display = "block";
                isValid = false;
            }
            
            // Validazione Cellulare
            if (!validateCellulare(cellulareInput)) {
                const err = document.getElementById("cellulare-error");
                if(err) err.style.display = "block";
                isValid = false;
            }
            
            // Validazione Password
            if (!validatePassword(passwordInput)) {
                const err = document.getElementById("password-error");
                if(err) err.style.display = "block";
                isValid = false;
            }
			
			if(!validateEmail(emailInput)){
				const err = document.getElementById("email-error");
				if(err) err.style.display = "block";
				isValid = false;
			}
            
            // 4. Se qualcosa non è valido, blocchiamo l'invio alla Servlet Java
            if (!isValid) {
                event.preventDefault(); // Ora funziona perché 'event' è passato nella funzione!
            }
        });
    }
});