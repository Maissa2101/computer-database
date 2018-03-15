package com.excilys.java.formation.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.CompanyDAO;
import com.excilys.java.formation.persistence.CompanyDAOInterface;

public enum CompanyValidator {
	
	INSTANCE;
	
	/**
	 * Method to verify if the id of the company is valid or not
	 * @param manufacturer the id of the company to validate
	 * @return true if the id exists in the DB, false otherwise
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 * @throws SQLException in case of a database access error
	 * @throws ValidatorException 
	 */
	public boolean idCompanyValidator(String manufacturer) throws ClassNotFoundException, SQLException, ValidatorException {
		if (manufacturer != null ) {
		Long companyId = Long.valueOf(manufacturer);
		int index = 0;
		
		CompanyDAOInterface companies = CompanyDAO.INSTANCE;
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
			throw new ValidatorException("ID not valid, give a new one : ");
		}
		return false;
	
	}
		return true;
	}
		
}
