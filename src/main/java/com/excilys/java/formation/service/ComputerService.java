package com.excilys.java.formation.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.interfaceDAO.ComputerDAOInterface;
import com.excilys.java.formation.persistence.ComputerDAO;

public enum ComputerService {
	
	INSTANCE;
	
	
	/**
	 * Method to show the list of computers
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 * @throws SQLException in case of a database access error
	 */
	public List<Computer> listComputers() throws ClassNotFoundException, SQLException {
		ComputerDAO computers = ComputerDAO.INSTANCE;
		return computers.getListComputer();
	}
	
	/**
	 * Method to show the details of a computer given its ID
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 * @throws SQLException in case of a database access error
	 * @throws ValidatorException 
	 */
	public String computerDetails(Long id) throws ClassNotFoundException, SQLException, ValidatorException {
		ComputerDAOInterface computers = ComputerDAO.INSTANCE;
		ComputerValidator computerV = ComputerValidator.INSTANCE;
		String rsult = null;
		
		try {
			if(computerV.idValidator(id)) {
			rsult = "\n"+ computers.getComputer(id) + "\n";
		}
		} catch (ValidatorException e) {
			rsult = e.getMessage();
		}
		return rsult;

	}
	
	/**
	 * Method to create a computer
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 * @throws SQLException in case of a database access error
	 * @throws ValidatorException 
	 */
	public void createComputer(String name, Date tm1, Date tm2, String manufacturer) throws ClassNotFoundException, SQLException, ValidatorException {
		ComputerDAOInterface computers = ComputerDAO.INSTANCE;
		ComputerValidator computerV = ComputerValidator.INSTANCE;
		CompanyValidator companyV = CompanyValidator.INSTANCE;
	
		try {
		    if((computerV.nameValidator(name)) && (computerV.DateValidator(tm1, tm2)) && (companyV.idCompanyValidator(manufacturer))) {
		    	computers.createComputer(name, tm1, tm2, manufacturer);
		    }	
		} catch (ValidatorException e) {
			System.out.println(e.getMessage());
		}	

	}
	
	/**
	 * Method to update a computer details given its ID
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 * @throws SQLException in case of a database access error
	 * @throws ValidatorException 
	 */
	public void updateComputer(Long id_update, String new_name, Date new_date, Date new_date2 ) throws ClassNotFoundException, SQLException, ValidatorException {
		ComputerDAO computers = ComputerDAO.INSTANCE;
		ComputerValidator computerV = ComputerValidator.INSTANCE;

		try {
			if((computerV.idValidator(id_update)) && (computerV.nameValidator(new_name)) && computerV.DateValidator(new_date, new_date2) ) {
				computers.updateComputer(id_update, new_name,new_date, new_date2);
			}
	    } catch (ValidatorException e) {
			System.out.println(e.getMessage());
		}	

	}
	
	/**
	 * Method to delete a computer given its ID
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 * @throws SQLException in case of a database access error
	 * @throws ValidatorException 
	 */
	public void deleteComputer(Long id_delete) throws ClassNotFoundException, SQLException, ValidatorException {
		ComputerDAOInterface computers = ComputerDAO.INSTANCE;
		ComputerValidator computerV = ComputerValidator.INSTANCE;
		
		
		try {
				if(computerV.idValidator(id_delete)) {
					computers.deleteComputer(id_delete);
			    }	
		} catch (ValidatorException e) {
			System.out.println(e.getMessage());
		}	
		
	}
}
