package com.excilys.java.formation.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.java.formation.mapper.Computer;
import com.excilys.java.formation.persistence.ComputerDAO;



public class ComputerValidator {
	
	/**
	 * The instance is initialized when the class is first referenced
	 */
	private final static ComputerValidator computerValidator = new ComputerValidator();
	
	private ComputerValidator() {
		
	}
	
	/**
	 * controls the access to the unique instance of the ComputerValidator class
	 * @return the unique instance of the ComputerValidator class
	 */
	public static ComputerValidator getComputerValidator() {	
		return computerValidator;
	}
	
	
	/**
	 * Method to verify if the name is not empty
	 * @param name
	 * @return true if the name is not empty, false otherwise
	 */
	public boolean nameValidator(String name) {
		
			if(name.equals("")) {
				System.out.println("name of the computer is mandatory");
				return false;
			}
			return true;
		
	}
	
	/**
	 * Method to verify if the id of the computer to delete, to update or to show its details is valid or not
	 * @param id
	 * @return true if the id is valid, false otherwise
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
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
	
	/**
	 * Method to verify if the dates are valid or not
	 * @param introduced
	 * @param discontinued
	 * @return true if the date the computer was discontinued is greater than the one it was introduced
	 */
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
