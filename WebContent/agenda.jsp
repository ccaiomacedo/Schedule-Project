<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import = "model.JavaBeans" %>
    <%@page import ="java.util.ArrayList" %>
    <%
    	ArrayList<JavaBeans> lista = (ArrayList<JavaBeans>) request.getAttribute("contatos");
    %>
    
    
<!DOCTYPE html>
<html>
	<head>
	<meta charset="utf-8">
	<title>Agenda de contatos</title>
	<link rel="icon" href="imagem/fone.png">
	<link rel="stylesheet" href="style.css">
	</head>
	<body>	
		<h1>Agenda de contatos</h1>
		<a href="novo.html" class="botao1">Novo contato</a>
		
		<table id="tabela">
		
			<thead>
				<tr>
					<th>Id</th>
					<th>Nome</th>
					<th>Fone</th>
					<th>Email</th>						
				</tr>
			</thead>
			
			<tbody>
			<%for(int i =0;i<lista.size();i++){ %>
				<tr>
					<td><%=lista.get(i).getIdcon()%></td>
					<td><%=lista.get(i).getNome()%></td>
					<td><%=lista.get(i).getFone()%></td>
					<td><%=lista.get(i).getEmail()%></td>
				</tr>
			<%} %>
			</tbody>
		
		</table>
	</body>
</html>