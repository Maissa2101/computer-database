package com.excilys.java.formation.persistence;

import static org.junit.Assert.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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

import com.excilys.java.formation.entities.Company;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class CompanyDAOTest {
	
	@Autowired
	private CompanyDAOSpring companyDAO;
	@Autowired
	private DataSource dataSource; 
	
	@Before
	public void init() throws SQLException, IOException, SqlToolError, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName("org.hsqldb.jdbcDriver").newInstance();
		Connection connection = DataSourceUtils.getConnection(dataSource);
		InputStream inputStream = HsqlDatabaseProperties.class.getResourceAsStream("/TEST.sql");
		SqlFile sqlFile = new SqlFile(new InputStreamReader(inputStream), "init", System.out, "UTF-8", false,
				new File("."));
		sqlFile.setConnection(connection);
		sqlFile.execute();
	}
	
	@Test
	public void testGetListCompany() {
		List<Company> list = companyDAO.getListCompany(3, 1);
		for(Company company: list) {
			if(company.getId() == 1) {
				assertEquals("Thinking Machines", company.getName());
			}
		}
	}
	
	@Test
	public void testGetCompany() {
		Optional<Company> details = companyDAO.getCompany(5L);
		assertEquals("Tandy Corporation", details.get().getName());
	}
	
	@Test
	public void testDeleteCompany() {
		companyDAO.deleteCompany(2L);
		List<Company> list = companyDAO.getListCompany(9,0);
		for(Company company : list) {
			if(company.getId() == 2L) {
				fail("Company still exists");				
			}
		}
	}
	

}
