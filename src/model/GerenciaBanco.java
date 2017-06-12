package model;

import java.security.KeyStore.PasswordProtection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.security.auth.callback.PasswordCallback;

import bean.Tarefa;
import interfaces.MVP;
import view.FrameLogin;

public class GerenciaBanco implements MVP.ModelImpl {

	private ConexaoBD conexao;
	private Connection c;

	public GerenciaBanco() {
		
		//Esta dando conexão fechada ao inserir e dar update
		conexao = new ConexaoBD();
		c = conexao.getConnection();
	}
	
	public boolean connect(){
		boolean conectado =conexao.connect(); 		
		c = conexao.getConnection();		
		return conectado ;		
	}

	
	public void update(int id, String descricao, String andamento, int prioridade, boolean status, String observacao,
			String data) {
		System.out.println((connect() == true ? "Conectado" : "Erro ao conectar"));
		String query = "update Tarefa set " + " descricao = ?," + " andamento =  ?," + " prioridade =  ?,"
				+ " status =  ?," + " observacao = ?, " + " data_tarefa = ? " + " where id= ?";

		PreparedStatement preparedStmt;
		try {
			preparedStmt = c.prepareStatement(query);
			preparedStmt.setString(1, descricao);
			preparedStmt.setString(2, andamento);
			preparedStmt.setInt(3, prioridade);
			preparedStmt.setBoolean(4, status);
			preparedStmt.setString(5, observacao);
			preparedStmt.setString(6, data);
			preparedStmt.setInt(7, id);

			// execute the java preparedstatement

			System.out.println((preparedStmt.executeUpdate() == 1 ? "Sucesso" : "Falha"));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			System.out.println((conexao.disconnect() == false ? "Desconectado" : "Falha ao desconectar"));
		}

	}

	/**
	 * //Executa uma query como update, delete ou insert. Retorna o número de
	 * registros afetados quando falamos de um update ou delete ou retorna 1
	 * quando o insert é bem sucedido. Em outros casos retorna -1
	 *
	 * @param query
	 *            A query que se deseja executar
	 * @return 0 para um insert bem sucedido. -1 para erro
	 */
	public void insert(String descricao, String andamento, int prioridade, boolean status, String observacao,
			String data) {
		System.out.println((connect() == true ? "Conectado" : "Erro ao conectar"));

		String query = "insert into Tarefa (descricao,andamento,prioridade,status,observacao,data_tarefa) values ("
				+ "?,?,?,?,?,?)";
		PreparedStatement st;
		int result = -1;

		try {
			st = c.prepareStatement(query);
			st.setString(1, descricao);
			st.setString(2, andamento);
			st.setInt(3, prioridade);
			st.setBoolean(4, status);
			st.setString(5, observacao);
			st.setString(6, data);
			result = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			System.out.println((conexao.disconnect() == false ? "Desconectado" : "Falha ao desconectar"));
		}

		// return result;
	}

	
	protected ResultSet executar(String query) {
		conexao.connect();
		c = conexao.getConnection();
		
		Statement st;
		ResultSet rs;

		try {
			st = c.createStatement();
			rs = st.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public List<Tarefa> select(String date) {

		List<Tarefa> lstTarefas = new ArrayList();
		Tarefa tarefa;
		// select CONVERT(VARCHAR(10), Tarefa.data_tarefa,
		// 103),CONVERT(VARCHAR(10), GETDATE(), 103) AS "103" from Tarefa order
		// by data_tarefa,prioridade ;
		
		//conexao.connect();

		ResultSet rs = null;
		try {
			if (date.equals("")) {
				rs = executar(
						"select Tarefa.* from Tarefa where CONVERT(VARCHAR(10),  Tarefa.data_tarefa, 103)=CONVERT(VARCHAR(10) ,  GETDATE(), 103) and codUsuario = "+FrameLogin.userId +"order by  data_tarefa ,prioridade desc");
			} else {
				rs = executar("select Tarefa.* from Tarefa where Tarefa.data_tarefa='" + date
						+ "' and codUsuario = "+ FrameLogin.userId 
						+ "order by  data_tarefa ,prioridade desc");
			}
		} catch (Exception e) {
			System.out.println("Erro método Select" + e.getMessage());
			e.printStackTrace();
		}

		try {
			
			
			while (rs.next()) {
				tarefa = new Tarefa();
				tarefa.setId(rs.getInt("id"));
				tarefa.setDescricao(rs.getString("descricao"));
				tarefa.setAndamento(rs.getString("andamento"));
				tarefa.setPrioridade(rs.getInt("prioridade"));
				tarefa.setStatus(rs.getBoolean("status"));
				tarefa.setObservacao(rs.getString("observacao"));
				tarefa.setDataTarefa(rs.getDate("data_tarefa"));

				lstTarefas.add(tarefa);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erro método Select" + e.getMessage());
			e.printStackTrace();

		} finally {
			conexao.disconnect();
		}

		return lstTarefas;
	}

	
	public int validateUser(String user, char[] psw) {
		
		int authenticated = -1 ;
		ResultSet rs = null;
		PasswordProtection p = new PasswordProtection(psw);
		
		
		rs = executar("select * from Usuario where \"user\" ='"+user+"'");
		
		try {
			if (rs.next()){
				
				char[] correctPassword = rs.getString("password").toCharArray();
				
				if( Arrays.equals(psw, correctPassword))
					authenticated=rs.getInt("id");
				
				
				Arrays.fill(correctPassword, '0');
				Arrays.fill(psw, '0');
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return authenticated;
	}

}
