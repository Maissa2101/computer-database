package com.excilys.java.formation.persistence;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.mapper.CompanyRowMapper;

@Repository
public class CompanyDAOSpring {

	private static final String SELECT_REQUEST_LIST = "SELECT id, name FROM company LIMIT ? OFFSET ?;";
	private static final String COUNT = "SELECT count(*) as total FROM company;";
	private static final String SELECT_REQUEST_DETAILS = "SELECT id, name FROM company WHERE id=?;";
	private static final String DELETE_COMPANY = "DELETE FROM company WHERE id=?;";

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ComputerDAOSpring computerDAO;

	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Company> getListCompany(int limit, int offset) {
		return this.jdbcTemplate.query(SELECT_REQUEST_LIST, new CompanyRowMapper(), limit, offset);
	}

	public Optional<Company> getCompany(long id) {
		try {
			Company company =  this.jdbcTemplate.queryForObject(SELECT_REQUEST_DETAILS, new CompanyRowMapper(), id);
			return Optional.ofNullable(company);
		} catch (IncorrectResultSizeDataAccessException e) {
			return Optional.empty();
		}
	}

	public int count() {
		return this.jdbcTemplate.queryForObject(COUNT, Integer.class);
	}

	public void deleteCompany(long id)  {
		computerDAO.deleteTransactionCompany(id);
		this.jdbcTemplate.update(DELETE_COMPANY, id);		
	}

}