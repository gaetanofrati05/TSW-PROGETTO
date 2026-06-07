


function validateEmail(uemail){
	const regex= /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
	return regex.test(uemail);
}
function validatePassword(upassword){
	const regex=/^[A-Za-z0-9@#!?\s_èòàù]{8,16}$/;
	return regex.test(upassword);	
}
function validateCellulare(ucellulare){
	const regex=/^\d{9,10}$/;
	return regex.test(ucellulare);
}
function validatePrefisso(uprefisso){
	const regex=/^\+\d{1,3}$/;
	return regex.test(uprefisso);
}
function validateNome(unome){
	const regex=/^[A-Za-zàèòù\s'-]{4,30}$/;
	return regex.test(unome);
}
function validateCognome(ucognome){
	const regex=/^[A-Za-zàèòù\s'-]{4,30}$/;
	return regex.test(ucognome);
}
