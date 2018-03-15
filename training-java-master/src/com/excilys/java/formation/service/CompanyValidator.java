package com.excilys.java.formation.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.CompanyDAO;

public class CompanyValidator {
	
	/**
	 * The instance is initialized when the class is first referenced
	 */
	private final static CompanyValidator companyValidator = new CompanyValidator();
	
	
	private CompanyValidator() {
		
	}
	
	/**
	 * controls the access to the unique instance of the CompanyValidator class
	 * @return the unique instance of the CompanyValidator class
	 */
	public static CompanyValidator getCompanyValidator() {
		return companyValidator;
	}
	
	/**
	 * Method to verify if the id of the company is valid or not
	 * @param manufacturer the id of the company to validate
	 * @return true if the id exists in the DB, false otherwise
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 * @throws SQLException in case of a database access error
	 */
	public boolean idCompanyValidator(String manufacturer) throws ClassNotFoundException, SQLException {
		if (manufacturer != null ) {
		Long companyId = Long.valueOf(manufacturer);
		int index = 0;
		
		CompanyDAO companies = CompanyDAO.getCompanyDAO();
		List<Company> sourceList = new ArrayList<Company>();
		for(Company company : companies.getListCompany()) {
			sourceList.add(company);
		}
		
		while(index < sourceList.size()) {
			if((manufacturer != null) && (companyId == Long.valueOf(sourceList.get(index).getId()))) {
				return true;
			}
			else {
				index++;
			}
		}
		if(index >= sourceList.size()) {
			System.out.println("ID not valid, give a new one : ");
		}
		return false;
	
	}
		return true;
	}
		
}
