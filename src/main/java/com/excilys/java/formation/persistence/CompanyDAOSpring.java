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
import com.excilys.java.formation.persistence.interfaceDAO.CompanyDAOInterface;

@Repository
public class CompanyDAOSpring implements CompanyDAOInterface {

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

	@Override
	public List<Company> getListCompany(int limit, int offset) throws DAOException {
		return this.jdbcTemplate.query(SELECT_REQUEST_LIST, new CompanyRowMapper(), limit, offset);
	}

	@Override
	public Optional<Company> getCompany(long id) throws DAOException{
		try {
			Company company =  this.jdbcTemplate.queryForObject(SELECT_REQUEST_DETAILS, new CompanyRowMapper(), id);
			return Optional.ofNullable(company);
		} catch (IncorrectResultSizeDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public int count() throws DAOException {
		int number = this.jdbcTemplate.queryForObject(COUNT, Integer.class);
		if (number > 0) {
			return number;
		} else {
			throw new DAOException("Problem in count number of companies");
		}
	}

	@Override
	public void deleteCompany(long id) throws DAOException {
		computerDAO.deleteTransactionCompany(id);
		this.jdbcTemplate.update(DELETE_COMPANY, id);		
	}

}