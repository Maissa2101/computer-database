package com.excilys.java.formation.service;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.SQLException;

import org.junit.Test;

import com.excilys.java.formation.service.ComputerValidator;
import com.excilys.java.formation.service.ValidatorException;

public class ComputerValidatorTest {

	@Test(expected = ValidatorException.class)
	public void testNameValidator() throws ValidatorException {
		ComputerValidator cv = ComputerValidator.INSTANCE;
		assertTrue(cv.nameValidator("ASUS"));
		assertFalse(cv.nameValidator(""));
	}
	
	
	
	
	@Test(expected = ValidatorException.class)
	public void testIdValidator() throws ValidatorException {
		ComputerValidator cv = ComputerValidator.INSTANCE;
		
		try {
			assertTrue(cv.idValidator(1L));
			assertFalse(cv.idValidator(8000L));
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = ValidatorException.class)
	public void testDateValidator() throws ValidatorException {
		ComputerValidator cv = ComputerValidator.INSTANCE;
		
		assertFalse(cv.DateValidator(Date.valueOf("2008-01-01"), Date.valueOf("2007-01-01")));
		assertTrue(cv.DateValidator(Date.valueOf("2008-01-01"), Date.valueOf("2010-01-01")));
		assertTrue(cv.DateValidator(null, null));
		assertTrue(cv.DateValidator(null, Date.valueOf("2007-01-01")));
	}

}