package com.excilys.java.formation.service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.CompanyDAO;
import com.excilys.java.formation.persistence.ComputerDAO;
import com.excilys.java.formation.persistence.DAOException;

@Service
@EnableTransactionManagement
public class ComputerService {
	
	private Logger logger = LoggerFactory.getLogger(ComputerService.class);
	@Autowired
	private ComputerDAO computerDAO;
	@Autowired
	private CompanyDAO companyDAO;
	
	public ComputerService(ComputerDAO computers) {
		this.computerDAO = computers;
	}

	/**
	 * Method to show the list of computers
	 * @param limit maximum number of computers in a page
	 * @param offset starting index
	 * @return list of computers starting from offset with length equals to limit
	 * @throws ServiceException
	 */
	public List<Computer> listComputers(int limit, int offset, String columnName, String order) throws ServiceException{
		try {
			if(columnName == null || (!columnName.equals("computer.name") && !columnName.equals("introduced") && !columnName.equals("discontinued") && !columnName.equals("company.name"))) {
				columnName = "computer.id";
			}
			if(order == null || (!order.equals("ASC") && !order.equals("DESC"))) {
				order = "ASC";
			}
			return computerDAO.getListComputer(limit, offset, columnName, order);
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
		ComputerValidator computerValidator = ComputerValidator.INSTANCE;
		String rsult = null;

		try {
			if(computerValidator.idValidator(id, computerDAO)) {
				rsult = "\n"+ computerDAO.getComputer(id) + "\n";
			}
		} catch (ValidatorException | DAOException e) {
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
		try {
			return computerDAO.getComputer(id);
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
		ComputerValidator computerValidator = ComputerValidator.INSTANCE;
		CompanyValidator companyValidator = CompanyValidator.INSTANCE;

		try {
			if((computerValidator.nameValidator(name)) && (computerValidator.dateValidator(time1, time2)) && (companyValidator.idCompanyValidator(manufacturer, companyDAO))) {
				computerDAO.createComputer(name, time1, time2, manufacturer);
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
		ComputerValidator computerValidator = ComputerValidator.INSTANCE;

		try {
			if((computerValidator.idValidator(idUpdate, computerDAO)) && (computerValidator.nameValidator(newName)) && (computerValidator.dateValidator(newDate, newDate2)) && (CompanyValidator.INSTANCE.idCompanyValidator(manufacturer, companyDAO)) ) {
				computerDAO.updateComputer(idUpdate, newName,newDate, newDate2, manufacturer);
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
		ComputerValidator computerValidator = ComputerValidator.INSTANCE;

		try {
			if(computerValidator.idValidator(idDelete, computerDAO)) {
				computerDAO.deleteComputer(idDelete);
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
			return computerDAO.count();
		} catch (DAOException e) {
			logger.debug("Problem in count computer", e);
			throw new ServiceException("ServiceException in count", e);
		}
	}
	
	public int countAfterSearch(String search) throws ServiceException {
		try {
			return computerDAO.countAfterSearch(search);
		} catch (DAOException e) {
			logger.debug("Problem in count computer after search", e);
			throw new ServiceException("ServiceException in countAfterSearch", e);
		}
	}
	
	/**
	 * Method to delete one or many computers with transactions
	 * @param ids list of computers to delete
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteTransaction(List<Long> ids) throws ServiceException {
		try {
			computerDAO.deleteTransaction(ids);
		} catch (DAOException e) {
			logger.debug("Problem in Delete Computer with transactions", e);
			throw new ServiceException("ServiceException in deleteTransaction", e);
		}	
	}
	
	public List<Computer> search(String search, String columnName, String order, int limit, int offset) throws ServiceException {
		try {
			if(columnName == null || (!columnName.equals("computer.name") && !columnName.equals("introduced") && !columnName.equals("discontinued") && !columnName.equals("company.name"))) {
				columnName = "computer.id";
			}
			if(order == null || (!order.equals("ASC") && !order.equals("DESC"))) {
				order = "ASC";
			}
			return computerDAO.search(search, columnName, order, limit, offset);
		} catch (DAOException e) {
			logger.debug("Problem in Search Computer", e);
			throw new ServiceException("ServiceException in search", e);
		}
		
		
	}
}
