package com.excilys.java.formation.interfaceDAO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.excilys.java.formation.entities.Computer;

public interface ComputerDAOInterface {

	/**
	 * Method to get the list of computers
	 * @return list of computers
	 * @throws SQLException in case of a database access error
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 */
	List<Computer> getListComputer() throws SQLException, ClassNotFoundException;

	/**
	 * Method to get a specific computer given its id
	 * @param id the id of the computer
	 * @return the computer where its id = parameter id
	 * @throws SQLException in case of a database access error
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 */
	Optional<Computer> getComputer(Long id) throws SQLException, ClassNotFoundException;

	/**
	 * Method to create a new computer
	 * @param name name of the computer to create 
	 * @param intro introduced time of the computer
	 * @param discontinued discontinued time of the computer
	 * @param manufacturer manufacturer of the new computer
	 * @throws SQLException in case of a database access error
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 */

	Long createComputer(String name, Date intro, Date discontinued, String manufacturer)
			throws SQLException, ClassNotFoundException;

	/**
	 * Method to update a computer
	 * @param id id of the computer to update
	 * @param name new name of the computer
	 * @param intro new introduced date 
	 * @param discontinued new discontinued date
	 * @throws SQLException in case of a database access error
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 */
	void updateComputer(Long id, String name, Date intro, Date discontinued)
			throws SQLException, ClassNotFoundException;

	/**
	 * Method to delete an existing computer
	 * @param id id of the computer to delete
	 * @throws SQLException in case of a database access error
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 */
	void deleteComputer(Long id) throws SQLException, ClassNotFoundException;

}