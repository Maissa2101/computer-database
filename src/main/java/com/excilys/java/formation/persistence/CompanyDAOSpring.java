package com.excilys.java.formation.persistence;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.interfaceDAO.CompanyDAOInterface;

@Repository
public class CompanyDAOSpring implements CompanyDAOInterface {

	private static final String SELECT_REQUEST_LIST = "SELECT id, name FROM company LIMIT ? OFFSET ?;";
	private static final String COUNT = "SELECT count(*) as total FROM company;";
	private static final String SELECT_REQUEST_DETAILS = "SELECT id, name FROM company WHERE id=?;";
	private static final String DELETE_COMPANY = "DELETE FROM company WHERE company.id=?;";

	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Company> getListCompany(int limit, int offset) throws DAOException {
		List<Company> listCompanies = (List<Company>) this.jdbcTemplate.queryForObject(SELECT_REQUEST_LIST, new Object[] {limit, offset}, Company.class);
		return listCompanies; 
	}

	@Override
	public Optional<Company> getCompany(long id) throws DAOException{
		Company company = (Company) this.jdbcTemplate.queryForList(SELECT_REQUEST_DETAILS, id, Company.class);
		return Optional.ofNullable(company);
	}

	@Override
	public int count() throws DAOException {
		int number = 0;
		number = this.jdbcTemplate.queryForObject(COUNT, Integer.class);
		if (number > 0) {
			return number;
		} else {
			throw new DAOException("Problem in count number of companies");
		}
	}

	@Override
	public void deleteCompany(long id) throws DAOException {
		this.jdbcTemplate.queryForList(DELETE_COMPANY, id, Company.class);		
	}

}