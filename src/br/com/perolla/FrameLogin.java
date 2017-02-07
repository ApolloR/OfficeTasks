package br.com.perolla;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.UIManager;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import CustomComponents.ImagePanel;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.awt.event.ActionEvent;
import java.awt.Point;
import javax.swing.JPanel;
import javax.swing.JDesktopPane;
import javax.swing.DebugGraphics;
import java.awt.Cursor;

public class FrameLogin implements ActionListener {

	private JFrame frame;
	private JTextField txt_Login;
	private JPasswordField txt_Senha;
	private JButton btn_Entrar;
	
	private JLabel lblLogin = new JLabel("Login");


	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameLogin window = new FrameLogin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FrameLogin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void initialize() {
		defineTam(307,353);	   
		defineBackground();
		
		
		/* Icone de usuário */
		setIconAplication();
		
		
		
			    
		txt_Login = new JTextField();
		txt_Login.setBounds(126, 131, 160, 30);
		frame.getContentPane().add(txt_Login);
		txt_Login.setColumns(10);
		
		btn_Entrar = new JButton("Entrar");
		btn_Entrar.addActionListener(this);
		
		
		btn_Entrar.setFocusPainted(false);
		btn_Entrar.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		btn_Entrar.setForeground(new Color(255, 255, 255));
		btn_Entrar.setBackground(new Color(0, 0, 0));
		btn_Entrar.setFont(new Font("Tahoma", Font.BOLD, 18));
		btn_Entrar.setBounds(62, 224, 224, 30);
		frame.getContentPane().add(btn_Entrar);
		
		txt_Senha = new JPasswordField();
		txt_Senha.setBounds(126, 176, 160, 30);
		frame.getContentPane().add(txt_Senha);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setForeground(new Color(255, 255, 255));
		lblSenha.setBounds(70, 176, 59, 30);
		frame.getContentPane().add(lblSenha);
		lblSenha.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JLabel lblLogin_1 = new JLabel("Login");
		lblLogin_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblLogin_1.setForeground(new Color(255, 255, 255));
		lblLogin_1.setBounds(70, 135, 59, 30);
		frame.getContentPane().add(lblLogin_1);
	}

	private void setIconAplication() {
		Image img = null;
		try {
			 img= ImageIO.read(FrameLogin.class.getResource("/images/log_icon.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Image bimg =  img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		
		
		ImageIcon icon = new ImageIcon(bimg);
		
		JLabel login_icon = new JLabel("",icon,JLabel.CENTER);
		login_icon.setForeground(Color.WHITE);
		login_icon.setFont(login_icon.getFont().deriveFont(login_icon.getFont().getStyle() | Font.BOLD));
		login_icon.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
		
	  
		login_icon.setDisabledIcon(new ImageIcon(FrameLogin.class.getResource("/images/log_icon.png")));
		login_icon.setDebugGraphicsOptions(DebugGraphics.BUFFERED_OPTION);
		login_icon.setBounds(126, 11, 100, 100);
		frame.getContentPane().add(login_icon);
	}
	
	public void defineTam(int altura,int largura){
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		frame = new JFrame();		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.getContentPane().setBackground(new Color(112, 128, 144));									
		frame.setBounds(100, 100, 353, 331);				
		int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(new Point(x, y));	
		frame.getContentPane().setLayout(null);
		
	}
	
	public void defineBackground(){
		File file = new File("C:/Users/Perolla/workspace/Tarefas/src/images/escritorio.jpg");
		Image img = null;
		try {
			img = ImageIO.read(new File(file.getAbsolutePath()));										
		} catch (IOException e) {
			e.printStackTrace();
		}
		/***********************************************/
		
		frame.setIconImage(img);		
		ImagePanel imagePanel = new ImagePanel(img);
		frame.setContentPane(imagePanel);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//if (e.getSource().equals("btn_entrar")){
			if (txt_Login.getText().toString().equals("Apolo") && (txt_Senha.getText().toString().equals("1234"))){
				FrameGerenciaTarefas tarefas = new FrameGerenciaTarefas();
				tarefas.setVisible(true);
			}else{
				JOptionPane.showMessageDialog(btn_Entrar, "Usuário ou senha incorretos.");				
			}
		//}
		
		
	}
}
