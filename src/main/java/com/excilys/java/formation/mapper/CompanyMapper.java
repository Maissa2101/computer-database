package com.excilys.java.formation.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.java.formation.entities.Company;

public enum CompanyMapper {

	INSTANCE;

	/**
	 * Method to get the list of companies from a ResultSet
	 * @param res is the result set
	 * @return the list of companies
	 * @throws SQLException in case of a database access error
	 */
	public List<Company> getListCompanyFromResultSet(ResultSet res) throws SQLException {
		List<Company> companies = new ArrayList<Company>();
		while(res.next()) 
		{
			companies.add(new Company.CompanyBuilder(res.getLong(1), res.getString(2)).build());
		}
		return companies;
	}

	public Company getCompanyDetailsFromResultSet(ResultSet res) throws SQLException {
		Company company = null;
		if(res.next()) 
		{
			company = new Company.CompanyBuilder(res.getLong(1), res.getString(2)).build();
		}
		return company;
	}
}
