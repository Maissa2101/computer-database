package com.excilys.java.formation.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.mapper.CompanyMapper;
import com.excilys.java.formation.persistence.interfaceDAO.CompanyDAOInterface;
import com.excilys.java.formation.servlets.AddComputerServlet;

public enum CompanyDAO implements CompanyDAOInterface {

	INSTANCE;
	Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);
	private final String SELECT_REQUEST_LIST = "SELECT id, name FROM company LIMIT ? OFFSET ?;";
	private final String COUNT = "SELECT count(*) as total FROM company;";
	private final String SELECT_REQUEST_DETAILS = "SELECT id, name FROM company WHERE id=?;";

	@Override
	public List<Company> getListCompany(int limit, int offset) throws DAOException {
		List<Company> listCompanies = null;
		try(Connection conn = SQLConnection.getInstance().getConnection();
				PreparedStatement stmt =  conn.prepareStatement(SELECT_REQUEST_LIST)) {
			stmt.setInt(1, limit);
			stmt.setInt(2, offset);
			ResultSet res = stmt.executeQuery();
			listCompanies = CompanyMapper.INSTANCE.getListCompanyFromResultSet(res);
			return listCompanies;
		} catch (SQLException | DAOConfigurationException | ClassNotFoundException e) {
			logger.debug("Problem in CompanyDAO", e);
			throw new DAOException("DAOException in getListComputer");
		} 
	}

	@Override
	public Optional<Company> getCompany(long id) throws DAOException{
		Company company = null;
		try(Connection conn = SQLConnection.getInstance().getConnection();
				PreparedStatement stmt =  conn.prepareStatement(SELECT_REQUEST_DETAILS);) {
			stmt.setLong(1, id);
			ResultSet res = stmt.executeQuery();
			company = CompanyMapper.INSTANCE.getCompanyDetailsFromResultSet(res);
			return Optional.ofNullable(company);
		} catch (DAOConfigurationException | ClassNotFoundException | SQLException e) {
			logger.debug("Problem in CompanyDAO", e);
			throw new DAOException("DAOException in getCompany");
		} 
	}

	@Override
	public int count() throws DAOException {
		int rslt = 0;
		try(Connection conn = SQLConnection.getInstance().getConnection();
				PreparedStatement stmt =  conn.prepareStatement(COUNT)) {
			ResultSet res = stmt.executeQuery();
			if (res.next()) {
				rslt = res.getInt("total");
			} else {
				throw new DAOException("Problem in count number of companies");
			}
			return rslt;
		} catch (DAOConfigurationException | ClassNotFoundException | SQLException e) {
			logger.debug("Problem in CompanyDAO", e);
		} 
		return rslt;

	}
}






