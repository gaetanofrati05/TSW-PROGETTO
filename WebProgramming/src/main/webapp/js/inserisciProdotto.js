document.addEventListener("DOMContentLoaded", function() { //controllo che il form esista nella pagina attuale
    const formInserisciProdotto = document.getElementById("formInserisciProdotto"); //seleziono il form
    if(formInserisciProdotto){ //controllo che il form esista
        formInserisciProdotto.addEventListener("submit", function(event){
            const nomeInput=document.getElementById("nomeProdotto").value; //recupero il valore dell'input nomeProdotto
            const stileInput=document.getElementById("stileProdotto").value;
            const coloreInput=document.getElementById("coloreProdotto").value; //recupero il valore dell'input coloreProdotto
            const dimensioniInput=document.getElementById("dimensioniProdotto").value; //recupero il valore dell'input dimensioniProdotto
            const prezzoInput=document.getElementById("prezzoProdotto").value; //recupero il valore dell'input prezzoProdotto
            const quantitaInput=document.getElementById("quantitaProdotto").value; //recupero il valore dell'input quantitaProdotto
            const descrizioneInput=document.getElementById("descrizioneProdotto").value; //recupero il valore dell'input descrizioneProdotto
            const immagineInput=document.getElementById("immagineProdotto");

            document.querySelectorAll('.js-error').forEach(el => el.style.display = 'none'); //nascondo tutti gli errori precedenti

            let isValid=true;

            if(!validateNomeProdotto(nomeInput)){ //validazione nome prodotto
                document.getElementById("nomeProdotto-error").style.display = "block"; //mostro l'errore
                isValid = false; 
            }

            if(!validateStile(stileInput)){
                document.getElementById("stileProdotto-error").style.display = "block";
                isValid = false;
            }

            if(!validateColore(coloreInput)){
                document.getElementById("coloreProdotto-error").style.display = "block";
                isValid = false;
            }

            if(!validateDimensioni(dimensioniInput)){
                document.getElementById("dimensioniProdotto-error").style.display = "block";
                isValid = false;
            }

            if(!validatePrezzo(prezzoInput)){
                document.getElementById("prezzoProdotto-error").style.display = "block";
                isValid = false;
            }

            if(!validateQuantita(quantitaInput)){
                document.getElementById("quantitaProdotto-error").style.display = "block";
                isValid = false;
            }

            if(!validateDescrizione(descrizioneInput)){
                document.getElementById("descrizioneProdotto-error").style.display = "block";
                isValid = false;
            }

            if(!validateImmagine(immagineInput)){
                document.getElementById("immagineProdotto-error").style.display = "block";
                isValid = false;
            }

            if(!isValid){
                event.preventDefault(); //blocco l'invio del form se qualcosa non è valido
            }
        });
    }
});