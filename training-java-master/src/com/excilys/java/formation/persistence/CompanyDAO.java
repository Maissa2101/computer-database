package com.excilys.java.formation.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.mapper.CompanyMapper;

public enum CompanyDAO {
	
	INSTANCE;
	
	private final String SELECT_REQUEST_LIST = "SELECT * FROM company;";
	
	/**
	 * Method to get the list of companies
	 * @return the list of companies
	 * @throws SQLException in case of a database access error
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 */
	public List<Company> getListCompany() throws SQLException, ClassNotFoundException{
		
		SQLConnection.getInstance();
		Connection conn = SQLConnection.getConnection();
		conn.setAutoCommit(false);
		String query = SELECT_REQUEST_LIST;
		PreparedStatement stmt =  conn.prepareStatement(query);
		ResultSet res = stmt.executeQuery(query);
		
		conn.commit();
		
		
		List<Company> l = CompanyMapper.INSTANCE.getListCompanyFromResultSet(res);
		stmt.close();
		SQLConnection.closeConnection();
		return l;
	}

	}

   
	
	
	

