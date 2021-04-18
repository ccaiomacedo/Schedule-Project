/**
 * validação de formulário
 */

function validar() {
	let nome = frmContato.nome.value
	let fone = frmContato.fone.value
	if(nome === ""){
		alert('Preencha o campo nome')
		frmContato.nome.focus()//foca o cursor em cima do espaço de nome
		return false
	}else if(fone===""){	
		alert('Preencha o campo fone')
		frmContato.fone.focus()
		return false
	}else{
		document.forms("frmContato").submit()//submete os dados para a classe controller
	}
	
	
	
	
}