package com.excilys.java.formation.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.java.formation.mapper.Computer;
import com.excilys.java.formation.persistence.ComputerDAO;



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
	
	public boolean idValidator(Long id) throws ClassNotFoundException, SQLException {
		int index = 0;
		
		ComputerDAO computers = ComputerDAO.getComputerDAO();
		List<Computer> sourceList = new ArrayList<Computer>();
		for(Computer computer : computers.getListComputer()) {
			sourceList.add(computer);
		}
		
		while(index < sourceList.size()) {
			if(id == sourceList.get(index).getId()) {
				return true;
			}
			else {
				index++;
			}
		}
		
		if(index >= sourceList.size()) {
			System.out.println("This computer doesn't exist, give another id : ");
		}
		return false;
	}
	
	public boolean DateValidator(Date introduced, Date discontinued) {
		if((discontinued != null) && (introduced != null)) {
			if (discontinued.after(introduced)) {
				return true;
			}
			else {
				System.out.println("\n Date problem : the discontinued date must be greater than the introduced date\n");
				return false;
			}
		}
		return false;
	}
		
		
		
		
}
