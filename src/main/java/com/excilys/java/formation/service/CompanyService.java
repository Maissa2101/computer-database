package com.excilys.java.formation.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.CompanyDAO;
import com.excilys.java.formation.persistence.ComputerDAO;
import com.excilys.java.formation.persistence.DAOException;

public enum CompanyService {

	INSTANCE;
	Logger logger = LoggerFactory.getLogger(CompanyService.class);
	
	/**
	 * Method to show the list of companies
	 * @param limit maximum number of companies in a page
	 * @param offset offset of the first element in the page
	 * @return list of companies starting from offset with length equals to limit
	 * @throws ServiceException
	 */
	public List<Company> listCompanies(int limit, int offset) throws ServiceException {
		CompanyDAO companies = CompanyDAO.INSTANCE;
		try {
			return companies.getListCompany(limit, offset);
		} catch (DAOException e) {
			logger.debug("DAOException problem in listCompanies",e);
			throw new ServiceException("ServiceException in listCompanies", e);
		}
	}
	
	public void deleteTransactionCompany(long id) throws ServiceException {
		CompanyDAO companies = CompanyDAO.INSTANCE;
		try {
			companies.deleteCompany(id);
		} catch (DAOException e) {
			logger.debug("Problem in Delete Company with transactions", e);
			throw new ServiceException("ServiceException in deleteTransactionCompany", e);
		}	
	}

	/**
	 * counts the number of companies in the DB
	 * @return the number of companies in the DB
	 * @throws ServiceException
	 */
	public int count() throws ServiceException {
		try {
			return CompanyDAO.INSTANCE.count();
		} catch (DAOException e) {
			logger.debug("DAOException problem in count companies",e);
			throw new ServiceException("ServiceException in count companies", e);
		}
	}
}
