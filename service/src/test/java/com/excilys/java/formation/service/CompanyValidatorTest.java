package com.excilys.java.formation.service;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.hsqldb.persist.HsqlDatabaseProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.CompanyDAOSpring;
import com.excilys.java.formation.service.CompanyValidator;
import com.excilys.java.formation.service.ValidatorException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class CompanyValidatorTest {
	
	static Logger logger = LoggerFactory.getLogger(CompanyValidatorTest.class);
	@Autowired
	private CompanyDAOSpring companyDAO;
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
	
	@Test
	public void testIdCompanyValidator() {
		CompanyValidator companyValidator = CompanyValidator.INSTANCE;
		Company company = new Company();
		try {
			for(int i = 1; i < 6 ; i++) {
				assertTrue(companyValidator.idCompanyValidator(company ,companyDAO));
			}
		} catch (ValidatorException e) {
			logger.debug("Problem in testIdCompanyValidator", e);
		}
	}
	
}

