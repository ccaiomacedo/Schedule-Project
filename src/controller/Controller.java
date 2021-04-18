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
@WebServlet(urlPatterns = { "/Controller", "/main","/insert" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	JavaBeans contato = new JavaBeans();

	public Controller() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//retorna o endere�o que faz com que a servlet seja executada.
		String action = request.getServletPath();
		System.out.println(action);
		
		//verifica se o pedido para acessar a p�gina main foi concedido
		if (action.equals("/main")) {
			contatos(request, response);
		//se n for ele volta pra p�gina insert	
		}else if(action.equals("/insert")) {
			novoContato(request,response);
		}

	}
			//listar contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			//criar um objeto que ir� receber os dados javaBeans
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
		//teste de reconhecimento dos dados do formul�rio
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
		//teste
	}
	
}