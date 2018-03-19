package com.excilys.java.formation.service;

import java.sql.SQLException;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.CompanyDAO;

public enum CompanyService {
	
	INSTANCE;
	
	Logger logger = LoggerFactory.getLogger(CompanyService.class);
	
	/**
	 * Method to show the list of companies
	 * @throws SQLException in case of a database access error
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 */
	public List<Company> listCompanies() throws SQLException, ClassNotFoundException {
		CompanyDAO companies = CompanyDAO.INSTANCE;
		return companies.getListCompany();
		
	}
}
