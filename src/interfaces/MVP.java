package interfaces;

import java.util.Date;
import java.util.List;

import bean.Tarefa;

public interface MVP {
	
	interface ModelImpl{
		void update(int id, String descricao, String andamento, int prioridade, boolean status, String observacao,
				String data);
		void insert(String descricao, String andamento, int prioridade, boolean status, String observacao,
				String data);
		List<Tarefa> select(String date);
	}
	
	interface ViewImpl{
		
	}
	
	interface ControllerImpl{
		List<Tarefa> getTasks(String date);
		void inserirTarefa(String descricao, String andamento, int prioridade, boolean status, String observacao,
				String data);
		void alterarTarefa(int id, String descricao, String andamento, int prioridade, boolean status, String observacao,
				String data);
		
		boolean verificaData(Date date);
	}
	

}
