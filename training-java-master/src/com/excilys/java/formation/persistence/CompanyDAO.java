package com.excilys.java.formation.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.excilys.java.formation.mapper.Company;
import com.excilys.java.formation.mapper.CompanyMapper;

public class CompanyDAO {
	Connection conn;
	
	public CompanyDAO(Connection conn) {
		this.conn = conn;
	}

	public List<Company> getListCompany() throws SQLException{
		
		
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

   
	
	
	

