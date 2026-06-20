document.addEventListener("DOMContentLoaded", function(){
	const modificaForm=document.getElementById("form-modifica");
	if(modificaForm){
		modificaForm.addEventListener("submit", function(event){
		const nomeInput=document.getElementById("nome").value;
		const cognomeInput= document.getElementById("cognome").value;
		const cellulareInput= document.getElementById("cellulare").value;
		const prefissoInput= document.getElementById("prefisso").value;
		
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
		            
		            // Se qualcosa non va fermo l'invio delle modifiche
		            if (!isValid) {
		                event.preventDefault();
		            }
	});
  }
 });