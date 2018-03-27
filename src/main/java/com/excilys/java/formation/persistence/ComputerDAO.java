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
import com.excilys.java.formation.mapper.ComputerMapper;
import com.excilys.java.formation.persistence.interfaceDAO.ComputerDAOInterface;
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
	public List<Computer> getListComputer(int limit, int offset) throws DAOException {
		List<Computer> listComputers = null;
		try(Connection conn = SQLConnection.getConnection(); 
				PreparedStatement stmt = conn.prepareStatement(SELECT_REQUEST_LIST)) {
			SQLConnection.getInstance();
			stmt.setInt(1, limit);
			stmt.setInt(2, offset);
			ResultSet res = stmt.executeQuery();	
			listComputers = ComputerMapper.INSTANCE.getListComputerFromResultSet(res);
			return listComputers;
		} catch (DAOConfigurationException | ClassNotFoundException | SQLException e) {
			logger.debug("Problem in ComputerDAO", e);
			throw new DAOException("DAOException in getListComputer");
		}
	}

	@Override
	public Optional<Computer> getComputer(long id) throws DAOException{
		Computer computer = null;
		try(Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt =  conn.prepareStatement(SELECT_REQUEST_DETAILS)) {
			SQLConnection.getInstance();
			stmt.setLong(1, id);
			ResultSet res = stmt.executeQuery();
			computer = ComputerMapper.INSTANCE.getComputerDetailsFromResultSet(res);
			return Optional.ofNullable(computer);
		} catch (DAOConfigurationException | ClassNotFoundException | SQLException e) {
			logger.debug("Problem in ComputerDAO" , e);
			throw new DAOException("DAOException in getComputer");
		}
	}

	@Override
	public long createComputer( String name, LocalDate intro, LocalDate discontinued, String manufacturer ) throws DAOException{
		int res = 0;
		long result = 0L;
		Computer computer = new Computer.ComputerBuilder(name).introduced(intro).discontinued(discontinued).manufacturer(manufacturer).build();

		try(Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt =  conn.prepareStatement(INSERT_REQUEST, Statement.RETURN_GENERATED_KEYS)) {
			SQLConnection.getInstance();
			stmt.setString(1, name);
			if (computer.getIntroduced() == null) 
			{
				stmt.setNull(2, java.sql.Types.DATE);
			}
			else 
			{
				Date introduced = Date.valueOf(intro);
				stmt.setDate(2, introduced);
			}
			if (computer.getDiscontinued() == null) 
			{
				stmt.setNull(3, java.sql.Types.DATE);
			}
			else 
			{
				Date disc = Date.valueOf(discontinued);
				stmt.setDate(3, disc);
			}

			if(computer.getManufacturer() == null || computer.getManufacturer().equals("null")) 
			{
				stmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else 
			{
				stmt.setString(4, manufacturer);
			}
			res = stmt.executeUpdate();
			ResultSet res2 = stmt.getGeneratedKeys();
			result = -1L;
			if(res == 1 ) 
			{
				res2.next();
				logger.info("computer added with id : {}", res2.getLong(1));
				result = res2.getLong(1);
			}
			else 
			{
				logger.info("computer not added");
			}
			return result;
		} catch (DAOConfigurationException | ClassNotFoundException | SQLException e) {
			logger.debug("Problem in ComputerDAO", e);
			throw new DAOException("DAOException in createComputer");
		}
	}

	@Override
	public void updateComputer(long id, String name, LocalDate intro, LocalDate discontinued) throws DAOException{
		int res = 0;
		Computer computer = new Computer.ComputerBuilder(id, name).introduced(intro).discontinued(discontinued).build();
		try(Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt =  conn.prepareStatement(UPDATE_REQUEST)) {
			SQLConnection.getInstance();
			stmt.setString(1, name);
			if (computer.getIntroduced() == null) {
				stmt.setNull(2, java.sql.Types.DATE);
			}
			else {
				Date introduced = Date.valueOf(intro);
				stmt.setDate(2, introduced);
			}
			if (computer.getDiscontinued() == null) {
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
		} catch (DAOConfigurationException | ClassNotFoundException | SQLException e) {
			logger.debug("Problem in ComputerDAO", e);
			throw new DAOException("DAOException in updateComputer");
		}

	}

	@Override
	public void deleteComputer(long id) throws DAOException{

		try(Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt =  conn.prepareStatement(DELETE_REQUEST)) {
			SQLConnection.getInstance();
			stmt.setLong(1, id);
			int res = stmt.executeUpdate();
			if(res == 1 )
			{
				logger.info("computer deleted");
			}
			else
			{
				logger.info("computer not deleted");
			}
		} catch (DAOConfigurationException | ClassNotFoundException | SQLException e) {
			logger.debug("Problem in ComputerDAO", e);
			throw new DAOException("DAOException in deleteComputer");
		}

	}

	@Override
	public int count() throws DAOException {
		int result = 0;
		try(Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt =  conn.prepareStatement(COUNT)) {
			SQLConnection.getInstance();
			ResultSet res = stmt.executeQuery();
			if (res.next()) 
			{
				result =  res.getInt("total");
			}
			else 
			{
				throw new DAOException("Problem in your count");
			}
			return result;
		} catch (DAOConfigurationException | ClassNotFoundException | SQLException e) {
			logger.debug("Problem in ComputerDAO", e);
			throw new DAOException("DAOException in count number of computers");
		}
	}
}
