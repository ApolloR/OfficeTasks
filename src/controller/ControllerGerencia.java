package controller;

import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import bean.Tarefa;
import interfaces.MVP;
import model.ConexaoBD;
import model.GerenciaBanco;

public class ControllerGerencia implements MVP.ControllerGerenciaImpl{
	
	private MVP.ViewImpl view;
	private MVP.ModelImpl model;
	//private ConexaoBD model;
	
	
	public ControllerGerencia(){
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



	@Override
	public boolean verificaData(Date date) {
		if (date==null){
			JOptionPane.showMessageDialog(new JButton(""),"É necessário escolher uma data.");
			return false;
		}else{		
			return true;
		}
	}

}
