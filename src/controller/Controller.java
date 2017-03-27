package controller;

import java.util.List;

import bean.Tarefa;
import interfaces.MVP;
import model.ConexaoBD;
import model.GerenciaBanco;

public class Controller implements MVP.ControllerImpl{
	
	private MVP.ViewImpl view;
	private MVP.ModelImpl model;
	//private ConexaoBD model;
	
	
	public Controller(){
		model = new GerenciaBanco();
		//model = new ConexaoBD();
	}

	@Override
	public List<Tarefa> getTasks(String date) {				
		return model.select(date);
	}

	@Override
	public void inserirTarefa(String descricao, String andamento, int prioridade, boolean status, String observacao,
			String data) {
		
		model.insert(descricao, andamento, prioridade, status, observacao, data);
	}

	@Override
	public void alterarTarefa(int id, String descricao, String andamento, int prioridade, boolean status,
			String observacao, String data) {		
		model.update(id, descricao, andamento, prioridade, status, observacao, data);
	}

}
