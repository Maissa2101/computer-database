package com.excilys.java.formation.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.hsqldb.cmdline.SqlToolError;
import org.junit.AfterClass;
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
			assertEquals("MacBook Pro", details.get().getName());
			assertEquals(Date.valueOf("2006-01-10").toLocalDate(), details.get().getIntroduced());
			assertNull(details.get().getDiscontinued());
			assertEquals("1", details.get().getManufacturer());
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