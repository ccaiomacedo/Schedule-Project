package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import model.JavaBeans;

/**
 * Servlet implementation class Controller
 */
@WebServlet(urlPatterns = { "/Controller", "/main", "/insert", "/select", "/update", "/delete","/report"})
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	JavaBeans contato = new JavaBeans();

	public Controller() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// retorna o endere?o que faz com que a servlet seja executada.
		String action = request.getServletPath();
		System.out.println(action);

		// verifica se o pedido para acessar a p?gina main foi concedido
		if (action.equals("/main")) {
			contatos(request, response);
			// se n for ele volta pra p?gina insert
		} else if (action.equals("/insert")) {
			novoContato(request, response);
		} else if (action.equals("/select")) {
			listarContato(request, response);
		} else if (action.equals("/update")) {
			editarContato(request, response);
		} else if (action.equals("/delete")) {
			removerContato(request, response);
		} else if (action.equals("/report")) {
			gerarRelatorio(request, response);
		} else {
			response.sendRedirect("index.html");
		}

	}

	// listar contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// criar um objeto que ir? receber os dados javaBeans
		ArrayList<JavaBeans> lista = dao.listarContatos();// ele executa o metodo que tem no dao

		// teste de recebimento da lista
		/*
		 * for(int i= 0;i<lista.size();i++) {
		 * System.out.println(lista.get(i).getIdcon());
		 * System.out.println(lista.get(i).getNome());
		 * System.out.println(lista.get(i).getFone());
		 * System.out.println(lista.get(i).getEmail());
		 * 
		 * }
		 */

		// enviar lista pra agenda.jsp
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);

	}

	// listar novo contato
	protected void novoContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// teste de reconhecimento dos dados do formul?rio
		/*
		 * System.out.println(request.getParameter("nome"));
		 * System.out.println(request.getParameter("fone"));
		 * System.out.println(request.getParameter("email"));
		 */

		// ele armazena na variavel setNome o parametro nome passado
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		// invocar o metodo inserirContato passando o objeto contato
		dao.inserirContato(contato);
		// redirecionar pra agenda.jsp
		response.sendRedirect("main");

	}

	// Editar contatos
	protected void listarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// recebimento do id do contato que ser? editado
		String idcon = request.getParameter("idcon");
		// setar a vari?vel JavaBeans
		contato.setIdcon(idcon);
		// executar o metodo selecionarContato()
		dao.selecionarContato(contato);
		// setar os atributos do formul?rio com o conte?do javaBeans
		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());
		// encaminhar ao documento editar.jsp
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);

	}

	// editar os contatos e salvar
	protected void editarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// setar vari?veis javaBeans
		contato.setIdcon(request.getParameter("idcon"));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		// executar o metodo
		dao.alterarContato(contato);
		// redirecionar pra agenda.jsp(atualizando as altera??es)
		response.sendRedirect("main");

	}

	// remover contato
	protected void removerContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// recebimento do id do contato a ser excluido(validador.js)
		String idcon = request.getParameter("idcon");
		// System.out.println(idcon);
		// setar a vari?vel idcon no javaBeans
		contato.setIdcon(idcon);
		dao.deletarContato(contato);
		// redirecionar pra agenda.jsp(atualizando as altera??es)
		response.sendRedirect("main");//quando eu uso o main, ele efetua a a??o do action no if la em cima

	}
	//gerar relat?rio
	protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Document documento = new Document();
		try {
			//tipo de conte?do 
			response.setContentType("/apllication/pdf");//tipo de resposta ser? um documento pdf
			//nome do documento
			response.addHeader("content-disposition", "inline;filename = "+"contatos.pdf");
			//criar o documento
			PdfWriter.getInstance(documento,response.getOutputStream());
			//abrir documento pra gerar conte?do
			documento.open();
			documento.add(new Paragraph("Lista de contatos:"));
			documento .add(new Paragraph(""));
			//criar tabela
			PdfPTable tabela = new PdfPTable(3);
			//cabe?alho
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
			PdfPCell col3 = new PdfPCell(new Paragraph("E-mail"));
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			//popular a tabela com os contatos
			ArrayList<JavaBeans> lista = dao.listarContatos();
			for(int i =0;i<lista.size();i++) {
				tabela.addCell(lista.get(i).getNome());
				tabela.addCell(lista.get(i).getFone());
				tabela.addCell(lista.get(i).getEmail());
			}
			documento.add(tabela);
			documento.close();
		}catch(Exception e) { 
			System.out.println(e);
			documento.close();
		}
		
		
	}

}
