package com.excilys.java.formation.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.excilys.java.formation.mapper.Computer;

public class ComputerDAO {
		Connection conn;
	
	public ComputerDAO(Connection conn) {
		this.conn = conn;
	}

	public List<Computer> getListComputer() throws SQLException{
		List<Computer> computers = new ArrayList<Computer>();
		
		conn.setAutoCommit(false);
		String query = "SELECT * FROM computer";
		PreparedStatement stmt =  conn.prepareStatement(query);
		ResultSet res = stmt.executeQuery(query);
		
		conn.commit();
		
		while(res.next()) {
			computers.add(new Computer(res.getLong(1), res.getString(2)));
		}
		stmt.close();
		return computers;
	}
	
	public Computer getComputer(Long id) throws SQLException{
		Computer c = null;
		
		conn.setAutoCommit(false);
		String query = "SELECT * FROM computer WHERE id = ?";
		PreparedStatement stmt =  conn.prepareStatement(query);
		stmt.setLong(1, id);
		ResultSet res = stmt.executeQuery();
		conn.commit();
		
		if(res.next()) {
			c = new Computer(res.getLong(1), res.getString(2));
		}
		
		
		if (stmt != null) {
		stmt.close();}
		return c;
		
	}
	
	public void createComputer(Long id, String name, Timestamp intro, Timestamp disconnect, String manufacturer ) throws SQLException{
		
		conn.setAutoCommit(false);
		String query = "INSERT INTO computer VALUES (?,?,?,?,?)";
		PreparedStatement stmt =  conn.prepareStatement(query);
		stmt.setLong(1, id);
		stmt.setString(2, name);
		stmt.setTimestamp(3, intro);
		stmt.setTimestamp(4, disconnect);
		stmt.setString(5, manufacturer);
		int res = stmt.executeUpdate();
		conn.commit();
		
		if(res == 1 )
			System.out.println("\n computer added");
		else
			System.out.println("\n computer not added");
		
		if (stmt != null) {
			stmt.close();}		
	}
	
public void updateComputer(Long id, String name, Timestamp intro, Timestamp disconnect) throws SQLException{
		
		conn.setAutoCommit(false);
		String query = "UPDATE computer SET name = ? AND introduced = ? AND discontinued = ? WHERE id = ?";
		PreparedStatement stmt =  conn.prepareStatement(query);
		stmt.setLong(1, id);
		stmt.setString(2, name);
		stmt.setTimestamp(3, intro);
		stmt.setTimestamp(4, disconnect);
		int res = stmt.executeUpdate();
		conn.commit();
		
		if(res == 1 )
			System.out.println("\n computer updated");
		else
			System.out.println("\n computer not updated");
		
		if (stmt != null) {
			stmt.close();}		
	}

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
