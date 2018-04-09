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

	private static final String SELECT_REQUEST_LIST = "SELECT computer.id, computer.name, introduced, discontinued, company.name FROM computer LEFT JOIN company ON computer.company_id=company.id ORDER BY";
	private static final String SELECT_REQUEST_DETAILS = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id = ?;";
	private static final String INSERT_REQUEST = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES (?,?,?,?);";
	private static final String UPDATE_REQUEST = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?;";
	private static final String DELETE_REQUEST = "DELETE FROM computer WHERE id = ?;";
	private static final String COUNT = "SELECT count(*) as total FROM computer;";
	private static final String SEARCH = "SELECT computer.id, computer.name, introduced, discontinued, company.name FROM computer LEFT JOIN company ON" +
			" computer.company_id=company.id WHERE computer.name LIKE ? OR company.name LIKE ? ORDER BY";
	private static final String COUNT_SEARCH = "SELECT count(*) as total FROM computer LEFT JOIN company ON" + 
			" computer.company_id=company.id WHERE computer.name LIKE ? OR company.name LIKE ?;";
	private static final String DELETE_COMPUTERS_COMPANY = "DELETE FROM computer WHERE company_id=?;";

	@Override
	public List<Computer> getListComputer(int limit, int offset, String columnName, String order) throws DAOException {
		List<Computer> listComputers = null;
		try(Connection conn = SQLConnection.getInstance().getConnection(); 
				PreparedStatement stmt = conn.prepareStatement(SELECT_REQUEST_LIST + " " + columnName + " " + order + " LIMIT ? OFFSET ?;")) {
			stmt.setInt(1, limit);
			stmt.setInt(2, offset);
			ResultSet res = stmt.executeQuery();	
			listComputers = ComputerMapper.INSTANCE.getListComputerFromResultSet(res);
			return listComputers;
		} catch (DAOConfigurationException | SQLException e) {
			logger.debug("Problem in getListComputer", e);
			throw new DAOException("DAOException in getListComputer", e);
		}
	}

	@Override
	public Optional<Computer> getComputer(long id) throws DAOException{
		Computer computer = null;
		try(Connection conn = SQLConnection.getInstance().getConnection();
				PreparedStatement stmt =  conn.prepareStatement(SELECT_REQUEST_DETAILS)) {
			stmt.setLong(1, id);
			ResultSet res = stmt.executeQuery();
			computer = ComputerMapper.INSTANCE.getComputerDetailsFromResultSet(res);
			return Optional.ofNullable(computer);
		} catch (DAOConfigurationException | SQLException e) {
			logger.debug("Problem in getComputer" , e);
			throw new DAOException("DAOException in getComputer", e);
		}
	}

	@Override
	public long createComputer( String name, LocalDate intro, LocalDate discontinued, String manufacturer ) throws DAOException{
		int res = 0;
		long result = 0L;
		Computer computer = new Computer.ComputerBuilder(name).introduced(intro).discontinued(discontinued).manufacturer(manufacturer).build();
		try(Connection conn = SQLConnection.getInstance().getConnection();
				PreparedStatement stmt =  conn.prepareStatement(INSERT_REQUEST, Statement.RETURN_GENERATED_KEYS)) {
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

			if(computer.getManufacturer() == null || computer.getManufacturer().equals("null")) {
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
				logger.info("computer added with id : {}", res2.getLong(1));
				result = res2.getLong(1);
			}
			else {
				logger.info("computer not added");
			}
			return result;
		} catch (DAOConfigurationException | SQLException e) {
			logger.debug("Problem in createComputer", e);
			throw new DAOException("DAOException in createComputer", e);
		}
	}

	@Override
	public void updateComputer(long id, String name, LocalDate intro, LocalDate discontinued, String manufacturer) throws DAOException{
		int res = 0;
		Computer computer = new Computer.ComputerBuilder(id, name).introduced(intro).discontinued(discontinued).manufacturer(manufacturer).build();
		try(Connection conn = SQLConnection.getInstance().getConnection();
				PreparedStatement stmt =  conn.prepareStatement(UPDATE_REQUEST)) {
			stmt.setString(1, computer.getName());
			if (computer.getIntroduced() == null) {
				stmt.setNull(2, java.sql.Types.DATE);
			}
			else {
				Date introduced = Date.valueOf(computer.getIntroduced());
				stmt.setDate(2, introduced);
			}
			if (computer.getDiscontinued() == null) {
				stmt.setNull(3, java.sql.Types.DATE);
			}
			else {
				Date disc = Date.valueOf(computer.getDiscontinued());
				stmt.setDate(3, disc);
			}

			if(computer.getManufacturer() == null || computer.getManufacturer().equals("null")) {
				stmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				stmt.setString(4, computer.getManufacturer());
			}	
			stmt.setLong(5, id);
			res = stmt.executeUpdate();
			if(res == 1)
				logger.info("computer updated");
			else
				logger.info("computer not updated");
		} catch (DAOConfigurationException | SQLException e) {
			logger.debug("Problem in updateComputer", e);
			throw new DAOException("DAOException in updateComputer", e);
		}

	}

	@Override
	public void deleteComputer(long id) throws DAOException{
		try(Connection conn = SQLConnection.getInstance().getConnection();
				PreparedStatement stmt =  conn.prepareStatement(DELETE_REQUEST)) {
			stmt.setLong(1, id);
			int res = stmt.executeUpdate();
			if(res == 1 ) {
				logger.info("computer deleted");
			}
			else {
				logger.info("computer not deleted");
			}
		} catch (DAOConfigurationException | SQLException e) {
			logger.debug("Problem in deleteComputer", e);
			throw new DAOException("DAOException in deleteComputer", e);
		}

	}

	@Override
	public int count() throws DAOException {
		int result = 0;
		try(Connection conn = SQLConnection.getInstance().getConnection();
				PreparedStatement stmt =  conn.prepareStatement(COUNT)) {
			ResultSet res = stmt.executeQuery();
			if (res.next()) {
				result =  res.getInt("total");
			}
			else {
				throw new DAOException("Problem in your count");
			}
			return result;
		} catch (DAOConfigurationException | SQLException e) {
			logger.debug("Problem in count", e);
			throw new DAOException("DAOException in count number of computers", e);
		}
	}

	@Override
	public int countAfterSearch(String search) throws DAOException {
		int result = 0;
		try(Connection conn = SQLConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(COUNT_SEARCH)) {
			stmt.setString(1, '%' + search + '%');
			stmt.setString(2, '%' + search + '%');
			ResultSet res = stmt.executeQuery();
			if (res.next()) {
				result =  res.getInt("total");
			}
			else {
				throw new DAOException("Problem in your count after search");
			}
			return result;
		} catch (DAOConfigurationException | SQLException e) {
			logger.debug("Problem in countAfterSearch", e);
			throw new DAOException("DAOException in countAfterSearch", e);
		}
	}

	@Override
	public void deleteTransaction(List<Long> ids) throws DAOException{
		try(Connection conn = SQLConnection.getInstance().getConnection();
				AutoSetAutoCommit autoCommit = new AutoSetAutoCommit(conn,false);
				AutoRollback autoRollback = new AutoRollback(conn);	
				) {
			deleteTransaction(ids, conn);
			autoRollback.commit();
		} catch (DAOConfigurationException | SQLException | DAOException e) {
			logger.debug("Problem in deleteTransaction", e);
			throw new DAOException("DAOException in deleteTransaction", e);
		}
	}

	public void deleteTransaction(List<Long> ids, Connection conn) throws DAOException{
		try(PreparedStatement stmt =  conn.prepareStatement(DELETE_REQUEST)) {
			int res = 0;
			for(Long id : ids) {
				stmt.setLong(1, id);
				res = stmt.executeUpdate();
			}
			if(res == ids.size()) {
				logger.info("computers deleted");
			}
			else {
				logger.info("computers not deleted");
			}
		} catch (SQLException e) {
			logger.debug("Problem in deleteTransaction", e);
			throw new DAOException("DAOException in deleteTransaction", e);
		}
	}

	@Override
	public List<Computer> search(String search, String columnName, String order, int limit, int offset) throws DAOException {
		List<Computer> listComputers = null;
		try(Connection conn = SQLConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(SEARCH + " " + columnName + " " + order + " LIMIT ? OFFSET ?;")) {
			stmt.setString(1, '%' + search + '%');
			stmt.setString(2, '%' + search + '%');
			stmt.setInt(3, limit);
			stmt.setInt(4, offset);
			ResultSet res = stmt.executeQuery();	
			listComputers = ComputerMapper.INSTANCE.getListComputerFromResultSet(res);
			return listComputers;
		} catch (DAOConfigurationException | SQLException e) {
			logger.debug("Problem in search", e);
			throw new DAOException("DAOException in Search", e);
		}
	}

	public void deleteTransactionCompany(long id, Connection conn) throws DAOException{
		try(PreparedStatement stmt =  conn.prepareStatement(DELETE_COMPUTERS_COMPANY)) {
			stmt.setLong(1, id);
			int res = stmt.executeUpdate();
			if(res >= 1) {
				logger.info("computers deleted after company deletion");
			}
			else {
				logger.info("computers not deleted after company deletion");
			}
		} catch (SQLException e) {
			logger.debug("Problem in deleteTransactionCompany", e);
			throw new DAOException("DAOException in deleteTransactionCompany", e);
		}
	}

}
