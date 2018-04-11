package com.excilys.java.formation.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.java.formation.persistence.ComputerDAO;
import com.excilys.java.formation.persistence.DAOException;



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
	 * @throws ValidatorException 
	 */
	public boolean idValidator(long id, ComputerDAO computerDAO) throws ValidatorException {
		try {
			if(computerDAO.getComputer(id).isPresent()) {
				return true;
			}
			else {
				throw new ValidatorException("This computer doesn't exist");
			}
		} catch (DAOException e) {
			throw new ValidatorException("This computer doesn't exist");
		}
	}

	/**
	 * Method to verify if the dates are valid or not
	 * @param time1 the introduced date of the computer
	 * @param time2 the discontinued date of the computer
	 * @return true if the date the computer was discontinued is greater than the one it was introduced
	 * @throws ValidatorException 
	 */
	public boolean dateValidator(LocalDate time1, LocalDate time2) throws ValidatorException {
		if((time2 != null) && (time1 != null)) 
		{
			if (time2.isAfter(time1)) {
				return true;
			}
			else {
				throw new ValidatorException("Date problem : the discontinued date must be greater than the introduced date");
			}
		}
		else if (((time2 == null) && (time1 != null)) || (time2 != null)) {
			return true;
		}
		return true;
	}
}
