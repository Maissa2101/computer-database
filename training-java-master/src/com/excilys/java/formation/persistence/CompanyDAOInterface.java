package com.excilys.java.formation.persistence;

import java.sql.SQLException;
import java.util.List;

import com.excilys.java.formation.entities.Company;

public interface CompanyDAOInterface {

	/**
	 * Method to get the list of companies
	 * @return the list of companies
	 * @throws SQLException in case of a database access error
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 */
	List<Company> getListCompany() throws SQLException, ClassNotFoundException;

}