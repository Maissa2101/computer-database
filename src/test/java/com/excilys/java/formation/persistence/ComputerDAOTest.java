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

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.ComputerDAO;

public class ComputerDAOTest {

	static Logger logger = LoggerFactory.getLogger(ComputerDAOTest.class);
	
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
	public void testGetListComputer() {
		ComputerDAO computerDAO = ComputerDAO.INSTANCE;
		try {
			List<Computer> list = computerDAO.getListComputer(9,0,"name","ASC");
			for(Computer computer : list) {
				if(computer.getId() == 3) {
					assertEquals("CM-2a", computer.getName());
					assertNull(computer.getIntroduced());
					assertNull(computer.getDiscontinued());
					assertEquals("2", computer.getManufacturer());
				}
			}
		} catch (DAOException e) {
			logger.debug("problem in testGetListComputer", e);
		}
	} 

	@Test
	public void testGetComputer() {
		ComputerDAO computerDAO = ComputerDAO.INSTANCE;
		try {
			Optional<Computer> details = computerDAO.getComputer(5L);
			assertEquals("CM-5", details.get().getName());
			assertEquals(Date.valueOf("1991-01-01").toLocalDate(), details.get().getIntroduced());
			assertNull(details.get().getDiscontinued());
			assertEquals("2", details.get().getManufacturer());
		} catch (DAOException e) {
			logger.debug("problem in testGetComputer", e);
		}

	}

	@Test
	public void testCreateComputer() {
		ComputerDAO cd = ComputerDAO.INSTANCE;	

		try {
			Long id = cd.createComputer("ASUS", Date.valueOf("2008-01-04").toLocalDate(), Date.valueOf("2018-01-01").toLocalDate(), "1");
			List<Computer> list = cd.getListComputer(9,0, "name", "ASC");
			for(Computer computer : list) {
				if(computer.getId() == id+1) {
					assertEquals("ASUS", computer.getName());
					assertEquals(Date.valueOf("2008-01-04").toLocalDate(), computer.getIntroduced());
					assertEquals(Date.valueOf("2018-01-01").toLocalDate(), computer.getDiscontinued());
					assertEquals("1", computer.getManufacturer());
				}
			}
		}	
		catch (DAOException e) {
			logger.debug("problem in testCreateComputer", e);
		}

	} 

	@Test
	public void testUpdateComputer() {
		ComputerDAO cd = ComputerDAO.INSTANCE;

		try {
			cd.updateComputer(3L, "HP", Date.valueOf("2008-01-04").toLocalDate(), null, null);
			Optional<Computer> c = cd.getComputer(3L);
			assertEquals("HP", c.get().getName());	
			assertEquals(Date.valueOf("2008-01-04").toLocalDate(), c.get().getIntroduced());	
			assertEquals(null, c.get().getDiscontinued());
			assertEquals(null, c.get().getManufacturer());
		} catch (DAOException e) {
			logger.debug("problem in testUpdateComputer", e);
		}
	}

	@Test
	public void testDeleteComputer() {
		ComputerDAO cd = ComputerDAO.INSTANCE;
		try {
			cd.deleteComputer(3L);
			List<Computer> list = cd.getListComputer(9,0, "name", "ASC");
			for(Computer computer : list) {
				if(computer.getId() == 3L) {
					fail("Computer still exists");				
				}
			}
		} catch (DAOException e) {
			logger.debug("problem in testDeleteComputer", e);
		}
	} 

}