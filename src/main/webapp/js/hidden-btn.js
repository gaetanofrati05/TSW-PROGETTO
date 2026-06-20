// File js per nascondere il bottone di selezione file e mostrare il nome del file selezionato

document.addEventListener("DOMContentLoaded", function() { // evento che viene invocato quando il DOM è completamente caricato
    const fileUpload = document.getElementById('file-upload'); // si prende l'input file
    const fileNameDisplay = document.getElementById('file-name-display'); // si prende l'elemento span che mostra il nome del file selezionato

    // Il controllo "if" serve a evitare errori nel caso questo JS 
    // venga incluso in pagine admin che non contengono l'input file
    if (fileUpload && fileNameDisplay) {
        fileUpload.addEventListener('change', function() { // evento che viene invocato quando il file viene selezionato
            if (this.files && this.files.length > 0) { // se il file è selezionato
                fileNameDisplay.textContent = this.files[0].name; // si setta il nome del file selezionato
                fileNameDisplay.style.color = 'var(--nero, #14181B)'; // si setta il colore del testo
            } else { // se non è selezionato nessun file
                fileNameDisplay.textContent = 'Nessun file selezionato'; // si setta il testo a 'Nessun file selezionato'
                fileNameDisplay.style.color = 'var(--pietra, #5A6670)'; // si setta il colore del testo
            }
        });
    } 
}); 