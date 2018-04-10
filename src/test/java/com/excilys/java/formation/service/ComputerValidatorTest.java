package com.excilys.java.formation.service;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Test;

import com.excilys.java.formation.service.ComputerValidator;
import com.excilys.java.formation.service.ValidatorException;

public class ComputerValidatorTest {

	@Test(expected = ValidatorException.class)
	public void testNameValidator() throws ValidatorException {
		ComputerValidator computerValidator = ComputerValidator.INSTANCE;
		assertTrue(computerValidator.nameValidator("ASUS"));
		assertFalse(computerValidator.nameValidator(""));
	}

	@Test()
	public void testIdValidator() {
		ComputerValidator computerValidator = ComputerValidator.INSTANCE;
		try {
			assertTrue(computerValidator.idValidator(1L));
			assertFalse(computerValidator.idValidator(8000L));
		} catch (ValidatorException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = ValidatorException.class)
	public void testDateValidator() throws ValidatorException {
		ComputerValidator computerValidator = ComputerValidator.INSTANCE;

		assertFalse(computerValidator.dateValidator(Date.valueOf("2008-01-01").toLocalDate(), Date.valueOf("2007-01-01").toLocalDate()));
		assertTrue(computerValidator.dateValidator(Date.valueOf("2008-01-01").toLocalDate(), Date.valueOf("2010-01-01").toLocalDate()));
		assertTrue(computerValidator.dateValidator(null, null));
		assertTrue(computerValidator.dateValidator(null, Date.valueOf("2007-01-01").toLocalDate()));
	}
}