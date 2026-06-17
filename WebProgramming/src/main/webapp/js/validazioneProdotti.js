function validateNomeProdotto(nomeprodotto){
    const regex=/^[A-Za-zàèòù\s'-]{2,50}$/;
    return regex.test(nomeprodotto);
}

function validateStile(stileprodotto){
    const regex=/^[A-Za-zàèòù\s'-]{2,10}$/;
    return regex.test(stileprodotto);
}

function validateColore(coloreprodotto){
    const regex=/^[A-za-zàèùò\s']{2,10}$/;
    return regex.test(coloreprodotto);
}

function validateDimensioni(dimensioniprodotto){
    const regex=/^[0-9]{1,3}x[0-9]{1,3}x[0-9]{1,3}$/;
    return regex.test(dimensioniprodotto);
}

function validatePrezzo(prezzoprodotto){
    const regex=/^\d{1,10$/;
    return regex.test(prezzoprodotto);
}

function validateQuantita(quantitaprodotto){
    const regex=/^\d{1,10}$/;
    return regex.test(quantitaprodotto);
}

function validateDescrizione(descrizioneprodotto){
    const regex=/^[A-Za-zàèòù\s'-]{2,250}$/;
    return regex.test(descrizioneprodotto);
}
