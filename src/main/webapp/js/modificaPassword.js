document.addEventListener("DOMContentLoaded", function(){
	const formCambiaPassword= document.getElementById("formRegistrazione");
	if(formCambiaPassword){
		formCambiaPassword.addEventListener("submit", function(event) {
	const nuovaPassword= document.getElementById("nuovaPassword").value;
	const confermaPassword= document.getElementById("confermaPassword").value;
	let isValid=true;
	document.getElementById("password-error-1").style.display = "none";
	document.getElementById("password-error-2").style.display = "none";
	if (!validatePassword(nuovaPassword)) {
	    document.getElementById("password-error-1").style.display = "block";
	    isValid = false;
	            }
	   if(!validatePassword(confermaPassword)) {
	   document.getElementById("password-error-2").style.display = "block";
		isValid = false;
		}
        if(nuovaPassword!=confermaPassword){
			document.getElementById("password-error-2").style.display="block";
			isValid=false;
		}
		if (!isValid){ 
		event.preventDefault();
		 }
	});
  }
 });