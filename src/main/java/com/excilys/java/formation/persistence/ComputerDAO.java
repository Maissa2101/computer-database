package com.excilys.java.formation.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.interfaceDAO.ComputerDAOInterface;
import com.excilys.java.formation.mapper.ComputerMapper;

public enum ComputerDAO implements ComputerDAOInterface {
		
	INSTANCE;
	
	private final String SELECT_REQUEST_LIST = "SELECT * FROM computer;";
	private final String SELECT_REQUEST_DETAILS = "SELECT * FROM computer WHERE id = ?;";
	private final String INSERT_REQUEST = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES (?,?,?,?);";
	private final String UPDATE_REQUEST = "UPDATE computer SET name = ?, introduced = ?, discontinued = ? WHERE id = ?;";
	private final String DELETE_REQUEST = "DELETE FROM computer WHERE id = ?;";
	
	
	@Override
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
	
	
	@Override
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
	
	
	@Override
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


	@Override
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


	@Override
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
