package com.excilys.java.formation.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.mapper.ComputerMapper;

public enum ComputerDAO {
		
	INSTANCE;
	
	private final String SELECT_REQUEST_LIST = "SELECT * FROM computer;";
	private final String SELECT_REQUEST_DETAILS = "SELECT * FROM computer WHERE id = ?;";
	private final String INSERT_REQUEST = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES (?,?,?,?);";
	private final String UPDATE_REQUEST = "UPDATE computer SET name = ?, introduced = ?, discontinued = ? WHERE id = ?;";
	private final String DELETE_REQUEST = "DELETE FROM computer WHERE id = ?;";
	
	/**
	 * Method to get the list of computers
	 * @return list of computers
	 * @throws SQLException in case of a database access error
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 */
	public List<Computer> getListComputer() throws SQLException, ClassNotFoundException{
		
		SQLConnection.getInstance();
		Connection conn = SQLConnection.getConnection();
		conn.setAutoCommit(false);
		String query = SELECT_REQUEST_LIST;
		PreparedStatement stmt =  conn.prepareStatement(query);
		ResultSet res = stmt.executeQuery(query);	
		conn.commit();
		List<Computer> l = ComputerMapper.INSTANCE.getListComputerFromResultSet(res);
		stmt.close();
		SQLConnection.closeConnection();
		return l;
	}
	
	/**
	 * Method to get a specific computer given its id
	 * @param id the id of the computer
	 * @return the computer where its id = parameter id
	 * @throws SQLException in case of a database access error
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 */
	public Computer getComputer(Long id) throws SQLException, ClassNotFoundException{
		
		SQLConnection.getInstance();
		Connection conn = SQLConnection.getConnection();
		conn.setAutoCommit(false);
		String query = SELECT_REQUEST_DETAILS;
		PreparedStatement stmt =  conn.prepareStatement(query);
		stmt.setLong(1, id);
		ResultSet res = stmt.executeQuery();
		conn.commit();
		
		Computer c = ComputerMapper.INSTANCE.getComputerDetailsFromResultSet(res);
		if (stmt != null) {
		stmt.close();}
		SQLConnection.closeConnection();
		return c;
		
	}
	
	/**
	 * Method to create a new computer
	 * @param name name of the computer to create 
	 * @param intro introduced time of the computer
	 * @param discontinued discontinued time of the computer
	 * @param manufacturer manufacturer of the new computer
	 * @throws SQLException in case of a database access error
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 */
	
	public Long createComputer( String name, Date intro, Date discontinued, String manufacturer ) throws SQLException, ClassNotFoundException{
		int res = 0;
		Computer c = new Computer(name, intro, discontinued, manufacturer);
		
		SQLConnection.getInstance();
		Connection conn = SQLConnection.getConnection();
		conn.setAutoCommit(false);
		String query = INSERT_REQUEST;
		PreparedStatement stmt =  conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		
		stmt.setString(1, name);
		
		if (c.getIntroduced() == null) {
			stmt.setNull(2, java.sql.Types.DATE);
		}
		else {
			stmt.setDate(2, intro);
		}
		
		if (c.getDiscontinued() == null) {
			stmt.setNull(3, java.sql.Types.DATE);
		}
		else {
			stmt.setDate(3, discontinued);
		}
		
		if(c.getManufacturer() == null) {
			stmt.setNull(4, java.sql.Types.VARCHAR);
		}
		else {
			stmt.setString(4, manufacturer);
		}
		
		res = stmt.executeUpdate();
		ResultSet res2 = stmt.getGeneratedKeys();
		conn.commit();
		
		Long result = -1L;
		if(res == 1 ) {
			res2.next();
			System.out.println("\n computer added with id : "+ res2.getLong(1));
			result = res2.getLong(1);
		}
		else {
			System.out.println("\n computer not added");
		}
		
		if (stmt != null) {
			stmt.close();
		}
		SQLConnection.closeConnection();
		return result;
	}

	/**
	 * Method to update a computer
	 * @param id id of the computer to update
	 * @param name new name of the computer
	 * @param intro new introduced date 
	 * @param discontinued new discontinued date
	 * @throws SQLException in case of a database access error
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 */
	public void updateComputer(Long id, String name, Date intro, Date discontinued) throws SQLException, ClassNotFoundException{
		int res = 0;
		Computer c = new Computer(id, name, intro, discontinued);
		
		SQLConnection.getInstance();
		Connection conn = SQLConnection.getConnection();
		conn.setAutoCommit(false);
		String query = UPDATE_REQUEST;
		PreparedStatement stmt =  conn.prepareStatement(query);
		stmt.setString(1, name);
		
		
		if (c.getIntroduced() == null) {
			stmt.setNull(2, java.sql.Types.DATE);
		}
		else {
			stmt.setDate(2, intro);
		}
		
		if (c.getDiscontinued() == null) {
			stmt.setNull(3, java.sql.Types.DATE);
		}
		else {
			stmt.setDate(3, discontinued);
		}

		stmt.setLong(4, id);
		
		res = stmt.executeUpdate();
		conn.commit();
		
		if(res == 1)
			System.out.println("\n computer updated");
		else
			System.out.println("\n computer not updated");
		
		if (stmt != null) {
			stmt.close();}	
		SQLConnection.closeConnection();
	}

	/**
	 * Method to delete an existing computer
	 * @param id id of the computer to delete
	 * @throws SQLException in case of a database access error
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 */
	public void deleteComputer(Long id) throws SQLException, ClassNotFoundException{
	
	SQLConnection.getInstance();
	Connection conn = SQLConnection.getConnection();
	conn.setAutoCommit(false);
	String query = DELETE_REQUEST;
	PreparedStatement stmt =  conn.prepareStatement(query);
	stmt.setLong(1, id);
	int res = stmt.executeUpdate();
	conn.commit();
	
	if(res == 1 )
		System.out.println("\n computer deleted");
	else
		System.out.println("\n computer not deleted");
	
	if (stmt != null) {
		stmt.close();}		
	SQLConnection.closeConnection();
}

	
}
