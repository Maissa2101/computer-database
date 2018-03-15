package com.excilys.java.formation.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.ComputerDAO;
import com.excilys.java.formation.persistence.ComputerDAOInterface;



public enum ComputerValidator {
	
	INSTANCE;
	
	
	/**
	 * Method to verify if the name is not empty
	 * @param name the name of the computer
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
	 * @param id the id to validate
	 * @return true if the id is valid, false otherwise
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 * @throws SQLException in case of a database access error
	 */
	public boolean idValidator(Long id) throws ClassNotFoundException, SQLException {
		int index = 0;
		
		ComputerDAOInterface computers = ComputerDAO.INSTANCE;
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
	 * @param introduced the introduced date of the computer
	 * @param discontinued the discontinued date of the computer
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
		else {
			return true;
		}
	}
		
		
		
		
}
