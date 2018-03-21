package com.excilys.java.formation.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.interfaceDAO.CompanyDAOInterface;
import com.excilys.java.formation.mapper.CompanyMapper;

public enum CompanyDAO implements CompanyDAOInterface {

	INSTANCE;

	private final String SELECT_REQUEST_LIST = "SELECT id, name FROM company LIMIT ? OFFSET ?;";
	private final String COUNT = "SELECT count(*) as total FROM company;";
	private final String SELECT_REQUEST_DETAILS = "SELECT id, name FROM company WHERE id=?;";

	@Override
	public List<Company> getListCompany(int limit, int offset) throws SQLException, ClassNotFoundException{
		SQLConnection.getInstance();
		Connection conn = SQLConnection.getConnection();
		String query = SELECT_REQUEST_LIST;
		PreparedStatement stmt =  conn.prepareStatement(query);
		stmt.setInt(1, limit);
		stmt.setInt(2, offset);
		ResultSet res = stmt.executeQuery();
		List<Company> l = CompanyMapper.INSTANCE.getListCompanyFromResultSet(res);
		stmt.close();
		SQLConnection.closeConnection(conn);
		return l;
	}

	@Override
	public Optional<Company> getCompany(Long id) throws SQLException, ClassNotFoundException{
		SQLConnection.getInstance();
		Connection conn = SQLConnection.getConnection();
		String query = SELECT_REQUEST_DETAILS;
		PreparedStatement stmt =  conn.prepareStatement(query);
		stmt.setLong(1, id);
		ResultSet res = stmt.executeQuery();
		Company c = CompanyMapper.INSTANCE.getCompanyDetailsFromResultSet(res);

		if (stmt != null) {
			stmt.close();
		}

		SQLConnection.closeConnection(conn);
		return Optional.ofNullable(c);
	}

	@Override
	public int count() throws SQLException, DAOConfigurationException, ClassNotFoundException {
		SQLConnection.getInstance();
		Connection conn = SQLConnection.getConnection();

		PreparedStatement stmt =  conn.prepareStatement(COUNT);
		ResultSet res = stmt.executeQuery();
		int rslt = 0;

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
	}
}






