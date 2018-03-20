package com.excilys.java.formation.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.interfaceDAO.ComputerDAOInterface;
import com.excilys.java.formation.mapper.ComputerMapper;
import com.excilys.java.formation.service.CompanyService;

public enum ComputerDAO implements ComputerDAOInterface {

	INSTANCE;
	Logger logger = LoggerFactory.getLogger(CompanyService.class);

	private final String SELECT_REQUEST_LIST = "SELECT id, name, introduced, discontinued, company_id FROM computer LIMIT ? OFFSET ?;";
	private final String SELECT_REQUEST_DETAILS = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id = ?;";
	private final String INSERT_REQUEST = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES (?,?,?,?);";
	private final String UPDATE_REQUEST = "UPDATE computer SET name = ?, introduced = ?, discontinued = ? WHERE id = ?;";
	private final String DELETE_REQUEST = "DELETE FROM computer WHERE id = ?;";
	private final String COUNT = "SELECT count(*) as total FROM computer;";

	@Override
	public List<Computer> getListComputer(int limit, int offset) throws SQLException, ClassNotFoundException{

		SQLConnection.getInstance();
		Connection conn = SQLConnection.getConnection();
		String query = SELECT_REQUEST_LIST;
		PreparedStatement stmt =  conn.prepareStatement(query);
		stmt.setInt(1, limit);
		stmt.setInt(2, offset);
		ResultSet res = stmt.executeQuery();	
		List<Computer> l = ComputerMapper.INSTANCE.getListComputerFromResultSet(res);
		stmt.close();
		SQLConnection.closeConnection(conn);
		return l;
	}

	@Override
	public Optional<Computer> getComputer(Long id) throws SQLException, ClassNotFoundException{

		SQLConnection.getInstance();
		Connection conn = SQLConnection.getConnection();
		String query = SELECT_REQUEST_DETAILS;
		PreparedStatement stmt =  conn.prepareStatement(query);
		stmt.setLong(1, id);
		ResultSet res = stmt.executeQuery();

		Computer c = ComputerMapper.INSTANCE.getComputerDetailsFromResultSet(res);
		if (stmt != null) {
			stmt.close();}
		SQLConnection.closeConnection(conn);
		return Optional.ofNullable(c);

	}

	@Override
	public Long createComputer( String name, Date intro, Date discontinued, String manufacturer ) throws SQLException, ClassNotFoundException{
		int res = 0;
		Computer c = new Computer(name, intro, discontinued, manufacturer);

		SQLConnection.getInstance();
		Connection conn = SQLConnection.getConnection();
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

		Long result = -1L;
		if(res == 1 ) {
			res2.next();
			logger.info("computer added with id : "+ res2.getLong(1));
			result = res2.getLong(1);
		}
		else {
			logger.info("computer not added");
		}

		if (stmt != null) {
			stmt.close();
		}
		SQLConnection.closeConnection(conn);
		return result;
	}

	@Override
	public void updateComputer(Long id, String name, Date intro, Date discontinued) throws SQLException, ClassNotFoundException{
		int res = 0;
		Computer c = new Computer(id, name, intro, discontinued);

		SQLConnection.getInstance();
		Connection conn = SQLConnection.getConnection();
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

		if(res == 1)
			logger.info("computer updated");
		else
			logger.info("computer not updated");

		if (stmt != null) {
			stmt.close();}	
		SQLConnection.closeConnection(conn);
	}

	@Override
	public void deleteComputer(Long id) throws SQLException, ClassNotFoundException{

		SQLConnection.getInstance();
		Connection conn = SQLConnection.getConnection();
		String query = DELETE_REQUEST;
		PreparedStatement stmt =  conn.prepareStatement(query);
		stmt.setLong(1, id);
		int res = stmt.executeUpdate();

		if(res == 1 )
			logger.info("computer deleted");
		else
			logger.info("computer not deleted");

		if (stmt != null) {
			stmt.close();}		
		SQLConnection.closeConnection(conn);
	}

	@Override
	public int count() throws SQLException {
		SQLConnection.getInstance();
		Connection conn = SQLConnection.getConnection();
		String query = COUNT;
		PreparedStatement stmt =  conn.prepareStatement(query);
		ResultSet res = stmt.executeQuery();
		int result = 0;
		if (res.next()) {
			result =  res.getInt("total");
		}
		else {
			throw new DAOException("Problem in your count");
		}
		if (stmt != null) {
			stmt.close();}	
		SQLConnection.closeConnection(conn);
		return result;

	}
}
