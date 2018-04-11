package com.excilys.java.formation.service;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.persistence.CompanyDAO;
import com.excilys.java.formation.persistence.DAOConfigurationException;
import com.excilys.java.formation.persistence.SQLConnection;
import com.excilys.java.formation.service.CompanyValidator;
import com.excilys.java.formation.service.ValidatorException;

public class CompanyValidatorTest {
	
	static Logger logger = LoggerFactory.getLogger(CompanyValidatorTest.class);
	private CompanyDAO companyDAO;
	
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
	
	@Test
	public void testIdCompanyValidator() {
		CompanyValidator companyValidator = CompanyValidator.INSTANCE;
		try {
			for(int i = 1; i < 6 ; i++) {
				assertTrue(companyValidator.idCompanyValidator(String.valueOf(i),companyDAO));
			}
		} catch (ValidatorException e) {
			logger.debug("Problem in testIdCompanyValidator", e);
		}
	}
	
}

