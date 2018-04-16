package com.excilys.java.formation.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.DAOConfigurationException;
import com.excilys.java.formation.persistence.DAOException;
import com.excilys.java.formation.persistence.interfaceDAO.CompanyDAOInterface;

@Service
@EnableTransactionManagement
public class CompanyServiceSpring {
	
	private Logger logger = LoggerFactory.getLogger(CompanyServiceSpring.class);
	@Autowired
	private CompanyDAOInterface companyDAOSpring;
	
	
	public List<Company> listCompanies(int limit, int offset) throws ServiceException {
		try {
			return companyDAOSpring.getListCompany(limit, offset);
		} catch (DAOException e) {
			logger.debug("DAOException problem in listCompanies",e);
			throw new ServiceException("ServiceException in listCompanies", e);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void deleteTransactionCompany(long id) throws ServiceException {
		try {
			companyDAOSpring.deleteCompany(id);
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
			return companyDAOSpring.count();
		} catch (DAOException | DAOConfigurationException e) {
			logger.debug("DAOException problem in count companies",e);
			throw new ServiceException("ServiceException in count companies", e);
		}
	}
}
