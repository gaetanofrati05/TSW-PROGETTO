document.addEventListener("DOMContentLoaded", function(){
	const modificaForm=docuement.getElementById("modifica-form");
	if(modificaForm){
		const nome=document.getElementById("nome").value;
		const cognome= document.getElementById("cognome").value;
		const cellulare= document.getElementById("cellulare").value;
		const prefisso= documenti.getElementById("prefisso").value;
		
		document.querySelectorAll('.js-error').forEach(el => el.style.display = 'none');
		let isValid=true;
		if (!validateNome(nomeInput)) {
		                document.getElementById("nome-error").style.display = "block";
		                isValid = false;
		            }
		            
		            // 2. Validazione Cognome
		            if (!validateCognome(cognomeInput)) {
		                document.getElementById("cognome-error").style.display = "block";
		                isValid = false;
		            }
		            
		            // 3. Validazione Prefisso
		            if (!validatePrefisso(prefissoInput)) {
		                document.getElementById("prefisso-error").style.display = "block";
		                isValid = false;
		            }
		            
		            // 4. Validazione Cellulare
		            if (!validateCellulare(cellulareInput)) {
		                document.getElementById("cellulare-error").style.display = "block";
		                isValid = false;
		            }
		            
		            // Se qualcosa non va, fermiamo l'invio delle modifiche
		            if (!isValid) {
		                event.preventDefault();
		            }
	}
});