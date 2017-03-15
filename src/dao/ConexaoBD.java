package dao;



import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import org.jdatepicker.impl.UtilDateModel;

import com.mysql.jdbc.Util;

public class ConexaoBD {

	private String pass,user,host,database;
	public Connection c;
	
	public ConexaoBD() {		 		
		 this.host="ADM04\\SQLSERVER2008";
		 this.database="Tarefas";
		 this.user="Apolo";
		 this.pass="1234";
	}
	
	public boolean connect(){

		boolean isConnected=false;
		
		String url;
		
		String portNumber = "1433";
		String userName    = this.user;
		String passName=this.pass;
	     url = "jdbc:sqlserver://"+ this.host+":" +portNumber + ";databaseName=" +this.database;

		

	     try {
	            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
	            this.c = DriverManager.getConnection(url,userName, passName);	            
	            isConnected = true;	            
	        } catch(SQLException e ) {
	            e.printStackTrace();
	            System.out.println(e.getMessage());
	            isConnected = false;
	        } catch ( ClassNotFoundException e ) {
	            e.printStackTrace();
	            System.out.println(e.getMessage());
	            isConnected = false;
	        } catch ( InstantiationException e ) {
	            e.printStackTrace();
	            System.out.println(e.getMessage());
	            isConnected = false;
	        } catch ( IllegalAccessException e ) {
	            e.printStackTrace();
	            System.out.println(e.getMessage());
	            isConnected = false;
	        }
	      
	        return isConnected;
	}
	
	
	public boolean disconnect() {
        
		boolean isConnected = true;
		
        try{
            c.close();
            isConnected = false;
        }
        catch(SQLException ex){
            System.out.print("SQLException: ");
            System.out.println(ex.getMessage());
        }
        
        return isConnected;
    }
	
	public ResultSet executar( String query ) {
        Statement st;
        ResultSet rs;
      
        try {
            st = this.c.createStatement();
            rs = st.executeQuery(query);
            return rs;
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
      
        return null;
    }
	
	
	public void update(int id,String descricao, String andamento,int prioridade,boolean status,String observacao){
		System.out.println((connect()==true?"Conectado":"Erro ao conectar")); 
		String query = "update Tarefa set "
				+ " descricao = ?,"
				+ " andamento =  ?,"
				+ " prioridade =  ?,"
				+ " status =  ?,"
				+ " observacao = ? "
				//+ "data_tarefa = " + table.getModel().getValueAt(i, 5)
				+ " where id= ?";
		
		
		PreparedStatement preparedStmt;
		try {
			preparedStmt = c.prepareStatement(query);
			preparedStmt.setString(1, descricao);
		    preparedStmt.setString(2, andamento);
		    preparedStmt.setInt(3, prioridade);
		    preparedStmt.setBoolean(4, status);
		    preparedStmt.setString(5, observacao);
		    preparedStmt.setInt(6, id);

		    // execute the java preparedstatement
		    
		    System.out.println((preparedStmt.executeUpdate()==1?"Sucesso":"Falha")); 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			System.out.println((disconnect()==false?"Desconectado":"Falha ao desconectar")); 
		}
		
	    
	}
  
    /**
     * //Executa uma query como update, delete ou insert.
     * Retorna o número de registros afetados quando falamos de um update ou delete
     * ou retorna 1 quando o insert é bem sucedido. Em outros casos retorna -1
     *
     * @param query A query que se deseja executar
     * @return 0 para um insert bem sucedido. -1 para erro
     */
    public void inserir(String descricao, String andamento,int prioridade,boolean status,String observacao) {
    	System.out.println((connect()==true?"Conectado":"Erro ao conectar")); 
    	String data_atual = "CONVERT(VARCHAR(10) ,  GETDATE(), 103)";
    	java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime()); 
    	
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
            st.setDate(6,date);
            result = st.executeUpdate();
        } catch ( SQLException e ) {
            e.printStackTrace();
        }finally {
        	System.out.println((disconnect()==false?"Desconectado":"Falha ao desconectar")); 
		}
      
        //return result;
    }
}
