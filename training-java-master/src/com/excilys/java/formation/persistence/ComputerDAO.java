package com.excilys.java.formation.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.java.formation.mapper.Computer;

public class ComputerDAO {
		Connection conn;
	
		/**
		 * Constructor
		 * @param conn connection to the BD
		 */
	public ComputerDAO(Connection conn) {
		this.conn = conn;
	}
	
	/**
	 * Method to get the list of computers
	 * @return list of computers
	 * @throws SQLException
	 */
	public List<Computer> getListComputer() throws SQLException{
		List<Computer> computers = new ArrayList<Computer>();
		
		conn.setAutoCommit(false);
		String query = "SELECT * FROM computer";
		PreparedStatement stmt =  conn.prepareStatement(query);
		ResultSet res = stmt.executeQuery(query);
		
		conn.commit();
		
		while(res.next()) {
			computers.add(new Computer(res.getLong(1), res.getString(2), res.getDate(3), res.getDate(4), res.getString(5)));
		}
		stmt.close();
		return computers;
	}
	
	/**
	 * Method to get a specific computer given its id
	 * @param id
	 * @return the computer where its id = parameter id
	 * @throws SQLException
	 */
	public Computer getComputer(Long id) throws SQLException{
		Computer c = null;
		
		conn.setAutoCommit(false);
		String query = "SELECT * FROM computer WHERE id = ?";
		PreparedStatement stmt =  conn.prepareStatement(query);
		stmt.setLong(1, id);
		ResultSet res = stmt.executeQuery();
		conn.commit();
		
		if(res.next()) {
			c = new Computer(res.getLong(1), res.getString(2),res.getDate(3), res.getDate(4), res.getString(5));
		}
		
		
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
	 */
	
	public void createComputer(Long id, String name, Date intro, Date discontinued, String manufacturer ) throws SQLException{
		int res = 0;
		Computer c = new Computer(id, name, intro, discontinued, manufacturer);
		
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
	 */
public void updateComputer(Long id, String name, Date intro, Date discontinued) throws SQLException{
		int res = 0;
		Computer c = new Computer(id, name, intro, discontinued);
		
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
	 */
	public void deleteComputer(Long id) throws SQLException{
	
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
