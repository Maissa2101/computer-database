package com.excilys.java.formation.test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import com.excilys.java.formation.service.CompanyValidator;

public class TestCompanyValidator {

	@Test
	public void testIdCompanyValidator() {
		CompanyValidator cv = CompanyValidator.getCompanyValidator();
		
			try {
				for(int i = 1; (i < 44 && i != 21) ; i++) {
					assertTrue(cv.idCompanyValidator(String.valueOf(i)));
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
		}
}

