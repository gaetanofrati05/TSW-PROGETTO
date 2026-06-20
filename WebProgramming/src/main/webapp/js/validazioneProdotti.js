function validateNomeProdotto(nomeprodotto){
    const regex=/^[A-Za-zàèòù\d\s'-]{2,50}$/;
    return regex.test(nomeprodotto);
}

function validateStile(stileprodotto){
    const regex=/^[A-Za-zàèòù\s'-]{2,10}$/;
    return regex.test(stileprodotto);
}

function validateColore(coloreprodotto){
    const regex=/^[A-Za-zàèùò\s']{2,10}$/;
    return regex.test(coloreprodotto);
}

function validateDimensioni(dimensioniprodotto){
    const regex=/^[0-9]{1,3}x[0-9]{1,3}x[0-9]{1,3}$/;
    return regex.test(dimensioniprodotto);
}

function validatePrezzo(prezzoprodotto){
    const regex=/^\d{1,10}(\.\d{1,2})?$/; //prima parte numeri da 1 a 10 dopo punto numeri da 1 a 2 opzionale
    return regex.test(prezzoprodotto);
}

function validateQuantita(quantitaprodotto){
    const regex=/^\d{1,10}$/;
    return regex.test(quantitaprodotto);
}

function validateDescrizione(descrizioneprodotto){
    const regex=/^[A-Za-zàèòù\d\s'-]{2,250}$/;
    return regex.test(descrizioneprodotto);
}

function validateImmagine(immagineProdotto) {
    const immagineInput = immagineProdotto || document.getElementById("immagineProdotto");
    if (!immagineInput || !immagineInput.files || immagineInput.files.length === 0) {
        return false;
    }

    const file = immagineInput.files[0];
    const allowedTypes = ["image/jpeg", "image/png"];
    const fileNameLower = file.name.toLowerCase();
    const allowedExtensions = [".jpg", ".jpeg", ".png"];
    const hasValidExtension = allowedExtensions.some(function (ext) {
        return fileNameLower.endsWith(ext);
    });
    const fileSize = 1024 * 1024 * 5;

    if (file.size === 0) {
        return false;
    }

    if (!allowedTypes.includes(file.type) && !hasValidExtension) {
        return false;
    }

    if (file.size > fileSize) {
        return false;
    }

    return true;
}