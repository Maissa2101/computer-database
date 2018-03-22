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
import com.excilys.java.formation.interfaceDAO.CompanyDAOInterface;
import com.excilys.java.formation.mapper.CompanyMapper;
import com.excilys.java.formation.servlets.AddComputerServlet;

public enum CompanyDAO implements CompanyDAOInterface {

	INSTANCE;
	Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);
	private final String SELECT_REQUEST_LIST = "SELECT id, name FROM company LIMIT ? OFFSET ?;";
	private final String COUNT = "SELECT count(*) as total FROM company;";
	private final String SELECT_REQUEST_DETAILS = "SELECT id, name FROM company WHERE id=?;";

	@Override
	public List<Company> getListCompany(int limit, int offset) throws DAOException{
		List<Company> l = null;
		try {
			SQLConnection.getInstance();
			Connection conn;
			conn = SQLConnection.getConnection();
			String query = SELECT_REQUEST_LIST;
			PreparedStatement stmt =  conn.prepareStatement(query);
			stmt.setInt(1, limit);
			stmt.setInt(2, offset);
			ResultSet res = stmt.executeQuery();
			l = CompanyMapper.INSTANCE.getListCompanyFromResultSet(res);
			stmt.close();
			SQLConnection.closeConnection(conn);
			return l;
		} catch (SQLException | DAOConfigurationException | ClassNotFoundException e) {
			logger.info("Problem in CompanyDAO");
		} 
		return l;
	}

	@Override
	public Optional<Company> getCompany(Long id) throws DAOException{
		Company c = null;
		try {
			SQLConnection.getInstance();
			Connection conn = SQLConnection.getConnection();
			String query = SELECT_REQUEST_DETAILS;
			PreparedStatement stmt =  conn.prepareStatement(query);
			stmt.setLong(1, id);
			ResultSet res = stmt.executeQuery();
			c = CompanyMapper.INSTANCE.getCompanyDetailsFromResultSet(res);

			if (stmt != null) {
				stmt.close();
			}

			SQLConnection.closeConnection(conn);
			return Optional.ofNullable(c);
		} catch (DAOConfigurationException | ClassNotFoundException | SQLException e) {
			logger.info("Problem in CompanyDAO");
		} 
		return Optional.ofNullable(c);
	}

	@Override
	public int count() throws DAOException {
		int rslt = 0;
		try {
			SQLConnection.getInstance();
			Connection conn = SQLConnection.getConnection();

			PreparedStatement stmt =  conn.prepareStatement(COUNT);
			ResultSet res = stmt.executeQuery();

			if (res.next()) {
				rslt = res.getInt("total");
			} else {
				throw new DAOException("Problem in your count");
			}

			if (stmt != null) 
			{
				stmt.close();
			}
			SQLConnection.closeConnection(conn);
			return rslt;
		} catch (DAOConfigurationException | ClassNotFoundException | SQLException e) {
			logger.info("Problem in CompanyDAO");
		} 
		return rslt;
		
	}
}






