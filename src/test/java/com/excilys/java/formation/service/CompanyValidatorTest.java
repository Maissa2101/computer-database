package com.excilys.java.formation.service;

import static org.junit.Assert.*;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.service.CompanyValidator;
import com.excilys.java.formation.service.ValidatorException;

public class CompanyValidatorTest {
	
	Logger logger = LoggerFactory.getLogger(CompanyValidatorTest.class);
	
	@Test
	public void testIdCompanyValidator() {
		CompanyValidator companyValidator = CompanyValidator.INSTANCE;
		try {
			for(int i = 1; (i < 44 && i != 21) ; i++) {
				assertTrue(companyValidator.idCompanyValidator(String.valueOf(i)));
			}
		} catch (ValidatorException e) {
			logger.debug("Problem in testIdCompanyValidator", e);
		}
	}
	
}

