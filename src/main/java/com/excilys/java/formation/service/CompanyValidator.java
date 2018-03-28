package com.excilys.java.formation.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.persistence.CompanyDAO;
import com.excilys.java.formation.persistence.DAOException;

public enum CompanyValidator {

	INSTANCE;
	Logger logger = LoggerFactory.getLogger(CompanyValidator.class);
	
	/**
	 * Method to verify if the id of the company is valid or not
	 * @param manufacturer the id of the company to validate
	 * @return true if the id exists in the DB, false otherwise
	 * @throws ValidatorException
	 */
	public boolean idCompanyValidator(String manufacturer) throws ValidatorException {
		try {
			if (manufacturer != null && !manufacturer.equals("null")) {
				Long companyId = Long.valueOf(manufacturer);
				CompanyDAO companies = CompanyDAO.INSTANCE;
				if(companies.getCompany(companyId).isPresent()) {
					return true;
				}
				else {
					throw new ValidatorException("ID not valid");
				}
			}
		} catch(NumberFormatException | DAOException e) {
			logger.debug("NumberFormatException");
		}
		return true;
	}

}
