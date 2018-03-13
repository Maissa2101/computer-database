package com.excilys.java.formation.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.excilys.java.formation.mapper.Computer;
import com.excilys.java.formation.mapper.ComputerMapper;

public class ComputerDAO {
		Connection conn;
	
		/**
		 * Constructor
		 * @param conn connection to the BD
		 */
	public ComputerDAO() {
	
	}
	
	/**
	 * Method to get the list of computers
	 * @return list of computers
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public List<Computer> getListComputer() throws SQLException, ClassNotFoundException{
		
		Connection conn = SQLConnection.getConnection();
		conn.setAutoCommit(false);
		String query = "SELECT * FROM computer";
		PreparedStatement stmt =  conn.prepareStatement(query);
		ResultSet res = stmt.executeQuery(query);	
		conn.commit();
		List<Computer> l = ComputerMapper.getComputerMapper().getListComputerFromResultSet(res);
		stmt.close();
		return l;
	}
	
	/**
	 * Method to get a specific computer given its id
	 * @param id
	 * @return the computer where its id = parameter id
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public Computer getComputer(Long id) throws SQLException, ClassNotFoundException{
		
		Connection conn = SQLConnection.getConnection();
		conn.setAutoCommit(false);
		String query = "SELECT * FROM computer WHERE id = ?";
		PreparedStatement stmt =  conn.prepareStatement(query);
		stmt.setLong(1, id);
		ResultSet res = stmt.executeQuery();
		conn.commit();
		
		Computer c = ComputerMapper.getComputerMapper().getComputerDetailsFromResultSet(res);
		if (stmt != null) {
		stmt.close();}
		return c;
		
	}
	
	/**
	 * Method to create a new computer
	 * @param id id of the computer to create
	 * @param name name of the computer to create 
	 * @param intro introduced time of the computer
	 * @param discontinued discontinued time of the computer
	 * @param manufacturer manufacturer of the new computer
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	
	public void createComputer(Long id, String name, Date intro, Date discontinued, String manufacturer ) throws SQLException, ClassNotFoundException{
		int res = 0;
		Computer c = new Computer(id, name, intro, discontinued, manufacturer);
		
		Connection conn = SQLConnection.getConnection();
		conn.setAutoCommit(false);
		String query = "INSERT INTO computer VALUES (?,?,?,?,?)";
		PreparedStatement stmt =  conn.prepareStatement(query);
		stmt.setLong(1, id);
		stmt.setString(2, name);
		
		if (c.getIntroduced() == null) {
			stmt.setNull(3, java.sql.Types.DATE);
		}
		else {
			stmt.setDate(3, intro);
		}
		
		if (c.getDiscontinued() == null) {
			stmt.setNull(4, java.sql.Types.DATE);
		}
		else {
			stmt.setDate(4, discontinued);
		}
		stmt.setString(5, manufacturer);
		if((discontinued != null) && (intro != null)) {
		 if(discontinued.after(intro)) {
			res = stmt.executeUpdate();
		 }
		 else {
			System.out.println("\n Date problem : the discontinued date must be greater than the introduced date");
		 }
		}
		else {
			res = stmt.executeUpdate();
		}
		
		conn.commit();
		
		if(res == 1 )
			System.out.println("\n computer added");
		else
			System.out.println("\n computer not added");
		
		if (stmt != null) {
			stmt.close();}		
	}

	/**
	 * Method to update a computer
	 * @param id id of the computer to update
	 * @param name new name of the computer
	 * @param intro new introduced date 
	 * @param discontinued new discontinued date
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
public void updateComputer(Long id, String name, Date intro, Date discontinued) throws SQLException, ClassNotFoundException{
		int res = 0;
		Computer c = new Computer(id, name, intro, discontinued);
		
		Connection conn = SQLConnection.getConnection();
		conn.setAutoCommit(false);
		String query = "UPDATE computer SET name = ?, introduced = ?, discontinued = ? WHERE id = ?";
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
		
		if((discontinued != null) && (intro != null)) {
			 if(discontinued.after(intro)) {
				 res = stmt.executeUpdate();
			 }
			 else{
					System.out.println("\n Date problem : the discontinued date must be greater than the introduced date");
				 }
		}
		else {
			res = stmt.executeUpdate();
		}
			
		conn.commit();
		
		if(res == 1 )
			System.out.println("\n computer updated");
		else
			System.out.println("\n computer not updated");
		
		if (stmt != null) {
			stmt.close();}		
	}

	/**
	 * Method to delete an existing computer
	 * @param id id of the computer to delete
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public void deleteComputer(Long id) throws SQLException, ClassNotFoundException{
	
	Connection conn = SQLConnection.getConnection();
	conn.setAutoCommit(false);
	String query = "DELETE FROM computer WHERE id = ?" + 
			"";
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
}
	
}
