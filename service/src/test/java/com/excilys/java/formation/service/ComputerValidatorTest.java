package com.excilys.java.formation.service;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.hsqldb.persist.HsqlDatabaseProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.java.formation.persistence.ComputerDAOSpring;
import com.excilys.java.formation.service.ComputerValidator;
import com.excilys.java.formation.service.ValidatorException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class ComputerValidatorTest {

	@Autowired
	private ComputerDAOSpring computerDAO;
	@Autowired
	private DataSource dataSource; 
	
	@Before
	public void init() throws SQLException, IOException, ClassNotFoundException, SqlToolError, InstantiationException, IllegalAccessException {
		Class.forName("org.hsqldb.jdbcDriver").newInstance();
		Connection connection = DataSourceUtils.getConnection(dataSource);
		InputStream inputStream = HsqlDatabaseProperties.class.getResourceAsStream("/TEST.sql");
		SqlFile sqlFile = new SqlFile(new InputStreamReader(inputStream), "init", System.out, "UTF-8", false,
				new File("."));
		sqlFile.setConnection(connection);
		sqlFile.execute();
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