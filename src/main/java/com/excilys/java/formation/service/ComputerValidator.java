package com.excilys.java.formation.service;

import java.sql.SQLException;
import java.time.LocalDate;

import com.excilys.java.formation.persistence.ComputerDAO;



public enum ComputerValidator {

	INSTANCE;

	/**
	 * Method to verify if the name is not empty
	 * @param name the name of the computer
	 * @return true if the name is not empty, false otherwise
	 * @throws ValidatorException 
	 */
	public boolean nameValidator(String name) throws ValidatorException {

		if(name == null || name.equals("")) {
			throw new ValidatorException(" The name of the computer is mandatory");
		}
		return true;
	}

	/**
	 * Method to verify if the id of the computer to delete, to update or to show its details is valid or not
	 * @param id the id to validate
	 * @return true if the id is valid, false otherwise
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 * @throws SQLException in case of a database access error
	 * @throws ValidatorException 
	 */
	public boolean idValidator(Long id) throws ClassNotFoundException, SQLException, ValidatorException {

		ComputerDAO computers = ComputerDAO.INSTANCE;
		if(computers.getComputer(id).isPresent()) {
			return true;
		}
		else {
			throw new ValidatorException("This computer doesn't exist \n");
		}
	}

	/**
	 * Method to verify if the dates are valid or not
	 * @param tm1 the introduced date of the computer
	 * @param tm2 the discontinued date of the computer
	 * @return true if the date the computer was discontinued is greater than the one it was introduced
	 * @throws ValidatorException 
	 */
	public boolean DateValidator(LocalDate tm1, LocalDate tm2) throws ValidatorException {
		if((tm2 != null) && (tm1 != null)) {
			if (tm2.isAfter(tm1)) {
				return true;
			}
			else {
				throw new ValidatorException("Date problem : the discontinued date must be greater than the introduced date");
			}
		}
		else if (((tm2 == null) && (tm1 != null)) || ((tm2 != null) && (tm1 == null)))
		{
			return true;
		}
		return true;
	}
}
