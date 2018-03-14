package com.excilys.java.formation.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.java.formation.mapper.Company;
import com.excilys.java.formation.persistence.CompanyDAO;

public class CompanyValidator {
	
	
	private final static CompanyValidator companyValidator = new CompanyValidator();
	
	private CompanyValidator() {
		
	}
	
	public static CompanyValidator getCompanyValidator() {
		return companyValidator;
	}
	
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