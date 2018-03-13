package com.excilys.java.formation.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyMapper {

	private static CompanyMapper companyMapper;
	
	public CompanyMapper() {
		
	}
	
	/**
	 * Method to get the list of companies from a ResultSet
	 * @param res
	 * @return the list of companies
	 * @throws SQLException
	 */
	public List<Company> getListCompanyFromResultSet(ResultSet res) throws SQLException {
		
		List<Company> companies = new ArrayList<Company>();
		
		while(res.next()) {
			companies.add(new Company(res.getLong(1), res.getString(2)));
		}
		return companies;
		
	}
	
	/**
	 * 
	 * @return the companyMapper
	 */
	public static CompanyMapper getCompanyMapper() {
		
		if(companyMapper == null)
			companyMapper = new CompanyMapper();
	return companyMapper;
	}
}
