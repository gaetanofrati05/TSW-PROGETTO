document.addEventListener("DOMContentLoaded", function() {
    const loginForm = document.getElementById("loginForm");
    // Controllo che il form esista nella pagina attuale
    if (loginForm) {
        loginForm.addEventListener("submit", function(event) {
            const emailInput = document.getElementById("email").value;
            const passwordInput = document.getElementById("password").value;
            
            document.querySelectorAll('.js-error').forEach(el => el.style.display = 'none');
            
            let isValid = true;

            if (!validateEmail(emailInput)) {
                document.getElementById("email-error").style.display = "block";
                isValid = false;
            }

            if (!validatePassword(passwordInput)) {
                document.getElementById("password-error").style.display = "block";
                isValid = false;
            }

            if (!isValid) {
                event.preventDefault();
            }
        });
	   }
	});
	
	