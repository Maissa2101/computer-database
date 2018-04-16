package com.excilys.java.formation.persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.interfaceDAO.ComputerDAOSpringInterface;

@Repository
public class ComputerDAOSpring implements ComputerDAOSpringInterface {

	private static final String SELECT_REQUEST_LIST = "SELECT computer.id, computer.name, introduced, discontinued, company.name FROM computer LEFT JOIN company ON computer.company_id=company.id ORDER BY";
	private static final String SELECT_REQUEST_DETAILS = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id=?;";
	private static final String INSERT_REQUEST = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES (?,?,?,?);";
	private static final String UPDATE_REQUEST = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;";
	private static final String DELETE_REQUEST = "DELETE FROM computer WHERE id=?;";
	private static final String COUNT = "SELECT count(*) as total FROM computer;";
	private static final String SEARCH = "SELECT computer.id, computer.name, introduced, discontinued, company.name FROM computer LEFT JOIN company ON" +
			" computer.company_id=company.id WHERE computer.name LIKE ? OR company.name LIKE ? ORDER BY";
	private static final String COUNT_SEARCH = "SELECT count(*) as total FROM computer LEFT JOIN company ON" + 
			" computer.company_id=company.id WHERE computer.name LIKE ? OR company.name LIKE ?;";
	private static final String DELETE_COMPUTERS_COMPANY = "DELETE FROM computer WHERE company_id=?;";
	
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Computer> getListComputer(int limit, int offset, String columnName, String order) throws DAOException {
		return this.jdbcTemplate.queryForList(SELECT_REQUEST_LIST + " " + columnName + " " + order + " LIMIT ? OFFSET ?;", new Object[] {limit, offset}, Computer.class);
	}

	@Override
	public Optional<Computer> getComputer(long id) throws DAOException{
		Computer computer = (Computer) this.jdbcTemplate.queryForList(SELECT_REQUEST_DETAILS, id, Computer.class);
			return Optional.ofNullable(computer);
	}

	@Override
	public long createComputer( String name, LocalDate intro, LocalDate discontinued, String manufacturer ) throws DAOException{
		Computer computer = new Computer.ComputerBuilder(name).introduced(intro).discontinued(discontinued).manufacturer(manufacturer).build();
		this.jdbcTemplate.queryForObject(INSERT_REQUEST, new Object[] {computer.getName(), computer.getIntroduced(), computer.getDiscontinued(), computer.getManufacturer()}, Computer.class);
		return computer.getId();
	}

	@Override
	public void updateComputer(long id, String name, LocalDate intro, LocalDate discontinued, String manufacturer) throws DAOException{
		Computer computer = new Computer.ComputerBuilder(id, name).introduced(intro).discontinued(discontinued).manufacturer(manufacturer).build();
		this.jdbcTemplate.update(UPDATE_REQUEST, new Object[] {computer.getName(), computer.getIntroduced(), computer.getDiscontinued(), computer.getManufacturer(), id}, Computer.class);
	}

	@Override
	public void deleteComputer(long id) throws DAOException{
		this.jdbcTemplate.queryForList(DELETE_REQUEST, id, Computer.class);		

	}

	@Override
	public int count() throws DAOException {
		int number = this.jdbcTemplate.queryForObject(COUNT, Integer.class);
		if (number > 0) {
			return number;
		} else {
			throw new DAOException("Problem in count number of computers");
		}
	}

	@Override
	public int countAfterSearch(String search) throws DAOException {
		return this.jdbcTemplate.queryForObject(COUNT_SEARCH, new Object [] {'%' + search + '%', '%' + search + '%'}, Integer.class);
	}

	@Override
	public void deleteTransaction(List<Long> ids) throws DAOException{
		for(Long id : ids) {
			this.jdbcTemplate.queryForList(DELETE_REQUEST, id);
		}
	}

	@Override
	public List<Computer> search(String search, String columnName, String order, int limit, int offset) throws DAOException {
		return this.jdbcTemplate.queryForList(SEARCH + " " + columnName + " " + order + " LIMIT ? OFFSET ?;", new Object [] {'%' + search + '%', '%' + search + '%', limit, offset}, Computer.class);
	}
	
	@Override
	public void deleteTransactionCompany(long id) throws DAOException{
		this.jdbcTemplate.queryForList(DELETE_COMPUTERS_COMPANY, id);
	}


}
