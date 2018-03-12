package com.excilys.java.formation.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.java.formation.mapper.Company;

public class CompanyDAO {
	Connection conn;
	
	public CompanyDAO(Connection conn) {
		this.conn = conn;
	}

	public List<Company> getListCompany() throws SQLException{
		List<Company> companies = new ArrayList<Company>();
		
		conn.setAutoCommit(false);
		String query = "SELECT * FROM company;";
		PreparedStatement stmt =  conn.prepareStatement(query);
		ResultSet res = stmt.executeQuery(query);
		
		conn.commit();
		
		while(res.next()) {
			companies.add(new Company(res.getLong(1), res.getString(2)));
		}
		stmt.close();
		return companies;
	}

	}

   
	
	
	

