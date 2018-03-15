package com.excilys.java.formation.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.mapper.CompanyMapper;

public class CompanyDAO {
	
	private final static CompanyDAO companyDao = new CompanyDAO();
	
	public CompanyDAO() {
		
	}
	
	/**
	 * controls the access to the unique instance of the CompanyDAO class
	 * @return the unique instance of the CompanyDAO class
	 */
	public static CompanyDAO getCompanyDAO() {
		return companyDao;
	}
	
	/**
	 * Method to get the list of companies
	 * @return the list of companies
	 * @throws SQLException in case of a database access error
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 */
	public List<Company> getListCompany() throws SQLException, ClassNotFoundException{
		
		Connection conn = SQLConnection.getConnection();
		conn.setAutoCommit(false);
		String query = "SELECT * FROM company;";
		PreparedStatement stmt =  conn.prepareStatement(query);
		ResultSet res = stmt.executeQuery(query);
		
		conn.commit();
		
		
		List<Company> l = CompanyMapper.getCompanyMapper().getListCompanyFromResultSet(res);
		stmt.close();
		return l;
	}

	}

   
	
	
	

