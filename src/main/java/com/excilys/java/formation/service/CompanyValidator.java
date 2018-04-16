package com.excilys.java.formation.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.persistence.DAOException;
import com.excilys.java.formation.persistence.interfaceDAO.CompanyDAOInterface;

public enum CompanyValidator {

	INSTANCE;
	private Logger logger = LoggerFactory.getLogger(CompanyValidator.class);
	
	public boolean idCompanyValidator(String manufacturer, CompanyDAOInterface companies) throws ValidatorException {
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
