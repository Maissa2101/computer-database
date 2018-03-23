package com.excilys.java.formation.service;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import com.excilys.java.formation.service.CompanyValidator;
import com.excilys.java.formation.service.ValidatorException;

public class CompanyValidatorTest {
	
	
	@Test
	public void testIdCompanyValidator() throws ValidatorException {
		CompanyValidator cv = CompanyValidator.INSTANCE;
		try {
			for(int i = 1; (i < 44 && i != 21) ; i++) {
				assertTrue(cv.idCompanyValidator(String.valueOf(i)));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
}

