package controller;


import interfaces.MVP;
import interfaces.MVP.ModelImpl;
import model.GerenciaBanco;

public class ControllerLogin implements MVP.ControllerLoginImpl {

	
	

	private MVP.ViewImpl view;
	private MVP.ModelImpl model;
	
	public ControllerLogin() {		
		this.model = new GerenciaBanco();
	}


	
	
	@Override
	public boolean validateUser(String user, String psw) {								
		return model.validateUser(user, psw);
	}

}
