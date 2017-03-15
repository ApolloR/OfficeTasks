package br.com.perolla;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import CustomComponents.DateLabelFormatter;
import Model.Tarefa;
import dao.ConexaoBD;
import javafx.scene.control.DatePicker;

import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.imageio.ImageIO;
import javax.rmi.CORBA.UtilDelegate;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.SystemColor;
import org.jdatepicker.*;
import java.awt.Color;
import java.awt.Panel;
import javax.swing.JLabel;
import java.awt.Font;

public class FrameGerenciaTarefas extends JFrame {

	private JPanel contentPane;
	public static int x;
	public static int y;
	private JTable table;
	private JButton btn_EscolheData;
	private JPanel panel;
	ConexaoBD conexaoBD = new ConexaoBD();
	JDatePickerImpl datePicker;
	JScrollPane scrollPane;
	
	
	
	List<Tarefa> lstTarefas = new ArrayList();
	private JLabel lblNewLabel;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameGerenciaTarefas frame = new FrameGerenciaTarefas();															
					frame.setVisible(true);
					
				} catch (Exception e) {
					System.out.println("Erro no Projeto:"+e.getMessage());
					e.printStackTrace();
					
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	/**
	 * 
	 */
		
	public FrameGerenciaTarefas() {
		setBackground(new Color(204, 204, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1170, 501);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		x = (int) ((dimension.getWidth() - getWidth())/2);
		y = (int) ((dimension.getHeight() - getHeight())/2);
		setLocation(x, y);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 204, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);				
		
		initButtons();		
		initImages();
		
		lstTarefas = getTasks("");		
		carregaTabela(lstTarefas);									
		setDatePicker();
		
		
	}

	private void initImages() {
		FrameLogin frameLogin= new FrameLogin();
		JLabel logoPerolla = frameLogin.setIconAplication("/images/LogoPerolla.png",70,350);				
		logoPerolla.setBounds(10, 11, 479, 89);
		contentPane.add(logoPerolla);
	}
	
	public void initButtons(){
		/*Botão Escolhe Data*/
		btn_EscolheData = new JButton("Escolher data");
		btn_EscolheData.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btn_EscolheData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				contentPane.remove(scrollPane);
				Date date = (Date) datePicker.getModel().getValue();				
				java.sql.Date sqlDate = new java.sql.Date(date.getTime());								
				lstTarefas = getTasks((sqlDate.toString()).equals("")?"2017-02-10":sqlDate.toString());								
				carregaTabela(lstTarefas);
				
			}
		});
		btn_EscolheData.setBackground(new Color(204, 204, 204));						
		btn_EscolheData.setBounds(260, 111, 124, 23);
		contentPane.add(btn_EscolheData);	
		
		
		/*Botão Salvar*/
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(883, 407, 111, 33);
		contentPane.add(btnSalvar);		
		btnSalvar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 String descricao,andamento,observacao;
				 int prioridade,id;
				 boolean status;
				
				ResultSet rs ;
				
				for (int i =0;i< table.getModel().getRowCount();i++){							
					if (table.getModel().getValueAt(i, 5)==null && table.getModel().getValueAt(i, 0)!=null){
						System.out.println(i + "º Loop");
						descricao = trataNulo(table.getModel().getValueAt(i, 0));
						

						andamento = trataNulo(table.getModel().getValueAt(i, 1));
									
						
						prioridade= Integer.parseInt(trataNulo((table.getModel().getValueAt(i, 2)==null?0:table.getModel().getValueAt(i, 2))));						

						
						status = (table.getModel().getValueAt(i, 3).equals("Pendente")? false : true);

						observacao = trataNulo(table.getModel().getValueAt(i, 4));							
																
						conexaoBD.inserir(descricao, andamento, prioridade, status, observacao);
						
					}else if (table.getModel().getValueAt(i, 5)!=null && table.getModel().getValueAt(i, 0)!=null){
						System.out.println(i + "º Loop");
						
						descricao = trataNulo(table.getModel().getValueAt(i, 0).toString());
						andamento = trataNulo(table.getModel().getValueAt(i, 1).toString());
						prioridade= Integer.parseInt(trataNulo(table.getModel().getValueAt(i, 2).toString()));
						status = (table.getModel().getValueAt(i, 3).equals("Pendente")? false : true);
						observacao = trataNulo(table.getModel().getValueAt(i, 4).toString());							
						id=  Integer.parseInt(table.getModel().getValueAt(i, 5).toString());
												
						conexaoBD.update(id, descricao, andamento, prioridade, status, observacao);
					}
				}	
				
			}
		});
		
		
		/*Botão Replicar*/
		JButton btnReplicar = new JButton("Replicar");
		btnReplicar.setBounds(1004, 407, 99, 33);
		contentPane.add(btnReplicar);
		
	}
		

	private void setDatePicker() {
		UtilDateModel model = new UtilDateModel();			
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);		
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());		
		//setContentPane(datePicker);		
		panel = new JPanel();
		panel.setBackground(new Color(204, 204, 255));
		panel.setBounds(10, 101, 240, 33);
		
		
		panel.add(datePicker);		
		contentPane.add(panel);
		
		lblNewLabel = new JLabel("Controle de Tarefas");
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 53));
		lblNewLabel.setBounds(590, 0, 554, 102);
		contentPane.add(lblNewLabel);
		
		
	}
	
	private Locale getLocale(String loc){
		if (loc !=null && loc.length()>0){
			return new Locale(loc);
		}else{
			return Locale.US;
		}
			
	}
	
	public void carregaTabela(List<Tarefa> lstTarefas){
						 		
		
		Object data[][] = new Object[20][6];
							
				
		for (int i = 0; i < lstTarefas.size(); i ++){
			data[i][0] = lstTarefas.get(i).getDescricao();
			data[i][1] = lstTarefas.get(i).getAndamento();
			data[i][2] = lstTarefas.get(i).getPrioridade();
			data[i][3] = (lstTarefas.get(i).isStatus()?"Finalizada":"Pendente");
			data[i][4] = lstTarefas.get(i).getObservacao();
			data[i][5] = lstTarefas.get(i).getId();
			
		}
						
		
		table = new JTable(data,new String[]{"Sexta"+"","dia","Prioridade","Status","Obs","Id"});
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		table.getColumnModel().getColumn(0).setPreferredWidth(450);
		table.getColumnModel().getColumn(4).setPreferredWidth(450);
		table.getColumnModel().getColumn(5).setPreferredWidth(0);
		
		
		scrollPane = new JScrollPane(table);
		scrollPane.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 145, 1144, 251);
		
		contentPane.add(scrollPane);
		
					
	}

	
	
	public List<Tarefa> getTasks(String date) {
		
		List<Tarefa> lstTarefas = new ArrayList();
		Tarefa tarefa;
		//select CONVERT(VARCHAR(10),  Tarefa.data_tarefa, 103),CONVERT(VARCHAR(10),  GETDATE(), 103)  AS "103" from Tarefa order by data_tarefa,prioridade ;
		conexaoBD.connect();
		
		ResultSet rs;
		if (date.equals("")){
			 rs = conexaoBD.executar("select Tarefa.* from Tarefa where CONVERT(VARCHAR(10),  Tarefa.data_tarefa, 103)=CONVERT(VARCHAR(10) ,  GETDATE(), 103) order by  data_tarefa ,prioridade desc");			 
		}else{
			 rs = conexaoBD.executar("select Tarefa.* from Tarefa where Tarefa.data_tarefa='"+ date +"' order by  data_tarefa ,prioridade desc");			 
		}
				
		try {
			while (rs.next()){							
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
			e.printStackTrace();
		}finally{
			conexaoBD.disconnect();
		}
		
		return lstTarefas;
	}
	
	public String trataNulo(Object object) {
		
			if (object==null){
				return "";
			}else{ 
				// Corrigir caso o objeto seja boolean
				return String.valueOf(object);
			}	
		
	}
}
