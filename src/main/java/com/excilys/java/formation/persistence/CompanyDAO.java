package com.excilys.java.formation.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.interfaceDAO.CompanyDAOInterface;
import com.excilys.java.formation.mapper.CompanyMapper;

public enum CompanyDAO implements CompanyDAOInterface {
	
	INSTANCE;
	
	private final String SELECT_REQUEST_LIST = "SELECT id, name FROM company;";
	

	@Override
	public List<Company> getListCompany() throws SQLException, ClassNotFoundException{
		
		SQLConnection.getInstance();
		Connection conn = SQLConnection.getConnection();
		String query = SELECT_REQUEST_LIST;
		PreparedStatement stmt =  conn.prepareStatement(query);
		ResultSet res = stmt.executeQuery(query);
		
		
		List<Company> l = CompanyMapper.INSTANCE.getListCompanyFromResultSet(res);
		stmt.close();
		SQLConnection.closeConnection();
		return l;
	}

	}

   
	
	
	

