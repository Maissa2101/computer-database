package com.excilys.java.formation.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.CompanyDAOSpring;

public enum CompanyValidator {

	INSTANCE;
	private Logger logger = LoggerFactory.getLogger(CompanyValidator.class);
	
	public boolean idCompanyValidator(Company manufacturer, CompanyDAOSpring companies) throws ValidatorException {
		try {
			if (manufacturer != null && !manufacturer.equals("null")) {
				Long companyId = manufacturer.getId();
				if(companies.getCompany(companyId).isPresent()) {
					return true;
				}
				else {
					throw new ValidatorException("ID not valid");
				}
			}
		} catch(NumberFormatException e) {
			logger.debug("NumberFormatException", e);
		}
		return true;
	}

}
