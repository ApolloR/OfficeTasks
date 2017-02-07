package Model;

import java.sql.Date;

public class Tarefa {
	private int id;
	private String descricao;
	private String andamento;
	private int prioridade;
	private boolean status;
	private String Observacao;
	private Date dataTarefa;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getAndamento() {
		return andamento;
	}
	public void setAndamento(String andamento) {
		this.andamento = andamento;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getObservacao() {
		return Observacao;
	}
	public void setObservacao(String observacao) {
		Observacao = observacao;
	}
	public Date getDataTarefa() {
		return dataTarefa;
	}
	public void setDataTarefa(Date dataTarefa) {
		this.dataTarefa = dataTarefa;
	}
	public int getPrioridade() {
		return prioridade;
	}
	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}

}
