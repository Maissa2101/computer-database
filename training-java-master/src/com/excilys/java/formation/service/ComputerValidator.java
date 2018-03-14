package com.excilys.java.formation.service;

import java.sql.Date;



public class ComputerValidator {
	
	private final static ComputerValidator computerValidator = new ComputerValidator();
	
	public ComputerValidator() {
		
	}
	
	public static ComputerValidator getComputerValidator() {	
		return computerValidator;
	}
	
	
	
	public boolean nameValidator(String name) {
		
			if(name.equals("")) {
				System.out.println("name of the computer is mandatory");
				return false;
			}
			return true;
		
	}
	
	public boolean DateValidator(Date introduced, Date dicontinued) {
		
		return false;
		
	}
		
		
		
		
}
