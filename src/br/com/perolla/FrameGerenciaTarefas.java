package br.com.perolla;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.Calendar;
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
import javafx.scene.control.DatePicker;

import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.rmi.CORBA.UtilDelegate;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.SystemColor;
import org.jdatepicker.*;

public class FrameGerenciaTarefas extends JFrame {

	private JPanel contentPane;
	public static int x;
	public static int y;
	private JTable table;
	private JButton btn_EscolheData;
	private JPanel panel;
	

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
	public FrameGerenciaTarefas() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1229, 501);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		x = (int) ((dimension.getWidth() - getWidth())/2);
		y = (int) ((dimension.getHeight() - getHeight())/2);
		setLocation(x, y);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);				
		
		carregaTabela();									
		setDatePicker();
	}

	private void setDatePicker() {
		UtilDateModel model = new UtilDateModel();		
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);		
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());		
		//setContentPane(datePicker);
		panel = new JPanel();
		panel.setBounds(10, 41, 240, 33);
		panel.add(datePicker);		
		contentPane.add(panel);
	}
	
	private Locale getLocale(String loc){
		if (loc !=null && loc.length()>0){
			return new Locale(loc);
		}else{
			return Locale.US;
		}
			
	}
	
	public void carregaTabela(){
						 
		// 3 será substituído pela quantidade de registros do dia atual.
		Object data[][] = new Object[20][5];
		
		//Será carregada uma lista das tarefas do dia ordenada pela prioridade.
		Tarefa tarefa = new Tarefa();
		tarefa.setDescricao("Ao chegar de manhã VERIFICAR!!!!!!!!!!!!!");
		tarefa.setAndamento("Testando");
		tarefa.setPrioridade(5);
		tarefa.setStatus(false);
		tarefa.setObservacao("O backup deu uma mensagem diferente");
		
		
		for (int i = 0; i < data.length; i ++){
			data[i][0] = tarefa.getDescricao();
			data[i][1] = tarefa.getAndamento();
			data[i][2] = tarefa.getPrioridade();
			data[i][3] = (tarefa.isStatus()?"Finalizada":"Pendente");
			data[i][4] = tarefa.getObservacao();			
		}
		
		table = new JTable(data,new String[]{"Sexta","dia","Prioridade","Status","Obs"});
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		table.getColumnModel().getColumn(0).setPreferredWidth(450);
		table.getColumnModel().getColumn(4).setPreferredWidth(450);
		
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 75, 1193, 170);
		contentPane.add(scrollPane);
		
		btn_EscolheData = new JButton("Escolher data");
		btn_EscolheData.setBackground(SystemColor.textHighlight);
		
		
		
		btn_EscolheData.setBounds(253, 41, 124, 23);
		contentPane.add(btn_EscolheData);
		
		
		
		
		
		
		 //String [] columunNames ={"Sexta","dia","Prioridade","Status","Obs"};
		
		
		
		//scrollPane.setViewportView(table);
		
		
	}
}
