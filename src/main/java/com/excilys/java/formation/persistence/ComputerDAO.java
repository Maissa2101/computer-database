package com.excilys.java.formation.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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

	private final String SELECT_REQUEST_LIST = "SELECT computer.id, computer.name, introduced, discontinued, company.name FROM computer LEFT JOIN company ON computer.company_id=company.id LIMIT ? OFFSET ?;";
	private final String SELECT_REQUEST_DETAILS = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id = ?;";
	private final String INSERT_REQUEST = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES (?,?,?,?);";
	private final String UPDATE_REQUEST = "UPDATE computer SET name = ?, introduced = ?, discontinued = ? WHERE id = ?;";
	private final String DELETE_REQUEST = "DELETE FROM computer WHERE id = ?;";
	private final String COUNT = "SELECT count(*) as total FROM computer;";

	@Override
	public List<Computer> getListComputer(int limit, int offset) throws DAOException{
		List<Computer> l = null;
		try {
			SQLConnection.getInstance();
			Connection conn = SQLConnection.getConnection();
			String query = SELECT_REQUEST_LIST;
			PreparedStatement stmt =  conn.prepareStatement(query);
			stmt.setInt(1, limit);
			stmt.setInt(2, offset);
			ResultSet res = stmt.executeQuery();	
			l = ComputerMapper.INSTANCE.getListComputerFromResultSet(res);
			stmt.close();
			SQLConnection.closeConnection(conn);
			return l;
		} catch (DAOConfigurationException | ClassNotFoundException | SQLException e) {
			logger.info("Problem in ComputerDAO");
		}
		return l;
	}

	@Override
	public Optional<Computer> getComputer(Long id) throws DAOException{
		Computer c = null;
		try {
			SQLConnection.getInstance();
			Connection conn = SQLConnection.getConnection();
			String query = SELECT_REQUEST_DETAILS;
			PreparedStatement stmt =  conn.prepareStatement(query);
			stmt.setLong(1, id);
			ResultSet res = stmt.executeQuery();

			c = ComputerMapper.INSTANCE.getComputerDetailsFromResultSet(res);
			if (stmt != null) {
				stmt.close();}
			SQLConnection.closeConnection(conn);
			return Optional.ofNullable(c);
		} catch (DAOConfigurationException | ClassNotFoundException | SQLException e) {
			logger.info("Problem in ComputerDAO");
		}
		return Optional.ofNullable(c);
	}

	@Override
	public Long createComputer( String name, LocalDate intro, LocalDate discontinued, String manufacturer ) throws DAOException{
		int res = 0;
		Long result = 0L;
		Computer c = new Computer(name, intro, discontinued, manufacturer);

		try {
			SQLConnection.getInstance();
			Connection conn = SQLConnection.getConnection();
			String query = INSERT_REQUEST;
			PreparedStatement stmt =  conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, name);


			if (c.getIntroduced() == null) {
				stmt.setNull(2, java.sql.Types.DATE);
			}
			else {
				Date introduced = Date.valueOf(intro);
				stmt.setDate(2, introduced);
			}

			if (c.getDiscontinued() == null) {
				stmt.setNull(3, java.sql.Types.DATE);
			}
			else {
				Date disc = Date.valueOf(discontinued);
				stmt.setDate(3, disc);
			}

			if(c.getManufacturer() == null) {
				stmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				stmt.setString(4, manufacturer);
			}

			res = stmt.executeUpdate();
			ResultSet res2 = stmt.getGeneratedKeys();

			result = -1L;
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
		} catch (DAOConfigurationException | ClassNotFoundException | SQLException e) {
			logger.info("Problem in ComputerDAO");
		}
		return result;

	}

	@Override
	public void updateComputer(Long id, String name, LocalDate intro, LocalDate discontinued) throws DAOException{
		int res = 0;
		Computer c = new Computer(id, name, intro, discontinued);

		try {
			SQLConnection.getInstance();
			Connection conn = SQLConnection.getConnection();
			String query = UPDATE_REQUEST;
			PreparedStatement stmt =  conn.prepareStatement(query);
			stmt.setString(1, name);


			if (c.getIntroduced() == null) {
				stmt.setNull(2, java.sql.Types.DATE);
			}
			else {
				Date introduced = Date.valueOf(intro);
				stmt.setDate(2, introduced);
			}

			if (c.getDiscontinued() == null) {
				stmt.setNull(3, java.sql.Types.DATE);
			}
			else {
				Date disc = Date.valueOf(discontinued);
				stmt.setDate(3, disc);
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
		} catch (DAOConfigurationException | ClassNotFoundException | SQLException e) {
			logger.info("Problem in ComputerDAO");
		}

	}

	@Override
	public void deleteComputer(Long id) throws DAOException{

		try {
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
		} catch (DAOConfigurationException | ClassNotFoundException | SQLException e) {
			logger.info("Problem in ComputerDAO");
		}

	}

	@Override
	public int count() throws DAOException {
		int result = 0;
		try {
			SQLConnection.getInstance();
			Connection conn = SQLConnection.getConnection();
			String query = COUNT;
			PreparedStatement stmt =  conn.prepareStatement(query);
			ResultSet res = stmt.executeQuery();
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
		} catch (DAOConfigurationException | ClassNotFoundException | SQLException e) {
			logger.info("Problem in ComputerDAO");
		}
		return result;

	}
}
