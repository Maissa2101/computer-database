package com.excilys.java.formation.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.persistence.CompanyDAO;
import com.excilys.java.formation.persistence.DAOException;

public enum CompanyValidator {

	INSTANCE;
	private Logger logger = LoggerFactory.getLogger(CompanyValidator.class);
	
	public boolean idCompanyValidator(String manufacturer, CompanyDAO companies) throws ValidatorException {
		try {
			if (manufacturer != null && !manufacturer.equals("null")) {
				Long companyId = Long.valueOf(manufacturer);
				if(companies.getCompany(companyId).isPresent()) {
					return true;
				}
				else {
					throw new ValidatorException("ID not valid");
				}
			}
		} catch(NumberFormatException | DAOException e) {
			logger.debug("NumberFormatException", e);
		}
		return true;
	}

}
