package com.excilys.java.formation.test;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.SQLException;

import org.junit.Test;

import com.excilys.java.formation.service.ComputerValidator;

public class TestComputerValidator {

	@Test
	public void testNameValidator() {
		ComputerValidator cv = ComputerValidator.getComputerValidator();
		assertTrue(cv.nameValidator("ASUS"));
		assertFalse(cv.nameValidator(""));
	}

	@Test
	public void testIdValidator() {
		ComputerValidator cv = ComputerValidator.getComputerValidator();
		
		try {
			assertTrue(cv.idValidator(1L));
			assertFalse(cv.idValidator(8000L));
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDateValidator() {
		ComputerValidator cv = ComputerValidator.getComputerValidator();
		
		assertFalse(cv.DateValidator(Date.valueOf("2008-01-01"), Date.valueOf("2007-01-01")));
		assertTrue(cv.DateValidator(Date.valueOf("2008-01-01"), Date.valueOf("2010-01-01")));
		assertTrue(cv.DateValidator(null, null));
		assertTrue(cv.DateValidator(null, Date.valueOf("2007-01-01")));
	}

}
