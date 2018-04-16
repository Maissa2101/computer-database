package com.excilys.java.formation.persistence.interfaceDAO;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import com.excilys.java.formation.entities.Computer;

public interface ComputerDAOInterface {

	/**
	 * Method to get the list of computers orders by id 
	 * @param limit maximum number of computers per page
	 * @param offset starting offset of the pagination
	 * @param columnName name of the column to sort by
	 * @param order order to apply 
	 * @return list of computers
	 * @throws DAOException
	 */
	List<Computer> getListComputer(int limit,int offset, String columnName, String order);

	/**
	 * Method to get a computer by its id
	 * @param id id of the computer to get
	 * @return the computer with id = id
	 * @throws DAOException
	 */
	Optional<Computer> getComputer(long id);

	/**
	 * Method to create a new computer
	 * @param name name of the computer to create
	 * @param intro introduced date of the computer to create
	 * @param discontinued discontinued date of the computer to create
	 * @param manufacturer new company of the computer to create
	 * @return the id of the created computer
	 * @throws DAOException
	 */
	long createComputer(String name, LocalDate intro, LocalDate discontinued, String manufacturer);

	/**
	 * Method to update a computer in the db
	 * @param id id of the computer to update
	 * @param name new name of the computer
	 * @param intro new introduced date of the computer
	 * @param discontinued new discontinued date of the computer
	 * @param manufacturer new company of the computer
	 * @throws DAOException
	 */
	void updateComputer(long id, String name, LocalDate intro, LocalDate discontinued, String manufacturer);

	/**
	 * Method to delete a computer from the db
	 * @param id id of the computer to delete
	 * @throws DAOException
	 */
	void deleteComputer(long id);
	
	/**
	 * Counts the total number of computers in the DB
	 * @return the total number of computers
	 * @throws DAOConfigurationException
	 * @throws DAOException
	 */
	int count();
	
	/**
	 * Counts the number of elements after the search execution
	 * @param search the word to search in the db 
	 * @return the number of elements after the search
	 * @throws DAOException
	 */
	int countAfterSearch(String search);
	
	/**
	 * Method to delete many computers 
	 * @param ids list of ids of the computers to delete
	 * @throws DAOException
	 */
	void deleteTransaction(List<Long> ids);
	
	/**
	 * Method to search elements in my db
	 * @param search the word to search in the db 
	 * @param columnName order by the column name  
	 * @param order order in which to order
	 * @param limit number of computers per page
	 * @param offset starting offset of the pagination
	 * @return list of computers after the search execution
	 * @throws DAOException
	 */
	List<Computer> search(String search, String columnName, String order, int limit, int offset);
	
	public void deleteTransactionCompany(long id);
}