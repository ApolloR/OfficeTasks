package view;

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
import bean.Tarefa;
import controller.Controller;
import interfaces.MVP;
import javafx.scene.control.DatePicker;
import javafx.scene.input.DataFormat;
import model.ConexaoBD;

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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.SystemColor;
import org.jdatepicker.*;
import java.awt.Color;
import java.awt.Panel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.Insets;

public class FrameGerenciaTarefas extends JFrame {

	private JPanel contentPane;
	private JLabel lblWeekDay;
	public static int x;
	public static int y;
	private JTable table;
	private JButton btn_EscolheData;
	private JPanel panel;
	private int day,month,year;
	private int selectedDay,selectedMonth,selectedYear;
	private String dayOfTheWeek;
	private MVP.ControllerImpl controler = new Controller();
	private LocalDate localCurrentDate;
	JDatePickerImpl datePicker;
	JScrollPane scrollPane;
	FrameLogin frameLogin;
	JLabel lbl_logo;	
	
	ConexaoBD conexao = new ConexaoBD();
	
	
	
	
	
	List<Tarefa> lstTarefas = new ArrayList();
	

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
		setJFrame();		
		
		try{
		initButtons();
		}catch(Exception e){
			System.out.println("Erro iniButtons" + e.getMessage());
		}
		
		try{
			initImages();
		}catch(Exception e){
			System.out.println("Erro initImages" + e.getMessage());
		}
		
		
		try{
			lstTarefas = controler.getTasks("");
		}catch(Exception e){
			System.out.println("Erro getTasks" + e.getMessage());
		}
		
		try{
			carregaTabela(lstTarefas);									
			
		}catch(Exception e){
			System.out.println("Erro carregaTabela" + e.getMessage());
		}
		
		try{
			setDatePicker();								
			
		}catch(Exception e){
			System.out.println("Erro setDatePicker" + e.getMessage());
		}
		
	}

	private void setJFrame() {
		setBackground(new Color(204, 204, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1170, 501);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		x = (int) ((dimension.getWidth() - getWidth())/2);
		y = (int) ((dimension.getHeight() - getHeight())/2);
		setLocation(x, y);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		lblWeekDay = new JLabel("Day Of the Week");
		lblWeekDay.setFont(new Font("Segoe UI", Font.BOLD, 23));
		lblWeekDay.setSize(new Dimension(10, 10));
		lblWeekDay.setBounds(411, 99, 337, 33);
		contentPane.add(lblWeekDay);		
		getCurrentDate();	
		
		
	}

	public void initImages() {
		frameLogin= new FrameLogin();
		lbl_logo = frameLogin.setIconAplication("/images/LogoPerolla_icon.png",80,350,11,126);				
		lbl_logo.setBounds(-40, 11, 479, 89);
		contentPane.add(lbl_logo);
	}
	
	public void initButtons(){
		/*Botão Escolhe Data*/
		btn_EscolheData = new JButton("Escolher data");
		btn_EscolheData.setMargin(new Insets(2, 1, 2, 14));
		btn_EscolheData.setMaximumSize(new Dimension(87, 13));
		btn_EscolheData.setHorizontalAlignment(SwingConstants.LEFT);
		
		btn_EscolheData.setIcon(new ImageIcon(FrameGerenciaTarefas.class.getResource("/images/calendar-question.png")));
		btn_EscolheData.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		
		
		
		btn_EscolheData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
							
				//conexao.selectAtoa();
				
				contentPane.remove(scrollPane);
				Date date = (Date) datePicker.getModel().getValue();
				
				if (controler.verificaData(date)){
					getSelectedDate(date);
					lblWeekDay.setText("Dia " + selectedDay +"/"+ selectedMonth +"  "+ getDayOfWeek(date));
					java.sql.Date sqlDate = new java.sql.Date(date.getTime());								
					//lstTarefas = controler.getTasks((sqlDate.toString()).equals("")?"2017-02-10":sqlDate.toString());
					lstTarefas = controler.getTasks((sqlDate.toString()).equals("")?"2017-02-10":sqlDate.toString());
					carregaTabela(lstTarefas);
				}
				
				
			}
		});
		btn_EscolheData.setBackground(SystemColor.control);						
		btn_EscolheData.setBounds(260, 101, 141, 33);
		contentPane.add(btn_EscolheData);	
		
		
		/*Botão Salvar*/
		JButton btnSalvar = new JButton("");
		btnSalvar.setIcon(new ImageIcon(FrameGerenciaTarefas.class.getResource("/images/content-save.png")));
		btnSalvar.setSize(new Dimension(20, 20));
		btnSalvar.setFocusPainted(false);
		btnSalvar.setBackground(SystemColor.control);
		btnSalvar.setBounds(883, 407, 111, 45);
		contentPane.add(btnSalvar);		
		btnSalvar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 String descricao,andamento,observacao,dataSelecionada;
				 int prioridade,id;
				 boolean status;
				
				ResultSet rs ;
				
				Date date = (Date) datePicker.getModel().getValue();
				if (controler.verificaData(date)){														
					for (int i =0;i< table.getModel().getRowCount();i++){							
						if (table.getModel().getValueAt(i, 5)==null && table.getModel().getValueAt(i, 0)!=null){
							System.out.println(i + "º Loop");
							
							descricao = trataNulo(table.getModel().getValueAt(i, 0));						
							andamento = trataNulo(table.getModel().getValueAt(i, 1));															
							prioridade= Integer.parseInt(trataNulo((table.getModel().getValueAt(i, 2)==null?0:table.getModel().getValueAt(i, 2))));											
							status = (trataNulo (table.getModel().getValueAt(i, 3)).equals("Ok") || trataNulo(table.getModel().getValueAt(i, 3)).equals("Finalizada")? true: false );
							observacao = trataNulo(table.getModel().getValueAt(i, 4));
							dataSelecionada = datePicker.getJFormattedTextField().getText().toString(); 
							controler.inserirTarefa(descricao, andamento, prioridade, status, observacao,dataSelecionada);
							
							
						}else if (table.getModel().getValueAt(i, 5)!=null && table.getModel().getValueAt(i, 0)!=null){
							System.out.println(i + "º Loop");
							
							descricao = trataNulo(table.getModel().getValueAt(i, 0).toString());
							andamento = trataNulo(table.getModel().getValueAt(i, 1).toString());
							prioridade= Integer.parseInt(trataNulo(table.getModel().getValueAt(i, 2).toString()));
							status = (trataNulo(table.getModel().getValueAt(i, 3)).equals("Ok") || trataNulo(table.getModel().getValueAt(i, 3)).equals("Finalizada")? true : false );
							observacao = trataNulo(table.getModel().getValueAt(i, 4).toString());
							dataSelecionada = datePicker.getJFormattedTextField().getText().toString();
							id=  Integer.parseInt(table.getModel().getValueAt(i, 5).toString());
													
							controler.alterarTarefa(id, descricao, andamento, prioridade, status, observacao,dataSelecionada) ;
						}										
					}
					btn_EscolheData.doClick();
				}
				
				
				
			}
		});
		
		
		/*Botão Replicar*/
		JButton btnReplicar = new JButton("Replicar");
		btnReplicar.setMaximumSize(new Dimension(61, 13));
		btnReplicar.setIconTextGap(1);
		btnReplicar.setIcon(new ImageIcon(FrameGerenciaTarefas.class.getResource("/images/content-duplicate.png")));
		btnReplicar.setForeground(SystemColor.desktop);
		btnReplicar.setBounds(1004, 407, 140, 45);
		btnReplicar.setBackground(SystemColor.control);
		contentPane.add(btnReplicar);
		
	}
		
	private void setDatePicker() {
		
		
		UtilDateModel model = new UtilDateModel();
		
		model.setDate(year, month-1, day);
		model.setSelected(true);
		
		
		Properties p = new Properties();
		
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		
		
		  
		 
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p); 		
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
				
		//setContentPane(datePicker);		
		panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaptionBorder);
		panel.setBounds(10, 101, 240, 33); 
		
		
		lblWeekDay.setText("Dia " + day +"/"+ month +"  "+ getDayOfWeek(new Date()));
		
		
		panel.add(datePicker);		
		
		contentPane.add(panel);			
				
	}

	private void getCurrentDate() {		
		localCurrentDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();		
		day = localCurrentDate.getDayOfMonth();
		month = localCurrentDate.getMonthValue();
		year = localCurrentDate.getYear();				
				
	}
	
	private void getSelectedDate(Date date){
		LocalDate localdate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		selectedDay = localdate.getDayOfMonth();
		selectedMonth = localdate.getMonthValue();
		selectedYear= localdate.getYear();		
	}
	
	private String getDayOfWeek(Date date){
		LocalDate localdate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		switch (localdate.getDayOfWeek().toString()){
		case("MONDAY"):
			return "Segunda - feira";
			
		case("TUESDAY"):
			return "Terça - feira";
			
		case("WEDNESDAY"):
			return "Quarta - feira";
			
		case("THURSDAY"):
			return "Quinta - feira";
			
		case("FRIDAY"):
			return "Sexta - feira";
			
		case("SATURDAY"):
			return "Sábado";			
		default:
			return "Domingo";			
		}
				
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
						
		
		table = new JTable(data,new String[]{"Descrição da Tarefa","Nota","Prioridade","Status","Obs","Id"});
		table.setSelectionBackground(new Color(204, 255, 153));
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
	
	public String trataNulo(Object object) {
		
			if (object==null){
				return "";
			}else{ 
				// Corrigir caso o objeto seja boolean
				return String.valueOf(object);
			}	
		
	}
}
