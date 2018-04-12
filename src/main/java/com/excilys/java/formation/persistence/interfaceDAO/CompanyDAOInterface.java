package com.excilys.java.formation.persistence.interfaceDAO;

import java.util.List;
import java.util.Optional;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.ComputerDAO;
import com.excilys.java.formation.persistence.DAOConfigurationException;
import com.excilys.java.formation.persistence.DAOException;

public interface CompanyDAOInterface {

	/**
	 * Method to get the list of companies
	 * @return the list of companies
	 * @throws SQLException in case of a database access error
	 * @throws DAOException
	 */
	List<Company> getListCompany(int limit, int offset) throws DAOException;
	
	/**
	 * Counts the number of companies in the DB 
	 * @return the number of companies
	 * @throws DAOConfigurationException
	 * @throws DAOException
	 */
	int count() throws DAOConfigurationException, DAOException ;
	
	/**
	 * Method to get the company by id
	 * @param id
	 * @return the company with id = id
	 * @throws DAOException
	 */
	Optional<Company> getCompany(long id) throws DAOException ;
	
	/**
	 * Method to delete a company and all its related computers
	 * @param id id of the company to delete
	 * @param ids ids of the computers to delete with company id = id
	 * @throws DAOException
	 */
	void deleteCompany(long id) throws DAOException;


}