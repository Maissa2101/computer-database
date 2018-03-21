package com.excilys.java.formation.service;

import java.sql.SQLException;
import java.util.List;


import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.CompanyDAO;
import com.excilys.java.formation.persistence.DAOConfigurationException;

public enum CompanyService {

	INSTANCE;


	/**
	 * Method to show the list of companies
	 * @throws SQLException in case of a database access error
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 */
	public List<Company> listCompanies(int limit, int offset) throws SQLException, ClassNotFoundException {
		CompanyDAO companies = CompanyDAO.INSTANCE;
		return companies.getListCompany(limit, offset);
	}

	/**
	 * counts the number of companies in the DB
	 * @return total number of companies
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 * @throws DAOConfigurationException 
	 */
	public int count() throws SQLException, DAOConfigurationException, ClassNotFoundException {
		return CompanyDAO.INSTANCE.count();
	}
}
