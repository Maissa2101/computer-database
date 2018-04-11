package com.excilys.java.formation.service;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.persistence.ComputerDAO;
import com.excilys.java.formation.persistence.DAOConfigurationException;
import com.excilys.java.formation.persistence.SQLConnection;
import com.excilys.java.formation.service.ComputerValidator;
import com.excilys.java.formation.service.ValidatorException;

public class ComputerValidatorTest {
	
	static Logger logger = LoggerFactory.getLogger(ComputerValidatorTest.class);
	private ComputerDAO computerDAO;
	
	@BeforeClass
	public static void init() throws SQLException, IOException, ClassNotFoundException, DAOConfigurationException, SqlToolError {
		try (Connection connection = SQLConnection.getInstance().getConnection(); 
				java.sql.Statement statement = connection.createStatement();
				InputStream inputStream = SQLConnection.class.getResourceAsStream("/TEST.sql"); ) {
		           SqlFile sqlFile = new SqlFile(new InputStreamReader(inputStream), "init", System.out, "UTF-8", false,
		                   new File("."));
		           sqlFile.setConnection(connection);
		           sqlFile.execute();
		} catch (SQLException e) {
			logger.debug("problem in init", e);
		}
	}
	
	@Test(expected = ValidatorException.class)
	public void testNameValidator() throws ValidatorException {
		ComputerValidator computerValidator = ComputerValidator.INSTANCE;
		assertTrue(computerValidator.nameValidator("ASUS"));
		assertFalse(computerValidator.nameValidator(""));
	}

	@Test(expected = ValidatorException.class)
	public void testIdValidator() throws ValidatorException {
		ComputerValidator computerValidator = ComputerValidator.INSTANCE;
			assertTrue(computerValidator.idValidator(1L, computerDAO));
			assertFalse(computerValidator.idValidator(10000L, computerDAO)); 
	
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