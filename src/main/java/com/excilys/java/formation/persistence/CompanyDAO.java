package com.excilys.java.formation.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.mapper.CompanyMapper;
import com.excilys.java.formation.persistence.interfaceDAO.CompanyDAOInterface;
import com.excilys.java.formation.persistence.interfaceDAO.ComputerDAOInterface;
import com.excilys.java.formation.servlets.AddComputerServlet;

@Repository
public class CompanyDAO implements CompanyDAOInterface {

	private Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);
	private static final String SELECT_REQUEST_LIST = "SELECT id, name FROM company LIMIT ? OFFSET ?;";
	private static final String COUNT = "SELECT count(*) as total FROM company;";
	private static final String SELECT_REQUEST_DETAILS = "SELECT id, name FROM company WHERE id=?;";
	private static final String DELETE_COMPANY = "DELETE FROM company WHERE company.id=?;";
	@Autowired
	private DataSource dataSource; 
	@Autowired
	private ComputerDAO computerDAO;

	@Override
	public List<Company> getListCompany(int limit, int offset) throws DAOException {
		List<Company> listCompanies = null;
		try(Connection conn = dataSource.getConnection();
				PreparedStatement stmt =  conn.prepareStatement(SELECT_REQUEST_LIST)) {
			stmt.setInt(1, limit);
			stmt.setInt(2, offset);
			ResultSet res = stmt.executeQuery();
			listCompanies = CompanyMapper.INSTANCE.getListCompanyFromResultSet(res);
			return listCompanies;
		} catch (SQLException e) {
			logger.debug("Problem in getListCompany", e);
			throw new DAOException("DAOException in getListComputer");
		} 
	}

	@Override
	public Optional<Company> getCompany(long id) throws DAOException{
		Company company = null;
		try(Connection conn = dataSource.getConnection();
				PreparedStatement stmt =  conn.prepareStatement(SELECT_REQUEST_DETAILS);) {
			stmt.setLong(1, id);
			ResultSet res = stmt.executeQuery();
			company = CompanyMapper.INSTANCE.getCompanyDetailsFromResultSet(res);
			return Optional.ofNullable(company);
		} catch (SQLException e) {
			logger.debug("Problem in getCompany", e);
			throw new DAOException("DAOException in getCompany");
		} 
	}

	@Override
	public int count() throws DAOException {
		int rslt = 0;
		try(Connection conn = dataSource.getConnection();
				PreparedStatement stmt =  conn.prepareStatement(COUNT)) {
			ResultSet res = stmt.executeQuery();
			if (res.next()) {
				rslt = res.getInt("total");
			} else {
				throw new DAOException("Problem in count number of companies");
			}
			return rslt;
		} catch (SQLException e) {
			logger.debug("Problem in count", e);
		} 
		return rslt;

	}

	@Override
	public void deleteCompany(long id) throws DAOException {
		try(Connection conn = dataSource.getConnection();
				AutoSetAutoCommit autoCommit = new AutoSetAutoCommit(conn,false);
				AutoRollback autoRollback = new AutoRollback(conn);	
				PreparedStatement stmt =  conn.prepareStatement(DELETE_COMPANY)) {
			computerDAO.deleteTransactionCompany(id, conn);
			stmt.setLong(1, id);
			int res = stmt.executeUpdate();
			if(res == 1 ) {
				logger.info("company deleted");
			}
			else {
				logger.info("company not deleted");
			}
			autoRollback.commit();
		} catch (SQLException | DAOException e) {
			logger.debug("Problem in CompanyDAO", e);
			throw new DAOException("DAOException in deleteCompany", e);
		}
	}

}






