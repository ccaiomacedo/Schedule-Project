package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DAO;
import model.JavaBeans;

/**
 * Servlet implementation class Controller
 */
@WebServlet(urlPatterns = { "/Controller", "/main","/insert","/select","/update" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	JavaBeans contato = new JavaBeans();

	public Controller() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//retorna o endereço que faz com que a servlet seja executada.
		String action = request.getServletPath();
		System.out.println(action);
		
		//verifica se o pedido para acessar a página main foi concedido
		if (action.equals("/main")) {
			contatos(request, response);
		//se n for ele volta pra página insert	
		}else if(action.equals("/insert")) {
			novoContato(request,response);
		}else if(action.equals("/select")) {
			listarContato(request,response);
		}else if(action.equals("/update")){
			editarContato(request,response);
		}else {
			response.sendRedirect("index.html");
		}

	}
			//listar contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			//criar um objeto que irá receber os dados javaBeans
			ArrayList<JavaBeans> lista = dao.listarContatos();//ele executa o metodo que tem no dao
			
			//teste de recebimento da lista
			/*
			for(int i= 0;i<lista.size();i++) {
				System.out.println(lista.get(i).getIdcon());
				System.out.println(lista.get(i).getNome());
				System.out.println(lista.get(i).getFone());
				System.out.println(lista.get(i).getEmail());
				
			}*/
			
			//enviar lista pra agenda.jsp
			request.setAttribute("contatos",lista);
			RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
			rd.forward(request, response);
	
	}
			//listar novo contato
	protected void novoContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//teste de reconhecimento dos dados do formulário
		/*
		System.out.println(request.getParameter("nome"));
		System.out.println(request.getParameter("fone"));
		System.out.println(request.getParameter("email"));*/
		
		//ele armazena na variavel setNome o parametro nome passado 
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		//invocar o metodo inserirContato passando  o objeto contato
		dao.inserirContato(contato);
		//redirecionar pra agenda.jsp
		response.sendRedirect("main");
		
	}
	//Editar contatos
	protected void listarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//recebimento do id do contato que será editado
		String idcon = request.getParameter("idcon");
		//setar a variável JavaBeans
		contato.setIdcon(idcon);
		//executar o metodo selecionarContato()
		dao.selecionarContato(contato);
		//setar os atributos do formulário com o conteúdo javaBeans
		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());
		//encaminhar ao documento editar.jsp
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request,response);
	
	}
	//editar os contatos e salvar
	protected void editarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//setar variáveis javaBeans
		contato.setIdcon(request.getParameter("idcon"));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email")); 
		//executar o metodo
		dao.alterarContato(contato);
		//redirecionar pra agenda.jsp(atualizando as alterações)
		response.sendRedirect("main");
		
	}
	
}
	

