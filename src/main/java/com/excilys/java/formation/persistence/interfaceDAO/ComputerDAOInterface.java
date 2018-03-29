package com.excilys.java.formation.persistence.interfaceDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.DAOConfigurationException;
import com.excilys.java.formation.persistence.DAOException;

public interface ComputerDAOInterface {

	/**
	 * Method to get the list of computers
	 * @return list of computers
	 * @throws SQLException in case of a database access error
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 */
	List<Computer> getListComputer(int limit,int offset, String columnName, String order) throws DAOException;

	/**
	 * Method to get a specific computer given its id
	 * @param id the id of the computer
	 * @return the computer where its id = parameter id
	 * @throws SQLException in case of a database access error
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 */
	Optional<Computer> getComputer(long id) throws DAOException;

	/**
	 * Method to create a new computer
	 * @param name name of the computer to create 
	 * @param intro introduced time of the computer
	 * @param discontinued discontinued time of the computer
	 * @param manufacturer manufacturer of the new computer
	 * @throws SQLException in case of a database access error
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 */

	long createComputer(String name, LocalDate intro, LocalDate discontinued, String manufacturer)
			throws DAOException;

	/**
	 * Method to update a computer
	 * @param id id of the computer to update
	 * @param name new name of the computer
	 * @param intro new introduced date 
	 * @param discontinued new discontinued date
	 * @param manufacturer new company of the computer
	 * @throws SQLException in case of a database access error
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 */
	void updateComputer(long id, String name, LocalDate intro, LocalDate discontinued, String manufacturer)
			throws DAOException;

	/**
	 * Method to delete an existing computer
	 * @param id id of the computer to delete
	 * @throws SQLException in case of a database access error
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 */
	void deleteComputer(long id) throws DAOException;
	
	/**
	 * Counts the number of computers in the DB
	 * @return the number of computers
	 * @throws DAOConfigurationException
	 * @throws DAOException
	 */
	int count() throws DAOConfigurationException, DAOException;
	
	/**
	 * Method to delete many computers 
	 * @param ids list of ids of the computers to delete
	 * @throws DAOException
	 */
	void deleteTransaction(List<Long> ids) throws DAOException;

	List<Computer> search(String search, String columnName, String order, int limit, int offset) throws DAOException;
	
}