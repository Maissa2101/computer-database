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
	public void testIdValidator() {
		ComputerValidator cv = ComputerValidator.INSTANCE;
		try {
			assertTrue(cv.idValidator(1L));
			assertFalse(cv.idValidator(8000L));
		} catch (ValidatorException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = ValidatorException.class)
	public void testDateValidator() throws ValidatorException {
		ComputerValidator cv = ComputerValidator.INSTANCE;

		assertFalse(cv.dateValidator(Date.valueOf("2008-01-01").toLocalDate(), Date.valueOf("2007-01-01").toLocalDate()));
		assertTrue(cv.dateValidator(Date.valueOf("2008-01-01").toLocalDate(), Date.valueOf("2010-01-01").toLocalDate()));
		assertTrue(cv.dateValidator(null, null));
		assertTrue(cv.dateValidator(null, Date.valueOf("2007-01-01").toLocalDate()));
	}
}