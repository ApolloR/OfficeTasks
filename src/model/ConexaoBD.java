package model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jdatepicker.impl.UtilDateModel;

import com.mysql.jdbc.Util;

import bean.Tarefa;

public class ConexaoBD {

	private final String pass;
	private final String user;
	private final String host;
	private final String database;
	private Connection c = null;

	public Connection getConnection() {
		return c;
	}

	public void setConnection(Connection c) {
		this.c = c;
	}

	public ConexaoBD() {
		//this.host = "ADM04\\SQLSERVER2008";
		this.host = "192.168.0.240";
		this.database = "Tarefas";
		this.user = "Apolo";
		this.pass = "1234";
		connect();

	}

	public boolean connect() {

		boolean isConnected = false;

		String url;

		String portNumber = "1433";
		String userName = this.user;
		String passName = this.pass;
		url = "jdbc:sqlserver://" + this.host + ":" + portNumber + ";databaseName=" + this.database;

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			this.c = DriverManager.getConnection(url, userName, passName);
			isConnected = true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			isConnected = false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			isConnected = false;
		} catch (InstantiationException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			isConnected = false;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			isConnected = false;
		}

		return isConnected;
	}

	public boolean disconnect() {

		boolean isConnected = true;

		try {
			c.close();
			isConnected = false;
		} catch (SQLException ex) {
			System.out.print("SQLException: ");
			System.out.println(ex.getMessage());
		}

		return isConnected;
	}

	
}
