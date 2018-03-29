package com.excilys.java.formation.service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.ComputerDAO;
import com.excilys.java.formation.persistence.DAOException;
import com.excilys.java.formation.persistence.interfaceDAO.ComputerDAOInterface;

public enum ComputerService {

	INSTANCE;
	Logger logger = LoggerFactory.getLogger(CompanyService.class);

	/**
	 * Method to show the list of computers
	 * @param limit maximum number of computers in a page
	 * @param offset starting index
	 * @return list of computers starting from offset with length equals to limit
	 * @throws ServiceException
	 */
	public List<Computer> listComputers(int limit, int offset) throws ServiceException{
		ComputerDAO computers = ComputerDAO.INSTANCE;
		try {
			return computers.getListComputer(limit, offset);
		} catch (DAOException e) {
			logger.debug("Problem in list Computer", e);
			throw new ServiceException("ServiceException in listComputer", e);
		}
	}

	/**
	 * Method to show the details of a computer given its ID
	 * @param id id of the computer
	 * @return computer details
	 * @throws ServiceException
	 * @throws ValidatorException
	 */
	public String computerDetails(long id) throws ServiceException, ValidatorException {
		ComputerDAO computers = ComputerDAO.INSTANCE;
		ComputerValidator computerValidator = ComputerValidator.INSTANCE;
		String rsult = null;

		try {
			if(computerValidator.idValidator(id)) {
				rsult = "\n"+ computers.getComputer(id) + "\n";
			}
		} catch (ValidatorException | DAOException e) {
			rsult = "Problem in Computer Details";
			throw new ServiceException("ServiceException in computerDetails", e);
		}
		return rsult;

	}

	/**
	 * Method to get a computer given its Id
	 * @param id the id of the computer to get
	 * @return the computer
	 * @throws ServiceException
	 */
	public Optional<Computer> getComputer(long id) throws ServiceException {
		ComputerDAO computers = ComputerDAO.INSTANCE;
		try {
			return computers.getComputer(id);
		} catch (DAOException e) {
			logger.debug("Problem in getComputer", e);
			throw new ServiceException("ServiceException in getComputer", e);
		}
	}
	/**
	 * Method to create a computer
	 * @param name name of the computer to create
	 * @param time1 introduced date of the computer to create
	 * @param time2 discontinued date of the computer to create
	 * @param manufacturer the company of the computer to create
	 * @throws ServiceException
	 * @throws ValidatorException
	 */
	public void createComputer(String name, LocalDate time1, LocalDate time2, String manufacturer) throws ServiceException, ValidatorException {

		ComputerDAO computers = ComputerDAO.INSTANCE;
		ComputerValidator computerValidator = ComputerValidator.INSTANCE;
		CompanyValidator companyValidator = CompanyValidator.INSTANCE;

		try {
			if((computerValidator.nameValidator(name)) && (computerValidator.DateValidator(time1, time2)) && (companyValidator.idCompanyValidator(manufacturer))) {
				computers.createComputer(name, time1, time2, manufacturer);
			}	
		} catch (ValidatorException | DAOException e) {
			logger.debug("Problem in Create Computer", e);
			throw new ServiceException("ServiceException in createComputer", e);
		}	

	}

	/**
	 * Method to update a computer details given its ID
	 * @param idUpdate
	 * @param newName new name of the computer to update
	 * @param newDate new introduced date of the computer to update
	 * @param newDate2 new discontinued date of the computer to update
	 * @param manufacturer new company of the computer to update
	 * @throws ServiceException
	 * @throws ValidatorException
	 */
	public void updateComputer(long idUpdate, String newName, LocalDate newDate, LocalDate newDate2, String manufacturer ) throws ServiceException, ValidatorException {
		ComputerDAO computers = ComputerDAO.INSTANCE;
		ComputerValidator computerValidator = ComputerValidator.INSTANCE;

		try {
			if((computerValidator.idValidator(idUpdate)) && (computerValidator.nameValidator(newName)) && (computerValidator.DateValidator(newDate, newDate2)) && (CompanyValidator.INSTANCE.idCompanyValidator(manufacturer)) ) {
				computers.updateComputer(idUpdate, newName,newDate, newDate2, manufacturer);
			}
		} catch (ValidatorException | DAOException e) {
			logger.debug("Problem in Update Computer", e);
			throw new ServiceException("ServiceException in updateComputer", e);
		}	

	}

	/**
	 * Method to delete a computer given its ID
	 * @param idDelete id of the computer to delete
	 * @throws ServiceException
	 * @throws ValidatorException
	 */
	public void deleteComputer(long idDelete) throws ServiceException, ValidatorException {
		ComputerDAO computers = ComputerDAO.INSTANCE;
		ComputerValidator computerValidator = ComputerValidator.INSTANCE;

		try {
			if(computerValidator.idValidator(idDelete)) {
				computers.deleteComputer(idDelete);
			}	
		} catch (ValidatorException | DAOException e) {
			logger.debug("Problem in Delete Computer", e);
			throw new ServiceException("ServiceException in deleteComputer", e);
		}	

	}

	/**
	 * Counts the number of computers in the DB
	 * @return total number of computers
	 * @throws ServiceException
	 */
	public int count() throws ServiceException {
		try {
			return ComputerDAO.INSTANCE.count();
		} catch (DAOException e) {
			logger.debug("Problem in count computer", e);
			throw new ServiceException("ServiceException in count", e);
		}
	}
	
	/**
	 * Method to delete one or many computers with transactions
	 * @param ids list of computers to delete
	 * @throws ServiceException
	 */
	public void deleteTransaction(List<Long> ids) throws ServiceException {
		ComputerDAO computers = ComputerDAO.INSTANCE;
		try {
			computers.deleteTransaction(ids);
		} catch (DAOException e) {
			logger.debug("Problem in Delete Computer with transactions", e);
			throw new ServiceException("ServiceException in deleteTransaction", e);
		}	
	}
	
	public List<Computer> search(String search, String columnName, String order, int limit, int offset) throws ServiceException {
		ComputerDAO computers = ComputerDAO.INSTANCE;
		try {
			if(columnName == null || !columnName.equals("computer.name") || !columnName.equals("introduced") || !columnName.equals("discontinued") || !columnName.equals("company.name")) {
				columnName = "computer.name";
			}
			if(order == null || !order.equals("ASC") || !order.equals("DESC")) {
				order = "ASC";
			}
			return computers.search(search, columnName, order, limit, offset);
		} catch (DAOException e) {
			logger.debug("Problem in Search Computer", e);
			throw new ServiceException("ServiceException in search", e);
		}
		
		
	}
}
