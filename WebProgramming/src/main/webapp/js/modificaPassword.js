document.addEventListener("DOMContentLoaded", function(){
	const formCambiaPassword= document.getElementById("form-modificaPassword").value;
	if(formCambiaPassword){
		formCambiaPassword.addEventListener("submit", function(event) {
	const nuovaPassword= document.getElementById("nuova-password").value;
	const confermaPassword= document.getElementById("conferma-password").value;
	let isValid=true;
	if (!validatePassword(nuovaPassword)) {
	    document.getElementById("password-error").style.display = "block";
	    isValid = false;
	            }
	   if(!validatePassword(confermaPassword)) {
	   document.getElementById("password-error").style.display = "block";
		isValid = false
								}
		if (!isValid){ 
		event.preventDefault();
		 }
	});
  }
 });