package com.excilys.java.formation.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class ComputerDAOTest {

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

	@Test
	public void testGetListComputer() {
		List<Computer> list = computerDAO.getListComputer(9,0,"introduced","ASC");
		for(Computer computer : list) {
			if(computer.getId() == 3) {
				assertEquals("CM-2a", computer.getName());
				assertNull(computer.getIntroduced());
				assertNull(computer.getDiscontinued());
				assertEquals("2", computer.getManufacturer());
			}
		}
	} 

	@Test
	public void testGetComputer() {
		Optional<Computer> computer = computerDAO.getComputer(5L);
		assertEquals("CM-5", computer.get().getName());
		assertEquals(Date.valueOf("1991-01-01").toLocalDate(), computer.get().getIntroduced());
		assertNull(computer.get().getDiscontinued());
		assertEquals("2", computer.get().getManufacturer());
	}

	@Test
	public void testCreateComputer() {	
		Company company = new Company();
		company.setId(1);
		Long id = computerDAO.createComputer("ASUS", Date.valueOf("2008-01-04").toLocalDate(), Date.valueOf("2018-01-01").toLocalDate(), company);
		List<Computer> list = computerDAO.getListComputer(20,0, "introduced", "ASC");
		for(Computer computer : list) {
			if(computer.getId() == id) {
				assertEquals("ASUS", computer.getName());
				assertEquals(Date.valueOf("2008-01-04").toLocalDate(), computer.getIntroduced());
				assertEquals(Date.valueOf("2018-01-01").toLocalDate(), computer.getDiscontinued());
				assertEquals("1", computer.getManufacturer());
			}
		}
	} 

	@Test
	public void testUpdateComputer() {
		computerDAO.updateComputer(3L, "HP", Date.valueOf("2008-01-04").toLocalDate(), null, null);
		Optional<Computer> computer = computerDAO.getComputer(3L);
		assertEquals("HP", computer.get().getName());	
		assertEquals(Date.valueOf("2008-01-04").toLocalDate(), computer.get().getIntroduced());	
		assertNull(computer.get().getDiscontinued());
		assertNull(computer.get().getManufacturer());
	}

	@Test
	public void testDeleteComputer() {
		computerDAO.deleteComputer(3L);
		List<Computer> list = computerDAO.getListComputer(9,0, "introduced", "ASC");
		for(Computer computer : list) {
			if(computer.getId() == 3L) {
				fail("Computer still exists");				
			}
		}
	} 

}