package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAO {
		/*M?dulo de conex?o*/
	//parametro de conex?o
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
	private String user = "root";
	private String password = "caio0101";
	//metodo de conex?o
	private Connection conectar(){
		Connection con = null;
		try {
			//serve pra ler o driver do banco de dados
			Class.forName(driver);
			//A classe  DriverManager gerencia o driver
			con = DriverManager.getConnection(url,user,password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		
	}
	/*
	public void testeConex?o() {
		try {
			//invoca(executa) o metodo conectar
			Connection con = conectar();
			System.out.println(con);
			
		}catch(Exception e) {
			System.out.println(e);
			
		}
	}*/
	public void inserirContato(JavaBeans contato) {
		String create = "insert into contatos(nome,fone,email) values(?,?,?)";
		try {
			//abrir conex?o com o banco
			Connection con = conectar();
			//prepara a query para execu??o no banco de dados
			//query ? responsavel por consultas ao banco de dados
			PreparedStatement pst = con.prepareStatement(create);
			//substituir os parametros(?) pelos conte?dos da JavaBeans
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			//Executar a query
			pst.executeUpdate();
			//encerrar a conex?o com o banco
			pst.close();
			
		}catch(Exception e){
			System.out.println(e);
		}
	}
	/** CRUD READ **/
	public ArrayList<JavaBeans> listarContatos(){
		//criando objeto pra acessar a classe java beans
		ArrayList<JavaBeans> contatos = new ArrayList<>();
		
		String read = "select*from contatos order by nome";
		try{
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);//prepara a execu??o da variavel read(a query)
			ResultSet rs = pst.executeQuery();//responsavel por executar a query, no caso o read
			//o la?o de repeti?a? abaixo ser? executado enquanto houver contatos
			while(rs.next()) {
				//Vari?veis de apoio que recebem os dados do banco de dados
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				//populando o ArrayList
				contatos.add(new JavaBeans(idcon,nome,fone,email));
				
			}
			con.close();
			return contatos;
			
		}catch(Exception e){
			System.out.println(e);
			return null;
		}
	}
	/**CRUD UPDATE**/
	//selecionar o contato
	public void selecionarContato(JavaBeans contato) {
		String read2 ="select*from contatos where idcon =?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read2);
			pst.setString(1,contato.getIdcon());
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				//setar as vari?veis javaBeans
				//os numeros 1,2,3,4 correspondem a coluna da tabela
				contato.setIdcon(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));	
				
			}
			con.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	//editar contato
	public void alterarContato(JavaBeans contato) {
		String create = "update contatos set nome=?,fone=?,email=? where idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement ps = con.prepareStatement(create);
			ps.setString(1,contato.getNome());
			ps.setString(2, contato.getFone());
			ps.setString(3, contato.getEmail());
			ps.setString(4, contato.getIdcon());
			ps.executeUpdate();
			con.close();
		}catch(Exception e) {
			System.out.println(e);
			
		}
		
	}
	/**CRUD DELETE**/
	public void deletarContato(JavaBeans contato) {
		String delete = "delete from contatos where idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(delete);
			pst.setString(1,contato.getIdcon());
			pst.executeUpdate();
			con.close();
		}catch(Exception e) {
			System.out.println(e);
		}
		
		
	}
	
}
