<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="utf-8">
	<title>Agenda de contatos</title>
	<link rel="icon" href="imagem/fone.png">
	<link rel="stylesheet" href="style.css">
	</head>
	<body>	
		<h1>Editar contato</h1>
		<form name ="frmContato" action="update">
			<table>
				<tr>
					<td><input type="text" name="idcon" id="caixa3" readonly value="<%=request.getAttribute("idcon") %>"></td>
				</tr>
				<tr>
					<td><input type="text" name="nome" class="caixa1" value="<%=request.getAttribute("nome") %>"></td>
				</tr>
				<tr>
					<td><input type="text" name="fone"  class="caixa2" value="<%=request.getAttribute("fone") %>"></td>
				</tr>
				<tr>
					<td><input type="text" name="email"  class="caixa1" value="<%=request.getAttribute("email") %>"></td>
				</tr>
			</table>
			<input type="submit" value="Salvar" class="botao1" onclick="validar()">
		<!--  esse onclick executa a função validar() do java script  -->
		</form>
		<script src="Scripts/validador1.js"></script>
	</body>
</html>