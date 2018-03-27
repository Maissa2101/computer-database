package com.excilys.java.formation.service;


import java.util.List;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.CompanyDAO;

public enum CompanyService {

	INSTANCE;

	/**
	 * Method to show the list of companies
	 * @param limit maximum number of companies in a page
	 * @param offset offset of the first element in the page
	 * @return list of companies starting from offset with length equals to limit
	 * @throws ServiceException
	 */
	public List<Company> listCompanies(int limit, int offset) throws ServiceException {
		CompanyDAO companies = CompanyDAO.INSTANCE;
		return companies.getListCompany(limit, offset);
	}

	/**
	 * counts the number of companies in the DB
	 * @return the number of companies in the DB
	 * @throws ServiceException
	 */
	public int count() throws ServiceException {
		return CompanyDAO.INSTANCE.count();
	}
}
