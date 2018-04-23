package com.excilys.java.formation.persistence;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.mapper.ComputerRowMapper;

@Repository
public class ComputerDAOSpring {

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

	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<Computer> getListComputer(int limit, int offset, String columnName, String order) {
		return this.jdbcTemplate.query(SELECT_REQUEST_LIST + " " + columnName + " " + order + " LIMIT ? OFFSET ?;", new ComputerRowMapper(), limit, offset);
	}

	public Optional<Computer> getComputer(long id) {
		try {
			Computer computer = this.jdbcTemplate.queryForObject(SELECT_REQUEST_DETAILS, new ComputerRowMapper(), id);
			return Optional.ofNullable(computer);
		} catch (IncorrectResultSizeDataAccessException e) {
			return Optional.empty();
		}
	}

	public long createComputer( String name, LocalDate intro, LocalDate discontinued, String manufacturer ) {
		Computer computer = new Computer.ComputerBuilder(name).introduced(intro).discontinued(discontinued).manufacturer(manufacturer).build();
		this.jdbcTemplate.update(INSERT_REQUEST, computer.getName(), (computer.getIntroduced() == null) ? null : Date.valueOf(computer.getIntroduced()), (computer.getDiscontinued() == null) ? null : Date.valueOf(computer.getDiscontinued()), (computer.getManufacturer() == null || computer.getManufacturer().equals("null")) ? null : computer.getManufacturer());
		return computer.getId();
	}

	public void updateComputer(long id, String name, LocalDate intro, LocalDate discontinued, String manufacturer) {
		Computer computer = new Computer.ComputerBuilder(id, name).introduced(intro).discontinued(discontinued).manufacturer(manufacturer).build();
		this.jdbcTemplate.update(UPDATE_REQUEST, computer.getName(), (computer.getIntroduced() == null) ? null : Date.valueOf(computer.getIntroduced()), (computer.getDiscontinued() == null) ? null : Date.valueOf(computer.getDiscontinued()), (computer.getManufacturer() == null || computer.getManufacturer().equals("null")) ? null : computer.getManufacturer(), id);
	}

	public void deleteComputer(long id) {
		this.jdbcTemplate.update(DELETE_REQUEST, id);		

	}

	public int count() {
		return this.jdbcTemplate.queryForObject(COUNT, Integer.class);
		
	}

	public int countAfterSearch(String search) {
		return this.jdbcTemplate.queryForObject(COUNT_SEARCH, Integer.class, search + '%', '%' + search + '%');
	}
	public void deleteTransaction(List<Long> ids) {
		for(Long id : ids) {
			this.jdbcTemplate.update(DELETE_REQUEST, id);
		}
	}

	public List<Computer> search(String search, String columnName, String order, int limit, int offset) {
		return this.jdbcTemplate.query(SEARCH + " " + columnName + " " + order + " LIMIT ? OFFSET ?;", new ComputerRowMapper(), '%' + search + '%', '%' + search + '%', limit, offset);
	}
	
	public void deleteTransactionCompany(long id) {
		this.jdbcTemplate.update(DELETE_COMPUTERS_COMPANY, id);
	}

}
