package com.excilys.java.formation.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import org.hsqldb.cmdline.SqlToolError;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.mapper.CompanyMapperTest;
import com.excilys.java.formation.persistence.DAOConfigurationException;
import com.excilys.java.formation.persistence.SQLConnection;
import com.excilys.java.formation.service.ComputerValidator;
import com.excilys.java.formation.service.ValidatorException;

public class ComputerValidatorTest {
	
	static Logger logger = LoggerFactory.getLogger(ComputerValidatorTest.class);
	
	@BeforeClass
	public static void init() throws SQLException, IOException, ClassNotFoundException, DAOConfigurationException, SqlToolError {
		try (Connection connection = SQLConnection.getInstance().getConnection(); 
				java.sql.Statement statement = connection.createStatement();) {
			statement.execute("CREATE TABLE company (id BIGINT NOT NULL identity, name VARCHAR(255),"
					+ "constraint pk_company PRIMARY KEY (id));");

			statement.execute("CREATE TABLE computer (id BIGINT NOT NULL identity, name VARCHAR(255), "
					+ "introduced DATE NULL, discontinued DATE NULL, "
					+ "company_id BIGINT default NULL,"
					+ "constraint pk_computer PRIMARY KEY (id));");
			connection.commit();
			statement.executeUpdate("INSERT INTO company (name) VALUES ('Apple Inc.');");
			statement.executeUpdate("INSERT INTO company (name) VALUES ('Thinking Machines');");
			statement.executeUpdate("INSERT INTO company (name) VALUES ('RCA');");
			statement.executeUpdate("INSERT INTO company (name) VALUES ('Netronics');");
			statement.executeUpdate("INSERT INTO company (name) VALUES ('Tandy Corporation');");
			statement.executeUpdate("INSERT INTO company (name) VALUES ('Commodore International');");

			statement.executeUpdate("INSERT INTO computer (name,introduced,discontinued,company_id) VALUES ('MacBook Pro 15.4 inch',null,null,1);");
			statement.executeUpdate("INSERT INTO computer (name,introduced,discontinued,company_id) VALUES ('CM-2a',null,null,2);");
			statement.executeUpdate("INSERT INTO computer (name,introduced,discontinued,company_id) VALUES ('CM-200',null,null,2);");
			statement.executeUpdate("INSERT INTO computer (name,introduced,discontinued,company_id) VALUES ('CM-5e',null,null,2);");
			statement.executeUpdate("INSERT INTO computer (name,introduced,discontinued,company_id) VALUES ('CM-5','1991-01-01',null,2);");
			statement.executeUpdate("INSERT INTO computer (name,introduced,discontinued,company_id) VALUES ('MacBook Pro','2006-01-10',null,1);");
			statement.executeUpdate("INSERT INTO computer (name,introduced,discontinued,company_id) VALUES ('Apple IIe',null,null,null);");
			statement.executeUpdate("INSERT INTO computer (name,introduced,discontinued,company_id) VALUES ('Apple IIGS',null,null,null);");
			statement.executeUpdate("INSERT INTO computer (name,introduced,discontinued,company_id) VALUES ('Apple IIc Plus',null,null,null);");
			
			connection.commit();
		}
	}

	@AfterClass
	public static void destroy() throws SQLException, ClassNotFoundException, IOException, DAOConfigurationException {
		SQLConnection.getInstance();
		try (Connection connection = SQLConnection.getConnection(); java.sql.Statement statement = connection.createStatement();) {
			statement.executeUpdate("DROP TABLE company");
			statement.executeUpdate("DROP TABLE computer");
			connection.commit();
		} 
	}
	
	@Test(expected = ValidatorException.class)
	public void testNameValidator() throws ValidatorException {
		ComputerValidator computerValidator = ComputerValidator.INSTANCE;
		assertTrue(computerValidator.nameValidator("ASUS"));
		assertFalse(computerValidator.nameValidator(""));
	}

	@Test(expected = ValidatorException.class)
	public void testIdValidator() {
		ComputerValidator computerValidator = ComputerValidator.INSTANCE;
		try {
			assertTrue(computerValidator.idValidator(1L));
			assertFalse(computerValidator.idValidator(10000L)); 
		} catch (ValidatorException e) {
			logger.debug("problem in testIdValidator");
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